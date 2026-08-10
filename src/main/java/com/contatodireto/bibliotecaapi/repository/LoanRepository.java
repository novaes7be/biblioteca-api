package com.contatodireto.bibliotecaapi.repository;

import com.contatodireto.bibliotecaapi.model.Loan;
import com.contatodireto.bibliotecaapi.model.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByUserAndReturnDateIsNull(User user);
}
