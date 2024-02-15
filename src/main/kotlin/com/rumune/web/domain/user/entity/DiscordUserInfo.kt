package com.rumune.web.domain.user.entity

class DiscordUserInfo(
    private val attributes: Map<String,Any>
): OAuth2UserInfo {
    override fun getId(): String {
        return attributes["id"].toString()
    }

    override fun getProvider(): String {
        return "discord"
    }

    override fun getEmail(): String {
        return attributes["email"].toString()
    }

    override fun getName(): String {
        return attributes["username"].toString()
    }

    override fun getProfileImage(): String {
        return "https://cdn.discordapp.com/avatars/${attributes["id"].toString()}/${attributes["avatar"].toString()}.png"
    }

    override fun getAttributes(): Map<String, String> {
        return mapOf(
            "id" to attributes["id"].toString(),
            "email" to attributes["email"].toString(),
            "name" to attributes["username"].toString(),
            "profile_image" to "https://cdn.discordapp.com/avatars/${attributes["id"].toString()}/${attributes["avatar"].toString()}.png"
        )
    }
}