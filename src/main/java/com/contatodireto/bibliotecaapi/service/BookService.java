package com.contatodireto.bibliotecaapi.service;

import com.contatodireto.bibliotecaapi.model.Book;
import com.contatodireto.bibliotecaapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> listAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    } // change to a customizable exception.

}
