# android-map-keyword
## STEP 1) 기능 목록 작성 
---
- 뷰 xml 제작하기
    - 검색어 입력 필드 : 힌트 텍스트=“검색어를 입력해 주세요”
    - 검색 버튼 : 검색어 입력 필드 오른쪽
    - 검색 결과 표시할 리사이클러뷰 : RecyclerView, 검색 결과 없을 경우 “검색 결과가 없습니다” 상태 메시지
- 로컬 데이터 베이스 생성하기
    - SQLite 데이터베이스 생성 및 Place 테이블 정의 (DBHelper)
    - 데이터베이스에 Place 삽입 메서드 추가 (Repository)
    - 데이터 조회 메서드 추가 (Repository)
    - Place 데이터를 위한 LiveData를 가진 PlaceViewModel 생성 (ViewModel)
    - repository를 사용하여 Place 데이터를 삽입하는 메서드 추가 (ViewModel)
    - 더미 데이터 삽입 메서드 추가 (ViewModel)

---
## STEP 2) 기능 목록 작성
- 입력 필드, X 버튼, 리사이클러 뷰 뷰 바인딩
- 리사이클러 뷰 아이템 xml 레이아웃 파일 추가
- 검색 로직 구현
- x 버튼 클릭 시 검색어 삭제
- 저장 목록 xl 레이아웃 파일 추가 
- 아이템 클릭 시 검색어 저장 목록에 추가하는 로직 구현 (SharedPreference)
- 저장 목록 가로 스크롤 가능하도록 UI 표시 
- X 버튼 클릭 시 저장된 검색어 삭제 로직 구현