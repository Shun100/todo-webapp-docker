#!/bin/bash

set -e  # エラー時にスクリプトを停止

# プロキシのURLは引数で受け取る
PROXY_URL="$1"
if [ -z "$PROXY_URL" ]; then
  echo "Usage: $0 <proxy_url>"
  echo "例: $0 http://proxy.example.com:8080"
  exit 1
fi

# apt用プロキシ設定
APT_PROXY_FILE="/etc/apt/apt.conf.d/95proxies"
sudo bash -c "cat <<EOF > $APT_PROXY_FILE
Acquire::http::Proxy \"$PROXY_URL\";
Acquire::https::Proxy \"$PROXY_URL\";
EOF"

# curl用プロキシ設定
BASHRC="$HOME/.bashrc"
grep -q "http_proxy" "$BASHRC" || {
  echo -e "\n# Proxy settings for curl" >> "$BASHRC"
  echo "export http_proxy=\"$PROXY_URL\"" >> "$BASHRC"
  echo "export https_proxy=\"$PROXY_URL\"" >> "$BASHRC"
}

# プロキシ設定を反映
# FYI: 本スクリプト終了後のセッションには反映されないため注意
source "$BASHRC"

# Docker Engineインストール
sudo apt update
curl -fsSL https://get.docker.com | sh
docker --version && echo " → Docker インストール成功" || echo " → Docker インストール失敗"

# sudoなしでdockerを実行できるよう権限を設定
sudo usermod -aG docker $USER

# Docker用プロキシ設定
DOCKER_PROXY_DIR="/etc/systemd/system/docker.service.d"
DOCKER_PROXY_CONF="$DOCKER_PROXY_DIR/http-proxy.conf"

sudo mkdir -p "$DOCKER_PROXY_DIR"
sudo bash -c "cat <<EOF > $DOCKER_PROXY_CONF
[Service]
Environment=\"HTTP_PROXY=$PROXY_URL\"
Environment=\"HTTPS_PROXY=$PROXY_URL\"
EOF"

# Docker用プロキシ設定を反映（Docker Daemonの再起動）
sudo systemctl daemon-reexec
sudo systemctl daemon-reload
sudo systemctl restart docker

# xmlstarlet（xml編集ツール）のインストール
sudo apt install xmlstarlet

# Maven用プロキシ設定
SETTINGS_FILE="./todo/settings.xml"
PROXY_HOST=$(echo "$PROXY_URL" | sed -E 's|https?://([^:/]+).*|\1|')
PROXY_PORT=$(echo "$PROXY_URL" | sed -E 's|.*:([0-9]+).*|\1|')

xmlstarlet ed -L -N x="http://maven.apache.org/SETTINGS/1.0.0" \
  -u "/x:settings/x:proxies/x:proxy/x:active" -v "true" "$SETTINGS_FILE"

xmlstarlet ed -L -N x="http://maven.apache.org/SETTINGS/1.0.0" \
  -u "/x:settings/x:proxies/x:proxy/x:host" -v "$PROXY_HOST" "$SETTINGS_FILE"

xmlstarlet ed -L -N x="http://maven.apache.org/SETTINGS/1.0.0" \
  -u "/x:settings/x:proxies/x:proxy/x:port" -v "$PROXY_PORT" "$SETTINGS_FILE"

echo "Finished"
