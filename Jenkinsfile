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
                bat """start /min mvn spring-boot:run """
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
        stage('Close API'){
            steps{
                bat """"Powershell -Command "& { Start-Process \"C:\\Users\\Egemen\\VSCode\\CloseAPI.bat\" -verb RunAs}"""
            }
        }
    }
}