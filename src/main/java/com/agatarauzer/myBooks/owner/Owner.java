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
	@Column(name="owner_id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="owner_type")
	@Enumerated(EnumType.STRING)
	private OwnerType type;
	@OneToOne
	private User user;
	@OneToMany(mappedBy="owner", cascade = CascadeType.PERSIST)
	private List<Book> book;
}
