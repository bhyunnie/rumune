package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.dto.AuthenticationRequestDto
import com.rumune.web.domain.user.dto.AuthenticationResponseDto
import com.rumune.web.domain.jwt.entity.RefreshToken
import com.rumune.web.domain.user.dto.CreateUserRequestDto
import com.rumune.web.domain.user.entity.Authority
import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.jwt.repository.RefreshTokenRepository
import com.rumune.web.domain.user.dto.UserDto
import com.rumune.web.domain.user.repository.UserRepository
import com.rumune.web.global.util.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Optional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val applicationContext: ApplicationContext,
): UserDetailsService {

    fun createUser(user: CreateUserRequestDto) {
        userRepository.save(User(
            name = user.username,
            email = user.email,
            provider = user.provider,
            providerId = user.providerId,
            profileImage = user.profileImage,
            pwd = user.pwd
        ))
    }

    fun findUserById(userId:Long): Optional<User> {
        return userRepository.findByUserId(userId)
    }

    fun findUserByEmail(email:String): List<UserDto> {
        val userOptional = userRepository.findByEmail(email)
        return if (userOptional.isPresent) {
            listOf(UserDto.from(userOptional.get()))
        } else {
            listOf()
        }
    }

    fun findUserByProviderId(providerId:String): Optional<User> {
        return userRepository.findByProviderId(providerId)
    }

    override fun loadUserByUsername(email: String): User {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) throw UsernameNotFoundException("유저 정보가 없습니다.")
        val user = userOptional.get()
        return user
    }

    fun checkAuthority(email:String, authority:String): Boolean {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isPresent) {
            val user = userOptional.get()
            return user.authorities.contains(Authority(userId = user.userId, name = authority))
        } else {
            return false
        }
    }

    fun addAuthority(email:String, authority: String) {
        try {
            userRepository.findByEmail(email).ifPresent{user ->
                val newRole = Authority(user.userId, authority)
                if (!user.authorities.contains(newRole)) {
                    user.authorities.add(newRole)
                    userRepository.save(user)
                }
            }
        } catch (e: Exception) {
            throw Exception("이상함")
        }
    }

    fun removeAuthority(email: String,authority: String) {
        userRepository.findByEmail(email).ifPresent{ user ->
            val newRole = Authority(user.userId, authority)
            if (user.authorities.contains(newRole)) {
                user.authorities.remove(newRole)
                userRepository.save(user)
            }

        }
    }

    fun findAll(): List<UserDto> {
        val userList = userRepository.findAll()
        return userList.map{
            user ->
            UserDto.from(user)
        }
    }

    fun authentication(authenticationRequest: AuthenticationRequestDto): AuthenticationResponseDto {
        val authenticationManager = applicationContext.getBean(AuthenticationManager::class.java)
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticationRequest.email, authenticationRequest.password))
        val user = loadUserByUsername(authenticationRequest.email)
        val accessToken = jwtUtil.generateAccessToken(user.email)
        val refreshToken = jwtUtil.generateRefreshToken(user.email)

        val refreshTokenOptional = refreshTokenRepository.findByUserId(user.userId)
        if (refreshTokenOptional.isEmpty) {
            refreshTokenRepository.save(RefreshToken(refreshToken=refreshToken, userId = user.userId))
        } else {
            val savedRefreshToken = refreshTokenOptional.get()
            savedRefreshToken.refreshToken = refreshToken
            refreshTokenRepository.save(savedRefreshToken)
        }

        return AuthenticationResponseDto(
            userId = user.id,
            email = user.email,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}