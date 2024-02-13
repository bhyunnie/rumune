package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.dto.CreateUserRequestDto
import com.rumune.web.domain.user.entity.Authority
import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
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

    fun findUserById(userId:Long): User {
        return userRepository.findByUserId(userId).get()
    }

    fun findUserByEmail(email:String): User {
        return userRepository.findByEmail(email).get()
    }

    fun findUserByProviderId(providerId:String): User {
        return userRepository.findByProviderId(providerId).get()
    }

    override fun loadUserByUsername(email: String): User {
        val userOptional = userRepository.findByEmail(email)
        if (userOptional.isEmpty) throw UsernameNotFoundException("유저 정보가 없습니다.")
        val user = userOptional.get()
        return user
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
}