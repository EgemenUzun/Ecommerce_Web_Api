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
                bat 'start mvn spring-boot:run'
             }
        }

        stage('Trigger Authentication Api Job') {
              steps {
                 build 'Authentication Api',wait: true
              }
        }
    }
}