package { 'openjdk-8-jdk':
    ensure => present,
}

exec{'get_latest_artifact_and_deploy':
  command => "get_latest_artifact_and_deploy.sh",
}

