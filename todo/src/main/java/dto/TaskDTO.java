package dto;

import jakarta.servlet.http.HttpServletRequest;

import common.Const;
import entity.TaskEntity;

public class TaskDTO {
  private int taskId;
  private String taskName;
  private String description;
  private int statusId;
  private String statusText;

  public TaskDTO() {
    setTaskId(Const.TASK_ID_NOT_ASSIGNED);
    setTaskName("");
    setDescription("");
    setStatusId(Const.INVALID_STATUS_ID);
    setStatusText("");
  }

  public int getTaskId() {
    return taskId;
  }
  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }
  public String getTaskName() {
    return taskName;
  }
  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public int getStatusId() {
    return statusId;
  }
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }
  public String getStatusText() {
    return statusText;
  }
  public void setStatusText(String statusText) {
    this.statusText = statusText;
  }

  /**
   * Request Request Object -> DTO
   * @param HttpServletRequest request
   * @return TaskDTO dto
   */
  public static TaskDTO fromRequest(HttpServletRequest request) {
    // 初期化
    TaskDTO dto = new TaskDTO();

    // タスクID読み取り
    String taskIdStr = request.getParameter("taskId");
    // taskIdがnullの場合は新規登録とみなす。エラーにはしない。
    if (!taskIdStr.isEmpty()) {
      dto.setTaskId(Integer.parseInt(taskIdStr));
    }

    // タスク名読み取り
    String taskName = request.getParameter("taskName");   
    dto.setTaskName(taskName);

    // 説明文読み取り
    String description = request.getParameter("description");
    dto.setDescription(description);

    // ステータス読み取り
    int statusId = Integer.parseInt(request.getParameter("statusId"));
    dto.setStatusId(statusId);
    dto.setStatusText(Const.STATUS_ID_TO_TEXT.get(statusId));
    
    return dto;
  }
}
