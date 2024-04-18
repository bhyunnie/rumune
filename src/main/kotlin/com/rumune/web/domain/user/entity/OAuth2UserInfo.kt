package com.rumune.web.domain.user.entity

interface OAuth2UserInfo {
    val id:String
    val provider:String
    val email:String
    val name:String
    val profileImage:String
    val attributes:Map<String,Any>
    val claims:Map<String,Any>
}