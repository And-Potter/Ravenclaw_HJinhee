
# Seminal 1주차 과제 level1, level2

### level1
 #### signIn, signUp, home 화면 구현
### level2
 #### activity_sign_in.xml에서 Guidline 활용
 #### activity_home.xml에서 자기소개 ScrollView 활용
 

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
