package com.roon.springboot.domain.book;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Account {
    @Id
    private String username;

    private int balance;    //통장 잔고

    public void pay(int price) {
        balance -= price;
    }
}
