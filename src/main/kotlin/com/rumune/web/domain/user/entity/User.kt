package com.rumune.web.domain.user.entity

import com.rumune.web.domain.common.dto.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name="users")
class User (
    @Id
    @Column(name = "user_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val pwd: String,

    @Column(name = "provider", nullable = false)
    val provider: String,

    @Column(name = "provider_id", nullable = true)
    val providerId: String? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name="profile_image", nullable = true)
    val profileImage: String? = null,

    @Column(name="deprecated", nullable = false)
    val deprecated: Boolean = false,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name="user_id"))
    var authorities: MutableSet<Authority> = mutableSetOf(),
):BaseEntity<Long>(),UserDetails {
    @Override
    override fun getId(): Long {
        return userId
    }

    @Override
    override fun isNew(): Boolean {
        return super.new
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities
    }

    override fun getPassword(): String {
        return this.pwd
    }

    override fun getUsername(): String {
        return this.name
    }

    override fun isAccountNonExpired(): Boolean {
        return !deprecated
    }

    override fun isAccountNonLocked(): Boolean {
        return !deprecated
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !deprecated
    }

    override fun isEnabled(): Boolean {
        return !deprecated
    }
}