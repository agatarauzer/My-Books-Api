package com.agatarauzer.myBooks.activity;

import com.agatarauzer.myBooks.bookTransfer.domain.BookTransfer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ActivityOnBook {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy="activityOnBook", cascade = CascadeType.PERSIST)
    private List<BookTransfer> bookTransfer;
}