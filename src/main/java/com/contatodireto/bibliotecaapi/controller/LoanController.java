package com.contatodireto.bibliotecaapi.controller;


import com.contatodireto.bibliotecaapi.model.Loan;
import com.contatodireto.bibliotecaapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(userId, bookId));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnLoan(@PathVariable Long id) {
        loanService.returnLoan(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
