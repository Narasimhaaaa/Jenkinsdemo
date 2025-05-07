@Library('my-shared-library') _
import org.demo.Util

pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "yourdockerhub/jenkins-demo:${params.APP_VERSION}"
    }

    parameters {
        string(name: 'APP_VERSION', defaultValue: '1.0.0', description: 'App version tag')
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging'], description: 'Deployment environment')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/yourusername/jenkins-cicd-demo.git', branch: 'main'
            }
        }

        stage('Install & Test') {
            steps {
                dir('app') {
                    sh 'npm install'
                    sh 'npm test'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('app') {
                    sh "docker build -t ${env.DOCKER_IMAGE} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u yourdockerhub --password-stdin"
                    sh "docker push ${env.DOCKER_IMAGE}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def util = new Util(this)
                    util.printHeader("Starting Deployment")
                    deployApp(env.DOCKER_IMAGE, params.DEPLOY_ENV)
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
