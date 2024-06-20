package com.sparta.everydrink.security;

import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Slf4j
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final String usrname;

    private final String password;

    @Getter
    private UserRoleEnum userRoleEnum;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.usrname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = UserRoleEnum.USER.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetailsImpl of(User user) {
        return UserDetailsImpl.builder()
                .usrname(user.getUsername())
                .password(user.getPassword())
                .userRoleEnum(user.getRole())
                .build();
    }
}
