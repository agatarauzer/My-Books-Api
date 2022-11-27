package com.agatarauzer.myBooks.domain;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="confirmation_tokens")
public class ConfirmationToken {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="confirmation_token")
	private String confirmationToken;
	
	@Column(name="created_date")
	private LocalDate createdDate;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public ConfirmationToken(User user) {
		this.user = user;
		this.createdDate = LocalDate.now();
		this.confirmationToken = UUID.randomUUID().toString();
	}
}
