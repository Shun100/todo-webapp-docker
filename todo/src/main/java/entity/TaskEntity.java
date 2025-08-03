package entity;

import common.Const;
import dto.TaskDTO;

/**
 * タスク情報
 */
public class TaskEntity {
  private int taskId;
  private String taskName;
  private int statusId;
  private String description;

  public TaskEntity() {
    taskId = Const.TASK_ID_NOT_ASSIGNED;
    taskName = "";
    statusId = Const.INVALID_STATUS_ID;
    description = "";
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
  public int getStatusId() {
    return statusId;
  }
  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Entity -> DTO
   * @param none
   * @return TaskDTO taskDTO
   */
  public TaskDTO toDTO() {
    var taskDTO = new TaskDTO();

    taskDTO.setTaskId(taskId);
    taskDTO.setTaskName(taskName);
    taskDTO.setDescription(description);
    taskDTO.setStatusId(statusId);
    taskDTO.setStatusText(Const.STATUS_ID_TO_TEXT.get(statusId));

    return taskDTO;
  }

  /**
   * DTO -> Entity
   * @param TaskDTO taskDto
   * @return TaskEntity taskEntity
   */
  public static TaskEntity fromDTO(TaskDTO taskDTO) {
    var taskEntity = new TaskEntity();

    taskEntity.setTaskId(taskDTO.getTaskId());
    taskEntity.setTaskName((taskDTO.getTaskName()));
    taskEntity.setDescription(taskDTO.getDescription());
    taskEntity.setStatusId(taskDTO.getStatusId());

    return taskEntity;
  }
}
