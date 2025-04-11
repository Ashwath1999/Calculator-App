pipeline {
    agent none

    stages {
        stage('Checkout Code') {
            agent { label 'checkout' }
            steps {
                echo 'Checking out code...'
                git branch: 'main', url: 'https://github.com/Ashwath1999/Calculator-App'
            }
        }

        stage('Build') {
            agent { label 'build' }
            steps {
                echo '🔨 Building the Calculator app...'
                bat 'mvn clean package -DskipTests'
                stash includes: 'target/calculator-1.0-SNAPSHOT.jar', name: 'calculator-jar'
            }
        }

        stage('Test') {
            agent { label 'test' }
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
        }
        
        stage('Deploy to Staging') {
            agent { label 'staging' }
            steps {
                unstash 'calculator-jar'
                echo "Deploying to staging on ${env.NODE_NAME} agent"
                bat '''
                    echo Deploying to STAGING environment...
                    if not exist C:\\calculator-staging mkdir C:\\calculator-staging
                    copy target\\calculator-1.0-SNAPSHOT.jar C:\\calculator-staging
                '''
            }
        }
        
        stage('Approval for Production') {
            agent { label 'approve' }
            steps {
                script {
                    def userInput = input(
                        message: 'Do you want to proceed to production deployment?',
                        parameters: [
                            booleanParam(name: 'Proceed to Production?', defaultValue: false, description: 'Check to deploy to Production')
                        ]
                    )

                    if (!userInput) {
                        error 'User declined to deploy to production.'
                    } else {
                        echo "User Approved. Proceeding to Production..."
                    }
                }
            }
        }


        stage('Deploy to Production') {
            agent { label 'production' }
            steps {
                unstash 'calculator-jar'
                echo "Deploying to production on ${env.NODE_NAME} agent"
                bat '''
                    echo Deploying to PRODUCTION environment...
                    if not exist C:\\calculator-production mkdir C:\\calculator-production
                    copy target\\calculator-1.0-SNAPSHOT.jar C:\\calculator-production
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check console output for errors.'
        }
    }
}
