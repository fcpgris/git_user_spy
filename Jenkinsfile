
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
  - name: awscli
    image: amazon/aws-cli
    command: ['cat']
    tty: true
  - name: kubectl
    image: bitnami/kubectl:latest
    command: ['cat']
    tty: true
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
  - name: maven-settings
    configMap:
      name: maven-settings
  serviceAccountName: jenkins

''') {
  node(POD_LABEL) {
    stage('Build Maven project') {
      checkout scm
      container('maven') {
          sh 'mvn -X -B clean deploy'
          sh "env;branch_prefix=\$(echo -n ${env.BRANCH_NAME} | sed -e 's|-.*||'); " +  'mvn -B sonar:sonar -Dsonar.login=4f607ae198fae2aa423bacd47959adeea6c36050 -Dsonar.projectKey=git_user_spy-$branch_prefix -Dsonar.projectName=git_user_spy-$branch_prefix'
      }
    }
    
    if ( env.BRANCH_NAME.contains("feature-") ) {
      echo "No need to create and upload docker image for feature branch"
      currentBuild.result = 'SUCCESS'
      return
    }
    
    def repo_url = 'nexus3.ericzhang-devops.com'
    def docker_repo = 'docker-testing'
    def docker_repo_port = '8483'
    if(env.BRANCH_NAME.equals("release")) {
      docker_repo = 'docker-staging'
      docker_repo_port = '8484'
    }
    def docker_image_version = "git_user_spy:${env.BRANCH_NAME}-${env.BUILD_ID}"
    
    stage('Build and Upload Docker image') {
      container('docker') {
          docker.withRegistry("https://${repo_url}:${docker_repo_port}/repository/${docker_repo}/", 'nexus3_deploy_user') {
            def customImage = docker.build(docker_image_version)
            customImage.push()
          }
      }
    }
    
    stage('deploy') {
      container('awscli') {
        sh 'aws --version'
        withAWS(region:'ap-east-1',credentials:'eks-deploy') {
          sh 'aws eks --region ap-east-1 update-kubeconfig --name eksworkshop-eksctl'
          sh 'curl -O https://storage.googleapis.com/kubernetes-release/release/v1.20.2/bin/linux/amd64/kubectl && chmod +x kubectl'
          sh './kubectl get pods -n testing'
          
          echo "deploy environment!"
          // generate deployment and service yaml
          def target_env = 'tesing'
          def docker_image_url = "${repo_url}:${docker_repo_port}/${docker_image_version}"
          echo "docker_image_version=${docker_image_version}"
          def deployment_yaml = readFile(file: 'deployment/deployment.yaml')
          def service_yaml = readFile(file: 'deployment/service.yaml')
          deployment_yaml = deployment_yaml.replaceAll('ENV', target_env).replaceAll('IMAGE', docker_image_url)
          service_yaml = service_yaml.replaceAll('ENV', target_env)
          echo deployment_yaml
          echo service_yaml
          writeFile file: "deployment/deployment-${target_env}.yaml", text: deployment_yaml
          writeFile file: "deployment/service-${target_env}.yaml", text: service_yaml
          sh "./kubectl apply -f deployment/deployment-${target_env}.yaml -n ${target_env}"
          sh "./kubectl apply -f deployment/service-${target_env}.yaml -n ${target_env}"
          sleep(time:20,unit:"SECONDS")
        }
      }
    }
    
    /*stage('Deploy') {
      container('kubectl') {
        withAWS(region:'ap-east-1',credentials:'rbac-user') {
          sh 'aws eks --region ap-east-1 update-kubeconfig --name eksworkshop-eksctl'
          echo "starting kubectl"
          sh 'ls -l ~/.kube || true'
          sh 'kubectl get pods -n rbac-test'
        }
        echo "deploy environment!"
        // generate deployment and service yaml
        def target_env = 'tesing'
        def docker_image_url = "${repo_url}:${docker_repo_port}/${docker_image_version}"
        echo "docker_image_version=${docker_image_version}"
        def deployment_yaml = readFile(file: 'deployment/deployment.yaml')
        def service_yaml = readFile(file: 'deployment/service.yaml')
        deployment_yaml = deployment_yaml.replaceAll('ENV', target_env).replaceAll('IMAGE', docker_image_url)
        service_yaml = service_yaml.replaceAll('ENV', target_env)
        echo deployment_yaml
        echo service_yaml
        withKubeConfig([credentialsId: 'kubernetes-config']) {
          sh 'kubectl get pods'  
        }  
      }//end of container
    }//end of stage
    */
  }
}