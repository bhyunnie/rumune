package com.rumune.web.domain.user.entity

class KakaoUserInfo(override val attributes:Map<String,Any>): OAuth2UserInfo {
    private val properties = attributes["properties"] as Map<*, *>
    override val id = properties["id"].toString()
    override val provider = "kakao"
    override val email = properties["email"].toString()
    override val name = properties["nickname"].toString()
    override val profileImage = properties["profile_image"].toString()
    override val claims = mapOf("id" to id, "email" to email, "name" to name, "profile_image" to profileImage)
}