pipeline {
    agent any

    tools {
        maven 'maven'  // This matches the name from Jenkins' Maven config
    }

    environment {
        IMAGE_NAME = 'customer-api'
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