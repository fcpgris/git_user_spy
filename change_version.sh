#!/bin/bash
set +x
echo 'Will change the version in pom.xml files...'

# get current sha1
sha1=$(git rev-parse --short HEAD)
timestamp=`date "+%s"`
build_number=$BUILD_NUMBER

# get current version of the top level pom
current_version=$(mvn -B help:evaluate -Dexpression=project.version | grep -v '\[.*' | grep -v 'Download')
echo "current pom version is $current_version"

# extract version suffix, in case the version is end with -SNAPSHOT
suffix=$(echo $current_version | cut -s -d \- -f 2)

if [ "$suffix" == "" ]; then
  # no suffix
  #pure_version=$current_version
  version=$build_number.$sha1.$timestamp
else
  # suffix exists
  #pure_version=$(echo $current_version | cut -s -d \- -f 1)
  version=$build_number.$sha1.$timestamp-$suffix
fi

# run maven versions plugin to set new version
mvn -B versions:set -DgenerateBackupPoms=false -DnewVersion=$version
echo "$version" > version.txt
echo "Changed version in pom.xml files to $version"
