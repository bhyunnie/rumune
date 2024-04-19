package com.rumune.web.domain.user.entity

import org.springframework.security.oauth2.core.user.OAuth2User

class KakaoUserInfo(user:OAuth2User): OAuth2UserInfo {
    override val attributes:Map<String,Any> = user.attributes
    private val properties = attributes["properties"] as Map<*, *>
    override val id = attributes["id"].toString()
    override val provider = "kakao"
    override val email = properties["email"].toString()
    override val name = properties["nickname"].toString()
    override val profileImage = properties["profile_image"].toString()
    override val claims = mapOf("id" to id, "email" to email, "name" to name, "profile_image" to profileImage)
}