# 📌 4th Seminar 

## 💡 세미나 내용

- Retrofit2로 서버와 통신하기



## 🐣 LEVEL1. 안린이 탈출하기

### 과제

- 로그인, 회원가입 통신 구현하기

### 📷 사진, 영상

👇회원가입, 로그인 서버 통신

<img width=300 alt="회원가입, 로그인 서버 통신" src="https://user-images.githubusercontent.com/53166299/118315372-686d8e00-b530-11eb-86f6-7f2440a8a190.gif"  style="float:left">

👇회원가입 전 로그인 PostMan
<img width="400" alt="회원가입 전 로그인postman" src="/Users/hanjinhee/Desktop/BE SOPT/회원가입 전 로그인postman.png" style="float:left">



​			👇회원가입 후 로그인 PostMan
<img width="400" alt="회원가입 후 로그인 postman" src="/Users/hanjinhee/Desktop/BE SOPT/회원가입 후 로그인 postman.png"  style="float:right">

















👇회원가입 후 회원가입 PostMan
<img width="400" alt="회원가입 후 회원가입 postman" src="/Users/hanjinhee/Desktop/BE SOPT/회원가입 후 회원가입 postman.png">



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

  1. 서버 Request, Response 객체 설계   (data class)
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





