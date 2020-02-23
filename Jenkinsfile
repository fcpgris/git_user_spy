pipeline {
    agent any

    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Deploy if build passed')
        string(name: 'DEPLOY_TARGET_HOST', defaultValue: 'xx.xx.xx.xx', description: 'Prod env IP')
    }

    stages {
        stage('Build') { 
            steps {
                git 'https://github.com/fcpgris/git_user_spy.git'
                sh 'rm -f mvn.log *.jar'
                sh 'set -o pipefail && mvn -B clean package 2>&1 | tee mvn.log'
            }
        }
        
    } // end of stages
    
}