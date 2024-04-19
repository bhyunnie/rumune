package com.rumune.web.domain.user.entity

import org.springframework.security.oauth2.core.user.OAuth2User

class GoogleUserInfo(
    user: OAuth2User,
): OAuth2UserInfo {
    override val attributes: Map<String, Any> = user.attributes
    override val id = attributes["sub"] as String
    override val email = attributes["email"] as String
    override val name = attributes["name"] as String
    override val profileImage = attributes["picture"] as String
    override val provider = "google"
    override val claims = mapOf("id" to attributes["sub"].toString(), "email" to attributes["email"].toString(), "name" to attributes["name"].toString(), "profile_image" to attributes["picture"].toString())
}