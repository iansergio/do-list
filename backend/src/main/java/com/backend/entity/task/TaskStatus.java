package com.backend.entity.task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING("PENDING"),
    FINISHED("COMPLETED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }
}
