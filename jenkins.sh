#!/bin/bash
echo on
#appium --session-override -p 2473  -bp 2474

rm -rf reports/*  || true
rm -rf failedcases/* || true
rm -rf log/* || true
echo "Delete ipa and apk..."
rm -f *.ipa  || true
rm -f *.apk  || true

DEVICE=$1
TESTSUITE=$2
UDID=$3

if [ -z "$1" ] || [ -z "$2" ]|| [ -z "$3" ]; then
    echo "Please input device name , testsuite name, udid"
    exit 1
fi

echo "Device name is : "  $DEVICE
echo "Testsuite name is : " $TESTSUITE
echo "udid : " $UDID

pwd && ls -l

MVN=/Users/ios_package/Tools/apache-maven-3.5.0/bin/mvn
RESULT=reports-history/"$BUILD_NUMBER"

if [ -z "$4" ] ; then
    echo "Android Device is : "  $UDID
    #Copy apk to local
    ADB=/Users/ios_package/Library/Android/sdk/platform-tools/adb
    curl  -O http://172.25.53.59:9191/job/android-dev/lastSuccessfulBuild/artifact/build/Stock/outputs/apk/Stock-dev-release.apk --user admin:1q2w3e4r
    #$ADB -s $UDID shell ls /data/local/tmp/
    #$ADB -s $UDID shell rm /data/local/tmp/com.tigerbrokers.stock.*
    $ADB -s $UDID install -r ./Stock-dev-release.apk
else
    echo "IOS Device is : "  $UDID
    curl -O http://172.25.48.15:8080/job/Dev/lastSuccessfulBuild/artifact/build/ipa/Stock.ipa --user admin:1q2w3e4r
    pwd && ls -l
    /usr/local/bin/ios-deploy -d --bundle Stock.ipa -i $UDID
fi

pwd && ls -l

$MVN test -DsuiteFile=$TESTSUITE -DoutputDir=$DEVICE surefire-report:report


mkdir -p $RESULT
cp -r ./log $RESULT  || true
cp -r ./reports/$DEVICE $RESULT  || true
cp -r ./failedcases  $RESULT || true
mv ./reports/$DEVICE/* ./reports/ || true
echo  $RESULT




#if [ -z "$1" ] ;then
#    echo "===================Adding in automatic way"
#    git add .
#else
#    echo "===================All the things need to be added, should be done manually"
#fi

#git commit -m "update $1"
#git push origin master
