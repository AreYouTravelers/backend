#!/bin/bash

# 로그 디렉토리 및 백업 디렉토리 생성
mkdir -p /home/ubuntu/deploy/log/backup

PROJECT_ROOT="/home/ubuntu/deploy"
APP_LOG="$PROJECT_ROOT/log/application.log"
ERROR_LOG="$PROJECT_ROOT/log/error.log"
DEPLOY_LOG="$PROJECT_ROOT/log/deploy.log"
BACKUP_LOG_DIR="$PROJECT_ROOT/log/backup"

# 기존 로그 파일 백업
if [ -f "$DEPLOY_LOG" ]; then
  mv "$DEPLOY_LOG" "$BACKUP_LOG_DIR/deploy.log.$(date +%Y%m%d_%H%M%S)"
fi

# 7일 이상된 백업 로그 파일 삭제
find "$BACKUP_LOG_DIR" -name "deploy.log.*" -type f -mtime +7 -exec rm -f {} \;

# 현재 실행중인 컨테이너 중지/삭제
{
  echo "========================================"
  echo "[$(date)] 현재 실행중인 Docker 컨테이너 확인"

  CURRENT_CONTAINERS=$(sudo docker ps --format "{{.ID}} {{.Names}}")

  if [ -z "$CURRENT_CONTAINERS" ]; then
    echo "[$(date)] 현재 실행중인 Docker 컨테이너가 없으므로 종료하지 않습니다."
  else
    echo "$CURRENT_CONTAINERS" | while read -r PID NAME; do
      echo "[$(date)] docker stop $NAME $PID"
      sudo docker stop "$PID"
      echo "[$(date)] docker rm $NAME $PID"
      sudo docker rm "$PID"
    done
    sleep 5
    echo "[$(date)] 모든 Docker 컨테이너가 중지 및 제거되었습니다."
  fi

  echo "========================================"
} >> "$DEPLOY_LOG"

# 새로운 컨테이너 시작 경로 설정
cd /home/ubuntu/deploy
sudo docker-compose -f docker-compose.yml up -d

# 새로운 컨테이너 시작
{
  echo "[$(date)] 새로운 Docker 컨테이너가 시작되었습니다."
  NEW_CONTAINERS=$(sudo docker ps --format "{{.ID}} {{.Names}}")
  echo "$NEW_CONTAINERS" |
  while
    read -r PID NAME;
  do
    echo "[$(date)] docker run $NAME $PID"
  done
  echo "========================================"
} >> "$DEPLOY_LOG"
