package ru.naumen.collection.task1;

/**
 * Билет
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Ticket {
    private long id;
    private String client;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Ticket ticket)) return false;

        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
