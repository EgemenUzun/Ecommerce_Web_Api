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
                bat '''@echo off
                setlocal

                set "PORT_TO_CLOSE=8081"

                for /f "tokens=5" %%a in (\'netstat -ano ^| findstr :%PORT_TO_CLOSE%\') do (
                    set "PID=%%a"
                )

                if not defined PID (
                    echo 8081 portunu kullanan herhangi bir uygulama bulunamadi.
                ) else (
                    taskkill /F /PID %PID%
                )

                pause'''
            }
        }
    }
}