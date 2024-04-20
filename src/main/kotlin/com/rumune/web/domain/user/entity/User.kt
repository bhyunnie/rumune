package com.rumune.web.domain.user.entity

import com.rumune.web.domain.common.dto.BaseEntity
import io.swagger.v3.oas.annotations.media.Schema
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
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name="users")
class User (
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "사용자 ID", nullable = false, example = "0")
    val id: Long = 0,

    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "이메일", nullable = false, example = "test@rumune.co.kr")
    val email: String,

    @Column(name = "password", nullable = false)
    @Schema(description = "패스워드", nullable = false, example = "****")
    val pwd: String,

    @Column(name = "provider", nullable = false)
    @Schema(description = "외부 서비스 종류 (가입경로)", nullable = false, example = "naver")
    val provider: String,

    @Column(name = "provider_id", nullable = true)
    @Schema(description = "외부 서비스에 등록된 사용자 ID", nullable = false, example= "1512519270192")
    val providerId: String? = null,

    @Column(name = "name", nullable = false)
    @Schema(description = "사용자 이름(본명)", nullable = false, example = "김개똥")
    val name: String,

    @Column(name="profile_image", nullable = true)
    @Schema(description = "프로필 사진", nullable = true, example="https://example-image.png")
    val profileImage: String? = null,

    @Column(name="deprecated", nullable = false)
    @Schema(description = "정지 여부", nullable = false, example="false")
    val deprecated: Boolean = false,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name="user_id"))
    var authorities: MutableSet<Authority> = mutableSetOf(),
):BaseEntity<Long>(),UserDetails {
    @Override
    override fun getId(): Long {
        return id
    }

    @Override
    override fun isNew(): Boolean {
        return this.new
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities.map{it->SimpleGrantedAuthority(it.authority)}.toMutableSet()
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