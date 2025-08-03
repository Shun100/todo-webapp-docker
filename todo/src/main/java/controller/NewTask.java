package controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import common.Const;
import dto.TaskDTO;
import service.TaskService;

/**
 * タスク作成画面用クラス
 */
@WebServlet("/newTask")
public class NewTask extends HttpServlet {
  private final String JSP = "/WEB-INF/jsp/NewTask.jsp";

  /**
   * タスク作成画面 作成
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String taskIdStr = request.getParameter("taskId");

    if (taskIdStr != null) {
      // 新規登録ではなく更新の場合
      int taskId = Integer.parseInt(taskIdStr);

      // タスク情報を取得
      var taskService = new TaskService();
      taskService.findById(taskId).ifPresent(taskDTO -> request.setAttribute("task", taskDTO));
    }

    // 新規登録・更新 共通
    request.setAttribute("statusIdToText", Const.STATUS_ID_TO_TEXT);

    var dispatcher = request.getRequestDispatcher(JSP);
    dispatcher.forward(request, response);
  }

  /**
   * 新規登録
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    var taskDTO = TaskDTO.fromRequest(request);

    // 登録
    var taskService = new TaskService();
    taskService.register(taskDTO);
    
    // タスク一覧画面にリダイレクト
    response.sendRedirect(request.getContextPath() + "/tasks");
  }
}