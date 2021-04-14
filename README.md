#Seminal 1주차 과제 level1

##1. 화면 전환 후 데이터를 가져오는 로직
SignInActivity.kt
```
private fun loginButtonClickEvent() {
        binding.btnLogin.setOnClickListener {

            ...
            } else {
                //HomeActivity로 이동
                val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                startActivity(intent)

                Toast.makeText(this@SignInActivity, "로그인 성공", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
```
```
    private fun signUpClickEvent() {
        binding.tvSignUp.setOnClickListener {
            //SignUpActivity로 이동, registerActivityForResult 이용
            //startActivity : 새 액티비티를 열어줌
            //startActivityForResult : 새 액티비티를 열어줌 + 결과값 전달

            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivityForResult(
                intent,
                REQUEST_CODE
            )     //int형 requestCode -> 여러 액티비티를 쓰는 경우, 어떤 Activity인지 식별하는 값

        }
    }

    //onActivityResult() 메소드 : 호출된 Activity에서 저장한 결과를 돌려줌
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(
                    this@SignInActivity, "RESULT_OK"
                            + "/n이름 : " + data?.getStringExtra("userName")
                            + "/n깃허브 아이디 : " + data?.getStringExtra("userId")
                            + "/n비밀번호 : " + data?.getStringExtra("userPw"), Toast.LENGTH_SHORT
                ).show();
            } else {   // RESULT_CANCEL
                Toast.makeText(this@SignInActivity, "RESULT_CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
    }
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
                intent.putExtra("userId", userId.toString())
                intent.putExtra("userPw", userPw.toString())

                setResult(
                    RESULT_OK,
                    intent
                )   //setResult() 메소드로 결과를 저장 -> 성공 : RESULT_OK, 실패 : RESULT_CANCEL
                finish()
            }
        }
 ```


##2. 생명주기 호출 로그
![image](https://user-images.githubusercontent.com/53166299/114308901-58712180-9b20-11eb-90b7-9a7bed4971a0.png)




##3. 배운 내용

startActivity만 사용하다가 startActivityForResult()를 알 수 있었다. 

startActivity() - 화면 전환

startActivityForResult() - 화면전환 + 결과값 반환
setResult() 메소드로 결과 저장
finish() 로 초기 화면으로 돌아가기
onActivityResult() 메소드에서 여러 개의 startActivityForResult() 구분 가능 / 호출된 Activity에서 저장한 결과를 돌려줌
-> 이 기능이 사라지면서 registerForActivityResult()에서 처리 가능
 다음 과제에서 registerForActivityResult()로 수정하면서 이론 정리할 예정
