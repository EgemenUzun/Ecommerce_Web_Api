pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Run App') {
             steps {
                bat """set JENKINS_NODE_COOKIE=dontKillMe && start /min mvn spring-boot:run """
             }
        }
        stage('Trigger Authentication Api Job') {
            steps {
                build job:'Authentication Api',wait: true
            }
        }
        stage('Trigger Angular Job') {
            steps {
                build job:'Pipeline' , wait:true
            }
        }
    }
}