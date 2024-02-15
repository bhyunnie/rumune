package com.rumune.web.domain.user.entity

class KakaoUserInfo(private val attributes:Map<String,Any>): OAuth2UserInfo {

    private val properties = attributes["properties"] as Map<*, *>
    override fun getId(): String {
        return attributes["id"].toString();
    }

    override fun getProvider(): String {
        return "kakao"
    }

    override fun getEmail(): String {
        return properties["email"].toString()
    }

    override fun getName(): String {
        return properties["nickname"].toString()
    }

    override fun getProfileImage(): String {
        return properties["profile_image"].toString()
    }

    override fun getAttributes(): Map<String, String> {
        return mapOf(
            "id" to attributes["id"].toString(),
            "email" to attributes["email"].toString(),
            "name" to attributes["nickname"].toString(),
            "profile_image" to attributes["profile_image"].toString()
        )
    }
}