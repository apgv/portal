package no.skotbuvel.portal.auth

object RoleChecker {

    fun hasRole(requiredRole: Role, roles: List<String>): Boolean {
        return roles
                .map { s -> s.toUpperCase() }
                .contains(requiredRole.name)
    }
}