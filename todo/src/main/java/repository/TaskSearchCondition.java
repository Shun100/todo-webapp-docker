package repository;

import java.util.List;

import java.sql.SQLException;
import java.sql.PreparedStatement;

public abstract class TaskSearchCondition {
  
  /**
   * SQL文生成
   * @param none
   * @return String sql
   */
  public abstract String createSql();

  /**
   * プレースホルダー設定
   * @param PreparedeStatement stmt
   * @return none
   * @throws SQLException
   */
  public abstract void setPlaceholders(PreparedStatement stmt) throws SQLException;


  /**
   * SQL文結合
   * @param StringBuilder mainSentence
   * @param StringBuilder[] whereSentences
   * @return StringBuilder concatenatedSentence
   */
  StringBuilder concat(StringBuilder mainSentence, List<StringBuilder> whereSentences) {
    var concatenatedSentence = new StringBuilder();
    concatenatedSentence.append(mainSentence);

    StringBuilder[] filteredWhereSentences = whereSentences.stream()
      .filter(whereSentence -> whereSentence.length() > 0)
      .toArray(StringBuilder[]::new);

    if (filteredWhereSentences.length > 0) {
      concatenatedSentence.append(" WHERE ");

      for (int i=0; i < filteredWhereSentences.length; i++) {
        var whereSentence = filteredWhereSentences[i];
        concatenatedSentence.append("( ");
        concatenatedSentence.append(whereSentence);
        concatenatedSentence.append(" )");

        if (i < filteredWhereSentences.length - 1) {
          concatenatedSentence.append(" AND ");
        }
      }
    }

    return concatenatedSentence;
  }
}
