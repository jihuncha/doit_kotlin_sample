### doit_kotlin_sample

#### 2021.09.23
1. 책을 위한 환경 설정
2. .gitignore 참고..
~~~
\.idea/
\.gradle/
\build/
\app/build/
\local.properties
~~~
3. 모듈 단위로 분할 커밋 진행


#### 2021.09.24 ~ 20201.09.26
1. ch9 resource
    - 가로/세로 전환에 대한 대응 (layout 폴더 참고)

2. ch10 notification
    [참고](ch10_notification/src/main/java/com/huni/engineer/ch10_notification/DialogTestActivity.kt)
    * 1. api 30 이후 Toast 기능 
    * 2. datepicker
    * 3. dialog 
    * 4. alarm and vibrate
    * 5. notification 채널
        - 알람의 터치 이벤트 : PendingIntent 사용
        - 액션 : 알람취소, 전화 앱의 수신 / 거절 등
        - 원력 입력 : remoteInput 사용
        - 프로그래스 : builder.setProgress 이용
        - 알림 스타일 : 큰 이미지 / 긴 텍스트 / 상자 / 메시지 

