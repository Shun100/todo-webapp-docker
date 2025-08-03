package common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 定数クラス
 */
public class Const {

  // タスクのステータスIDと表示テキストの対応
  public static final Map<Integer, String> STATUS_ID_TO_TEXT = new LinkedHashMap<>();

  public static final int TASK_ID_NOT_ASSIGNED = 0;
  public static final int INVALID_STATUS_ID = -1;

  static {
    STATUS_ID_TO_TEXT.put(0, "TODO");
    STATUS_ID_TO_TEXT.put(1, "DOING");
    STATUS_ID_TO_TEXT.put(2, "DONE");
  }
}
