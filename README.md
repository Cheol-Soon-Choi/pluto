## 0. 요약
- https://www.ccsoon.shop
- [쇼핑몰 세부설명(Notion)으로 이동]()

## 1. 기본 사항         
- Spring Boot 및 REST API를 통한 쇼핑몰 웹 서비스 구현
- Spring Security를 통한 기본적인 보안 설정
- AWS EC2를 이용하여 서비스 배포

## 2. 주요 기능 
+ 쇼핑몰 운영에 필요한 기본 CRUD 기능 구현
  - 생성(Create)
    - 주문
    - 회원가입
    - 제품 등록
    - 장바구니 추가
  - 읽기(Read)
      - 제품 목록
      - 주문 목록
      - 장바구니 목록
      - 제품 상세 정보
  - 수정(Update)
      - 장바구니 수량 변경
  - 삭제(Delete)
      - 장바구니 제품 삭제
      
- 쿠키-세션 방식으로 기본적인 보안 설정
  - 인증(Authentication)
      - Guest: 쇼핑몰 기능 사용 제한
      - Formlogin 이후 쇼핑몰 기능 사용
  - 인가(Authorization)
      - 인증된 사용자의 역할에 따라 기능 구분
         + USER : 제품 보기, 제품 주문 등
         + ADMIN : USER 기능 + 제품 등록/수정
        
## 3. 쇼핑몰 배포
- AWS
  + EC2
    - Amazon Linux 2
  + RDS
    - MariaDB
  - S3
    + jar 파일 저장
    + 쇼핑몰 제품 이미지 저장/삭제
  + CodeDeploy
    - 자동 배포

+ Jenkins
  + 자동 빌드
  + Travis CI 무료 기한 만료에 따라 Jenkins로 변경   

## 4. 개발 환경 및 사용 기술
- JAVA 11
- Spring boot 2.5.5 (Gradle)
- HTML/CSS, JavaScript, Bootstrap, Thymeleaf
- AWS(EC2, S3, RDS, CodeDeploy)
- Intellij ULTIMATE
