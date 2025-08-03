package controller;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

/**
 * トップ画面用クラス
 */
@WebServlet("/top")
public class Top extends HttpServlet {
  private final String JSP = "/WEB-INF/jsp/Top.jsp";

  /**
   * トップ画面 作成
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    
    var dispatcher = request.getRequestDispatcher(JSP);
    dispatcher.forward(request, response);
  }
}
