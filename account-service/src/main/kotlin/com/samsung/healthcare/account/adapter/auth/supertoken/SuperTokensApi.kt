package com.samsung.healthcare.account.adapter.auth.supertoken

import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_ASSIGN_ROLE_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_CREATE_ROLE_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_GENERATE_RESET_TOKEN_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_GET_USER_ROLE_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_JWT_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_LIST_USERS_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_REMOVE_USER_ROLE_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_RESET_PASSWORD_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_SIGN_IN_PATH
import com.samsung.healthcare.account.adapter.auth.supertoken.PathConstant.SUPER_TOKEN_SIGN_UP_PATH
import feign.Headers
import feign.Param
import feign.RequestLine
import reactor.core.publisher.Mono

@Headers("Accept: application/json")
interface SuperTokensApi {
    @RequestLine("POST $SUPER_TOKEN_SIGN_IN_PATH")
    @Headers("Content-Type: application/json")
    fun signIn(signInRequest: SignRequest): Mono<SignInResponse>

    @RequestLine("POST $SUPER_TOKEN_SIGN_UP_PATH")
    @Headers("Content-Type: application/json")
    fun signUp(signUpRequest: SignRequest): Mono<SignInResponse>

    @RequestLine("POST $SUPER_TOKEN_RESET_PASSWORD_PATH")
    @Headers("Content-Type: application/json")
    fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Mono<StatusResponse>

    @RequestLine("POST $SUPER_TOKEN_GENERATE_RESET_TOKEN_PATH")
    @Headers("Content-Type: application/json")
    fun generateResetToken(userId: UserId): Mono<ResetTokenResponse>

    @RequestLine("PUT $SUPER_TOKEN_ASSIGN_ROLE_PATH")
    @Headers("Content-Type: application/json")
    fun assignRoles(roleBinding: RoleBinding): Mono<Void>

    @RequestLine("POST $SUPER_TOKEN_REMOVE_USER_ROLE_PATH")
    @Headers("Content-Type: application/json")
    fun removeUserRole(roleBinding: RoleBinding): Mono<Void>

    @RequestLine("PUT $SUPER_TOKEN_CREATE_ROLE_PATH")
    @Headers("Content-Type: application/json")
    fun createRoles(createRoleRequest: CreateRoleRequest): Mono<Void>

    @RequestLine("GET $SUPER_TOKEN_GET_USER_ROLE_PATH?userId={userId}")
    fun listUserRoles(@Param userId: String): Mono<RolesResponse>

    @RequestLine("GET $SUPER_TOKEN_LIST_USERS_PATH")
    fun listUsers(): Mono<ListUserResponse>

    @RequestLine("POST $SUPER_TOKEN_JWT_PATH")
    @Headers("Content-Type: application/json")
    fun generateSignedJwt(generateJwtRequest: GenerateJwtRequest): Mono<JwtResponse>

    class ResetPasswordRequest(
        val resetToken: String,
        val newPassword: String,
    ) {
        val method = "token"
    }

    data class StatusResponse(val status: Status)

    data class ResetTokenResponse(val status: Status, val token: String?)

    data class SignRequest(
        val email: String,
        val password: String
    )

    data class SignInResponse(val status: Status, val user: User?)

    data class ListUserResponse(val status: Status, val users: Collection<UserWrapper>)

    data class UserWrapper(val user: User)

    data class User(val id: String, val email: String)

    data class UserId(val userId: String)

    data class RoleBinding(val userId: String, val role: String)

    data class CreateRoleRequest(val role: String)

    data class RolesResponse(val roles: Collection<String>)

    data class GenerateJwtRequest(
        val payload: Map<String, Any>,
        val jwksDomain: String,
        val validity: Long
    ) {
        val algorithm = "RS256"
    }

    data class JwtResponse(
        val jwt: String
    )

    enum class Status {
        OK,
        EMAIL_ALREADY_EXISTS_ERROR,
        WRONG_CREDENTIALS_ERROR,
        UNKNOWN_USER_ID_ERROR,
        RESET_PASSWORD_INVALID_TOKEN_ERROR
    }
}
