package com.backend.domain.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(1),
    USER(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }
}
