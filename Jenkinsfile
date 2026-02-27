// pipeline {
//     agent any

//     environment {
//         IMAGE_NAME = 'cart-api'
//         MAVEN_CMD = '/usr/bin/mvn'
//         DOCKER_CMD = '/usr/bin/docker'
//     }

//     stages {
//         stage('Build with Maven') {
//             steps {
//                 sh "${MAVEN_CMD} clean install -DskipTests"
//             }
//         }

//         stage('Test') {
//             steps {
//                 sh "${MAVEN_CMD} test"
//             }
//         }

//         stage('Build Docker Image') {
//             steps {
//                 //sh "${DOCKER_CMD} build -t ${IMAGE_NAME} ."
//                 //sh "HOME=/tmp docker-compose up --build"
//                 //sh"docker-compose -f ./docker-compose.yml up --build"
//                 sh"docker-compose up -d --build"
//                 sh "docker images"
//                 sh "docker rmi images"
//                 //sh "docker run --network host -d ${IMAGE_NAME}"
//             }
//         }

//         stage('Run Docker Container') {
//             steps {
//                 sh "${DOCKER_CMD} run -d -p 8089:8089 java-pipeline_app"
//                 //sh "${DOCKER_CMD} run -d -p 5432:5432 postgres"
//             }
//         }
//     }

//     post {
//         success {
//             echo '✅ Pipeline completed successfully.'
//         }
//         failure {
//             echo '❌ Pipeline failed.'
//         }
//     }
// }
pipeline {
    agent any

    environment {
        MAVEN_CMD   = "/usr/bin/mvn"
        DOCKER_CMD  = "/usr/bin/docker"
        COMPOSE_CMD = "docker-compose"

        // must match docker-compose "image:" (or what you want to remove)
        APP_IMAGE   = "cart-api:latest"

        // container names from your compose file
        APP_CONTAINER = "cart-api"
        DB_CONTAINER  = "postgres"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build (Maven)') {
            steps {
                sh "${MAVEN_CMD} clean install -DskipTests"
            }
        }

        stage('Test (Maven)') {
            steps {
                sh "${MAVEN_CMD} test"
            }
        }

        stage('Cleanup old containers/images') {
            steps {
                sh """
                    set +e

                    echo "==> Bringing down existing compose stack (safe rerun)..."
                    ${COMPOSE_CMD} down --remove-orphans

                    echo "==> Extra safety: remove containers if still exist..."
                    ${DOCKER_CMD} rm -f ${APP_CONTAINER} 2>/dev/null || true
                    ${DOCKER_CMD} rm -f ${DB_CONTAINER} 2>/dev/null || true

                    echo "==> Remove old app image so build is always fresh..."
                    ${DOCKER_CMD} rmi -f ${APP_IMAGE} 2>/dev/null || true

                    set -e
                """
            }
        }

        stage('Build & Run (docker-compose)') {
            steps {
                sh """
                    echo "==> Building and starting containers..."
                    ${COMPOSE_CMD} up -d --build

                    echo "==> Running containers:"
                    ${DOCKER_CMD} ps

                    echo "==> Images:"
                    ${DOCKER_CMD} images | head -n 30
                """
            }
        }
    }

    post {
        always {
            sh """
                echo "==> Final docker status:"
                ${DOCKER_CMD} ps -a
            """
        }

        failure {
            sh """
                echo "==> Compose logs (for debugging):"
                ${COMPOSE_CMD} logs --no-color --tail=200 || true
            """
        }
    }
}
