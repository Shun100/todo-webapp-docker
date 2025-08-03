package controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import service.TaskService;

/**
 * タスク作成画面用クラス
 */
@WebServlet("/taskDetail/delete")
public class TaskDelete extends HttpServlet {
  /**
   * 削除
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    int taskId = Integer.parseInt(request.getParameter("taskId"));

    // 削除
    var taskService = new TaskService();
    taskService.delete(taskId);
    
    // タスク一覧画面にリダイレクト
    response.sendRedirect(request.getContextPath() + "/tasks");
  }
}