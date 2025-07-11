pipeline {
    agent any

    environment {
        IMAGE_NAME = 'customer-api'
        //DOCKER_REGISTRY = '' // optional: set this if pushing to DockerHub/ECR
    }

    stages {
        stage('Build with Maven') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:latest")
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                bat "docker run -d -p 8089:8089 ${IMAGE_NAME}:latest"
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully.'
        }
        failure {
            echo '❌ Pipeline failed.'
        }
    }
}