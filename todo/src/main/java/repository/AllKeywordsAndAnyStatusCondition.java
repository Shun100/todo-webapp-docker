package repository;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AllKeywordsAndAnyStatusCondition extends TaskSearchCondition {
  private final List<String> keywords;
  private final List<Integer> statusIds;

  public AllKeywordsAndAnyStatusCondition(List<String> keywords, List<Integer> statusIds) {
    this.keywords = keywords;
    this.statusIds = statusIds;
  }

  /**
   * SQL文作成
   * @param none
   * @return String sql
   */
  @Override
  public String createSql() {
    // SELECT句
    var sbMain = new StringBuilder(
      String.format("SELECT * FROM %s", RepositoryConst.TABLE));
    
    // WHERE句 - 検索ワード
    var sbKeywords = new StringBuilder();
    for (int i=0; i<keywords.size(); i++) {
      sbKeywords.append(String.format("( %s LIKE ? OR %s LIKE ? )",
        RepositoryConst.COL_TASK_NAME, RepositoryConst.COL_DESC));


      if (i < keywords.size() - 1) {
        sbKeywords.append(" AND ");
      }
    }

    // WHERE句 - ステータス
    var sbStatusIds = new StringBuilder(); 
    for (int i=0; i<statusIds.size(); i++) {
      sbStatusIds.append(String.format("%s = ?", RepositoryConst.COL_STATUS_ID));

      if (i < statusIds.size() - 1) {
        sbStatusIds.append(" OR ");
      }
    }

    // SQL文の結合
    var sbAll = concat(sbMain, List.of(sbKeywords, sbStatusIds));

    return sbAll.toString();
  }

  /**
   * プレースホルダー設定
   * @param PreparedStatement stmt
   * @return none
   * @throws SQLException
   */
  @Override
  public void setPlaceholders(PreparedStatement stmt) throws SQLException {
    int index = 1;

    // 検索ワード
    for (String keyword : keywords) {
      stmt.setString(index++, "%" + keyword + "%"); // タスク名用
      stmt.setString(index++, "%" + keyword + "%"); // 説明文用
    }

    // ステータス
    for (Integer statusId : statusIds) {
      stmt.setInt(index++, statusId);
    }

    System.out.println(stmt.toString());
  }
}
