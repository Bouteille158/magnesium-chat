pipeline {
    agent any

    stages {
        stage('Build Java API') {
            steps {
                dir('sodium-api') {
                    configFileProvider([configFile(fileId: '1742fa25-2248-460a-8b34-37871645f7c2', targetLocation: 'src/main/resources/application.properties')]) {
                        sh './mvnw clean install'
                    }
                }
            }
        }
        stage('Test Java API') {
            steps {
                dir('sodium-api') {
                    configFileProvider([configFile(fileId: '1742fa25-2248-460a-8b34-37871645f7c2', targetLocation: 'src/main/resources/application.properties')]) {
                        sh './mvnw test'
                    }
                }
            }
        }
        stage('Build React App') {
            steps {
                dir('aluminium-frontend-app') {
                    configFileProvider([configFile(fileId: 'b4252485-75dc-4b63-b8ff-1875dc58f67e', targetLocation: '.env')]) {
                        sh 'npm install -g pnpm'
                        sh 'pnpm install'
                        sh 'pnpm run build'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                dir('sodium-api') {
                    echo 'Need to deploy Java API'
                }
                dir('aluminium-frontend-app') {
                    sshagent (credentials: ['a2f348fc-7284-439e-8dbb-f791c41ea0a3']) {
                        sh "scp -r dist ${USERNAME}@${env.SERVER_ADDRESS}:${env.SERVER_PATH}"
                    }
                }
            }
        }
    }
}