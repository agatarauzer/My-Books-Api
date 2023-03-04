package com.agatarauzer.myBooks.user;

import com.agatarauzer.myBooks.authentication.role.Role;
import com.agatarauzer.myBooks.owner.Owner;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	private String email;
	private String username;
	private String password;
	@Column(name="registration_date")
	private LocalDate registrationDate;
	@Builder.Default
	private Boolean enabled = false;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@OneToOne
	@JoinColumn(name="owner_id")
	private Owner owner;
}
