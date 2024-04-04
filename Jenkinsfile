pipeline {
    agent any
    
    tools {
        maven "mvn_wsl_from_windows"
    }

    stages {        
        stage('Build') {
            steps {
                echo "building JAR"
                sh "mvn clean package"
            }
        }
        
        stage('Unit tests'){
            steps{
                echo "Running unit tests"
                sh "mvn test"
            }
        }

        stage('Create Docker image'){
          steps{
            echo "Creating Docker image"
            sh "sudo docker build -t mini_2_app_deploy ."
          }
        }

        stage('Deploy'){
          steps{
            // Push Docker image to Docker registry. For now, skipping.

            // Run the image. NOTE: mysql container should be running.
                  // before running, stop and remove the previously started container for this application.
                  sh 'sudo docker stop mini_2_app_deploy || true'
                  sh 'sudo docker rm mini_2_app_deploy || true'
            sh "sudo docker run --name mini_2_app_deploy -d --link mysql:mysql -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mini2_2 -p 4000:4000 mini_2_app_deploy"
            echo "deployed successfully"
          }
        }
      
    }
}
