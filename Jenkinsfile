
podTemplate(yaml: '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:3.3.9-jdk-8-alpine
    command:
    - cat
    tty: true
    resources:
      limits:
        cpu: "1"
        memory: "1024Mi"
    volumeMounts:
    - name: maven-settings
      mountPath: /root/.m2/settings.xml
      subPath: settings.xml
    - name: maven-settings
      mountPath: /root/.m2/settings-security.xml
      subPath: settings-security.xml
  - name: docker
    image: docker:latest
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
  - name: maven-settings
    configMap:
      name: maven-settings

''') {
  node(POD_LABEL) {
    stage('Build Maven project') {
      checkout scm
      //git 'https://github.com/fcpgris/git_user_spy.git'
      //checkout([$class: 'GitSCM', branches: [[name: '*/*']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/fcpgris/git_user_spy.git']]])
      container('maven') {
          sh 'mvn -B clean deploy'
          sh "env;branch_prefix=\$(echo -n ${env.BRANCH_NAME} | sed -e 's|-.*||'); " +  'mvn -B sonar:sonar -Dsonar.login=4f607ae198fae2aa423bacd47959adeea6c36050 -Dsonar.projectKey=git_user_spy-$branch_prefix -Dsonar.projectName=git_user_spy-$branch_prefix'
      }
    }
    
    stage('Build and Upload Docker image') {
      echo env.BRANCH_NAME
      if ( env.BRANCH_NAME.contains("feature-") ) {
        echo "No need to create and upload docker image for feature branch"
        currentBuild.result = 'SUCCESS'
        return
      }
      container('docker') {
          docker.withRegistry('https://nexus3.ericzhang-devops.com:8485/repository/docker-release/', 'nexus3_deploy_user') {
            def customImage = docker.build("git_user_spy:${env.BRANCH_NAME}-${env.BUILD_ID}")
            customImage.push()
          }
      }
    }
    
    stage('Deploy') {
      echo "deploy environment!"
      
    }
    
  }
}