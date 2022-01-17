package com.roon.springboot.service.book;

import com.roon.springboot.domain.book.*;
import com.roon.springboot.web.exception.NoStockException;
import com.roon.springboot.web.exception.NotEnoughMoneyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookStockRepository bookStockRepository;
    private final AccountRepository accountRepository;


//    @Transactional
    @Transactional(propagation = Propagation.REQUIRED)  // 기존 트랙잭션이 있다면, 기존 트랜잭션이 주도함
    public void purchase(String username, String bookNumber){
        // 1. 책 가격 조회
        // 2. 통장 잔고 조회
        // 3. 살 수 있으면 책 사기 , 책 재고 줄이기, 통장 잔고 줄이기

        Book book = bookRepository.findById(bookNumber)
                .orElseThrow(()-> new IllegalArgumentException("해당 책 번호 없음"));

        int price = book.getPrice();

        Account account = accountRepository.findById(username)
                .orElseThrow(()-> new IllegalArgumentException("해당 계좌 아이디 없음"));

        int balance = account.getBalance();

        BookStock bookStock = bookStockRepository.findById(bookNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 책 번호의 재고 정보 없음"));


        // 이런식으로도 하는지..?
        if(price > balance){
            throw new NotEnoughMoneyException();
        }

        if(bookStock.getStock() <= 0 ){
            throw new NoStockException();
        }

        account.pay(price);
        bookStock.sell(1);
    }

    @Transactional
    public void checkout(List<String> bookNumbers, String username){
        for(String bookNumber : bookNumbers){
            purchase(bookNumber,username);
        }
    }
}
