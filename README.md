# Word Sketch 서버 Repository

> TEAM PANTS

<!-- ## Devcontainer로 빠르게 환경 구축하기 (추가 예정)
[![Open in Dev Containers](https://img.shields.io/static/v1?label=Dev%20Containers&message=Open&color=blue&logo=visualstudiocode)](https://vscode.dev/redirect?url=vscode://ms-vscode-remote.remote-containers/cloneInVolume?url=https://github.com/PANTS-GOORM/BackEnd) -->

## 프로젝트 실행하는 법

### 1. Repository를 Clone

```bash
git clone https://github.com/PANTS-GOORM/BackEnd.git
```

### 2. secrets 파일들을 submodule로 불러오기

```bash
git submodule init
git submodule update

# if you want to upate secrets with current repository
git submodule update --remote
```

### 3. 로컬 또는 컨테이너 환경에서 PostgreSQL 실행하기

> 로컬 환경에서 실행할 경우

1. 로컬에서 [PostgreSQL](https://www.postgresql.org/) 16.2 버전을 설치합니다.
2. 설치 후, psql 쉘에서 커넥션에 사용할 유저와 데이터베이스를 생성하고 유저에게 권한을 설정합니다. ([관련 포스트 링크](https://jiurinie.tistory.com/60))

> 컨테이너 환경에서 실행할 경우

1. [Docker Desktop](https://www.docker.com/products/docker-desktop/)을 설치합니다.
2. CLI 환경에서 명령어를 통해 컨테이너를 실행합니다.
```bash
docker run --name WordSketch-DB -e POSTGRES_PASSWORD={원하는 root 패스워드} -p 5432:5432 -d postgres:16.2
```
3. 실행된 컨테이너의 쉘에 접속하여 커넥션에 사용할 유저와 데이터베이스를 생성하고 유저에게 권한을 설정합니다.
```bash
# DB 컨터이너 쉘 접속
docker exec -it WordSketch-DB /bin/bash
# postgres 계정으로 전환
su - postgres

# 유저 생성

# createuser {secrets에 해당하는 username}
createuser pantsadmin

# 데이터베이스 생성
createdb wordsketch

# psql 쉘 접속
psql

# 계정 비밀번호 설정 및 권한 부여

# alter user {secrets에 해당하는 username} with encrypted password '{secrets에 해당하는 password}';
alter user pantsadmin with encrypted password 'ddongpants';
# grant all privileges on database wordsketch to {secrets에 해당하는 username};
grant all privileges on database wordsketch to pantsadmin;
```
