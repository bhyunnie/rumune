package com.rumune.web.domain.user.entity

class DiscordUserInfo(
    override val attributes: Map<String,Any>,
): OAuth2UserInfo {
    override val id = attributes["id"].toString()
    override val provider = "discord"
    override val email = attributes["email"].toString()
    override val name = attributes["username"].toString()
    override val profileImage = "https://cdn.discordapp.com/avatars/${attributes["id"].toString()}/${attributes["avatar"].toString()}.png"
    override val claims = mapOf("id" to id,"email" to email,"name" to name,"profile_image" to profileImage,"provider" to provider)
}