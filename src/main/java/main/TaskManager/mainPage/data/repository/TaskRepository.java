package main.TaskManager.mainPage.data.repository;

import main.TaskManager.mainPage.data.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);

    List<Task> findByUserId(Long userId);

    Task save(Task task);

    void deleteById(Long id);
}