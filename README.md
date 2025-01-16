# ** 이마트 물류 관리 시스템 (SSGINC_E-WMS)**

## **🔍 프로젝트 소개**
> **신세계 I&C JAVA 기반 백엔드 개발자 양성 과정 5차수 미니 프로젝트**

**E-WMS** (*EMART-Warehouse Management System*)는 이마트의 물류 관리를 혁신적으로 효율화하기 위해 설계된 **창고 관리 시스템**입니다.  
이 시스템은 창고 운영의 **효율성, 정확성, 실시간 데이터 관리**를 강조하며, 다양한 물류 관리 기능을 제공합니다.

---
## 목차
- [👥 팀원 및 담당 업무](#-팀원-및-담당-업무)
- [📌 프로젝트 개요](#-프로젝트-개요)
- [🚀 주요 기능](#-주요-기능)
- [📂 폴더 구조](#-폴더-구조)
- [🌐 프로젝트 페이지 미리보기](#-프로젝트-페이지-미리보기)
---

## 👥 팀원 및 담당 업무

| **이름**   | **담당 업무**                  |
| ---------- | ------------------------------ |
| **김진오** | 팀장, 입고관리                 |
| **김대철** | 프로젝트 발표, 재고관리         |
| **김동현** | 형상관리, 회원관리 및 대시보드 |
| **신가연** | 프로젝트 문서화, 게시판 관리   |
| **조민호** | 스케줄러, 출고관리             |

---

## 📌 프로젝트 개요

이 프로젝트는 **이마트 물류 시스템**의 핵심인 **입고, 적치, 검수, 재고, 출고** 등을 효율적으로 관리하고, **실시간 재고 현황**을 제공하기 위해 기획되었습니다.  
사용자 친화적인 **UI/UX**와 다양한 기능을 통해 업무의 효율성을 높이고, 정확하고 신속한 데이터 제공으로 물류 운영의 최적화를 목표로 합니다.

---

## 🚀 주요 기능

### 1. **회원관리**
- 회원가입 및 로그인
- 회원 정보 수정
- 회원 탈퇴

### 2. **입고관리**
- 입고 처리 및 조회
- 물품 위치 배정 및 조회
- 입고 물품 검수 및 상태 관리

### 3. **재고관리**
- 재고 조정
- 입·출고 요청
- 재고 조회

### 4. **출고관리**
- 출고 주문 내역 조회
- 출고 승인 대기 처리
- 출고 완료 내역 확인

### 5. **대시보드**
- 공지사항 등록 및 조회
- 데이터 시각화

### 6. **예외처리**
- 에러 코드 및 메시지 정의
- 커스텀 예외 클래스 정의
- @ExceptionHandler 를 통해 커스텀 예외 클래스 중 최상위 클래스(추상클래스-RuntimeException 상속-) 와 이외 Exception 클래스로 발생한 예외들을 전역적으로 처리하여 Http 응답 객체로 처리 ENUM ErrorCode 를 통해 어플리케이션 동작 중 발생 가능한 에러들을 중앙집중적으로 관리

### 7. **로그관리**
- 어플리케이션 이벤트 및 데이터 변경 이력 로깅

---

## 📂 폴더 구조

```bash
📦 E-WMS
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ ssginc
   │  │        └─ ewms
   │  │           ├─ EWmsApplication.java
   │  │           ├─ ServletInitializer.java
   │  │           ├─ board
   │  │           │  ├─ controller
   │  │           │  │  └─ BoardController.java
   │  │           │  ├─ dao
   │  │           │  │  └─ BoardMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ BoardService.java
   │  │           │  │  └─ BoardServiceImpl.java
   │  │           │  └─ vo
   │  │           │     └─ BoardVO.java
   │  │           ├─ branch
   │  │           │  ├─ mapper
   │  │           │  │  └─ BranchMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ BranchService.java
   │  │           │  │  └─ BranchServiceImpl.java
   │  │           │  └─ vo
   │  │           │     └─ BranchVO.java
   │  │           ├─ comment
   │  │           │  ├─ controller
   │  │           │  │  └─ CommentController.java
   │  │           │  ├─ dao
   │  │           │  │  └─ CommentMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ CommentService.java
   │  │           │  │  └─ CommentServiceImpl.java
   │  │           │  └─ vo
   │  │           │     └─ CommentVO.java
   │  │           ├─ config
   │  │           │  ├─ AppConfig.java
   │  │           │  └─ WebConfig.java
   │  │           ├─ dashboard
   │  │           │  ├─ controller
   │  │           │  │  ├─ DashboardController.java
   │  │           │  │  └─ WeatherApiController.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ IncomeResponseDto.java
   │  │           │  │  ├─ OutgoingResponseDto.java
   │  │           │  │  └─ SectorResponseDto.java
   │  │           │  ├─ mapper
   │  │           │  │  └─ DashboardMapper.java
   │  │           │  └─ service
   │  │           │     ├─ DashboardService.java
   │  │           │     ├─ DashboardServiceImpl.java
   │  │           │     ├─ WeatherService.java
   │  │           │     └─ WeatherServiceImpl.java
   │  │           ├─ error
   │  │           │  └─ CustomErrorController.java
   │  │           ├─ exception
   │  │           │  ├─ AbstractionException.java
   │  │           │  ├─ ApiWeatherException.java
   │  │           │  ├─ DashboardException.java
   │  │           │  ├─ DbCustomException.java
   │  │           │  ├─ InvalidFormatException.java
   │  │           │  ├─ MemberInsertFailedException.java
   │  │           │  ├─ MemberNotFoundException.java
   │  │           │  ├─ MemberUpdateFailedException.java
   │  │           │  ├─ SendFailedException.java
   │  │           │  └─ ValueCustomException.java
   │  │           ├─ handler
   │  │           │  └─ GlobalExceptionHandler.java
   │  │           ├─ income
   │  │           │  ├─ controller
   │  │           │  │  └─ IncomeController.java
   │  │           │  ├─ mapper
   │  │           │  │  └─ IncomeMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ IncomeService.java
   │  │           │  │  └─ IncomeServiceImpl.java
   │  │           │  └─ vo
   │  │           │     ├─ IncomeFormVO.java
   │  │           │     ├─ IncomeProductSectorWarehouseInventoryVO.java
   │  │           │     ├─ IncomeRequestVO.java
   │  │           │     └─ IncomeShipperProductSuppierVO.java
   │  │           ├─ interceptor
   │  │           │  └─ LoginCheckInterceptor.java
   │  │           ├─ inventory
   │  │           │  ├─ controller
   │  │           │  │  └─ InventoryController.java
   │  │           │  ├─ mapper
   │  │           │  │  └─ InventoryMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ InventoryService.java
   │  │           │  │  └─ InventoryServiceImpl.java
   │  │           │  └─ vo
   │  │           │     ├─ InventoryAdjustVO.java
   │  │           │     └─ InventoryStateVO.java
   │  │           ├─ member
   │  │           │  ├─ controller
   │  │           │  │  ├─ AuthController.java
   │  │           │  │  ├─ HomeController.java
   │  │           │  │  ├─ LoginController.java
   │  │           │  │  ├─ ModifyController.java
   │  │           │  │  └─ RegisterController.java
   │  │           │  ├─ dto
   │  │           │  │  ├─ MemberInsertRequest.java
   │  │           │  │  ├─ MemberUpdateRequest.java
   │  │           │  │  └─ ResponseDto.java
   │  │           │  ├─ mapper
   │  │           │  │  └─ MemberMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ AuthService.java
   │  │           │  │  ├─ AuthServiceImpl.java
   │  │           │  │  ├─ LoginService.java
   │  │           │  │  ├─ LoginServiceImpl.java
   │  │           │  │  ├─ ModifyService.java
   │  │           │  │  ├─ ModifyServiceImpl.java
   │  │           │  │  ├─ RegisterService.java
   │  │           │  │  └─ RegisterServiceImpl.java
   │  │           │  └─ vo
   │  │           │     └─ MemberVO.java
   │  │           ├─ outgoing
   │  │           │  ├─ controller
   │  │           │  │  └─ OutgoingController.java
   │  │           │  ├─ mapper
   │  │           │  │  └─ OutgoingMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ OutgoingService.java
   │  │           │  │  └─ OutgoingServiceImpl.java
   │  │           │  └─ vo
   │  │           │     ├─ OutgoingFormVO.java
   │  │           │     ├─ OutgoingRequestVO.java
   │  │           │     └─ OutgoingVO.java
   │  │           ├─ poi
   │  │           │  ├─ PoiService.java
   │  │           │  └─ PoiServiceImpl.java
   │  │           ├─ product
   │  │           │  ├─ mapper
   │  │           │  │  └─ ProductMapper.java
   │  │           │  └─ vo
   │  │           │     └─ ProductVO.java
   │  │           ├─ sector
   │  │           │  ├─ mapper
   │  │           │  │  └─ SectorMapper.java
   │  │           │  └─ vo
   │  │           │     └─ SectorVO.java
   │  │           ├─ shipper
   │  │           │  ├─ mapper
   │  │           │  │  └─ ShipperMapper.java
   │  │           │  ├─ service
   │  │           │  │  ├─ ShipperService.java
   │  │           │  │  └─ ShipperServiceImpl.java
   │  │           │  └─ vo
   │  │           │     └─ ShipperVO.java
   │  │           ├─ smtp
   │  │           │  └─ service
   │  │           │     ├─ SmtpService.java
   │  │           │     └─ SmtpServiceImpl.java
   │  │           ├─ util
   │  │           │  ├─ ErrorCode.java
   │  │           │  ├─ RandomGenerator.java
   │  │           │  └─ validator
   │  │           │     ├─ MemberValidator.java
   │  │           │     └─ Validator.java
   │  │           └─ warehouse
   │  │              └─ mapper
   │  │                 └─ WarehouseMapper.java
   │  └─ resources
   │     ├─ applicationContext.xml
   │     ├─ attach
   │     │  ├─ income.docx
   │     │  └─ outgoing.docx
   │     ├─ mapper
   │     │  ├─ boardMapper.xml
   │     │  ├─ branchMapper.xml
   │     │  ├─ commentMapper.xml
   │     │  ├─ dashboardMapper.xml
   │     │  ├─ incomeMapper.xml
   │     │  ├─ inventoryMapper.xml
   │     │  ├─ memberMapper.xml
   │     │  ├─ outgoingMapper.xml
   │     │  ├─ productMapper.xml
   │     │  ├─ sectorMapper.xml
   │     │  ├─ shipperMapper.xml
   │     │  └─ warehouseMapper.xml
   │     ├─ static
   │     │  ├─ assets
   │     │  │  └─ favicon.ico
   │     │  ├─ css
   │     │  │  ├─ dashboard
   │     │  │  │  └─ dashboard.css
   │     │  │  ├─ income
   │     │  │  │  └─ register.css
   │     │  │  ├─ inventory
   │     │  │  │  └─ inventory.css
   │     │  │  ├─ member
   │     │  │  │  ├─ login.css
   │     │  │  │  ├─ modify.css
   │     │  │  │  ├─ registration.css
   │     │  │  │  └─ verify.css
   │     │  │  ├─ outgoing
   │     │  │  │  ├─ outgoing.css
   │     │  │  │  └─ register.css
   │     │  │  └─ style.css
   │     │  ├─ img
   │     │  │  ├─ dashboard
   │     │  │  │  └─ weather
   │     │  │  │     ├─ clodyDay.png
   │     │  │  │     ├─ grayDay.png
   │     │  │  │     ├─ grayNight.png
   │     │  │  │     ├─ overcast.png
   │     │  │  │     ├─ rainny.png
   │     │  │  │     ├─ snow.png
   │     │  │  │     ├─ sunnyDay.png
   │     │  │  │     └─ sunnyNight1.png
   │     │  │  └─ member
   │     │  │     ├─ Icon_search.svg
   │     │  │     ├─ emart-logo-small.png
   │     │  │     ├─ emart-logo.png
   │     │  │     ├─ google.png
   │     │  │     ├─ kakao.png
   │     │  │     ├─ karina.png
   │     │  │     └─ naver.png
   │     │  └─ js
   │     │     ├─ dashboard
   │     │     │  └─ dashboard.js
   │     │     ├─ income
   │     │     │  └─ register.js
   │     │     ├─ inventory
   │     │     │  ├─ adjust.js
   │     │     │  └─ inventory.js
   │     │     ├─ member
   │     │     │  ├─ login.js
   │     │     │  ├─ modify.js
   │     │     │  ├─ registration.js
   │     │     │  └─ verify.js
   │     │     ├─ outgoing
   │     │     │  ├─ complete.js
   │     │     │  ├─ outgoing.js
   │     │     │  ├─ picking.js
   │     │     │  └─ register.js
   │     │     └─ scripts.js
   │     └─ templates
   │        ├─ board
   │        │  ├─ board.html
   │        │  ├─ create.html
   │        │  ├─ read.html
   │        │  └─ update.html
   │        ├─ dashboard
   │        │  └─ dashboard.html
   │        ├─ error
   │        │  └─ error.html
   │        ├─ income
   │        │  ├─ accumulationmanagement.html
   │        │  ├─ incomemanagement.html
   │        │  ├─ inspectionmanagement.html
   │        │  └─ register.html
   │        ├─ index.html
   │        ├─ inventory
   │        │  ├─ adjust.html
   │        │  └─ inventory.html
   │        ├─ layout.html
   │        ├─ member
   │        │  ├─ login.html
   │        │  ├─ modify.html
   │        │  ├─ registration.html
   │        │  └─ verify.html
   │        └─ outgoing
   │           ├─ complete.html
   │           ├─ outgoing.html
   │           ├─ picking.html
   │           └─ register.html
   └─ test
      └─ java
         └─ com
            └─ ssginc
               └─ ewms
                  └─ EWmsApplicationTests.java
```

## 🌐 프로젝트 페이지 미리보기

---

#### 📌 **로그인 화면**
![로그인 화면](https://github.com/user-attachments/assets/8c051c22-aa11-4080-998e-78baedf51201)

---

#### 📌 **회원가입 화면**
![회원가입 화면](https://github.com/user-attachments/assets/35503cd3-8327-4194-807c-9feaa54f35e1)

---

#### 📊 **대시보드**
![대시보드](https://github.com/user-attachments/assets/a8c8a7ca-aed6-4a38-885e-b35abbc33a99)

---

#### 📦 **재고 페이지**
![재고 페이지 1](https://github.com/user-attachments/assets/34b66afb-ccd5-4ca3-a187-91cf2f7ae5ab)
![재고 페이지 2](https://github.com/user-attachments/assets/d6e37d76-aa58-4a5c-97e4-d47e18152c58)

---

#### 🛠️ **입고 페이지**
![입고 페이지 1](https://github.com/user-attachments/assets/1dc542d8-dc67-4dff-999d-8a5fccdd8f6c)
![입고 페이지 2](https://github.com/user-attachments/assets/9f8f25c6-2e7e-4f7e-9cbe-a99f0cae3f51)
![입고 페이지 3](https://github.com/user-attachments/assets/e0f0fb12-8be2-403f-93f3-d2a2981c4384)

---

#### 🚚 **출고 페이지**
![출고 페이지 1](https://github.com/user-attachments/assets/f993b6c6-7099-4cf6-a289-566ab55168d9)
![출고 페이지 2](https://github.com/user-attachments/assets/9bd0dee7-8dd5-4163-8d86-f115ae093f98)
![출고 페이지 3](https://github.com/user-attachments/assets/7aa23f02-dccb-44ee-ac7a-c4638922e640)

---

#### 📝 **게시판**
![게시판](https://github.com/user-attachments/assets/bfc75cbb-2c96-498e-96bf-8870238d99eb)
