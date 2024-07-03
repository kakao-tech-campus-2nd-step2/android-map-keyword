# android-map-keyword
**카카오 테크 캠퍼스 STEP2 Android 2주차 미션 - 검색어 저장**
## 기능 요구사항
### 1단계 기능
- 검색어 입력 및 검색 결과를 표시할 기본 레이아웃을 구현한다.
- 검색에 사용될 데이터를 로컬 데이터베이스에 생성한다.
### 2단계 기능
- 검색어를 입력하면 검색 결과 목록이 표시된다.
- 검색 결과 목록은 세로 스크롤이 된다.
- 입력한 검색어는 X를 눌러서 삭제할 수 있다.
- 검색 결과 목록에서 하나의 항목을 선택할 수 있다.
- 선택된 항목은 검색어 저장 목록에 추가된다.
- 저장된 검색어 목록은 가로 스크롤이 된다.
- 저장된 검색어는 X를 눌러서 삭제할 수 있다.
- 저장된 검색어는 앱을 재실행하여도 유지된다.
## 프로그래밍 요구사항
### 1단계 요구사항
- 검색 데이터 저장은 `SQLite`를 사용한다.
- 가능한 MVVM 아키텍처 패턴을 적용하도록 한다.
- 코드 컨벤션을 준수하여 프로그래밍 한다.
### 2단계 요구사항
- 검색 결과 목록은 `RecyclerView`를 사용한다.
- 가능한 MVVM 아키텍처 패턴을 적용하도록 한다.

## 구현할 기능 목록
### 1단계 기능
- [x] 검색어 입력 레이아웃
- [x] 검색 결과 레이아웃
- [x] 검색 데이터 저장할 데이터베이스
- [x] MVVM 패턴 적용
### 2단계 기능
- [x] 검색 결과 목록 RecyclerView로 구현
- [x] 검색어 입력시 DB에서 검색
- [x] 검색어 우측 X버튼으로 입력값 삭제 
- [X] 목록 터치시 별도로 저장
- [X] 저장된 검색어 X버튼으로 삭제
- [ ] 저장된 검색어 가로로 스크롤 기능