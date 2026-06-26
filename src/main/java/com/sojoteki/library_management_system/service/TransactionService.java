package com.sojoteki.library_management_system.service;

import com.sojoteki.library_management_system.model.Transaction;
import com.sojoteki.library_management_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public String saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
        return "Transaction saved successfully";
    }
}
