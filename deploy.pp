package { 'java-1.8.0-openjdk':
  ensure => present,
}

exec { 'nohup java -jar git-user-spy.jar &':
  cwd     => '/home/ec2-user',
  path    => ['/usr/bin', '/usr/sbin',],
}
