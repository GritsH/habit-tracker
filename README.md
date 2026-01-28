# Habit Tracker

## Prerequisites
* Docker
* Docker Compose
* Minikube
* Ingress Controller

## Docker Setup
### 1. Create Environment File
Create a `.env.[name]` file (replace `[name]` with your preferred environment name, e.g., `dev`, `local`, or `test`) in the project root directory with the following contents:

```
JWT_SECRET=[your_secret]
DB_USER=[user]
DB_PASSWORD=[your_password]
DB_ROOT_PASSWORD=[your_root_password]
REDIS_HOST=[host]
REDIS_USER=[user]
REDIS_PASSWORD=[your_redis_password]
````
**Note:** Replace all data in `[]` with your actual credentials.
Replace `[your_secret]` with your suitable JWT secret.

Example `.env.local`
```
JWT_SECRET=jwtSecretKey
DB_USER=local_user
DB_PASSWORD=password123
DB_ROOT_PASSWORD=rootPassword123
REDIS_HOST=redis
REDIS_USER=default
REDIS_PASSWORD=Password123
````
### 2. Build and Run With Docker Compose
Run the following command from the project root directory:
```
docker-compose --env-file .env.[name] up --build
````

## Local Setup
### 1. In IntelliJ
Go to `Run -> Edit Configurations`, select your application and in `Environment variables` add: 
```
JWT_SECRET=[your_secret];
SPRING_DATA_REDIS_HOST=[host];
SPRING_DATA_REDIS_PASSWORD=[redis_password];
SPRING_DATA_REDIS_USERNAME=[user];
SPRING_DATASOURCE_PASSWORD=[database_password];
SPRING_DATASOURCE_URL=[database_url];
SPRING_DATASOURCE_USERNAME=[database_user];
```
**Note:** Replace all data in `[]` with your actual credentials.
Replace `[your_secret]` with your suitable JWT secret.

## Local Deployment
### 1. Local `secrets.yaml` file
Create a `secrets.yaml` file in the `k8s` directory with the following contents:
```
apiVersion: v1
kind: Secret
metadata:
  name: habit-tracker-secrets
  namespace: habit-tracker-dev
type: Opaque
stringData:
  jwt-secret: "your_secret"
  db-user: "your_user"
  db-password: "your_password"
  db-root-password: "your_root_password"
  redis-password: "your_redis_password"
  redis-user: "your_user"
```
**Note:** Replace all data in `""` with your actual credentials (must be the same as in the `.env.[name]` file).  
Example:
```
apiVersion: v1
kind: Secret
metadata:
  name: habit-tracker-secrets
  namespace: habit-tracker-dev
type: Opaque
stringData:
  jwt-secret: "jwtSecretKey"
  db-user: "local_user"
  db-password: "password123"
  db-root-password: "rootPassword123"
  redis-password: "Password123"
  redis-user: "default"
```

### 2. Point Docker CLI to Minikube's Docker Daemon
In PowerShell running as Administrator, run the following command to start minikube:
```
minikube start
```

Then run the following command to point Docker CLI to Minikube's Docker Daemon:
```
minikube docker-env | Invoke-Expression
```
### 3. Build docker image
Change directory to the root of the project and build the application image inside minikube.  
For this, run the following command:
```
docker build -t habit-tracker-app:latest .
```
When the building is complete, apply `.yaml` files in the following order:

```
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/mysql-pvc.yaml
kubectl apply -f k8s/mysql-deployment.yaml
kubectl apply -f k8s/redis-deployment.yaml
kubectl apply -f k8s/app-deployment.yaml
kubectl apply -f k8s/ingress.yaml
```
### 4. Set namespace as default
Run the following command to set the `habit-tracker-dev` namespace as the default namespace:
```
kubectl config set-context --current --namespace=habit-tracker-dev
``` 
### 5. Access API
Run the following command to start a minikube tunnel:
```
minikube tunnel
```
Then, in a separate PowerShell window (run as Administrator), run the following command to get the ingress address:  
**Note:** The namespace must be set to `habit-tracker-dev`.
```
kubectl get ingress
```
The result must show the table with ingress information. Copy the address of the `grits.habittracker.com` column and run the following command:
```
notepad C:\Windows\System32\drivers\etc\hosts
```
This will open a notepad app with the `hosts` file. Add the following line to the end of the file:
```
<your_ingress_address> grits.habittracker.com
```
**Note:** Replace `<your_ingress_address>` with the address from the table (e.g., `127.0.0.1`).


