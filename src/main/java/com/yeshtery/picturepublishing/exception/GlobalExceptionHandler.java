package com.yeshtery.picturepublishing.exception;

import com.yeshtery.picturepublishing.exception.files.*;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return badRequest(request, "Missing Path Variable");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return badRequest(request, "Argument Not Valid");
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return badRequest(request, "JSON parse error");
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return badRequest(request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return badRequest(request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return badRequest(request);
    }


    @ExceptionHandler({
            NotFoundImage.class,
            NotFoundFile.class
    })
    public ResponseEntity<Object> notFoundHandler(WebRequest request, Exception e) {
        return errorMessageBuilder(HttpStatus.NOT_FOUND, request, e);
    }

    @ExceptionHandler(ImageProcessedException.class)
    public ResponseEntity<Object> conflictHandler(WebRequest request, Exception e) {
        return errorMessageBuilder(HttpStatus.CONFLICT, request, e);
    }

    @ExceptionHandler({
            UnableToCopyFile.class,
            UnableToCreateFolder.class,
            UnableToDeleteFile.class
    })
    public ResponseEntity<Object> internetServerHandler(WebRequest request, Exception e) {
        return errorMessageBuilder(HttpStatus.INTERNAL_SERVER_ERROR, request, e);
    }


    @ExceptionHandler({
            ImageContentTypeNotSupported.class,
            NotFoundFileStatus.class
    })
    public ResponseEntity<Object> badRequestHandler(WebRequest request, Exception e) {
        return badRequest(request, e.getMessage());
    }


    private static ResponseEntity<Object> badRequest(WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Unspecified request problem",
                getRequestUri(request)
        ).BuildResponseEntity();
    }

    private static ResponseEntity<Object> badRequest(WebRequest request, String massages) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                massages,
                getRequestUri(request)
        ).BuildResponseEntity();
    }

    private static String getRequestUri(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private static ResponseEntity<Object> errorMessageBuilder(HttpStatus status, WebRequest request, Exception e) {
        return new ErrorMessage(
                status,
                e.getMessage(),
                getRequestUri(request)
        ).BuildResponseEntity();
    }

}
