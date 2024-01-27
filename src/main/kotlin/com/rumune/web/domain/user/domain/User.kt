package com.rumune.web.domain.user.domain

import java.util.UUID

class User (
    uuid: UUID,
    userId: String,
    userName: String,
    email: String,
    provider: String,
)