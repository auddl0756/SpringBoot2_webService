package com.roon.springboot.service.book;

import com.roon.springboot.domain.book.*;
import com.roon.springboot.web.exception.NoStockException;
import com.roon.springboot.web.exception.NotEnoughMoneyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookStockRepository bookStockRepository;
    private final AccountRepository accountRepository;


    //    @Transactional
    @Transactional(propagation = Propagation.REQUIRES_NEW)  //무조건 새 트랜잭션 만들기 (기존 트랜잭션은 잠시 중단)  =>  꽤 좋을듯?
//    @Transactional(propagation = Propagation.REQUIRED)  // 기존 트랙잭션이 있다면, 기존 트랜잭션이 주도함
    public void purchase(String username, String bookNumber) throws NotEnoughMoneyException, NoStockException {
        Book book = bookRepository.findById(bookNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 책 번호 없음"));

        int price = book.getPrice();

        Account account = accountRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌 아이디 없음"));

        int balance = account.getBalance();

        BookStock bookStock = bookStockRepository.findById(bookNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 책 번호의 재고 정보 없음"));

        account.pay(price);
        bookStock.sell(1);
    }

    @Transactional
    public void checkout(List<String> bookNumbers, String username) {
        for (String bookNumber : bookNumbers) {
            purchase(bookNumber, username);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)  //default
    public void increaseStock(String bookNumber, int count) throws EntityNotFoundException {
        String threadName = Thread.currentThread().getName();

        BookStock bookStock = bookStockRepository.getOne(bookNumber);

        bookStock.add(count);
        System.out.println(threadName + " book stock increased by " + count +", so now stock = "+bookStock.getStock());

        bookStockRepository.save(bookStock);
        bookStockRepository.flush();

        //sleep(threadName);

        //System.out.println(threadName + " rolled back");
        //throw new RuntimeException("increased by mistake");

        System.out.println(threadName + " has been committed");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int checkStock(String bookNumber) {
        String threadName = Thread.currentThread().getName();

        BookStock bookStock = bookStockRepository.getOne(bookNumber);
        System.out.println(threadName + " 첫번째 조회, book stock = " + bookStock.getStock());

        sleep(threadName);

        System.out.println(threadName + " 두번째 조회, book stock = " + bookStock.getStock());

        return bookStock.getStock();
    }

    private void sleep(String threadName) {
        System.out.println(threadName + " is sleeping");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(threadName + " is wake up");
    }
}
