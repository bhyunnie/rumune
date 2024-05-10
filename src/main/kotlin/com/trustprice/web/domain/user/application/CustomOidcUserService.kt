package com.trustprice.web.domain.user.application

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.stereotype.Component

@Component
class CustomOidcUserService() : OidcUserService()
