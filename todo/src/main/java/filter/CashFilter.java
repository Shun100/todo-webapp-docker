package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

/**
 * キャッシュ無効化のためのFilterクラス
 * Filterクラスでは全てのリクエスト・レスポンスが通過するようにして、共通的な処理を実装する。
 */

@WebFilter("/*")
public class CashFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {

      HttpServletResponse httpRes = (HttpServletResponse) res;

      // キャッシュ無効設定
      httpRes.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      httpRes.setHeader("Pragme", "no-cache");
      httpRes.setDateHeader("Expires", 0);
      
      chain.doFilter(req, res);
    }
}
