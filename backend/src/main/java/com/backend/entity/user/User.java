package com.backend.entity.user;

import com.backend.core.BaseEntity;
import com.backend.entity.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email
    private String email;

    private String password;
    private UserRole role;

    /**
     * Um usuário tem várias tarefas
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public User() {
    }

    public User(UUID userId) {
        this.id = userId;
    }

}
