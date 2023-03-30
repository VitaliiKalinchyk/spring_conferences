package ua.kalin.spring.conferences.model.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_VISITOR,ROLE_SPEAKER,ROLE_MODERATOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}