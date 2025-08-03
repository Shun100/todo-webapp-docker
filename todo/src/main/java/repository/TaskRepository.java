package repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.Const;

import java.time.Instant;

import entity.TaskEntity;

public class TaskRepository {
  /**
   * static initializer
   * DBの初期化（テーブル作成）を行う
   */
  static {
    // JDBCドライバのクラスをロード
    try {
      Class.forName(RepositoryConst.DRIVER);

      // テーブル作成
      var taskRepository = new TaskRepository();
      try (
        Connection conn = taskRepository.getConnection();
        Statement stmt = conn.createStatement()) {

          String sql = String.format("""
            CREATE TABLE IF NOT EXISTS %s (
            %s INT AUTO_INCREMENT PRIMARY KEY,
            %s VARCHAR(32),
            %s INT,
            %s VARCHAR(128),
            %s TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """,
            RepositoryConst.TABLE,
            RepositoryConst.COL_TASK_ID,
            RepositoryConst.COL_TASK_NAME,
            RepositoryConst.COL_STATUS_ID,
            RepositoryConst.COL_DESC,
            RepositoryConst.COL_CREATED_AT);

          stmt.executeUpdate(sql);

      } catch (SQLException e) {
        e.printStackTrace();
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * DB接続
   * @param none
   * @return Connection conn
   * @throws SQLException
   */
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
      RepositoryConst.DB_URL, RepositoryConst.DB_USER, RepositoryConst.DB_PASSWORD);
  }

  /**
   * タスク登録
   * @param TaskEntity taskEntity
   * @return boolean is_succeeded
   */
  public boolean upsert(TaskEntity taskEntity) {
    if (taskEntity.getTaskId() == Const.TASK_ID_NOT_ASSIGNED) {
      return insert(taskEntity);  // 新規登録
    } else {
      return update(taskEntity);  // 更新
    }
  }

  /**
   * 新規登録
   * @param TaksEntity taskEntity
   * @return boolean is_succeeded
   */
  private boolean insert(TaskEntity taskEntity) {
    String sql = String.format(
      "INSERT INTO %s (%s, %s, %s, %s) VALUES(?, ?, ?, ?)",
      RepositoryConst.TABLE,
      RepositoryConst.COL_TASK_NAME,
      RepositoryConst.COL_STATUS_ID,
      RepositoryConst.COL_DESC,
      RepositoryConst.COL_CREATED_AT);
    
    try (
      Connection conn = getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, taskEntity.getTaskName());
        stmt.setInt(2, taskEntity.getStatusId());
        stmt.setString(3, taskEntity.getDescription());
        stmt.setTimestamp(4, Timestamp.from(Instant.now()));
        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 更新
   * @param TaskEntity taskEintity
   * @return boolean is_succeeded
   */
  private boolean update(TaskEntity taskEntity) {
    String sql = String.format(
      "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
      RepositoryConst.TABLE,
      RepositoryConst.COL_TASK_NAME,
      RepositoryConst.COL_STATUS_ID,
      RepositoryConst.COL_DESC,
      RepositoryConst.COL_TASK_ID);

    try (
      Connection conn = getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, taskEntity.getTaskName());
        stmt.setInt(2, taskEntity.getStatusId());
        stmt.setString(3, taskEntity.getDescription());
        stmt.setInt(4, taskEntity.getTaskId());
        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * タスク削除
   * @param int id
   * @return boolean is_succeeded
   */
  public boolean delete(int id) {
    String sql = String.format("DELETE FROM %s WHERE %s = ?",
      RepositoryConst.TABLE, RepositoryConst.COL_TASK_ID);

    try (
      Connection conn = getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
      
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
  }

  /**
   * タスク取得
   * @param int id
   * @return Optional<TaskEntity> optionalTaskEntity
   */
  public Optional<TaskEntity> findById(int id) {
    String sql = String.format("SELECT * FROM %s WHERE %s = ?",
      RepositoryConst.TABLE, RepositoryConst.COL_TASK_ID);

    try (
      Connection conn = getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            var taskEntity = new TaskEntity();
            taskEntity.setTaskId(id);
            taskEntity.setTaskName(rs.getString(RepositoryConst.COL_TASK_NAME));
            taskEntity.setStatusId(rs.getInt(RepositoryConst.COL_STATUS_ID));
            taskEntity.setDescription(rs.getString(RepositoryConst.COL_DESC));
            return Optional.of(taskEntity);
          
          } else {
            return Optional.empty();
          }

        } catch (SQLException e) {
          e.printStackTrace();
          return Optional.empty();
        }

    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * 全件取得
   * @param none
   * @return List<TaskEntity> taskEntities
   */
  public List<TaskEntity> findAll() {
    List<TaskEntity> taskEntities = new ArrayList<>();
    String sql = String.format("SELECT * FROM %s", RepositoryConst.TABLE);

    try (
      Connection conn = getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql)) {
        
        while(rs.next()) {
          var taskEntity = new TaskEntity();
          taskEntity.setTaskId(rs.getInt(RepositoryConst.COL_TASK_ID));
          taskEntity.setTaskName(rs.getString(RepositoryConst.COL_TASK_NAME));
          taskEntity.setStatusId(rs.getInt(RepositoryConst.COL_STATUS_ID));
          taskEntity.setDescription(rs.getString(RepositoryConst.COL_DESC));
          taskEntities.add(taskEntity);
        }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return taskEntities;
  }

  /**
   * 検索
   * @param TaskSearchCondition condition - 検索条件
   * @return List<TaskEntity> taskEntities - 検索結果
   */
  public List<TaskEntity> findByCondition(TaskSearchCondition condition) {
    List<TaskEntity> taskEntities = new ArrayList<>();
    String sql = condition.createSql();

    try (
      Connection conn =  getConnection();
      PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        // プレースホルダーの設定
        condition.setPlaceholders(stmt);

        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            var taskEntity = new TaskEntity();
            taskEntity.setTaskId(rs.getInt(RepositoryConst.COL_TASK_ID));
            taskEntity.setTaskName(rs.getString(RepositoryConst.COL_TASK_NAME));
            taskEntity.setStatusId(rs.getInt(RepositoryConst.COL_STATUS_ID));
            taskEntity.setDescription(rs.getString(RepositoryConst.COL_DESC));
            taskEntities.add(taskEntity);
          }

        } catch (SQLException e) {
          e.printStackTrace();
        }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return taskEntities;
  }
}