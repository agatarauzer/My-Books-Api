package com.agatarauzer.myBooks.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Rate")
public class Rate {
	
	private boolean isRated;
	private int rate;

}
