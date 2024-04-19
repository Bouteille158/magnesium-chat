# magnesium-chat

Simple application de chat avec une BDD PostgreSQL, une API Java Spring Boot et un front React Typescript.

## Installation

L'application est séparée en deux parties : le back-end et le front-end.

### Prérequis

- [Node.js](https://nodejs.org/en/)
- [Pnpm](https://pnpm.io/)
- [Java 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)

### Installation des dépendances

#### Front-end

```bash
git clone
cd magnesium-chat/aluminium-frontend-app
pnpm install
```

#### Back-end

```bash
git clone
cd magnesium-chat/sodium-api
./mvnw clean install
```

### Configuration

#### Back-end

Créez un fichier `application.properties` dans le dossier `sodium-api/src/main/resources` en suivant le modèle `application.properties.exemple`.

```properties
# URL of the React frontend application
aluminium.app.url=http://localhost:5173

# VAPID keys for web push notifications
vapid.public.key=BL
vapid.private.key=00

# H2 database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Enable H2 console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Logging configuration
logging.level.org.springframework.security=DEBUG

# RSA key pair for JWT
rsa.private.key=rpk
rsa.public.key=skoq
```

#### Front-end

Créez un fichier `.env` dans le dossier `aluminium-frontend-app` en suivant le modèle `.env.exemple`.

```properties
VITE_SODIUM_API_URL=http://localhost:8080
```

### Lancement de l'application en mode développement

#### Front-end

```bash
cd magnesium-chat/aluminium-frontend-app
pnpm run dev
```

#### Back-end

```bash
cd magnesium-chat/sodium-api
./mvnw spring-boot:run
```

### Création de l'image Docker

#### Back-end

```bash
cd magnesium-chat/sodium-api
docker build -t magnesium-chat/sodium-api .
```
