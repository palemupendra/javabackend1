pipeline {
    agent any

    environment {
        IMAGE_NAME = 'customer-api'
        //DOCKER_REGISTRY = 'your-docker-repo-name' // replace with your Docker Hub or ECR repo
    }

    stages {
        stage('Checkout') {
            steps {
                /* groovylint-disable-next-line LineLength */
                git url: 'https://github.com/palemupendra/javabackend1.git', branch: 'main'  
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:latest")
                }
            }
        }

        // stage('Push Docker Image') {
        //     when {
        //         expression { return env.DOCKER_REGISTRY != '' }
        //     }
        //     steps {
        //         withDockerRegistry([credentialsId: 'docker-credentials-id', url: '']) {
        //             sh "docker tag ${IMAGE_NAME}:latest ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest"
        //             sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest"
        //         }
        //     }
        // }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                // Example for Docker run locally
                sh "docker run -d -p 8089:8089 ${IMAGE_NAME}:latest"
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }

}