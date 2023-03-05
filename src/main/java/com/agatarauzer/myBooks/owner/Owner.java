package com.agatarauzer.myBooks.owner;

import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="owners")
public class Owner {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="owner_type")
	@Enumerated(EnumType.STRING)
	private OwnerType type;

	@OneToOne(mappedBy = "owner")
	private User user;
	@OneToMany(mappedBy="owner", cascade = CascadeType.ALL)
	private List<Book> book;
}
