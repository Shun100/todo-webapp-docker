package controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import service.TaskService;

/**
 * タスク詳細画面用クラス
 */
@WebServlet("/taskDetail")
public class TaskDetail extends HttpServlet {
  private final String JSP = "/WEB-INF/jsp/TaskDetail.jsp";

  /**
   * 詳細画面 作成
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    // リクエストからタスクIDを取得
    int taskId = Integer.parseInt(request.getParameter("taskId"));
    System.out.println("taskId = " + taskId);

    // タスクIDを元にタスク情報取得
    var taskService = new TaskService();
    taskService.findById(taskId).ifPresent(taskDTO -> request.setAttribute("task", taskDTO));

    // レスポンスを返す
    response.setCharacterEncoding("UTF-8");
    var dispatcher = request.getRequestDispatcher(JSP);
    dispatcher.forward(request, response);
  }
}
