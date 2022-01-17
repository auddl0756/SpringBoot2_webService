package com.roon.springboot.service.book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void notNullTest() {
        System.out.println(bookService);
        assertThat(bookService).isNotNull();

    }

    @Test
    public void test() {
        Thread thread1 = new Thread(() -> {
            try {
                bookService.increaseStock("1", 5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, "Thread 1");

        Thread thread2 = new Thread(() -> {
            bookService.checkStock("1");
        }, "Thread 2");

        thread1.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        thread2.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}