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

    @Autowired
    private BookService_JDBC bookServiceJdbc;

    @Test
    public void notNullTest() {
        assertThat(bookService).isNotNull();
    }

    @Test
    public void REPEATABLE_READ_JDBC_test(){
        Thread t1 = new Thread(() ->{
            bookServiceJdbc.checkStock("1");
        },"Thread 1");

        Thread t2 = new Thread(()->{
            try{
                bookServiceJdbc.increaseStock("1",5);
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }
        },"Thread 2");

        t1.start();

        try{
            Thread.sleep(2000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            t2.start();
        }

        try {
            Thread.sleep(10000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void REPEATABLE_READ_test(){
        Thread t1 = new Thread(() ->{
            bookService.checkStock("1");
        },"Thread 1");

        Thread t2 = new Thread(()->{
            try{
                bookService.increaseStock("1",5);
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }
        },"Thread 2");

        t1.start();

        try{
            Thread.sleep(2000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            t2.start();
        }

        try {
            Thread.sleep(10000);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void DIRTY_READ_test() {
        // dirty read??? READ_COMMITTED??? ???????????? ???????????? ?????????.
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