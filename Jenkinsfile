
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
''') {
  node(POD_LABEL) {
    stage('Build a Maven project') {
      git 'https://github.com/fcpgris/git_user_spy.git'
      container('maven') {
          sh 'mvn -U -B -Dsettings.security=settings-security.xml -s settings.xml clean deploy'
      }
    }
    
    stage('Build Docker image') {
      container('docker') {
          //sh 'docker build -t git_user_spy .'
          docker.withRegistry('http://nexus3.ericzhang-devops.com:8485/repository/docker-release/', 'nexus3_deploy_user') {

            def customImage = docker.build("git_user_spy:${env.BUILD_ID}")

            /* Push the container to the custom Registry */
            customImage.push()
          }
      }
    }
  }
}