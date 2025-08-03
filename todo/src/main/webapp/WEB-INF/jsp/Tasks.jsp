<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="common/Header.jsp" />

<div>
  <!-- タスク一覧 -->
  <div class="table-responsive" style="max-height: 50%; overflow-y: auto;">
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>タスク名</th>
          <th>ステータス</th>
        </tr>
      </thead>
      <tbody>
        <%-- タスクの数だけ繰り返す --%>
        <c:forEach var="task" items="${tasks}">
          <tr>
            <td><a href="${pageContext.request.contextPath}/taskDetail?taskId=${task.taskId}">#${task.taskId}</a></td>
            <td><a href="${pageContext.request.contextPath}/taskDetail?taskId=${task.taskId}">${task.taskName}</a></td>
            <td>${task.statusText}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- 検索 -->
  <div class="d-flex justify-content-end">
    <a href="${pageContext.request.contextPath}/newTask"><button class="btn btn-primary">新規作成</button></a>
  </div>
</div>

<div class="mt-5 p-3 border border-secondary border-2 rounded">
  <p>検索</p>
  <form action="${pageContext.request.contextPath}/tasks/search" method="GET">
    <div class="d-grid gap-3"> 
      
      <!-- 検索条件: キーワード -->
      <div class="row align-items-center">
        <label class="col-2" for="keywords">キーワード</label>
        <input class="col-6" type="text" id="keywords" name="keywords" value="${keywords}">

        <!-- 検索方式 -->
        <div class="col-4 d-flex gap-2 ps-4">
          <c:choose>
            <c:when test="${isSearchByAnd eq 'false'}">
              <input type="radio" class="btn-check" id="searchByAnd" value="true" name="isSearchByAnd"></input>
              <label for="searchByAnd" class="btn btn-outline-primary">AND</label>

              <input type="radio" class="btn-check" id="searchByOr" value="false" name="isSearchByAnd" checked></input>
              <label for="searchByOr" class="btn btn-outline-primary">OR</label>
            </c:when>
            <c:otherwise>
              <input type="radio" class="btn-check" id="searchByAnd" value="true" name="isSearchByAnd" checked></input>
              <label for="searchByAnd" class="btn btn-outline-primary">AND</label>

              <input type="radio" class="btn-check" id="searchByOr" value="false" name="isSearchByAnd"></input>
              <label for="searchByOr" class="btn btn-outline-primary">OR</label>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <!-- 検索条件: ステータス -->
      <div class="row align-items-center">
        <span class="col-2">ステータス</span>
        <div class="d-flex gap-2 col-6">
          <%-- ステータスの数だけ繰り返す --%>
          <c:forEach var="entry" items="${statusIdToText}">
            <c:choose>
              <c:when test="${statusIds.contains(entry.key)}">
                <input type="checkbox" class="btn-check" id="statusCondition_${entry.key}" name="statusIds" value="${entry.key}" checked>
                <label class="btn btn-outline-primary" for="statusCondition_${entry.key}">${entry.value}</label>
              </c:when>
              <c:otherwise>
                <input type="checkbox" class="btn-check" id="statusCondition_${entry.key}" name="statusIds" value="${entry.key}">
                <label class="btn btn-outline-primary" for="statusCondition_${entry.key}">${entry.value}</label>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>
    </div>

    <!-- 検索ボタン -->
    <div class="d-flex justify-content-end">
      <button type="submit" class="btn btn-primary">検索</button>
    </div>
  </form>
</div>

<jsp:include page="common/Footer.jsp" />
