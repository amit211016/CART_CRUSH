@echo off
echo Starting Discovery Server...
start "Discovery Server" cmd /c ".\mvnw.cmd spring-boot:run -pl discovery-server"

echo Starting API Gateway...
start "API Gateway" cmd /c ".\mvnw.cmd spring-boot:run -pl api-gateway"

echo Starting User Service...
start "User Service" cmd /c ".\mvnw.cmd spring-boot:run -pl user-service"

echo Starting Catalog Service...
start "Catalog Service" cmd /c ".\mvnw.cmd spring-boot:run -pl catalog-service"

echo Starting Webapp...
start "Webapp" cmd /c ".\mvnw.cmd spring-boot:run -pl webapp"

echo All Core Services are booting up! Check the newly opened terminal windows.

    