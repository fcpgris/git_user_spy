
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
    - name: dockersock
      mountPath: /var/run/docker.sock
  - name: docker
    image: docker:latest
    command: ['cat']
    tty: true
    volumeMounts:
    - name: maven-settings
      mountPath: /root/.m2/
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
  - name: maven-settings
    configMap:
      name: maven-settings

''') {
  node(POD_LABEL) {
    stage('Build a Maven project') {
      git 'https://github.com/fcpgris/git_user_spy.git'
      container('maven') {
          sh 'whoami'
          sh 'cd && pwd'
          sh 'ls -l ~/.m2/'
          sh 'mvn -X -U -B clean deploy'
      }
    }
    
    stage('Build Docker image') {
      container('docker') {
          //sh 'docker build -t git_user_spy .'
          docker.withRegistry('https://nexus3.ericzhang-devops.com:8485/repository/docker-release/', 'nexus3_deploy_user') {

            def customImage = docker.build("git_user_spy:${env.BUILD_ID}")

            /* Push the container to the custom Registry */
            customImage.push()
          }
      }
    }
  }
}