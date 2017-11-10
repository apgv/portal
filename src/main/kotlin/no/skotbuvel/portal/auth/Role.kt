package no.skotbuvel.portal.auth

enum class Role {
    BOARD_MEMBER,
    ADMIN;

    companion object {
        fun valueOfIgnoreCase(role: String): Role {
            return Role.valueOf(role.toUpperCase())
        }
    }
}
