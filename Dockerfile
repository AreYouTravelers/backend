# Github Actions에서 만들어진 jar 파일을 실행하는 도커 이미지 생성

# 베이스 이미지 생성
FROM openjdk:17-jdk-alpine

# tzdata 패키지 설치 및 시간대 설정
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone

# 작업 디렉토리 설정
WORKDIR /app

# Github Actions에서 생성된 JAR 파일 복사
COPY travelers-0.0.1-SNAPSHOT.jar app.jar

# 도커 컨테이너 실행
ENTRYPOINT [ "java", "-jar", "app.jar" ]
