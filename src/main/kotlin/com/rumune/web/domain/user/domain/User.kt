package com.rumune.web.domain.user.domain

import net.minidev.json.annotate.JsonIgnore
import java.util.UUID

data class User (
    @JsonIgnore
    val uuid: UUID,
    val userId: String,
    val userName: String,
    val email: String,
    val provider: String,
)