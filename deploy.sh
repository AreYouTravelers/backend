#!/bin/bash

mkdir -p /home/ubuntu/deploy/log

PROJECT_ROOT="/home/ubuntu/deploy"
APP_LOG="$PROJECT_ROOT/log/application.log"
ERROR_LOG="$PROJECT_ROOT/log/error.log"
DEPLOY_LOG="$PROJECT_ROOT/log/deploy.log"

echo "> 현재 실행 중인 Docker 컨테이너 pid 확인했습니다." >> "$DEPLOY_LOG"
CURRENT_PIDS=$(sudo docker container ls -q)

if [ -z "$CURRENT_PIDS" ]; then
  echo "> 현재 구동중인 Docker 컨테이너가 없으므로 종료하지 않습니다." >> "$DEPLOY_LOG"
else
  for PID in $CURRENT_PIDS
  do
    echo "> docker stop $PID" >> "$DEPLOY_LOG" # 현재 구동중인 Docker 컨테이너가 있다면 모두 중지
    sudo docker stop $PID
    sudo docker rm $PID
  done
  sleep 5
fi

cd /home/ubuntu/deploy
sudo docker-compose -f docker-compose.yml up
