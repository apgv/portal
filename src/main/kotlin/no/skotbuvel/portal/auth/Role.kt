package no.skotbuvel.portal.auth

enum class Role {
    ADMIN,
    STYRELEDER,
    KASSERER,
    MATERIALEFORVALTER,
    STYREMEDLEM;

    companion object {
        fun valueOfIgnoreCase(role: String): Role {
            return Role.valueOf(role.toUpperCase())
        }
    }
}
