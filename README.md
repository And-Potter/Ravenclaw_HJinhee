# 📌 2nd Seminal 

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



