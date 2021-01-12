podTemplate(containers: [
  containerTemplate(
  name: 'maven', 
  image: 'maven:3.3.9-jdk-8-alpine', 
  ttyEnabled: true, 
  command: 'cat',
  resourceLimitCpu: '1000m',
  resourceLimitMemory: '400Mi')
  ]) {

  node(POD_LABEL) {
    stage('Build a Maven project') {
      git 'https://github.com/fcpgris/git_user_spy.git'
      container('maven') {
          sh 'mvn -U -B -Dsettings.security=settings-security.xml -s settings.xml clean deploy'
      }
    }
  }
}