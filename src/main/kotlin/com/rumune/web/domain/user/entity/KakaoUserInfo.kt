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
}