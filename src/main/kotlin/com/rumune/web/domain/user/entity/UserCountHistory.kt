package com.rumune.web.domain.user.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "user_count_histories")
data class UserCountHistory(
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="snapshot_time", nullable = false, updatable = false)
    var snapShotTime: OffsetDateTime? = OffsetDateTime.now(),

    @Column(name = "count")
    val count: Int,
) {
}