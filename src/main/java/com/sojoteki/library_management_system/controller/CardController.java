package com.sojoteki.library_management_system.controller;

import com.sojoteki.library_management_system.model.Card;
import com.sojoteki.library_management_system.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/save")
    public String saveCard(@RequestBody Card card){
        return cardService.saveCard(card);
    }
}
