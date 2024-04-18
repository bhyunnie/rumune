package com.rumune.web.domain.user.entity

class NaverUserInfo(
    override val attributes: Map<String,Any>,
): OAuth2UserInfo {
    private val response:Map<String, Any> = attributes["response"] as Map<String,Any>
    override val id = response["id"].toString();
    override val email = response["email"].toString()
    override val name = response["name"].toString()
    override val profileImage = response["profile_image"].toString()
    override val provider = "naver"
    override val claims = mapOf("id" to id,"email" to email,"name" to name,"profile_image" to profileImage)
}