package com.application.Recipe.Models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.application.Recipe.Enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Inheritance(strategy=InheritanceType.JOINED)
public class user implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	@Id
	//@Basic(optional = false)
	@Column(name= "user_id", updatable = false, columnDefinition = "CHAR(36)", nullable=false)
	//@GeneratedValue(generator = "uuid2")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "first_name",nullable=false)
	private String firstName;
	
	@Column(name = "last_name",nullable=false)
	private String lastName;
	
	@Email(message="Invalid email format")
	@NotBlank(message="Email cannot be empty")
	@Column(name = "email",nullable=false)
	private String email;
	
	@Size(min=8, max = 30, message="Password must be between 8 and 20 characters")
	@NotBlank(message="Password is required")
	@Column(name = "password", nullable=false)
	private String password;
	
	/*@Basic(optional = false)
	@Transient
	private String confirm_password;*/
	
	@Column(name = "image_url",nullable=true)
	private String image_url;
	
	@Column(name = "role", nullable =false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
	private Set<recentSearch> recentSearches;
	
	@ManyToMany
	@JoinTable(
			name = "favorites",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "recipe_id")			
			)
	
	private Set<Recipe> favorites;
	
	@ManyToMany(mappedBy="followers")
	private Set<chef> following;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<Review> reviews;

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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		user user = (user) o;

		return id != null ? id.equals(user.id) : user.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
