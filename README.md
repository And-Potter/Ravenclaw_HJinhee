# 📌 4th Seminar 

## 💡 세미나 내용

- Retrofit2로 서버와 통신하기



## 🐣 LEVEL1. 안린이 탈출하기

### 과제

- 로그인, 회원가입 통신 구현하기

### 📷 사진, 영상

👇회원가입, 로그인 서버 통신

<img width=300 alt="회원가입, 로그인 서버 통신" src="https://user-images.githubusercontent.com/53166299/118315372-686d8e00-b530-11eb-86f6-7f2440a8a190.gif" style="float:left">

👉회원가입 전 로그인 PostMan
<img width="400" alt="회원가입 전 로그인postman" src="https://user-images.githubusercontent.com/53166299/118391617-2c414700-b670-11eb-8bf0-5cb34f3fbc18.png" >

👉회원가입 후 로그인 PostMan
<img width="400" alt="회원가입 후 로그인 postman" src="https://user-images.githubusercontent.com/53166299/118391629-42e79e00-b670-11eb-9334-e748f9a3ea2f.png" >

👉회원가입 후 회원가입 PostMan
<img width="400" alt="회원가입 후 회원가입 postman" src="https://user-images.githubusercontent.com/53166299/118391646-53981400-b670-11eb-804c-bb6df98ca044.png">



### 코드

- ServiceCreator - Retrofit Interface 구현체

  ```kotlin
  object ServiceCreator {
      private const val BASE_URL = "http://cherishserver.com"
  
      private val retrofit: Retrofit = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
  
      val loginService: LoginService = retrofit.create(LoginService::class.java)
  }
  ```

- LoginService : 서버 요청 동작

  ```kotlin
  interface LoginService {
      @POST("/login/signin")
      fun postLogin(
          @Body body: RequestLoginData
      ) : Call<ResponseLoginData>
  
      @POST("/login/signup")
      fun postSignUp(
          @Body body: RequestSignUpData
      ) : Call<ResponseSignUpData>
  }
  ```

- 서버 Request, Response 객체 설계

  ```kotlin
  //RequestLoginData.kt
  data class RequestLoginData (
      @SerializedName("email")
      val id: String,
      val password: String,
  )
  
  //RequestSignUpData.kt
  data class RequestSignUpData (
      @SerializedName("email")
      val id: String,
      val password: String,
      val sex: String,
      val nickname: String,
      val phone: String,
      val birth: String,
  )
  
  //ResponLoginData.kt
  data class ResponseLoginData (
      @SerializedName("email")
      val id: String,
      val password: String,
      val data: LoginData?
  )
  
  data class LoginData(
      @SerializedName("UserId")
      val userId: Int,
      val user_nickname: String,
      val token: String
  )
  
  //ResponSignUpData.kt
  data class ResponseSignUpData (
      @SerializedName("email")
      val id: String,
      val password: String,
      val sex: String,
      val nickname: String,
      val phone: String,
      val birth: String,
      val data: SignUpData?
  )
  
  data class SignUpData(
      val nickname: String
  )
  ```

- 통신 요청 - SignInActivity.kt : 로그인 버튼 클릭 시 빈칸이 없다면 서버 요청

  ```kotlin
  private fun loginButtonClickEvent() {
          binding.btnLogin.setOnClickListener {
  
              val userId = binding.etLoginId.text
              val userPw = binding.etLoginPw.text
              if (userId.isNullOrBlank() || userPw.isNullOrBlank()) {
                  Toast.makeText(this@SignInActivity, "아이디/비밀번호를 확인해주세요!", Toast.LENGTH_SHORT)
                      .show()
              } else {
                  loginCommunicateServer()
              }
          }
      }
  
      private fun loginCommunicateServer(){
          //서버로 보낼 로그인 데이터 생성
          val requestLoginData = RequestLoginData(
              id = binding.etLoginId.text.toString(),
             password = binding.etLoginPw.text.toString()
          )
  
          val call: Call<ResponseLoginData> = ServiceCreator.loginService.postLogin((requestLoginData))
  
          call.enqueue(object: Callback<ResponseLoginData> {
              override fun onResponse(
                  call: Call<ResponseLoginData>,
                  response: Response<ResponseLoginData>
              ) {
                  if(response.isSuccessful){
                      val data = response.body()?.data
                      Toast.makeText(this@SignInActivity, "로그인 성공 \n userNickname : "+data?.user_nickname, Toast.LENGTH_SHORT).show()
  
                      startHomeActivity()
                  }else{
                      //에러났을 때 코드
                      Toast.makeText(this@SignInActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                  }
              }
  
              override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                  Log.d("NetworkTestSignIn","error:$t")
              }
          })
      }
  ```

  

  통신 요청 - SignUpActivity.kt : 회원가입 버튼 클릭 시 빈칸이 없다면 서버 요청

  ```kotlin
  private fun signUpButtonClickEvent() {
      binding.btnSignUp.setOnClickListener {
  ...
          } else {
              signUpCommunicateServer()
  
              val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
              intent.putExtra("userName", userName.toString())
              .putExtra("userId", userId.toString())
              .putExtra("userPw", userPw.toString())
  
              setResult(
                  RESULT_OK,
                  intent
              )   
              finish()
          }
      }
  }
  
  private fun signUpCommunicateServer(){
      //서버로 보낼 회원가입 데이터 생성
      val requestSignUpData = RequestSignUpData(
          id = binding.etSignupId.text.toString(),
          password = binding.etSignupPw.text.toString(),
          sex = "0",
          nickname = binding.etSignupName.text.toString(),
          phone = "010-0000-0000",
          birth = "1999-00-00"
      )
  
      val call: Call<ResponseSignUpData> = ServiceCreator.loginService.postSignUp((requestSignUpData))
  
      call.enqueue(object: Callback<ResponseSignUpData> {
          override fun onResponse(
              call: Call<ResponseSignUpData>,
              response: Response<ResponseSignUpData>
          ) {
              if(response.isSuccessful){
                  val data = response.body()?.data
                  Toast.makeText(this@SignUpActivity, "회원가입 성공 \n 이름 : "+data?.nickname, Toast.LENGTH_SHORT).show()
  
              }else{
                  //에러났을 때 코드
                  Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
              }
          }
  
          override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
              Log.d("NetworkTestSignUp","error:$t")
          }
      })
  }
  ```



### 배운 내용

- retrofit2 을 이용한 서버 통신 방법

  1. 서버 Request, Response 객체 설계 (data class)
  2. Retrofit Interface 구현체 만들기 (ApiService, ServiceCreator)
  3. callback을 등록하며 통신 요청하기 (Activity, Fragment)

- recyclerview 데이터바인딩 사용 (repo, follow)

  - Item_repository.xml

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <layout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">
  
      <data>
          <variable name="repoData" type="com.example.sopt_1.data.RepositoryInfo" />
      </data>
  ...
          <TextView
              android:id="@+id/tv_repository_name"
              ...
              android:text="@{repoData.repoName}"
              .../>
  
          <TextView
              android:id="@+id/tv_repository_info"
              ...
              android:text="@{repoData.repoInfo}"
              ... />
  
          <TextView
              android:id="@+id/tv_language"
              ...
              android:text="@{repoData.repoLanguage}"
              .../>
      </androidx.constraintlayout.widget.ConstraintLayout>
  </layout>
  ```

  - RepositoryListAdapter.kt

  ```kotlin
  ...
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
          val binding = ItemRepositoryBinding.inflate(
              LayoutInflater.from(parent.context),
              parent,
              false
          )
          return RepositoryViewHolder(binding)
  ...
  class RepositoryViewHolder(
      private val binding: ItemRepositoryBinding
  ) : RecyclerView.ViewHolder(binding.root) {
      fun onBind(repositoryInfo: RepositoryInfo) {
          binding.repoData = repositoryInfo
      }
  }
  ```


# 📌 2nd Seminar 

## 💡 세미나 내용

- RecyclerView를 사용하여 리스트 만들기
- 팔로잉 화면 구현



## 🐣 LEVEL1. 안린이 탈출하기

### 과제

- 홈 화면의 User 정보에 more 버튼 추가하여 팔로잉 화면으로 넘어가기 하기
- 홈 화면에 레포지터리 리스트 구현하기 (RecyclerView)
  - 레포지터리 이름과 설명이 길 경우 ... 으로 표시



### 구현 방법

1. Recyclerview에 사용될 item layout 만들기 

   - item_repository.xml

2. 리스트가 보여질 activiry에 Recyclerview 추가 

   - activity_home.xml에 추가함

3. 레포지터리 리스트에 들어갈 내용을 data class 로 만들기

   - RepositoryInfo.kt

4. 어댑터 클래스 만들기

   - RepositoryListAdapter.kt
   - RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>() 상속
     - RepositoryListAdapter class 안에 RepositoryViewHolder 클래스도 생성 
   - Adapter는 ViewHolder로 변경할 Data 가지고 있어야 함
     - ``` userList = mutableListOf<RepositoryInfo>() ```
   - Adapter는 아이템마다 ViewHolder를 만드는 방법 정의
   - Adapter는 전체 아이템의 수 알아야 함
   - Adapter는 ViewHolder에 Data 전달하는 방법 정의

5. Recyclerview가 들어갈 화면의 Activity/Fragment에 어댑터 추가

   - HomeActivity.kt

   - ``` kotlin
     // 1. 우리가 사용할 어뎁터의 초기 값을 넣어준다
             repositoryListAdapter =
                 RepositoryListAdapter()
     
             // 2. RecyclerView 에 어뎁터를 우리가 만든 어뎁터로 만들기
             binding.listRepository.adapter = repositoryListAdapter
     
             repositoryListAdapter.userList.addAll(
                 listOf<RepositoryInfo>(
                     RepositoryInfo(
                         repoName = "And-Potter/Ravenclaw_HJinhee",
                         repoInfo = "진희의 솝트 레포지터리",
                         repoLanguage = "Kotlin"
                     ),
                     RepositoryInfo(
                         repoName = "레포지터리 이름이 엄청 길 때 ... 표시되도록 하는거 보여주려고 이렇게 길게 쎄보는 중입니다아",
                         repoInfo = "레포지터리 설명이 엄청 길 때 ... 표시되도록 하는거 보여주려고 이렇게 길게 쎄보는 중입니다아",
                         repoLanguage = "Java"
                     )
                 )
             )
     
             repositoryListAdapter.notifyDataSetChanged()
     ```
     
     
# 📌 1st Seminar Seminar

## 🐣 LEVEL1. 안린이 탈출하기
- signIn, signUp, home 화면 구현

## 🐣 LEVEL12.
- activity_sign_in.xml에서 Guidline 활용
- activity_home.xml에서 자기소개 ScrollView 활용
 

## 1. 화면 전환 후 데이터를 가져오는 로직
SignInActivity.kt
```
private val signUpActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            //데이터를 받아서 할 일이 들어가는 칸
            if (it.resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    binding.etLoginId.setText(it.data?.extras?.getString("userId"))
                    binding.etLoginPw.setText(it.data?.extras?.getString("userPw"))
                }
            }

        }
 ```
 ```
 private fun signUpClickEvent() {
        binding.tvSignUp.setOnClickListener {

            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            signUpActivityLauncher.launch(intent)

```

 
SignUpActivity.kt
 ```
private fun signUpButtonClickEvent() {
        binding.btnSignUp.setOnClickListener {

            ...

            } else {
                //초기 SignUpActivity로 돌아갈 수 있도록 종료
                // 종료 전 putExtra를 이용해 모든 값을 intent에 넣어 전달
                val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                intent.putExtra("userName", userName.toString())
                    .putExtra("userId", userId.toString())
                    .putExtra("userPw", userPw.toString())

                setResult(
                    RESULT_OK,
                    intent
                )   //setResult() 메소드로 결과를 저장 -> 성공 : RESULT_OK, 실패 : RESULT_CANCEL
                finish()
            }
        ...
 ```


## 2. 생명주기 호출 로그
![image](https://user-images.githubusercontent.com/53166299/114308901-58712180-9b20-11eb-90b7-9a7bed4971a0.png)



## 3. 기타
### 화면전환 _ startActivityForResult()
##### startActivityForResult() - 화면전환 + 결과값 반환
##### setResult() 메소드로 결과 저장
##### finish() 로 초기 화면으로 돌아가기
##### onActivityResult() 메소드에서 여러 개의 startActivityForResult() 구분 가능 / 호출된 Activity에서 저장한 결과를 돌려줌
![start 1](https://user-images.githubusercontent.com/53166299/114663747-c0e51c00-9d35-11eb-96c3-a42116302ac1.png)
![start 2](https://user-images.githubusercontent.com/53166299/114663755-c3e00c80-9d35-11eb-9b3a-4eec441872b6.png)
![start 3](https://user-images.githubusercontent.com/53166299/114663764-c6426680-9d35-11eb-9983-0f7af93c3de7.png)



-> 이 기능이 사라지면서 registerForActivityResult()에서 처리 가능

### 화면전환_ registerForActivityResult()
##### registerForActivityResult() 메소드를 통해 ActivityLauncher 만들기
##### -> 여기서 바로 데이터 불러오고 사용 가능
##### ActivityLauncher 를 launch 할 때 intent를 인자로 전달
##### -> 어떤 액티비티에서 요청을 보냈는지 확인할 필요없이, 해당 액티비티에서 보낸 요청은 무조건 registerForActivityResult()의 collback으로 떨어짐
##### setResult() , finish()는 이전과 동일





