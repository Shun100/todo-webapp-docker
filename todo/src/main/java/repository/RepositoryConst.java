package repository;

class RepositoryConst {
  // DB接続情報
  static final String DB_URL = "jdbc:h2:tcp://h2db:1521//opt/h2-data/mydb;DB_CLOSE_DELAY=-1";
  static final String DB_USER = "sa";
  static final String DB_PASSWORD = "";
  static final String DRIVER = "org.h2.Driver";

  // テーブル情報
  static final String TABLE = "tasks";
  static final String COL_TASK_ID = "id";
  static final String COL_TASK_NAME = "name";
  static final String COL_STATUS_ID = "status_id";
  static final String COL_DESC = "description";
  static final String COL_CREATED_AT = "created_at";
}
