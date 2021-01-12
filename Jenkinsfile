podTemplate(containers: [
  containerTemplate(
    name: 'maven', 
    image: 'maven:3.3.9-jdk-8-alpine', 
    ttyEnabled: true, 
    command: 'cat',
    resourceLimitCpu: '1000m',
    resourceLimitMemory: '1024Mi'),
  containerTemplate(
    name: 'docker', 
    image: 'docker:20.10.2', 
    ttyEnabled: true, 
    command: 'cat')
  ]) {
  
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
  }
}