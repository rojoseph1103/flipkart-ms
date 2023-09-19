pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools {
        maven 'Maven_3.9.4'
    }

    stages {
        stage('Code Compilation') {
            steps {
                echo 'Code Compilation is In Progress!'
                sh 'mvn clean compile'
                echo 'Code Compilation is Completed Successfully!'
            }
        }
        stage('Code QA Execution') {
            steps {
                echo 'Junit Test case check in Progress!'
                sh 'mvn clean test'
                echo 'Junit Test case check Completed!'
            }
        }
        stage('Code Package') {
            steps {
                echo 'Creating War Artifact'
                sh 'mvn clean package'
                echo 'Creating War Artifact done'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo 'Starting Building Docker Image'
                sh "docker build -t satyam88/flipkart-ms:dev-flipkart-ms-v1.${BUILD_NUMBER} ."
                sh "docker build -t flipkart-ms:dev-flipkart-ms-v1.${BUILD_NUMBER} ."
                echo 'Completed Building Docker Image'
            }
        }
        stage('Docker Image Scanning') {
            steps {
                echo 'Docker Image Scanning Started'
                sh 'java -version'
                echo 'Docker Image Scanning Started'
            }
        }
        stage('Docker push to Docker Hub') {
          steps {
            script {
                withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]) {
                sh """
                echo "\$dockerhubCred" | docker login --username romeo11111  --password-stdin docker.io
                echo "Push Docker Image to DockerHub: In Progress"
                docker push romeo11111 /flipkart-ms:dev-flipkart-ms-v1.${BUILD_NUMBER}
                echo "Push Docker Image to DockerHub: Completed"
				"""
                }
            }
          }
        }
        stage('Docker Image Push to Amazon ECR') {
            steps {
              script {
                def dockerImageTag = "dev-flipkart-ms-v1.25"
                def ecrRepositoryUrl = "559220132560.dkr.ecr.ap-south-1.amazonaws.com/flipkart-ms:$dockerImageTag"

                // Check if the Docker image exists locally
                def dockerImageExists = sh(script: "docker images -q flipkart-ms:$dockerImageTag", returnStatus: true) == 0

                if (dockerImageExists) {
                echo "Docker image 'flipkart-ms:$dockerImageTag' found locally."


                // Proceed with tagging and pushing to ECR
                sh "docker tag flipkart-ms:$dockerImageTag $ecrRepositoryUrl"
                sh "docker push $ecrRepositoryUrl"


                
                // Proceed with tagging and pushing to ECR
                sh "docker tag flipkart-ms:$dockerImageTag $ecrRepositoryUrl"
                sh "docker push $ecrRepositoryUrl"
                
>>>>>>> 6332bfc9756e44bf61bdb1f4481b85c30dd0e5d2
                echo "Pushed Docker image to ECR successfully."
            } else {
                error "Docker image 'flipkart-ms:$dockerImageTag' not found locally. Ensure it is built or pulled before pushing to ECR."
            }
                }
            }
       }
	}
}
