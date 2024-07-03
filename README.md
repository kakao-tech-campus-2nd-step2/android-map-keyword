# android-map-keyword

## 구현 기능 목록
1. MVVM 아키텍처 구조 설립
2. 기본 레이아웃 구현
3. SQLite 활용하여 데이터베이스 구축
4. 로컬 데이터베이스에 데이터 생성

## Step 2 구현 기능 목록
- [x]  SQLiteOpenHelper을 통해 DB를 업데이트 하거나 요청받은 LiveData 데이터를 가져오는 Repository 클래스 구현
- [x]  유저 입력에 따라 Repository로 검색 결과 데이터 조회를 요청하는 ViewModel 클래스 구현
- [x]  검색 결과 Fragment에 리스트를 출력하는 RecyclerView 추가
- [x]  RecyclerView Adapter에서 ViewModel의 데이터를 Observe하여 ViewModel의 검색 결과 데이터의 변화에 따라 리스트의 값을 갱신하도록 구현
- [x]  SearchInput 에서 텍스트가 변화할 때마다 ViewModel에서 검색 결과를 갱신하도록 구현
- [ ]  SearchKeyword 테이블 스키마 및 Contract 생성
- [ ]  SearchKeyword 테이블 관련 헬퍼 메소드 작성
- [ ]  Keyword를 저장하는 레포지토리 클래스 생성
- [ ]  검색 바 아래 KeywordList 생성
- [ ]  검색 바 Text값을 ViewModel에 바인딩
- [ ]  ViewModel에 Keyword 클릭 시 해당 검색어 검색하는 메소드 생성
- [ ]  ViewModel에 Keyword 제거하는 메소드 생성
- [ ]  ViewModel의 검색 메소드에 KeywordRepository로 새로운 Keyword 저장하는 기능 추가
- [ ]  MainActivity에서 Keyword 목록을 Observe하여 자동으로 갱신되는 KeywordList Layout 추가 (ListView 활용)