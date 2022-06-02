package com.company.users;

public enum Role {

    READER("READER"),
    LIBRARIAN("LIBRARIAN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static Role fromRole(String role) {
        if (role.equals(LIBRARIAN.role)) {
            return LIBRARIAN;
        } else if (role.equals(READER.role)) {
            return READER;
        } else {
            return null;
        }
    }

    public String getRole() {
        return role;
    }
}
