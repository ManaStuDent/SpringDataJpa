package com.romition.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private int age;
	private String name;
	private String genre;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "author", orphanRemoval = true)
	private List<Book> books = new ArrayList<>();

	public void addBook(Book book) {
		this.books.add(book);
		book.setAuthor(this);
	}

	public void removeBook(Book book) {
		book.setAuthor(null);
		this.books.remove(book);
	}

}
