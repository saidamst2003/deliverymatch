package delivery.example.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN,
    EXPEDITEUR,
    CONDUCTEUR;

    @JsonCreator
    public static Role fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value.trim())) {
                return role;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}