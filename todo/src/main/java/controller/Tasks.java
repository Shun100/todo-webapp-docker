package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import common.Const;
import dto.TaskDTO;
import service.TaskService;

/**
 * タスク一覧画面用クラス
 */
@WebServlet("/tasks")
public class Tasks extends HttpServlet {
  private final String JSP = "/WEB-INF/jsp/Tasks.jsp";

  /**
   * タスク一覧画面 作成
   * @param HttpServletRequest request
   * @param HttpServletResponse response
   * @return none
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    // タスク一覧の取得
    var taskService = new TaskService();
    List<TaskDTO> taskDTOs = taskService.findAll();

    request.setAttribute("tasks", taskDTOs);
    request.setAttribute("statusIdToText", Const.STATUS_ID_TO_TEXT);
    request.setAttribute("statusIds", Const.STATUS_ID_TO_TEXT.keySet());

    var dispatcher = request.getRequestDispatcher(JSP);
    dispatcher.forward(request, response);
  }
}