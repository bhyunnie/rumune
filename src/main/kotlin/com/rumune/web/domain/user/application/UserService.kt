package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.dto.AuthenticationRequestDto
import com.rumune.web.domain.user.dto.AuthenticationResponseDto
import com.rumune.web.domain.jwt.entity.JsonWebToken
import com.rumune.web.domain.user.dto.CreateUserRequestDto
import com.rumune.web.domain.user.entity.Authority
import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.jwt.repository.JwtRepository
import com.rumune.web.domain.user.dto.UserDto
import com.rumune.web.domain.user.repository.UserRepository
import com.rumune.web.domain.jwt.application.JwtService
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
    private val jwtService: JwtService,
    private val refreshTokenRepository: JwtRepository,
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

    fun findUserById(userId:Long): List<User> {
        return userRepository.findByUserId(userId)
    }

    fun findUserByEmail(email:String): List<User> {
        return userRepository.findByEmail(email)
    }

    fun findUserByProviderId(providerId:String): List<User> {
        return userRepository.findByProviderId(providerId)
    }

    override fun loadUserByUsername(email: String): User {
        return userRepository.findByEmail(email)[0]
    }

    fun checkAuthority(email:String, authority:String): Boolean {
        val userList = userRepository.findByEmail(email)
        if (userList.isNotEmpty()) {
            val user = userList[0]
            return user.authorities.contains(Authority(userId = user.userId, name = authority))
        } else {
            return false
        }
    }

    fun addAuthority(email:String, authority: String) {
        try {
            val userList = userRepository.findByEmail(email)
            if (userList.isNotEmpty()) {
                val user = userList[0]
                val newRole = Authority(user.userId, authority)
                if (!user.authorities.contains(newRole)) {
                    user.authorities.add(newRole)
                    userRepository.save(user)
                }
            } else {
                throw Exception("유저가 없습니다.")
            }
        } catch (e: Exception) {
            throw Exception("이상함")
        }
    }

    fun removeAuthority(email: String,authority: String) {
        val userList = userRepository.findByEmail(email)
        if (userList.isNotEmpty()) {
            val user = userList[0]
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
        val accessToken = jwtService.generateAccessToken(user.email)
        val refreshToken = jwtService.generateRefreshToken(user.email)

        val refreshTokenList = refreshTokenRepository.findByUserId(user.userId)
        if (refreshTokenList.isEmpty()) {
            refreshTokenRepository.save(JsonWebToken(jwt=refreshToken, userId = user.userId))
        } else {
            val savedRefreshToken = refreshTokenList[0]
            savedRefreshToken.jwt = refreshToken
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