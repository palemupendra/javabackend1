pipeline {
    agent any

    environment {
        IMAGE_NAME = 'cart-api'
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
                //sh "${DOCKER_CMD} build -t ${IMAGE_NAME} ."
                //sh "HOME=/tmp docker-compose up --build"
                //sh"docker-compose -f ./docker-compose.yml up --build"
                sh"docker-compose up -d --build"
                sh "docker images"
                //sh "docker run --network host -d ${IMAGE_NAME}"
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "${DOCKER_CMD} run -d -p 8089:8089 java-pipeline_app"
                //sh "${DOCKER_CMD} run -d -p 5432:5432 postgres"
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