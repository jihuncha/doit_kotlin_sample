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


#### 2021.09.24 ~ 2021.09.26
* ch9_resource
    - 가로/세로 전환에 대한 대응 (layout 폴더 참고)

* ch10_notification
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

#### 2021.10.01 ~ 2021.10.06
3. ch11 - jetpack 
   3-1. Actionbar
        * 화면 위쪽에 타이틀 문자열이 출력되는 영역  
   
   3-2. Toolbar
        * Actionbar 와 동작은 비슷하나, 개발자가 직접 제어하는 View
   
   3-3. Fragment