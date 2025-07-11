pipeline {
    agent any

    environment {
        IMAGE_NAME = 'customer-api'
        MAVEN_CMD = '/usr/bin/mvn'
        DOCKER_CMD = '/usr/bin/docker'
    }

    stages {
        stage('Build with Maven') {
            steps {
                sh "${MAVEN_CMD} clean install -DskipTests"
            }
        }

        stage('Test') {
            steps {
                sh "${MAVEN_CMD} test"
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
                sh "${DOCKER_CMD} run -d -p 8089:8089 ${IMAGE_NAME}:latest"
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