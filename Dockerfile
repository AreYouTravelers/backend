# Docker 'Multi Stage' Build

#-------------------------------------------------------------#
# 빌드 환경 - 빌드 도구와 라이브러리가 포함된 이미지를 빌드
#-------------------------------------------------------------#

# 베이스 이미지 + 별칭
FROM openjdk:17-jdk-alpine as builder
# 작업 디렉토리 설정
WORKDIR /build

# Gradle Wrapper와 관련 파일들을 빌드 환경으로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
# 소스 코드를 빌드 환경으로 복사
COPY src src

# Gradle 캐시를 활용하기 위해 초기화 작업 실행
RUN ./gradlew --version

# grdlew 실행 권한 부여 (gradlew를 실행가능한 상태로 만듦)
RUN chmod +x ./gradlew

# 빌드 후 실행 가능한 jar파일이 만들어지는 과정
# - 아래 명령어를 실행하면 '/build/libs/에 *.jar가 생성됨
# - 최종 : '/build/libs/*.jar'
RUN ./gradlew bootJar

#-------------------------------------------------------------#
# 런타임 환경 - 빌드 결과물(jar파일)만을 포함하는 경량의 런타임 이미지를 생성
#-------------------------------------------------------------#

# 베이스 이미지 생성
FROM openjdk:17-jdk-alpine
# 작업 디렉토리 설정
WORKDIR /build

# 빌드된 파일 복사 - 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=builder /build/libs/*.jar app.jar

# 컨테이너(jar파일) 실행
ENTRYPOINT [ "java", "-jar", "app.jar" ]