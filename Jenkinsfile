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

        stage('Trigger Angular Job') {
              steps {
                 build 'Pipeline'
              }
        }
    }
}