# Arquitectura Microservicios

```mermaid
flowchart TD

Client[Cliente / Postman / Frontend]

Gateway[API Gateway (Spring Cloud Gateway)]

BooksMS[Books Microservice]
AuthMS[Auth Microservice]

BooksController[Books Controller]
BooksService[Books Service]
BooksRepo[Books Repository]

AuthController[Auth Controller]
AuthService[Auth Service]

DB[(Database)]

Client --> Gateway

Gateway --> BooksMS
Gateway --> AuthMS

BooksMS --> BooksController
BooksController --> BooksService
BooksService --> BooksRepo
BooksRepo --> DB

AuthMS --> AuthController
AuthController --> AuthService
AuthService --> DB
```