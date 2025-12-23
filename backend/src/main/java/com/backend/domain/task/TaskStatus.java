package com.backend.domain.task;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING("PENDING"),
    ONGOING("ONGOING"),
    FINISHED("FINISHED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }
}
