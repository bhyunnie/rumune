# Response code 분류

### 파일 위치
> {도메인 명}/domain/{도메인 명}Response.kt

### 규칙
```text
    1. 총 5 글자 숫자로 이뤄진다
    2. example) dddSSSnnn
    3. ddd : domain 번호 example) user - 10 (100 부터 시작)
    4. sss : status 코드 example) success - 2 (HttpStatus 의 첫자리)
    5. nnn : number 번호 example) userInfoResponse - 01 (ddd + sss 마다의 순번)
    
    example ) userInfoResponse - 10201
```
