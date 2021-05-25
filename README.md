<span style="font-size:30px">내</span><span style="font-size:25px">려</span><strong style="font-size:20px">가</strong><strong style="font-size:17px">유</strong>

SSAFY 4기 자율 1반 3팀 <span style="color: green; font-size: 25px">더치페이</span>입니다.

![LOGO1](https://user-images.githubusercontent.com/68232654/119427154-7d72ca00-bd45-11eb-96a5-2e62d16a98b8.png)

- 리퍼브 상품에 하향식 경매를 이용해 판매하는 쇼핑몰 프로젝트입니다.
- 저희가 취급하는 리퍼브 상품이란 소비자 기준에 미치지 못하는 상품들로 <br />
  못난이 농산품, 포장 불량 이나 상품 흠집들이 존재하는 공산품, 유통기한 임박상품이 있습니다.
-  하향식 경매는 최고가로 시작되어 시간이 지나면서 값이 점점 내려가는 경매 입니다.
- 리퍼브 상품들은 재고로 처리되며 시간이 지나면 점점 가치가 하락한다는 공통점이 있습니다.
- 따라서 리퍼브 상품에 하향식 경매를 적용해 시간에 따라 가치가 달라지는 점을 반영했습니다.
---

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


## 📗목차

- [기획배경](#기획배경)
- [기획](#기획)
  - 와이어프레임
  - ERD
  - API Table
- [주요기능](#주요기능)
- [기술스택](#기술스택)
- [개발환경](#개발환경)
  - 소스트리(Sourcetree)
  - Git 컨벤션
- [시작하기](#시작하기)

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;


# 💡기획배경
![BACKGROUND2](https://user-images.githubusercontent.com/68232654/119427287-bad75780-bd45-11eb-8172-b311e4c96adf.png)

&nbsp;
&nbsp;
---
&nbsp;
&nbsp;

# 💾기획

## [와이어 프레임](https://www.figma.com/file/6Qi8Xm9QdUBCSITZJSI4Qw/%EC%9E%90%EC%9C%A8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0%3A1)

![PLAN3](https://user-images.githubusercontent.com/68232654/119427320-ca56a080-bd45-11eb-9e3c-470c5cedf555.png)

## [ERD](https://www.erdcloud.com/d/Q2J7PtKbnRL55vnw4)

![ERD4](https://user-images.githubusercontent.com/68232654/119427333-ce82be00-bd45-11eb-9886-204bd2ef7bda.jpg)


## [API Table](https://www.notion.so/API-ad638aad80a84f769262a9eabb2930d0)

![API5](https://user-images.githubusercontent.com/68232654/119427352-d3e00880-bd45-11eb-85a9-44c5719e6e66.jpg)
<br />
![API6](https://user-images.githubusercontent.com/68232654/119427357-d6426280-bd45-11eb-9a1c-cec0448f7615.jpg)
<br />
![API7](https://user-images.githubusercontent.com/68232654/119427365-db9fad00-bd45-11eb-96cb-6f345df5871c.jpg)


&nbsp;
&nbsp;
---
&nbsp;
&nbsp;

# 🛒주요기능
![MAIN8](https://user-images.githubusercontent.com/68232654/119427444-00942000-bd46-11eb-8cbe-b7956265f593.jpg)
<br />
![MAIN8_1](https://user-images.githubusercontent.com/68232654/119427450-02f67a00-bd46-11eb-8e5e-f5c5ec761322.jpg)
<br />
![MAIN8_2](https://user-images.githubusercontent.com/68232654/119427459-07229780-bd46-11eb-8ea5-6339e1a41bfe.jpg)


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
![ENV9](https://user-images.githubusercontent.com/68232654/119427484-11dd2c80-bd46-11eb-99dd-10f7e5ce3d78.jpg)


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
    2. cd exec/fronted
    3. npm install
    4. npm run serve
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
