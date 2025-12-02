package de.seuhd.campuscoffee.domain.model;

import lombok.Builder;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder(toBuilder = true)
public record User (
        //TODO: Implement user domain object
        Long id,
        LocalDateTime created,
        LocalDateTime updated,
        String username,
        String email,
        String firstName,
        String lastName
) implements Serializable { // serializable to allow cloning (see TestFixtures class).
    @Serial
    private static final long serialVersionUID = 1L;
    public User {
        Objects.requireNonNull(username, "username must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");


        if (created == null) {
            created = LocalDateTime.now();
        }

        if (updated == null) {
            updated = LocalDateTime.now();
        }
    }
}