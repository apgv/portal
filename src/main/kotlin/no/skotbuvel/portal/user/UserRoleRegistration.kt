package no.skotbuvel.portal.user

data class UserRoleRegistration(
        val userId: Int,
        val roleIds: List<Int>
)