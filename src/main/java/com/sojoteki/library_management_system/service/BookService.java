package com.sojoteki.library_management_system.service;

import com.sojoteki.library_management_system.model.Book;
import com.sojoteki.library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public String saveBook(Book book){
        bookRepository.save(book);
        return "Book saved successfully!!";
    }

    public Book getBookById(int bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        return book.orElse(null);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
