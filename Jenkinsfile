pipeline {
    agent any

    stages {
        stage('Build Java API') {
            steps {
                dir('sodium-api') {
                    configFileProvider([configFile(fileId: '1742fa25-2248-460a-8b34-37871645f7c2', targetLocation: 'sodium-api/src/main/resources/application.properties')]) {
                        sh './mvnw clean install'
                    }
                }
            }
        }
        stage('Test Java API') {
            steps {
                dir('sodium-api') {
                    configFileProvider([configFile(fileId: '1742fa25-2248-460a-8b34-37871645f7c2', targetLocation: 'sodium-api/src/main/resources/application.properties')]) {
                        sh './mvnw test'
                    }
                }
            }
        }
        stage('Build React App') {
            steps {
                dir('aluminium-frontend-app') {
                    sh 'pnpm install'
                    sh 'pnpm run build'
                }
            }
        }
        stage('Test React App') {
            steps {
                dir('aluminium-frontend-app') {
                    sh 'pnpm test'
                }
            }
        }
        stage('Deploy') {
            steps {
                dir('sodium-api') {
                    echo 'Need to deploy Java API'
                }
                dir('aluminium-frontend-app') {
                    withCredentials([usernamePassword(credentialsId: 'be54c242-e2b7-4901-9781-1d0434d5f6f7', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh 'scp -r dist ${USERNAME}@${env.SERVER_ADDRESS}:${env.SERVER_PATH}'
                    }
                }
            }
        }
    }
}