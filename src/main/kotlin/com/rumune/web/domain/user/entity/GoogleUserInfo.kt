package com.rumune.web.domain.user.entity

class GoogleUserInfo(
    private val attributes: Map<String,Any>
): OAuth2UserInfo {
    override fun getId(): String {
        return attributes["sub"].toString()
    }

    override fun getProvider(): String {
        return "google"
    }

    @Override
    override fun getEmail(): String {
        return attributes["email"].toString()
    }

    @Override
    override fun getName(): String {
        return attributes["name"].toString()
    }

    override fun getProfileImage(): String {
        return attributes["picture"].toString()
    }
}