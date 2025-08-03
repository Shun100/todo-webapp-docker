package service;

import java.util.List;
import java.util.Optional;

import dto.TaskDTO;
import entity.TaskEntity;
import repository.AllKeywordsAndAnyStatusCondition;
import repository.AnyKeywordsAndAnyStatusCondition;
import repository.TaskRepository;
import repository.TaskSearchCondition;

/**
 * タスク検索クラス
 */
public class TaskService {
  // DAO
  private final TaskRepository taskRepository = new TaskRepository();

  /**
   * タスク登録
   * @param TaskDTO dto
   * @return boolean isOk - 登録成否
   */
  public void register(TaskDTO dto) {
    // Entityの作成
    var taskEntity = new TaskEntity();
    taskEntity.setTaskId(dto.getTaskId());
    taskEntity.setTaskName(dto.getTaskName());
    taskEntity.setDescription(dto.getDescription());
    taskEntity.setStatusId(dto.getStatusId());

    // 登録
    taskRepository.upsert(taskEntity);
  }

  /**
   * IDを元にタスクを取得する
   * @param int taskId - タスクID
   * @return Optional<TaskEntity> optionalTaskEntity - タスク情報
   */
  public Optional<TaskDTO> findById(int taskId) {
    var optionalTaskEntity = taskRepository.findById(taskId);

    if (optionalTaskEntity.isPresent()) {
      return Optional.of(optionalTaskEntity.get().toDTO());
    } else {
      return Optional.empty();
    }
  }

  /**
   * 全件取得
   * @param none
   * @return List<TaskDTO> taskDTOs
   */
  public List<TaskDTO> findAll() {
    List<TaskEntity> taskEntities = taskRepository.findAll();
    
    return taskEntities.stream()
      .map(entity -> entity.toDTO())
      .toList();
  }

  /**
   * 削除
   * @param keywords
   * @param isAndSearch
   * @param statusList
   * @return
   */
  public void delete(int taskId) {
    taskRepository.delete(taskId);
  }

  /**
   * 検索条件に合致するタスク一覧を取得する
   * @param List<String> keywords - キーワード一覧(AND検索)
   * @param boolean isAndSearch - true: AND検索, false: OR検索
   * @param int status - タスクのステータス
   * @return List<TaskEntity> taskEntities - タスク一覧
   */
  public List<TaskDTO> search(
    List<String> keywords,
    boolean isAndSearch,
    List<Integer> statusIds) {
    
    // 検索条件オブジェクト
    TaskSearchCondition condition;

    if (isAndSearch) {
      condition = new AllKeywordsAndAnyStatusCondition(keywords, statusIds);
    } else {
      // taskEntities = taskRepository.searchByOrCondition(keywords, statusIds);
      condition = new AnyKeywordsAndAnyStatusCondition(keywords, statusIds);
    }

    // 検索
    List<TaskEntity> taskEntities = taskRepository.findByCondition(condition); 

    // 検索結果をDTOに変換
    return taskEntities.stream()
      .map(taskEntity -> taskEntity.toDTO())
      .toList();
  }
}