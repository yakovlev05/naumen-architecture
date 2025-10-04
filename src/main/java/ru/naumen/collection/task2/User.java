package ru.naumen.collection.task2;

import java.util.Arrays;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User user)) return false;

        return username.equals(user.username)
                && email.equals(user.email)
                && Arrays.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + Arrays.hashCode(passwordHash);
        return result;
    }
}
