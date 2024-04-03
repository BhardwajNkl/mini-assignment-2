pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "mvn_wsl_from_windows"
    }

    stages {        
        stage('Build') {
            steps {
                echo "building"
                sh "mvn clean package"
            }
            
            // post {
            //     // If Maven was able to run the tests, even if some of the test
            //     // failed, record the test results and archive the jar file.
            //     success {
            //         // junit '**/target/surefire-reports/TEST-*.xml'
            //         // archiveArtifacts 'target/*.jar'
            //     }
            // }
        }
        
        stage('Unit tests'){
            steps{
                echo "Running unit tests"
                sh "mvn test"
            }
        }

        stage('Create Docker image'){
          steps{
            sh "sudo docker build -t mini_2_app_deploy ."
          }
        }

        stage('Deploy'){
          steps{
            // Push Docker image to Docker registry. For now, skipping.
            // sh 'docker push myregistry/myapp:latest'

            //pull and run. for now, just run the locally created image. NOTE: mysql container should be running.
            sh "sudo docker run -d --name mini_2_app_deploy --link mysql:mysql -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/mini2_2 -p 4000:4000 mini_2_app_deploy"
            echo "deployed successfully"
          }
        }
      
    }
}
