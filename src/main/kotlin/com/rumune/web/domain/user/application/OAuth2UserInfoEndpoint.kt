package com.rumune.web.domain.user.application

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserInfoEndpoint: DefaultOAuth2UserService() {
    @Override
    override fun loadUser(request: OAuth2UserRequest):OAuth2User {
        val oAuth2User:OAuth2User = super.loadUser(request)
        val oAuthClientName = request.clientRegistration.clientName


        // TODO DB 저장 로직
        println("bhlog => hello")

        var userId:String = "";

        if (oAuthClientName.equals("discord")) {
            userId = "discord_" + oAuth2User.attributes["id"];

        }


        return oAuth2User
    }
}