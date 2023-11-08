package main.TaskManager.mainPage.service;

import main.TaskManager.mainPage.data.entity.Task;
import main.TaskManager.mainPage.data.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllEntities(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task getTaskById(Long Id) {
        return taskRepository.findById(Id).orElseThrow();
    }

    public Task updateTask(Long taskId, Task newTask) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setHeader(newTask.getHeader());
                    task.setDescription(newTask.getDescription());
                    task.setAttachment(newTask.getAttachment());
                    task.setUserId(newTask.getUserId());
                    return taskRepository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(taskId);
                    return taskRepository.save(newTask);
                });
    }


    public List<Task> deleteTask(long id, Long userId) {
        taskRepository.deleteById(id);
        return taskRepository.findByUserId(userId);
    }
}
