<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="common/Header.jsp" />

<form action="${pageContext.request.contextPath}/newTask" method="POST">
  <!-- タスクID -->
  <input type="text" name="taskId" value="${task.taskId}" hidden>

  <div class="d-grid gap-3">
    <!-- タスク名 -->
    <div class="row">
      <label class="col-2" for="taskName">タスク名</label>
      <input class="col-6" type="text" id="taskName" name="taskName" value="${task.taskName}">
    </div>

    <!-- 説明 -->
    <div class="row">
      <label class="col-2" for="description">説明</label>
      <textarea class="col-10" id="description" name="description" rows="3">${task.description}</textarea>
    </div>

    <!-- ステータス -->
    <div class="row">
      <label class="col-2" for="status">ステータス</label>
      <select class="col-4" id="status" name="statusId">
        <option value="" hidden>選択してください</option>
        <c:forEach var="entry" items="${statusIdToText}">
          <c:choose>
            <%-- 新規作成 --%>
            <c:when test="${task == null}">
              <option value="${entry.key}">${entry.value}</option>
            </c:when>
            <%-- 更新 --%>
            <c:otherwise>
              <option value="${entry.key}" selected = "${entry.key == task.taskId}">${entry.value}</option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </select>
    <div>
  </div>

  <div class="hstack justify-content-end gap-2 mt-3">
    <!-- タスク作成 -->
    <c:choose>
      <%-- 新規作成 --%>
      <c:when test="${task == null}">
        <button class="btn btn-primary" type="submit">作成</button>
      </c:when>
      <%-- 更新 --%>
      <c:otherwise>
        <button class="btn btn-primary" type="submit">更新</button>
      </c:otherwise>
    </c:choose>

    <!-- タスク一覧に戻る -->
    <a href="tasks"><button class="btn btn-secondary" type="button">戻る</button></a>
  </div>
</form>

<jsp:include page="common/Footer.jsp" />
