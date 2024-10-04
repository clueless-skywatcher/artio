package io.duskmare.artio.models;

public enum ArtioUserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String roleName;

    ArtioUserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getValue() {
        return this.roleName;
    }
}
