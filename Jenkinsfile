pipeline {
    agent any

    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: true, description: 'Deploy if build passed')
        string(name: 'DEPLOY_TARGET_HOST', defaultValue: 'gituserspy.ericzhang-devops.com', description: 'Prod env IP')
    }

    stages {
        stage('Build') { 
            steps {
                git 'https://github.com/fcpgris/git_user_spy.git'
                sh 'rm -f mvn.log *.jar'
                sh 'set -o pipefail && mvn -B -X -s settings.xml clean deploy 2>&1 | tee mvn.log'
            }
        }
        stage('Deploy') {
            environment {
                artifact_name = sh(returnStdout: true, script: 'basename target/*jar')
            }
            when { equals expected: true, actual: params.DEPLOY }
            steps {
                sh "chmod 600 deploykey.idrsa"
                sh "scp -o StrictHostKeyChecking=no -i deploykey.idrsa target/*jar ec2-user@${params.DEPLOY_TARGET_HOST}:~"
                sh "ssh -o StrictHostKeyChecking=no -i deploykey.idrsa ec2-user@${params.DEPLOY_TARGET_HOST} killall java || true"
                sh "ssh -o StrictHostKeyChecking=no -i deploykey.idrsa ec2-user@${params.DEPLOY_TARGET_HOST} \"nohup java -jar ~/${env.artifact_name} > /dev/null 2>&1 &\""
            }
        }
    } // end of stages
    
}
