package com.sojoteki.library_management_system.controller;

import com.sojoteki.library_management_system.model.Book;
import com.sojoteki.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public String saveDoctor(@RequestBody Book book){
        return bookService.saveBook(book);
    }
}
