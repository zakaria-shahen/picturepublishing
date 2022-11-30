
## restful API


### image

- GET `image\`

```json
[
  {
    "id": 3,
    "category": "MACHINE",
    "status": "ACCEPT",
    "description": "any",
    "path": "http://localhost:8080/image/3"
  }
]
```

- GET `image\3`

```json
  {
    "id": 3,
    "category": "MACHINE",
    "status": "ACCEPT",
    "description": "any",
    "path": "http://localhost:8080/image/3"
  }
```


- GET `login`
```json
{
  "authorities": [
    {
      "authority": "ROLE_USER"
    }
  ],
  "details": {
    "remoteAddress": "127.0.0.1",
    "sessionId": "5FE3C7D47A54C9EDF3F05B5202C4E43F"
  },
  "authenticated": true,
  "principal": {
    "password": null,
    "username": "admin",
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ],
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true
  },
  "credentials": null,
  "name": "admin"
}
```


- POST `registration`
  - response
  ```json
    {
    "Message": "created"
    }
  ```
  - request
    ```json
    {
      "email": "user",
      "password": "user"
    }
    ```


- image/1/admin/{stauts}
