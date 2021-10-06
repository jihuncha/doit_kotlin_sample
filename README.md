### doit_kotlin_sample
    1. 안드로이드의 기본적인 내용을 코틀린 사용하여 구현 및 실습

#### Begin

* .gitignore 

~~~
\.idea/
\.gradle/
\build/
\app/build/
\local.properties
~~~

* 모듈 단위로 분할하여 진행

#### ch9_resource
    - 가로/세로 전환에 대한 대응 (layout 폴더 참고)

#### ch10_notification
    
[참고](ch10_notification/src/main/java/com/huni/engineer/ch10_notification/DialogTestActivity.kt)
  
* api 30 이후 Toast 기능
* datepicker
* dialog
* alarm and vibrate
* notification 채널
  - 알람의 터치 이벤트 : PendingIntent 사용
  - 액션 : 알람취소, 전화 앱의 수신 / 거절 등
  - 원력 입력 : remoteInput 사용
  - 프로그래스 : builder.setProgress 이용
  - 알림 스타일 : 큰 이미지 / 긴 텍스트 / 상자 / 메시지 
    
#### ch11 - jetpack1. Actionbar

   * ActionBar
        * 화면 위쪽에 타이틀 문자열이 출력되는 영역  
   
   * Toolbar
        * Actionbar 와 동작은 비슷하나, 개발자가 직접 제어하는 View
   
   * Fragment