package com.rumune.web.domain.user.entity

interface OAuth2UserInfo {
    fun getId():String
    fun getProvider():String
    fun getEmail():String
    fun getName():String
    fun getProfileImage():String
    fun getAttributes():Map<String, String>
}