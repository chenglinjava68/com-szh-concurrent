#!/bin/bash

export JAVA_HOME=/ROOT/server/jdk/
export PATH=$PATH:$JAVA_HOME/bin
export LANG=en_US.UTF-8
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
mypath=$DEPLOY_DIR
files=`ls $mypath/lib/*.jar`
myclasspath=""
#添加目录参数，部署时会有jenkins对变量值进行替换
LOG_DIR="/ROOT/logs/worker/dj-pt-sms-vendor-worker"

# 自动创建目录
if [ ! -d $LOG_DIR ]; then
    mkdir $LOG_DIR
fi

STDOUT_FILE=$LOG_DIR/stdout.log

for file in $files
do
  if [ -z $myclasspath ]
  then
    myclasspath=$file
  else
    myclasspath=$myclasspath:$file
  fi
done

cd $mypath

PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then

for PID in $PIDS ; do
    kill $PID > /dev/null 2>&1
done

echo "PID: $PIDS"
fi
#jps | awk -F" " '{if($2=="SmsSender")print $1}' | xargs kill
#sh sendsms.sh 20(启动进程数)
nohup   java  -Dlog.dir=$LOG_DIR -cp $mypath/lib:$myclasspath:. com.dajie.pt.sms.SmsSender > $STDOUT_FILE 2>&1 &

