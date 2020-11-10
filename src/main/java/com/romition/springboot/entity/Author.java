package com.romition.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "author")
public class Author implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Override
	public String toString() {
		return "Author{" +
				"id=" + id +
				", age=" + age +
				", name='" + name + '\'' +
				", genre='" + genre + '\'' +
				'}';
	}
}
