package com.withcalendar.api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
    val userId: Long,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    init {
        super.setAuthenticated(true)  // 이미 필터에서 인증됨
    }

    override fun getCredentials(): Any? = null

    override fun getPrincipal(): Any = userId
}