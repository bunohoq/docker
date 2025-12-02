# 📌 Docker 기반 스프링부트 스케일 아웃 & Nginx 로드밸런싱 실습

스프링부트 애플리케이션을 Docker Compose 기반으로 컨테이너 1대 → 3대로 확장하고, Nginx를 통해 라운드로빈 방식의 로드밸런싱을 구현한 실습 프로젝트입니다.

이 구성은 하나의 애플리케이션을 멀티 인스턴스(수평 확장, Scale-out) 방식으로 배포하고, Nginx 로드밸런서를 사용해 트래픽을 균등하게 분산하는 전형적인 단일 서버 기반 로드밸런싱 구조를 다룹니다.

# 📁 프로젝트 구조
```
src/
 └─ main/
     └─ java/com/test/docker/controller/MainController.java
     └─ resources/templates/index.html

Dockerfile
docker-compose.yml
nginx.conf
README.md
```
---

# 🧩 구현한 기능 요약

Spring Boot 컨테이너 이미지 빌드

Docker Compose로 애플리케이션 컨테이너 1 → 3대로 수평 확장(scale-out)

nginx.conf 기반 Nginx 로드밸런서 구성

라운드 로빈 방식으로 요청을 A→B→C 순서로 순환 처리

index.html에서 요청이 어느 컨테이너에서 응답됐는지 확인 가능(hostname 출력)

# 🚀 실행 방법
### 1️⃣ 기존 컨테이너 종료
```
docker-compose down
```
### 2️⃣ 앱 컨테이너 3개로 스케일아웃하여 실행
```
docker-compose up -d --scale app=3
```
### 3️⃣ 실행된 컨테이너 확인
```
docker ps -a
```
### 4️⃣ 종료 시
```
docker-compose down
```

---
# 🔧 주요 파일 설명
1. Dockerfile

Spring Boot 애플리케이션이 동작할 컨테이너 이미지 정의.

2. docker-compose.yml

app 컨테이너를 원하는 개수만큼 쉽게 확장

nginx 컨테이너와 연결

네트워크/포트 설정 포함

3. nginx.conf

로드밸런싱 전략을 정의한 핵심 설정 파일.

예시:
```
upstream app_servers {
    server app:8080;
    server app_1:8080;
    server app_2:8080;
}

server {
    listen 80;

    location / {
        proxy_pass http://app_servers;
    }
}
```

4. MainController.java

컨테이너 hostname을 반환해 어떤 인스턴스가 응답했는지 확인하는 역할.

5. index.html

브라우저에서 접속하면 어떤 컨테이너가 응답했는지 표시.

# ⚙️ 스케일 아웃(Scale-out)란?

기존 서버의 성능을 높이는 Scale-up 방식과 달리,
동일한 서버(컨테이너)를 여러 대 추가해 성능과 안정성을 높이는 방식이다.

예시:

인스턴스	포트	역할
서버 A	8080	요청 처리
서버 B	8080	요청 처리
서버 C	8080	요청 처리

여기서 Nginx가 세 개의 서버 중 하나로 요청을 분배한다.

# 🔀 로드밸런싱 구조
# ❌ 로드밸런서가 없는 구조

클라이언트 요청이 어느 서버로 갈지 보장 없음 → 직접 관리해야 함
```
Client → Server A
Client → Server B
Client → Server C
```

✔️ Nginx 로드밸런서가 있는 구조
```
Client → NGINX → Server A
                   → Server B
                   → Server C

```

Nginx가 요청을 균등하게 분산(라운드로빈 방식).

# 📌 이번 실습의 핵심 포인트

Docker Compose의 --scale 옵션을 통해 손쉽게 애플리케이션 인스턴스 확장

컨테이너 환경에서 로드밼런서(Nginx)가 어떻게 동작하는지 실습

단일 서버 환경에서도 간단한 형태의 트래픽 분산 구조 구성 가능

실제 서비스 아키텍처의 기본 개념(멀티 인스턴스 운영 + LB)을 직접 구현하는 경험 제공

# 📝 요약

이 프로젝트는 Docker 기반에서 다중 인스턴스 운영과 트래픽 분산 구조를 실습하기 위한 기본 아키텍처를 구축한 예제입니다.
스프링부트 → 컨테이너화 → 스케일아웃 → Nginx 로드밸런싱까지의 전체 흐름을 손쉽게 경험할 수 있습니다.
