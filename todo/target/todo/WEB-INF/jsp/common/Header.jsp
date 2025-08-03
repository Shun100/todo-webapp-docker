<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タスク管理</title>
    <!-- Sanitizer -->
    <link rel="stylesheet" href="https://unpkg.com/sanitize.css">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  </head>
  <body>
  
    <!-- ナビゲーションバー -->
    <nav class="navbar navbar-expand-lg bg-body-tertiary bg-dark-subtle">
      <div class="container-fluid ps-3">
        <span class="navbar-brand">TODO</span>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
          <div class="navbar-nav">
            <a class="nav-link" href="${pageContext.request.contextPath}/top">HOME</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/tasks">タスク一覧</a>
          </div>
        </div>
      </div>
    </nav>

    <div class="mt-3 ms-3" style="width: 30%">
