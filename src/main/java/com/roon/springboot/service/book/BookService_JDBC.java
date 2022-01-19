package com.roon.springboot.service.book;

import com.roon.springboot.domain.book.BookStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class BookService_JDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void increaseStock(String bookNumber, int count) throws EntityNotFoundException {
        String threadName = Thread.currentThread().getName();

        jdbcTemplate.update("update book_stock set stock = stock + "+count+" where book_number = "+bookNumber);

        int stock = jdbcTemplate.queryForObject("select stock from book_stock where book_number = "+bookNumber,Integer.class);
        System.out.println(threadName + " book stock increased by " + count +", so now stock = " + stock);

        System.out.println(threadName + " has been committed");
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int checkStock(String bookNumber) {
        String threadName = Thread.currentThread().getName();

        int stock = jdbcTemplate.queryForObject("select stock from book_stock where book_number = "+bookNumber,Integer.class);
        System.out.println(threadName + " 첫번째 조회, book stock = " + stock);

        sleep(threadName);

        stock = jdbcTemplate.queryForObject("select stock from book_stock where book_number = "+bookNumber,Integer.class);
        System.out.println(threadName + " 두번째 조회, book stock = " + stock);

        return stock;
    }

    private void sleep(String threadName) {
        System.out.println(threadName + " is sleeping");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(threadName + " is wake up");
    }
}
