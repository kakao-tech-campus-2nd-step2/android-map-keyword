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
<img src="https://github.com/YJY1220/DATA/assets/93771689/ae287ed5-0a0b-4ef7-8214-0b39f9e01bba" width="200" height="400"/>
<img src="https://github.com/YJY1220/DATA/assets/93771689/cf8d23cd-3f89-4bc0-9cfe-e3d03e9b8e1d" width="200" height="400"/>
<img src="https://github.com/YJY1220/DATA/assets/93771689/3cf47693-6e45-4ea1-8819-5c8889b99a83" width="200" height="400"/>
<img src="https://github.com/YJY1220/DATA/assets/93771689/7ccd9316-d501-4e9c-b1c4-e68da9f63a7a" width="200" height="400"/>
<img src="https://github.com/YJY1220/DATA/assets/93771689/d5b085ae-0acc-4ca6-9767-a408d5a11b48" width="200" height="400"/>

### 실행 화면 
https://youtube.com/shorts/Pe93VjSbMnk?feature=share
