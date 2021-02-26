import groovy.json.JsonSlurper

def rollback = env.ApllyBuild_NAME.replace("#", "")
def jsonSlurper = new JsonSlurper()
pipeline {
    agent any

    parameters {
        booleanParam(name: "ROOLBACK", defaultValue: false)
    }

    stages {



        stage("POST API") {
            steps {
               script{
                    def post = new URL("https://httpbin.org/post").openConnection();
                    def message = '{"message":"this is a message"}'
                    post.setRequestMethod("POST")
                    post.setDoOutput(true)
                    post.setRequestProperty("Content-Type", "application/json")
                    post.getOutputStream().write(message.getBytes("UTF-8"));
                    def postRC = post.getResponseCode();
                    println(postRC);
                    if(postRC.equalstage("GET API") {
                        steps {
                            script{
                                // GET
                                def get = new URL("https://hub.docker.com/v2/repositories/library/?page=1&page_size=15").openConnection() as HttpURLConnection;
                                get.setRequestProperty( 'Accept', 'application/json' )
                                def object = jsonSlurper.parseText(get.getInputStream().getText())
                                assert object instanceof Map
                                def getRC = get.getResponseCode();
                                //println(getRC.);
                                if(getRC.equals(200)) {
                                    //println(props['count'].keySet())
                                    println(object.count);
                                    // println(get.getInputStream().getText());
                                }
                            }
                        }
                    }
                        s(200)) {
                        println(post.getInputStream().getText());
                    }
               }
            }
        }

        stage("Publish") {
            when { expression { !params.ROOLBACK } }
            steps {
                gitCloneFrom('master','user_pass_gitlab','http://172.17.177.120:10080/root/ansible_file_deploy.git' )
                sh "echo Publish ROOLBACK : ${rollback} "
            }
        }
        stage ('01 Workspace') {
            when { expression { !params.ROOLBACK } }
            steps {
               workspace01()
            }
		}

		stage ('02 Workspace') {
            when { expression { !params.ROOLBACK } }
            steps {
                script {
                    if (rollback >= env.BUILD_ID) {
                        stage ('Stage 1') {
                           sh "echo Stage x : ${rollback} "
                        }
                    } else {
                        stage ('Stage 2') {
                            sh 'echo Stage 2'
                        }
                    }
                }

                 workspace02()
            }
        }

		stage ('03 Workspace') {
            when { expression { !params.ROOLBACK } }
            steps {
                workspace03(rollback)
            }
        }
		stage ('03 Workspace ROOLBACK') {
		    when { expression { params.ROOLBACK } }
            steps {
                workspace03(rollback)
            }
        }
    }
    post {
        always {
            echo 'One way or another, I have finished'
            deleteDir() /* clean up our workspace */
        }
        success {
            echo 'I succeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }


}

def gitCloneFrom(branch,credentialsId,url ){
    try{
        git branch: "${branch}", credentialsId: "${credentialsId}", url: "${url}"
    } catch(Exception e) {
        println("Exception: ${e}")
    }
}

def gitTagFrom(branch,credentialsId,url, buildNumber, branchName ){
    try{
        checkout scm: [$class: 'GitSCM', userRemoteConfigs: [[url: "${url}", credentialsId: "${credentialsId}" ]], branches: [[name: 'refs/tags/${branchName}.${buildNumber} ']]], poll: false

        //git push credentialsId: "${credentialsId}", url: "${url}
        //git branch: "${branch}", credentialsId: "${credentialsId}", url: "${url}"
        //git tag
        //sh("git tag -a ${branchName}.${buildNumber} -m 'Jenkins'")
        //sh("git push https://${env.GIT_USERNAME}:${env.GIT_PASSWORD}@<REPO> --tags")

        //sh "git tag ${branchName}.${buildNumber}"
        //sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${repository} ${your_tag}"


    } catch(Exception e) {
        println("Exception: ${e}")
    }
}

def workspace01(){
    echo " = = = = = = = = = = = = = = = = = = = = = = "
    echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
}

def workspace02(){
    echo " = = = = = = = = = = = = = = = = = = = = = = "
    sh 'printenv'
}

def workspace03(rollback){
    echo " = = = = = = = change = = = = = = = = = = = = = = = "
    // println env.ApllyBuild_NAME.replace("#", "")
    // String rollback = env.ApllyBuild_NAME.replace("#", "")
    sh "echo ansible-playbook -i hosts app_provisioning.yml --extra-vars REGISTER=${env.BUILD_ID}  nada=${rollback} -vv"
    echo " = = = = = = =  / change = = = = = = = = = = = = = = = "
}