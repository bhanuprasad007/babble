package app.chat.babble.security;

import app.chat.babble.model.UserPermission;
import app.chat.babble.model.UserRole;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static app.chat.babble.model.UserPermission.DELETE_OWN_MESSAGE;
import static app.chat.babble.model.UserPermission.EDIT_PROFILE;
import static app.chat.babble.model.UserPermission.JOIN_CHAT_ROOM;
import static app.chat.babble.model.UserPermission.LEAVE_CHAT_ROOM;
import static app.chat.babble.model.UserPermission.READ_MESSAGE;
import static app.chat.babble.model.UserPermission.SEND_MESSAGE;
import static app.chat.babble.model.UserPermission.VIEW_OTHER_PROFILES;

public class UserRolePermissionResolver {
    private static final Map<UserRole, Set<UserPermission>> rolePermissionMap = new HashMap<>();

    static {
        rolePermissionMap.put(
                UserRole.USER,
                Set.of(READ_MESSAGE,
                        SEND_MESSAGE,
                        DELETE_OWN_MESSAGE,
                        JOIN_CHAT_ROOM,
                        LEAVE_CHAT_ROOM,
                        EDIT_PROFILE,
                        VIEW_OTHER_PROFILES));
        rolePermissionMap.put(UserRole.ADMIN, Arrays.stream(UserPermission.values())
                .collect(Collectors.toSet()));
    }

    public static Set<UserPermission> getPermissionsForRole(UserRole role) {
        return rolePermissionMap.getOrDefault(role, Set.of());
    }
}
