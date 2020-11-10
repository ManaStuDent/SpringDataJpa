package com.romition.springboot.service;

import com.romition.springboot.dao.BatchDao;
import com.romition.springboot.entity.Author;
import com.romition.springboot.entity.Book;
import com.romition.springboot.projection.AuthorNameAge;
import com.romition.springboot.repository.AuthorRepository;
import com.romition.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	BookRepository bookRepository;

//	@Autowired
	BatchDao batchDao;

	public void persistAuthor() {
		Author author = new Author();
		author.setName("刘成龙");
		author.setAge(27);
		author.setGenre("male");
		authorRepository.save(author);

		Author queryAuthor = authorRepository.findByName("刘成龙");
		System.out.println(queryAuthor);
	}

	public void batchAuthorsAndBooks() {
		List<Author> authors = new ArrayList<>();

		long pk = 0;
		for (int i = 0; i < 40; i++) {

			Author author = new Author();
			author.setId((long) i + 1);
			author.setName("Name_" + i);
			author.setGenre("Genre_" + i);
			author.setAge(18 + i);

			for (int j = 0; j < 5; j++) {
				Book book = new Book();
				book.setId(++pk);
				book.setTitle("Title: " + j);
				book.setIsbn("Isbn: " + j);

				author.addBook(book);
			}
			authors.add(author);
		}

		batchDao.saveInBatch(authors);
	}

	public void findFirst2ByGenre() {
		List<AuthorNameAge> author = authorRepository.findFirst2ByGenre("Genre_1");
		author.forEach(_authoor -> {
			System.out.println(_authoor.getAge());
			System.out.println(_authoor.getName());
		});
	}

	@Transactional(readOnly = true)
	public void find() {
		Optional<Author> author = authorRepository.findById(1L);

		List<Book> books = author.get().getBooks();

		System.out.println(books);
	}

	@Transactional(readOnly = true)
	public void fetchBooksAndAuthors() {

		List<Book> books = bookRepository.findAll();

		for (Book book : books) {
			Author author = book.getAuthor();
			System.out.println("Book: " + book.getTitle() + " Author: " + author.getName());
		}
	}

	@Transactional(readOnly = true)
	public void fetchAuthorsAndBooks() {

		List<Author> authors = authorRepository.findAll();

		for (Author author: authors) {
			List<Book> books = author.getBooks();
			System.out.println("Author: " + author.getName() + " Books: " + books);
		}
	}

}
