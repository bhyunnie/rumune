package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.repository.UserEntity
import com.rumune.web.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserInfoService(val userRepository: UserRepository) {
    fun createUser(email:String, nickname:String, provider:String) {
        val userEntity = UserEntity(email=email, nickname = nickname, provider = provider)
        userRepository.save(userEntity)
    }
}