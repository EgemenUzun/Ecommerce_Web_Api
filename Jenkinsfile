pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'java -version'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn -version'
            }
        }
    }
}