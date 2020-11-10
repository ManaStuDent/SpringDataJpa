package com.romition.springboot;

import com.romition.springboot.entity.Author;
import com.romition.springboot.entity.Book;
import com.romition.springboot.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JpaTest {

	@Autowired
	AuthorRepository authorRepository;

	@Test
	public void testFind() {
		Optional<Author> authorOptional = authorRepository.findById(1L);
		authorOptional.ifPresent(System.out::println);
	}

	@Test
	@Transactional(readOnly = true)
	public void testGetOne() {
		// getOne 延迟加载
		// findById 立即加载
		Author author = authorRepository.getOne(1L);
		System.out.println(author);
		List<Book> books = author.getBooks();
		for (Book book : books) {
			System.out.println(book);
		}
	}

	@Test
	@Transactional(readOnly = true)
	public void findOne() {
		Optional<Author> authorOptional = authorRepository.findOne((root, query, criteriaBuilder) -> {
			Predicate p1 = criteriaBuilder.like(root.get("name"), "Name_0");
			Predicate p2 = criteriaBuilder.equal(root.get("age"), "18");
			return criteriaBuilder.or(p1, p2);
		});
		authorOptional.ifPresent(System.out::println);
	}

	@Test
	@Transactional(readOnly = true)
	public void findAll() {
		List<Author> authors = authorRepository.findAll((root, query, criteriaBuilder) -> {
			Predicate p1 = criteriaBuilder.like(root.get("name"), "Name_0");
			Predicate p2 = criteriaBuilder.equal(root.get("age"), "19");
			return criteriaBuilder.or(p1, p2);
		}, Sort.by(Sort.Direction.DESC, "age"));
		authors.forEach(System.out::println);
	}

	@Test
	@Transactional(readOnly = true)
	public void findAllByPage() {
		Page<Author> page = authorRepository.findAll(PageRequest.of(0, 10));
		page.getContent().forEach(System.out::println);
	}
}
