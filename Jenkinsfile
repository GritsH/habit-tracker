pipeline {
    agent any

    tools {
            maven 'Maven-3'
        }

    environment {
        IMAGE_NAME = "habit-tracker-app:latest" //${BUILD_NUMBER}
        TEST_NAMESPACE = "habit-tracker-test"
        DEV_NAMESPACE = "habit-tracker-dev"
        TEST_PORT = "8090"
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {

        stage('Start Minikube') {
            steps {
                bat '''
                minikube status || minikube start
                '''
            }
        }

        stage('Enable Ingress') {
            steps {
                bat '''
                minikube addons enable ingress
                '''
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }


        stage('Configure Docker To Use Minikube') {
            steps {
                bat '''
                for /f "tokens=*" %%i in ('minikube docker-env --shell cmd') do %%i
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                bat '''
                docker build -t %IMAGE_NAME% .
                '''
            }
        }

        stage('Deploy Test Namespace') {
            steps {
                bat '''
                kubectl delete namespace %TEST_NAMESPACE% --ignore-not-found=true
                kubectl apply -f k8s/test/namespace.yaml
                kubectl apply -f k8s/test/secrets.yaml
                kubectl apply -f k8s/test/mysql-deployment.yaml
                kubectl apply -f k8s/test/redis-deployment.yaml
                kubectl apply -f k8s/test/app-deployment.yaml
                kubectl apply -f k8s/test/ingress.yaml
                '''
            }
        }

        stage('Wait For Test Pods') {
            steps {
                bat '''
                kubectl wait --for=condition=ready pod --all -n %TEST_NAMESPACE% --timeout=180s
                '''
            }
        }

        stage('Wait For Ingress') {
            steps {
                bat 'ping 127.0.0.1 -n 10 > nul'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn clean verify -DskipITs'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Run API Tests') {
            steps {
                bat '''
                mvn -pl habit-tracker-api-tests test -Dbase.url=http://grits.test.habittracker.com
                '''
            }
            post {
                always {
                    junit 'habit-tracker-api-tests/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Cleanup Test Namespace') {
            steps {
                bat '''
                kubectl delete namespace %TEST_NAMESPACE%
                '''
            }
        }

        stage('Deploy Dev Environment') {
            when {
                branch 'main'
            }
            steps {
                bat '''
                kubectl apply -f k8s/namespace.yaml
                kubectl apply -f k8s/secrets.yaml
                kubectl apply -f k8s/mysql-pvc.yaml
                kubectl apply -f k8s/mysql-deployment.yaml
                kubectl apply -f k8s/redis-deployment.yaml
                kubectl apply -f k8s/app-deployment.yaml
                kubectl apply -f k8s/ingress.yaml
                '''
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."
        }
        failure {
            echo "Build failed."
        }
        success {
            echo "Build succeeded."
        }
    }
}