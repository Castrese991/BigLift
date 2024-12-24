package com.big_lift.palestra.utils;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.big_lift.palestra.model.UserModel;


public class CustomUserDetails implements UserDetails
{
	private final UserModel user;

	public CustomUserDetails(UserModel user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return List.of(() -> "ROLE_" + user.getRole().name());
	}

	@Override
	public String getPassword()
	{
		return user.getPassword();
	}

	@Override
	public String getUsername()
	{
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled()
	{
		return UserDetails.super.isEnabled();
	}
}