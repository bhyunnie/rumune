<img src="https://github.com/bhyunnie/rumune/assets/129918927/62fe3888-0255-4299-b4da-d7b5e03a7ef6" width="400" height="300"/>

## 🚀 배포링크 : https://rumune.co.kr

## 📖 프로젝트 소개
RUMUNE 는 이커머스 프로젝트의 일환으로 같은 상품이지만 다른 가격에 다른 게시글에서 판매되는 것에 대한 의문으로부터 시작되었습니다.
초기에는 개인이 제작한 핸드메이드 굿즈를 판매하는 것으로 시작하지만, 차후에는 커뮤니티 및 개인이 판매를 할 수 있는 소싱 플랫폼으로도 리워크 가능하도록
확장에 열려있는 유연한 코드를 작성하는데 집중한 프로젝트입니다.

## 🚧 아키텍처
![image](https://github.com/bhyunnie/trust-price/assets/129918927/4b8747fc-6b27-4894-8137-3e834c729c89)

## 📚 ERD
![image](https://github.com/bhyunnie/trust-price/assets/129918927/ebe4f856-4133-4fa7-b680-e88e315bc165)

## 🚀 기술 스택
![image](https://github.com/bhyunnie/trust-price/assets/129918927/7611defb-7f07-451d-9721-a69063e1d8ca)

## 🎁 주요 기능
1. 인기 상품 추천 기능
![image](https://github.com/bhyunnie/trust-price/assets/129918927/51b7e5cf-2322-4f5d-867a-ab64a736a9b3)

Redis 의 List 자료구조를 이용하여 Queue 와 같이 활용함
- 게시물 조회 시 Redis 에 저장
- 저장 되어 있는 500길이의 list 를 5분마다 스케줄링으로 10개로 축약함 (빈도수 상위 10개)
- 상위 10개로 축약된 list 를 redis에 저장하여 제공

2. OAuth2.0 로그인 기능
![image](https://github.com/bhyunnie/trust-price/assets/129918927/1fa78067-22ac-40e9-86ab-6a5b9d5d6ac3)

Spring Security 를 활용하여 OAuth2.0 로그인 기능 구현
- OAuth2.0 로그인 요청
- 기존 회원 일 시 로그인
- 기존 회원 아닐 시 회원 가입
- 로그인 시 jwt (access, refresh Token) 쿠키로 반환
