package com.sojoteki.library_management_system.controller;

import com.sojoteki.library_management_system.model.Transaction;
import com.sojoteki.library_management_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/save")
    public String saveTransaction(@RequestBody Transaction transaction){
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping("")
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("{id}")
    public Transaction getTransactionById(@PathVariable int id){
        return transactionService.getTransactionById(id);
    }
}
