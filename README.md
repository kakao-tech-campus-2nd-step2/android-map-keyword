# android-map-keyword
## KakaoTechCampus 2기 Step2
### 2주차 2단계 과제 - 검색

### 기능 구현
0. 1단계 코드 기반 기능 업데이트 
1. 데이터 목록 구분 & 마커표시 레이아웃
2. Db 업데이트 시 기존 데이터 유지 기능
3. 데이터 검색 기능
4. 데이터 모델 정의
5. Db 데이터 가져오는 기능
6. MVVM 패턴 적용하여 검색 데이터 기반 항목 선택 및 삭제 기능
7. 검색결과 데이터 목록 RecyclerView 기능
8. 선택된 데이터 여부 판단 후 경고 메시지 기능
9. 선택된 데이터 변경 시 어댑터에 변경사항 넘기는 기능 
10. RecyclerView로 데이터 표시 기능
11. RecyclerView로 선택된 데이터 표시 기능 

### 전체 디렉토리 구조 
```bash
app
├── build
├── src
│   ├── androidTest
│   │   └── java
│   │       └── campus.tech.kakao,map
│   │           └── .gitkeep
│   ├── main
│   │   ├── java
│   │   │   └── campus.tech.kakao.map
│   │   │       ├── MainActivity.kt
│   │   │       ├── MapAccess.kt
│   │   │       ├── MapItem.kt
│   │   │       ├── MapViewModel.kt
│   │   │       └── SelectedAdapter.kt
│   │   │       ├── SelectedAdapter.kt
│   │   │       └── SQLiteHelper.kt
│   │   └── res
│   │       ├── drawable
│   │       │   ├── id_delete.xml
│   │       │   ├── ic_launcher_background.xml
│   │       │   ├── ic_launcher_foreground.xml
│   │       │   └── ic_location_marker.xml
│   │       ├── layout
│   │       │   ├── activity_main.xml
│   │       │   ├── item_search_result.xml
│   │       │   └── item_selected.xml
│   │       └── layout
│   │           ├── colors.xml
│   │           ├── strings.xml
│   │           └── themes.xml
├── .gitignore
├── build.gradle.kts
└── proguard-rules.pro
```

### 구현 화면


### 실행 화면 