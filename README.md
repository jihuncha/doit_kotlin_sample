### doit_kotlin_sample

1. 안드로이드의 기본적인 내용 복습 
2. 코틀린 사용해보기

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

<hr>

#### Resource

* 가로/세로 전환에 대한 대응 (layout 폴더 참고)

<hr>

#### Dialog and Notification

[SourceCode](ch10_notification/src/main/java/com/huni/engineer/ch10_notification/DialogTestActivity.kt)

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

<hr>

#### Jetpack

   * ActionBar
        * 화면 위쪽에 타이틀 문자열이 출력되는 영역  
   
   * Toolbar
        * Actionbar 와 동작은 비슷하나, 개발자가 직접 제어하는 View
   
   * Fragment

<hr>

#### Material Design

<hr>

#### Activity Component
3. 엑티비티 제어

   * 3-1. 입력 매니저
     
     * InputMethodManager (SoftKeyboard 관련)
   
   * 3-2. 입력 모드 - Manifest 의 windowSoftInputMode 사용

     * adjustPan: 키보드가 올라올 때 입력 에디트 테그슽에 맞춰서 화면을 위로 올림
     * adjustResize: 키보드가 올라올 때 엑티비티의 크기 조정
     * adjustUnspecified: 상황에 맞는 옵션을 시스템이 알아서 조정 (기본값)
     * stateHidden: 액티비티 실행 시 키보드가 자동으로 올라오는 것을 방지
     * stateVisible:
     * stateUnspecified

   * 3-3. 방향과 전체 화면 설정
     
     * 방향 - screenOrientation 사용
     * 전체화면 - NoActionBar 사용
     * API 30 이상의 경우 : WindowInsetsController를 사용해야함

   * 3-4. 태스크 관리

     * 액티비티를 어떻게 생성하고 관리하는지를 제어하는 일
       1. Standard - 엑티비티에서 인텐트가 발생하면 항상 객체가 생성되고 테스크에 등록
       2. SingleTop - 액티비티 정보가 테스크의 위쪽에 있을 때 인텐트가 발생해도 객체를 생성하지 않음 (onNewIntent)
                              테스크 위쪽에 있을때만 객체를 생성하지 않는다. (ex. 채팅에서 노티로 타 채팅방에 이동하는 경우)
       3. SingleTask - 새로운 테스크를 만들어 등록 (다른 앱일 경우에만 적용)
       4. ingleInstance - 새로운 테스크를 만들어 등록하지만, 그 테스트에는 해당 설정이 적용된 엑티비티 하나만 등록 = 액티비티 하나가 혼자 테스크를 차지한다.

   * 3-5. ANR and Coroutine

     * ANR (Activity Not Response) : 액티비티가 응답하지 않는 오류
     * 액티비티를 실행한 메인 스레드 이외에 실행 흐름을 따로 만들어서 시간이 오래 걸리는 작업을 담당하게 해야한다.
     * 그러나 이 방법으로는 화면 변경이 안됨. 화면 변경은 메인스레드만 할 수 있음
     * 코루틴의 사용

       * Coroutine (비동기 경량 스레드)
         
       * 특징
         * 경량 
         * 메모리 누수가 적음
         * 취소 등 다양한 기능 제공
         * 많은 제트팩 라이브러리에 적용
   
       * Scope
         1. Dispatchers.Main : 액티비티의 메인 스레드에서 코루틴 생성
         2. Dispatchers.IO : 파일에 읽거나 쓰기 또는 네트워크 작업에 최적화
         3. Dispatchers.Default : CPU를 많이 사용하는 작업을 백그라운드에서 실행
         
       * Channel
         * 코루틴의 값을 전달받을 수 있는 방법을 제공 (Queue 알고리즘과 비슷)

<hr>

#### Broadcast Receiver

* 이벤트 모델로 실행되는 컴포넌트 (사용자 이벤트가 아닌 시스템의 특정한 상황) 
  * ex) 부팅이 완료됨 
* 만들기
  1. 명시적 - 클래스명만 등록
  2. 암시적 - intent-filter에 선언필요
* 등록과 해제
  * registerReceiver()
  * unregisterReciver()

* 부팅완료 
  * BOOT_COMPLETED 사용
* 화면끔 / 킴
  * Intent.ACTION_SCREEN_OFF
  * Intent.ACTION_SCREEN_ON
* 배터리상태
  * BATTERY_LOW - 베터리 낮은 상태로 변경
  * BATTERY_OKAY - 정상상태로 변경
  * BATTERY_CHANGED - 충전상태로 변경
  * ACTION_POWER_CONNECTED - 전원이 공급되기 시작한 순간
  * ACTION_POWER_DISCONNECTED - 전원이 공급끊은 순간


<hr>

#### Service

* 백그라운드 작업을 목적으로 하는 컴포넌트
* 서비스를 실행하는 Intent 함수는 2가지 (startService / bindService)

* startService
  * 해당 서비스를 인텐트에 담아서 실행 (명시적으로)
  
~~~kotlin
                //startService
                val intent = Intent(this, MyService::class.java)
                startService(intent)

                //외부 앱의 경우 암시적 인텐트 사용필요 -> 패키지 name 전달
                val intent = Intent("ACTION_OUTER_SERVICE")
                intent.setPackage("com.example.test_outer")
                startService(intent)

                //stopService를 통해 종료
                val intent = Intent(this, MyService::class.java)
                stopService(intent)
~~~

* bindService
  * ServiceConnection 인터페이스를 구현한 객체가 필요
  
~~~kotlin
                val connection: ServiceConnection = object : ServiceConnection {
                    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                        //서비스 구동시 자동 호출
                    }

                    override fun onServiceDisconnected(p0: ComponentName?) {
                        //서비스 소멸시 자동 호출
                    }
                }

                val intent = Intent(this, MyService::class.java)
                //intent, connection, flag
                bindService(intent, connection, Context.BIND_AUTO_CREATE)

                //종료
                unbindService(connection)
~~~

* 서비스 생명주기
  * https://developer.android.com/guide/components/services?hl=ko
  * ![서비스 생명 주기](ch15_service/src/main/java/com/huni/temp/img/service_lifecycle.PNG)

* 특징
  1. startService
    * 백그라운드 작업이 필요하지만, 엑티비티와 데이터를 주고받을 일이 없는 등 서로 관련이 없는 경우
  
  2. bindService
    * 엑티비티와 서비스가 상호작용해야하는 경우
      1. IBinder
        IBinder interface 사용하여 fun 구현하여, Activity에서 connection 생겼을때 사용 가능함.
         
      2. Messenger 바인딩 (프로세스 간 통신을 할때도 사용 가능)
        2-1. 내부앱 연동
~~~kotlin
//onbind반환값으로 messenger 생성하여 binder속성을 반환한다.
messenger = Messenger(IncomingHandler(this))
return messenger.binder
~~~
        2-2. 외부앱
          - intent-filter로 외부앱 연동하도록 선언해야한다.
          - queries로 연동하고자하는 앱의 패키지명을 지정해야한다.
          - 프로세스 간 통신은 parcelable 이나 bundle을 사용해야한다.


      


