package com.application.Recipe.Models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class user implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name= "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Basic(optional = false)
	@Column(name = "first_name")
	private String firstName;
	
	@Basic(optional = false)
	@Column(name = "last_name")
	private String lastName;
	
	@Basic(optional = false)
	@Email(message="Invalid email format")
	@NotBlank(message="Email cannot be empty")
	@Column(name = "email")
	private String email;
	
	
	@Basic(optional = false)
	@Size(min=8, max = 30, message="Password must be between 8 and 20 characters")
	@NotBlank(message="Password is required")
	@Column(name = "password")
	private String password;
	
	/*@Basic(optional = false)
	@Transient
	private String confirm_password;*/
	
	@Basic(optional = true)
	@Column(name = "image_url")
	private String image_url;
	
	@Enumerated(EnumType.STRING)
	private role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return email;
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
}
