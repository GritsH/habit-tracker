pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    environment {
        IMAGE_NAME = "habit-tracker-app:latest"
        TEST_NAMESPACE = "habit-tracker-test"
        BASE_URL = "http://grits.test.habittracker.com"
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

        stage('Enable Ingress Addon') {
            steps {
                bat 'minikube addons enable ingress'
            }
        }

        stage('Start Minikube Tunnel') {
            steps {
                bat '''
                echo Starting tunnel in background...
                start "" /B minikube tunnel
                ping 127.0.0.1 -n 10 > nul
                '''
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Deploy Test Environment') {
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
                kubectl rollout status deployment/mysql-test -n %TEST_NAMESPACE% --timeout=180s
                kubectl rollout status deployment/redis-test -n %TEST_NAMESPACE% --timeout=180s
                kubectl rollout status deployment/habit-tracker-test -n %TEST_NAMESPACE% --timeout=180s
                '''
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
                bat "mvn -pl habit-tracker-api-tests test -Dbase.url=%BASE_URL%"
            }
            post {
                always {
                    junit 'habit-tracker-api-tests/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Cleanup Test Namespace') {
            steps {
                bat 'kubectl delete namespace %TEST_NAMESPACE%'
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