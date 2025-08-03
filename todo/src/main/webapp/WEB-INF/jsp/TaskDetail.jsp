<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="common/Header.jsp" />

<div>
  <div class="d-flex gap-3">
    <h2>#${task.taskId}</h2>
    <h2>${task.taskName}</h2>
  </div>
  <p>${task.statusText}</p>
  <p>${task.description}</p>
</div>

<form action="${pageContext.request.contextPath}/taskDetail/delete" method="POST">
  <input value="${task.taskId}" name="taskId" hidden>

  <div class="d-flex justify-content-end gap-2">
    <a href="${pageContext.request.contextPath}/newTask?taskId=${task.taskId}"><button class="btn btn-primary" type="button">編集</button></a>
    <button class="btn btn-secondary" type="submit">削除</button>
    <a href="${pageContext.request.contextPath}/tasks"><button class="btn btn-secondary" type="button">戻る</button></a>
  </div>
</form>

<jsp:include page="common/Footer.jsp" />