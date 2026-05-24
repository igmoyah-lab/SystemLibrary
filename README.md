# Arquitectura Microservicios

```mermaid
flowchart TD
    Usuario --> API_Gateway
    API_Gateway --> Books_Service
    API_Gateway --> Auth_Service
    Books_Service --> Database
    Auth_Service --> Database
```