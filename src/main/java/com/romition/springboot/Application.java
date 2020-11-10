package com.romition.springboot;

import com.romition.springboot.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	@Autowired
	AuthorService authorService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ApplicationRunner init() {
		return args -> {
			System.out.println("\nFetch all books and authors  ...");
			authorService.fetchBooksAndAuthors();

			System.out.println("\nFetch all authors and books  ...");
			authorService.fetchAuthorsAndBooks();
		};
	}
}