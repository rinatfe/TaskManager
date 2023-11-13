package main.TaskManager.mainPage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.TaskManager.userService.entity.User;

@Entity
@Getter
@Setter
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String description;
    private String attachment;

    private Long userId;
}
