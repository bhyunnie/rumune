package com.rumune.web.domain.user.entity

import org.springframework.security.oauth2.core.user.OAuth2User

class NaverUserInfo(user:OAuth2User): OAuth2UserInfo {
    override val attributes: Map<String,Any> = user.attributes
    private val response:Map<String, Any> = attributes["response"] as Map<String,Any>
    override val id = response["id"].toString();
    override val email = response["email"].toString()
    override val name = response["name"].toString()
    override val profileImage = response["profile_image"].toString()
    override val provider = "naver"
    override val claims = mapOf("id" to id,"email" to email,"name" to name,"profile_image" to profileImage)
}