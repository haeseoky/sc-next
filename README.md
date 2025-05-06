# SC-Next 프로젝트

SC-Next는 Spring Boot 3.4.3 기반의 멀티 모듈 프로젝트입니다. Java 23으로 개발된 이 프로젝트는 관리자 시스템(Admin), API 서버, 배치 처리 시스템을 포함한 확장 가능한 아키텍처를 제공합니다.

## 프로젝트 구조

프로젝트는, 다음과 같이 구성되어 있습니다:

```
sc-next/
├── admin/       # 관리자 시스템 모듈
├── api/         # API 서버 모듈
├── batch/       # 배치 처리 모듈
└── shared/      # 공유 라이브러리 및 유틸리티 모듈
```

## 기술 스택

- **Java**: Java 23
- **Framework**: Spring Boot 3.4.3
- **Build Tool**: Gradle
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **웹**: Spring WebMVC, Spring WebFlux
- **개발 도구**: Lombok, Spring DevTools
- **테스트**: JUnit, Reactor Test

## 요구 사항

- Java 23 이상
- Gradle 7 이상

## 시작하기

### 프로젝트 클론

```bash
git clone https://github.com/haeseoky/sc-next.git
cd sc-next
```

### 빌드

```bash
./gradlew clean build
```

### 모듈 실행

각 모듈은 독립적으로 실행할 수 있습니다:

```bash
# Admin 모듈 실행
./gradlew admin:bootRun

# API 모듈 실행
./gradlew api:bootRun

# Batch 모듈 실행
./gradlew batch:bootRun
```

## 모듈 설명

### Admin 모듈

관리자 기능을 제공하는 웹 애플리케이션입니다.

### API 모듈

RESTful API를 제공하는 웹 서버입니다. Swagger UI를 통한 API 문서화를 지원합니다.

### Batch 모듈

정기적인 작업 및 데이터 처리를 위한 배치 프로세싱 시스템입니다.

### Shared 모듈

여러 모듈에서 공유하는 공통 코드, 유틸리티, 도메인 모델 등을 포함합니다.

## API 문서

API 문서는 Swagger UI를 통해 접근할 수 있습니다:

```
http://localhost:8080/swagger-ui.html
```

## 라이선스

이 프로젝트는 [LICENSE 파일]의 조건에 따라 라이선스가 부여됩니다.

## 기여하기

기여는 언제나 환영합니다! Pull Request를 제출하기 전에 Issue를 먼저 생성해 주세요.
