package com.rumune.web.domain.user.entity

class GoogleUserInfo(
    override val attributes: Map<String,Any>,
): OAuth2UserInfo {
    override val id = attributes["sub"] as String
    override val email = attributes["email"] as String
    override val name = attributes["name"] as String
    override val profileImage = attributes["picture"] as String
    override val provider = "google"
    override val claims = mapOf("id" to attributes["sub"].toString(), "email" to attributes["email"].toString(), "name" to attributes["name"].toString(), "profile_image" to attributes["picture"].toString())
}