package com.contatodireto.bibliotecaapi.controller;

import com.contatodireto.bibliotecaapi.model.Book;
import com.contatodireto.bibliotecaapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody @Valid Book book){
        return bookService.createBook(book);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> listBooks() { return bookService.listAvailableBooks(); }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book findById(@PathVariable Long id) { return bookService.findById(id); }
}
