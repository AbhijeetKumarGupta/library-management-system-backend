package com.sojoteki.library_management_system.controller;

import com.sojoteki.library_management_system.model.Card;
import com.sojoteki.library_management_system.request_dto.CardRequestDto;
import com.sojoteki.library_management_system.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCard(@RequestBody CardRequestDto cardRequestDto){
        try {
            String response = cardService.saveCard(cardRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass()+":\n"+"Save operation failed - "+e.getMessage());
        }
    }

    @GetMapping("")
    public List<Card> getAllCards(){
        return cardService.getAllCards();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCardById(@PathVariable int id){
        try {
            Card card = cardService.getCardById(id);
            return ResponseEntity.status(HttpStatus.OK).body(card);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getClass()+":\n"+"Fetch card by id failed - "+e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCard(@PathVariable int id, @RequestBody CardRequestDto cardRequestDto){
        try{
            String response = cardService.updateCard(id, cardRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getClass()+":\n"+"Update card with id failed - "+e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable int id){
        try{
            String response = cardService.deleteCard(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getClass()+":\n"+"Delete card with id failed - "+e.getMessage());
        }
    }
}
