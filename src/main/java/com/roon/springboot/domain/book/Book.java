package com.roon.springboot.domain.book;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Book {
    @Id
    private String bookNumber;

    private String name;
    private int price;
}
