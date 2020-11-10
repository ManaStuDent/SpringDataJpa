package com.romition.springboot.repository;

import com.romition.springboot.entity.Author;
import com.romition.springboot.projection.AuthorNameAge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	Author findByName(String name);

	List<AuthorNameAge> findFirst2ByGenre(String genre);
}
