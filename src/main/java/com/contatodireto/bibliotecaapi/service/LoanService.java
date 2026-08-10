package com.contatodireto.bibliotecaapi.service;

import com.contatodireto.bibliotecaapi.model.Book;
import com.contatodireto.bibliotecaapi.model.Loan;
import com.contatodireto.bibliotecaapi.model.User;
import com.contatodireto.bibliotecaapi.repository.BookRepository;
import com.contatodireto.bibliotecaapi.repository.LoanRepository;
import com.contatodireto.bibliotecaapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoanService {

    final BookRepository bookRepository;
    final LoanRepository loanRepository;
    final UserRepository userRepository;

    @Transactional
    public Loan createLoan(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if (!book.getAvailable()) {
            throw new RuntimeException("The book is not available");
        }
        if (loanRepository.countByUserAndReturnDateIsNull(user) >= 3) {
            throw new RuntimeException("This user has already three loans");
        }
        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan();

        loan.setUser(user);
        loan.setBook(book);

        loan.setLoanDate(LocalDateTime.now());
        loan.setReturnDate(null);
        loanRepository.save(loan);

        return loan;
    }
    @Transactional
    public void returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        Book book = loan.getBook();

        loan.setReturnDate(LocalDateTime.now());
        book.setAvailable(true);

        loanRepository.save(loan);
        bookRepository.save(book);
    }
} //Transactional used because of the two operations of save on both methods.