package com.sojoteki.library_management_system.service;

import com.sojoteki.library_management_system.model.Transaction;
import com.sojoteki.library_management_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public String saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
        return "Transaction saved successfully";
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int transactionId){
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        return transaction.orElse(null);
    }
}
