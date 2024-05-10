package com.rumune.web.domain.user.application

import com.amazonaws.services.kms.model.NotFoundException
import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.domain.jwt.entity.JsonWebToken
import com.rumune.web.domain.jwt.repository.JwtRepository
import com.rumune.web.domain.user.dto.request.AuthenticationRequest
import com.rumune.web.domain.user.dto.request.CreateUserRequest
import com.rumune.web.domain.user.dto.response.AuthenticationResponse
import com.rumune.web.domain.user.entity.Authority
import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val refreshTokenRepository: JwtRepository,
    private val applicationContext: ApplicationContext,
) : UserDetailsService {
    /**
     * 유저 생성 (단건)
     */
    fun createUser(user: CreateUserRequest) {
        userRepository.save(
            User(
                name = user.username,
                email = user.email,
                provider = user.provider,
                providerId = user.providerId,
                profileImage = user.profileImage,
                pwd = user.pwd,
            ),
        )
    }

    /**
     * 유저 조회 (단건)
     */
    fun findUserById(id: Long): User {
        val userOptional = userRepository.findById(id)
        if (userOptional.isEmpty) throw NotFoundException("유저를 찾을 수 없습니다.")
        return userOptional.get()
    }

    /**
     * 유저 조회 by email (단건)
     */
    fun findUserByEmail(email: String): User {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) throw NotFoundException("유저를 찾을 수 없습니다.")
        return userOptional.get()
    }

    /**
     * 유저 조회 by provider id (단건)
     */
    fun findUserByProviderId(providerId: String): User {
        val userOptional = userRepository.findByProviderId(providerId)
        if (userOptional.isEmpty) throw NotFoundException("유저를 찾을 수 없습니다.")
        return userOptional.get()
    }

    /**
     * 유저 조회 by email (단건)
     */
    override fun loadUserByUsername(email: String): User {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) throw NotFoundException("유저를 찾을 수 없습니다.")
        return userOptional.get()
    }

    /**
     * 권한 조회
     */
    fun checkAuthority(
        email: String,
        authority: String,
    ): Boolean {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) return false
        val user = userOptional.get()
        return user.authorities.contains(Authority(userId = user, name = authority))
    }

    /**
     * 권한 추가 (단건)
     */
    fun addAuthority(
        email: String,
        authority: String,
    ): Authority {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) throw NotFoundException("유저를 찾을 수 없습니다.")
        val user = userOptional.get()
        val newRole = Authority(user, authority)
        if (!user.authorities.contains(newRole)) {
            user.authorities.add(newRole)
            userRepository.save(user)
        }
        return newRole
    }

    /**
     * 권한 제거
     */
    fun removeAuthority(
        email: String,
        authority: String,
    ) {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) throw NotFoundException("유저를 찾을 수 없습니다.")
        val user = userOptional.get()
        val newRole = Authority(user, authority)
        if (user.authorities.contains(newRole)) {
            user.authorities.remove(newRole)
            userRepository.save(user)
        }
    }

    /**
     * 전체 유저 조회
     */
    fun findAll(): List<User> {
        val userList = userRepository.findAll()
        if (userList.isEmpty()) throw NotFoundException("유저를 찾을 수 없습니다.")
        return userList
    }

    /**
     * 인증
     */
    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        val authenticationManager = applicationContext.getBean(AuthenticationManager::class.java)
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password,
            ),
        )
        val user = loadUserByUsername(authenticationRequest.email)
        val accessToken = jwtService.generateAccessToken(user.email)
        val refreshToken = jwtService.generateRefreshToken(user.email)
        val refreshTokenOptional = refreshTokenRepository.findByUserId(user.id)
        if (refreshTokenOptional.isEmpty) {
            refreshTokenRepository.save(JsonWebToken(jwt = refreshToken, userId = user.id))
        } else {
            val savedRefreshToken = refreshTokenOptional.get()
            savedRefreshToken.jwt = refreshToken
            refreshTokenRepository.save(savedRefreshToken)
        }
        return AuthenticationResponse(
            userId = user.id,
            email = user.email,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
