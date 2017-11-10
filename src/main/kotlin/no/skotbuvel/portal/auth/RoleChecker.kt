package no.skotbuvel.portal.auth

object RoleChecker {

    fun hasRole(requiredRole: Role, roles: Array<String>): Boolean {
        return roles
                .map { s -> s.toUpperCase() }
                .contains(requiredRole.name)
    }
}