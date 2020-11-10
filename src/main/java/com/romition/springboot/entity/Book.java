package com.romition.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String title;
	private String isbn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Author author;

}
