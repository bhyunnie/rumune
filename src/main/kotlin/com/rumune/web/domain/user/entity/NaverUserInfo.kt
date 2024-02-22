package com.rumune.web.domain.user.entity

class NaverUserInfo(
    private val attributes: Map<String,Any>
): OAuth2UserInfo {

    private val response:Map<String, Any> = attributes["response"] as Map<String,Any>
    private val id = response["id"].toString();
    private val email = response["email"].toString()
    private val name = response["name"].toString()
    private val profileImage = response["profile_image"].toString()


    override fun getId(): String {
        return id
    }

    override fun getProvider(): String {
        return "naver"
    }

    override fun getEmail(): String {
        return email
    }

    override fun getName(): String {
        return name
    }

    override fun getProfileImage(): String {
        return profileImage
    }

    override fun getAttributes(): Map<String, String> {
        return mapOf(
            "id" to id,
            "email" to email,
            "name" to name,
            "profile_image" to profileImage
        )
    }
}