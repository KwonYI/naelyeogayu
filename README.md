<span style="font-size:30px">내</span><span style="font-size:25px">려</span><strong style="font-size:20px">가</strong><strong style="font-size:17px">유</strong>

SSAFY 4기 자율 1반 3팀 <span style="color: green; font-size: 25px">더치페이</span>입니다.

<p align="center">
![__2_-removebg-preview](/uploads/834f423c23139e6ccc50b9a66c5dd10a/__2_-removebg-preview.png)

<p style="padding: 15px">
<img src="https://img.shields.io/static/v1?label=SSAFY&message=4%EA%B8%B0&color=0ABAB5">
<img src="https://img.shields.io/static/v1?label=서울&message=1반&color=00a3d2">
<img src="https://img.shields.io/static/v1?label=Domain&message=자율프로젝트&color=ABF200">
</p>

- 리퍼브 상품에 하향식 경매를 이용해 판매하는 쇼핑몰 프로젝트입니다.
- 저희가 취급하는 리퍼브 상품이란 소비자 기준에 미치지 못하는 상품들로 <br />
  못난이 농산품, 포장 불량 이나 상품 흠집들이 존재하는 공산품, 유통기한 임박상품이 있습니다.
-  하향식 경매는 최고가로 시작되어 시간이 지나면서 값이 점점 내려가는 경매 입니다.
- 리퍼브 상품들은 재고로 처리되며 시간이 지나면 점점 가치가 하락한다는 공통점이 있습니다.
- 따라서 리퍼브 상품에 하향식 경매를 적용해 시간에 따라 가치가 달라지는 점을 반영했습니다.
</p>
---

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


## 📗목차

- [기획배경](#💡기획배경)
- [기획](#💾기획)
  - 와이어프레임
  - ERD
  - API Table
- [주요기능](#🛒주요기능)
- [기술스택](#🛠기술스택)
- [개발환경](#📂개발환경)
  - 소스트리(Sourcetree)
  - Git 컨벤션
- [시작하기](#🐣시작하기)
- [만든사람들](#만든사람들)

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


# 💡기획배경

![A103_기획발표](/uploads/81ae4b2081d5c2ceebee714fdf1867f4/A103_기획발표.png)

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;

# 💾기획

## [와이어 프레임](https://www.figma.com/file/6Qi8Xm9QdUBCSITZJSI4Qw/%EC%9E%90%EC%9C%A8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0%3A1)

![Image_Pasted_at_2021-5-20_16-37](/uploads/62c28d740bc3274f52439764adedbc5e/Image_Pasted_at_2021-5-20_16-37.png)

## [ERD](https://www.erdcloud.com/d/Q2J7PtKbnRL55vnw4)

![erd](/uploads/c0274eca643542e1cc651b14c3a3c806/erd.jpg)

## [API Table](https://www.notion.so/API-ad638aad80a84f769262a9eabb2930d0)

![api_1](/uploads/6c176f210da4fc37342adac4f9c1b3f7/api_1.jpg)
<br />
![api_2](/uploads/a7c3ab6d87ab34ec7fe860e9ef81a90f/api_2.jpg)
<br />
![api_3](/uploads/59e5900a4e90e936a92db6eb37a306a1/api_3.jpg)

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;

# 🛒주요기능

![메인_1](/uploads/4c20d46648a890ad742190f2788893d1/메인_1.jpg)
<br />
![메인_2](/uploads/b11534176bb60f147c016075d1e193f7/메인_2.jpg)
<br />
![메인_3](/uploads/b695086209b89bd56cf63ca96edf855e/메인_3.jpg)

## 1. 테마 별 상품 조회
## 2. 상품 카드화로 간편하게 정보 확인
## 3. 마이페이지를 통해 활동 내역 확인
## 4. 스케쥴러를 통해 지속적인 가격 변동, 경매 기간 확인, 예약 구매 기능


&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


# 🛠기술스택

## FrontEnd

```
  
    "axios": "^0.21.1",
    "core-js": "^3.6.5",
    "eslint-config-prettier": "^8.2.0",
    "firebase": "^8.4.2",
    "jwt-decode": "^3.1.2",
    "moment": "^2.29.1",
    "swiper": "^5.3.7",
    "vue": "^2.6.11",
    "vue-awesome-swiper": "^4.1.1",
    "vue-infinite-loading": "^2.4.5",
    "vue-router": "^3.2.0",
    "vuetify": "^2.4.11",
    "vuex": "^3.4.0"
  
```

## [BackEnd](https://www.notion.so/Version-e100ebc4cd9640128633f853f336202e)

```
    "jdk" : "zulu-1.8.0_192",
    "sts" : "^3.9.14",
    "spring starter" : "^2.4.2",
    "lombok" : "^1.18.12",
    "jwt" : "^0.9.1",
    "swagger" : "^2.9.2",
    "spring security" : "^2.4.4",
    "JPA" : "^2.4.5"
```



&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


# 📂개발환경

### 소스트리(Sourcetree)

![소스트리](/uploads/b795ae873572fb860ebad31b541c48e7/소스트리.jpg)

- 소스트리를 활용하여 깃을 체계적으로 관리했습니다.
- branch를 쉽게 생성하며, 어떤 branch가 존재하는지 파악하기 쉽습니다.
- stash, push, pull, commit 등 알기 쉽게 구성되어 있고, 현재 어떤 상태인지 쉽게 파악할 수 있습니다.

### Git 컨벤션

```

    Commit : #<JIRA issue number> <type>:<subject>
    Branch : <type>/<Fe/Be><summary>  ⇒ Pascal 표기법으로 적기
    MR(PR) : <JIRA issue number> <description>
    
```


&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


# 🐣시작하기

```
    FrontEnd 
    
    1. .env.local파일을 exec/frontend/src에 이동
    2. npm install
    3. npm run serve
```
```
    BackEnd
    
    1. application yml파일을 exec/backend/a103/src/main/resources에 이동
    2. 설정에 맞게 파일 수정(DB 설정)
    3. Run As -> Spring Boot App 실행
```
```
    DB
    
    1. a103_DB_Dump.zip을 이용하여 DB를 설정합니다.
```


&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


# 👨‍💻만든사람들

## 팀장 : 하태민 (BackEnd + DevOps)
##  팀원 
## 1. 김명규 (BackEnd)
## 2. 권영일 (BackEnd)
## 3. 송지헌 (FrontEnd + 최종 발표)
## 4. 천창민 (FrontEnd + 중간 발표)

1. a103_DB_Dump.zip을 이용하여서 DB를 설정해줍니다.

2. application.yml파일을 exec/backend/a103/src/main/resources로 옮긴뒤 설정에 맞게 파일을 수정해줍니다.
    ({mysql server url}, {mysql username}, {mysql userpasswor} 부분)

3. .env.local파일을 exec/frontend로 이동시킨다.

4. cd exec/frontend

5. npm install

6. npm run serve

<h2>지금까지 <strong style="color: green; font-size: 45px;">더치페이</strong>의 리드미였습니다. <br/> 감사합니다!</h2>

![A103_기획발표](/uploads/82c2c8bfe89b7df58215cfb7b2d89602/A103_기획발표.jpg)
