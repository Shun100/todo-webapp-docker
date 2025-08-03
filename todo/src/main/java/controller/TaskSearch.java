package controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import common.Const;
import service.TaskService;

/**
 * タスク検索用クラス
 */
@WebServlet("/tasks/search")
public class TaskSearch extends HttpServlet {
  private final String JSP = "/WEB-INF/jsp/Tasks.jsp";

  /**
   * タスク検索
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    // パラメータ読取
    List<String> keywords = readKeywords(request);
    boolean isSearchByAnd = Boolean.parseBoolean(request.getParameter("isSearchByAnd"));
    List<Integer> statusIds = readStatusIds(request);

    System.out.println("TaskSearch doPost");
    System.out.println(keywords.toString());
    System.out.println(isSearchByAnd);
    System.out.println(statusIds.toString());

    // 検索
    var taskService = new TaskService();
    var taskDTOs = taskService.search(keywords, isSearchByAnd, statusIds);

    // 検索結果
    request.setAttribute("tasks", taskDTOs);
    request.setAttribute("statusIdToText", Const.STATUS_ID_TO_TEXT);

    // 検索条件(ページのリロードで消えるため)
    request.setAttribute("keywords", request.getParameter("keywords"));
    request.setAttribute("isSearchByAnd", isSearchByAnd);
    request.setAttribute("statusIds", statusIds);

    var dispatcher = request.getRequestDispatcher(JSP);
    dispatcher.forward(request, response);
  }

  /**
   * キーワード読取
   * @param HttpServletRequest request
   * @return List<String> keywords
   */
  private List<String> readKeywords(HttpServletRequest request) {
    String keywordsBeforeSplit = request.getParameter("keywords");

    if (keywordsBeforeSplit.isBlank()) {
      // 未入力の場合
      return new ArrayList<>();

    } else {
      // 何かしら入力がある場合
      return Arrays.asList(keywordsBeforeSplit.split(" "));
    }
  }

  /**
   * ステータスID読取
   * @param HttpServletRequest request
   * @return List<Integer> statusIds
   */
  private List<Integer> readStatusIds(HttpServletRequest request) {
    String[] statusIdsAsArray = request.getParameterValues("statusIds");

    if (statusIdsAsArray == null) {
      // 1つもチェックが付いていない場合
      return new ArrayList<>();

    } else {
      // 1つでもチェックが付いている場合
      return Arrays.asList(statusIdsAsArray).stream()
        .map(Integer::parseInt)
        .toList();
    }
  }
}