package com.rumune.web.global.exception

import org.springframework.security.core.AuthenticationException

class OAuth2AlreadyExistException(msg: String, val provider: String, val email: String) : AuthenticationException(msg)
