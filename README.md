# _✈️ Are You Travelers?_
![너-T-야_-최종발표-001](https://github.com/AreYouTravelers/backend/assets/70623290/d5f6be9f-12af-4e4d-aa99-c55b2cf8e73a)<br/><br/><br/>

## **🗂️ 프로젝트 요약**
|분류|내용|
|---|---|
|주제|동행 구하기 서비스|
|팀원 구성|[👑김서현](https://github.com/se-ize) [김도훈](https://github.com/Dothur) [유채연](https://github.com/yeooniyeoon) [이예지](https://github.com/yezyaa) [최한빈](https://github.com/NamBBal)|
|개발|2023.08.09 ~ 2023.09.15|
|리팩토링|2024.05.20 ~ 2024.06.30|
|테스트용 ID/PW|traveler/1234|
|배포 링크|[🔗areyoutravelers.store](https://areyoutravelers.store/)|
|API 명세서|[🔗Link](https://www.notion.so/API-5dedc3f98af14eb89275f06b9176aa9d)|

<br/><br/>

## **🎯 프로젝트 개요**
### **프로젝트 주제**
다양한 지역과 나라별로 MBTI와 성향을 고려한 여행 동행자 및 개인 로컬 투어 가이드를 찾을 수 있는 온라인 서비스<br/><br/>

### **기획 배경**
- 코로나19 관련 규제가 완화되며 여행 수요 급증
- 팬데믹 동안 자신만의 맞춤형 여행을 선호하는 경향 증가
- 개인 성향에 맞는 여행 동행자 및 가이드를 찾아주는 서비스 부재
<br/><br/>

### **프로젝트 목표**
- MBTI 및 성향을 기반으로 **여행 동행자** 및 **가이드** 연결
- **여행 온도** 및 **후기**를 통해 신뢰할 수 있는 서비스 제공
- 다양한 나라의 동행자와 가이드를 통해 **문화 교류** 및 **풍부한 경험** 제공
- 여행을 좋아하는 다양한 나라의 사용자들이 모인 **다국적 커뮤니티** 형성<br/><br/><br/>

## **🛠️ 개발 환경**
### 기술 스택
- FE ![HTML5](https://img.shields.io/badge/HTML5-%23E34F26.svg?style=square&logo=html5&logoColor=white) ![JavaScript](https://img.shields.io/badge/JavaScript-%23323330.svg?style=square&logo=javascript&logoColor=%23F7DF1E) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=square&logo=Thymeleaf&logoColor=white) ![CSS3](https://img.shields.io/badge/CSS3-%231572B6.svg?style=square&logo=css3&logoColor=white)
- BE ![Java](https://img.shields.io/badge/Java17-%23ED8B00.svg?style=square&logo=openjdk&logoColor=white) <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=square&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=square&logo=Spring Security&logoColor=white"> ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=square&logo=Spring&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-black?style=square&logo=JSON%20web%20tokens) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=square&logo=Gradle&logoColor=white)
- DB ![MySQL](https://img.shields.io/badge/MySQL-4479A1.svg?style=square&logo=mysql&logoColor=white) ![AmazonDynamoDB](https://img.shields.io/badge/Amazon%20RDS-527FFF?style=square&logo=AmazonRDS&logoColor=white)
- Infra ![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=square&logo=ubuntu&logoColor=white) <img src="https://img.shields.io/badge/Redis-DC382D?style=square&logo=Redis&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=square&logo=Amazon%20EC2&logoColor=white"> ![Route 53](https://img.shields.io/badge/Amazon%20Route%2053-FF9900?style=square&logo=amazonroute53&logoColor=white) ![AWS ACM](https://img.shields.io/badge/Amazon%20ACM-569A31?style=square&logo=Amazon%20AWS&logoColor=white) <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=square&logo=Amazon%20S3&logoColor=white"> ![CloudFront](https://img.shields.io/badge/Amazon%20CloudFront-8C4FFF?style=square&logo=Amazon%20AWS&logoColor=white) ![AWS ELB](https://img.shields.io/badge/Amazon%20Load%20Balancer-8C4FFF?style=square&logo=awselasticloadbalancing&logoColor=white)
- CI/CD <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=square&logo=GitHub Actions&logoColor=white"> <img src="https://img.shields.io/badge/Docker-%230db7ed.svg?style=square&logo=docker&logoColor=white"> 
- Tools ![GitHub](https://img.shields.io/badge/Github-%23121011.svg?style=square&logo=github&logoColor=white) ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=square&logo=intellij-idea&logoColor=white) ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=square&logo=postman&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=square&logo=notion&logoColor=white) ![Figma](https://img.shields.io/badge/Figma-%23F24E1E.svg?style=square&logo=figma&logoColor=white) ![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=square&logo=discord&logoColor=white) <br/><br/>

### **패키지 구조**
[🔗Link](https://github.com/AreYouTravelers/backend/blob/main/docs/tree.md)
<br/><br/>

### **커밋 컨벤션**
|타입|내용|
|---|---|
|Feat|새로운 기능 추가|
|Fix|버그 수정|
|Refactor|리팩토링|
|Chore|기능 외 기타 빌드 수정|
|Docs|문서 편집|
<br/>

### **Github Flow 전략**
![github-flow](docs/github-flow.jpg)<br/><br/><br/>

## **📝 프로젝트 문서**
|문서|URL|
|---|---|
|ERD|[🔗Link](https://www.erdcloud.com/d/5kvEhNp2RC5TpguTo)|
|화면 설계서|[🔗Link](https://www.figma.com/file/y4NxtggXSIq4BzHiSdcLdS/AreYouTravelers%3F?type=design&mode=design&t=KJMTROHjadPj61El-0)|
|API 명세서|[🔗Link](https://www.notion.so/API-5dedc3f98af14eb89275f06b9176aa9d)|
|요구사항 정의서|[🔗Link](https://docs.google.com/spreadsheets/d/e/2PACX-1vQVrljP9sEIWozBVtbqQhPIGBbvJNRBMK0zZWDWfjcJa3LI2mSKbrVnE2JM2tMXJyhJi-g2XoPR7TdY/pubhtml?widget=true&amp;headers=false)|
|WBS|[🔗Link](https://docs.google.com/spreadsheets/d/1jiwqOm9IUyIO0H23LbWmKhMXi55cCEFRxGzutb1FegQ/edit?gid=50422208#gid=50422208)|
|단위 테스트|[🔗Link](https://documenter.getpostman.com/view/28055214/2sA3XPD3JJ#729924e2-cc05-4b0b-b643-d284db6fe61d)|
<br/>

### **ERD**
![erd](docs/erd.jpg)<br/><br/>

### **FlowChart**
![flow-chart](docs/flow-chart.jpg)<br/><br/><br/>

## **🖥️ 주요 기능 및 화면 테스트**
<details>
    <summary><h3>사용자</h3></summary>
    <ul>
        <li>
            <b>회원가입</b>
            <ul>
                <li>아이디, 이름, 비밀번호, MBTI, 성별, 생년월일을 입력하여 계정 생성</li>
            </ul>
        </li>
        <li><b>로그인</b></li>
    </ul>
</details>
<details>
    <summary><h3>동행 구하기 및 가이드 구하기</h3></summary>
</details>
<details>
    <summary><h3>댓글 및 답글</h3></summary>
</details>
<details>
    <summary><h3>동행 요청 및 응답</h3></summary>
</details>
<details>
    <summary><h3>후기 작성 및 여행온도</h3></summary>
</details>
<details>
    <summary><h3>유저 신고</h3></summary>
</details>
<br/><br/>

## **☁️ 시스템 아키텍처**
![architecture](docs/architecture.jpg)<br/><br/><br/>

## **🌎 CI/CD 계획서**



------
Powered by AreYouTravelers

Website designed by 3355

© 2023 3355Corp. All rights reserved.

AreYouTravelers는 통신판매중개자이며 통신판매 당사자가 아닙니다. 상품정보 및 거래에 관한 책임은 판매자에게 있습니다.

![로고회색 255x52](https://github.com/AreYouTravelers/backend/assets/70623290/dcec6656-79bd-4f72-a076-10bed82e4883)
