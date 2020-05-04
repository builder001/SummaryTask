package ua.nure.tereshchenko.SummaryTask4.db;

import ua.nure.tereshchenko.SummaryTask4.db.entity.User;

/**
 * Role entity.
 *
 * @author A.Tereshchenko
 */
public enum  Role {
    ADMIN, LIBRARIAN, READER;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
