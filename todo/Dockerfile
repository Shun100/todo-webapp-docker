FROM maven:3.9-eclipse-temurin-17

WORKDIR /todo

# プロキシの設定ファイルをコピーする
COPY settings.xml /root/.m2/settings.xml

# MavenのSSL検証を無効化
ENV MAVEN_OPTS="-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true"

# ホストの./todo配下のファイル・フォルダを全てコンテナの/todo配下にコピーする
COPY . .
