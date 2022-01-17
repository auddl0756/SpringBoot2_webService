package com.roon.springboot.domain.book;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class BookStock {
    @Id
    private String bookNumber;
    private int stock;

    public void sell(int count) {
        stock -= count;
    }
}
