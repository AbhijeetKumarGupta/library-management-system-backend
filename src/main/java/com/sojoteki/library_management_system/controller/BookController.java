package com.sojoteki.library_management_system.controller;

import com.sojoteki.library_management_system.model.Book;
import com.sojoteki.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public String saveDoctor(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping("")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id){
        return bookService.getBookById(id);
    }
}
