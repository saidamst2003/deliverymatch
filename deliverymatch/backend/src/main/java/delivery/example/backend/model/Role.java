package delivery.example.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN,
    EXPEDITEUR,
    CONDUCTEUR;

    @JsonCreator
    public static Role fromString (String value) {
        try {
            return Role.valueOf(value.toUpperCase());
        }
        catch (Exception e) {
            return null;
        }
    }

    @JsonValue
    public String getValue() {
        return name();
    }



}
