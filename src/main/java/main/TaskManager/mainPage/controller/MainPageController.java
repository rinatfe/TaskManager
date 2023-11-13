package main.TaskManager.mainPage.controller;

import main.TaskManager.mainPage.data.entity.Task;
import main.TaskManager.mainPage.service.TaskService;
import main.TaskManager.userService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainPageController {

    private final TaskService taskService;

    @Autowired
    public MainPageController(TaskService taskService) {
        this.taskService = taskService;
    }
    // Метод GET, возвращающий все сущности
    @GetMapping("/all")
    @PreAuthorize("#user.id == authentication.principal.id")
    public List<Task> getAllEntities(@SessionAttribute User user) {
        // Здесь нужно реализовать логику для получения всех сущностей
        // Например, можно использовать сервис для доступа к данным
        List<Task> tasks = taskService.getAllEntities(user.getId());
        if (tasks == null) {
            return new ArrayList<>(); // or Collections.emptyList();
        }
        return tasks;
    }

    @GetMapping("/{id}")
    @PreAuthorize("#user.id == authentication.principal.id")
    public Task getEntityById(@PathVariable Long id) {
        // Здесь нужно реализовать логику для получения всех сущностей
        // Например, можно использовать сервис для доступа к данным
        return taskService.getTaskById(id);
    }

    // Метод POST, создающий сущность
    @PostMapping("/create")
    @PreAuthorize("#user.id == authentication.principal.id")
    public void createEntity(@RequestBody Task entity) {
        // Здесь нужно реализовать логику для создания сущности
        // Например, можно использовать сервис для сохранения сущности
        taskService.createTask(entity);
    }

    // Метод PUT, редактирующий сущность
    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public void updateEntity(@PathVariable Long id, @RequestBody Task entity) {

        taskService.updateTask(id,entity);
    }

    // Метод DELETE, удаляющий сущность
    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public void deleteEntity(@RequestParam("id") Long userId, @PathVariable Long id) {
        // Здесь нужно реализовать логику для удаления сущности по ее ID
        // Например, можно использовать сервис для удаления сущности
        taskService.deleteTask(id, userId);
    }
}
