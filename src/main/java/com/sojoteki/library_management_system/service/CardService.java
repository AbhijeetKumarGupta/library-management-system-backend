package com.sojoteki.library_management_system.service;

import com.sojoteki.library_management_system.model.Card;
import com.sojoteki.library_management_system.repository.CardRepository;
import com.sojoteki.library_management_system.request_dto.CardRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public String saveCard(CardRequestDto cardRequestDto){
        Card card = new Card();
        card.setCardStatus(cardRequestDto.getCardStatus());
        card.setExpiryDate(cardRequestDto.getExpiryDate());

        cardRepository.save(card);

        return "Card saved successfully";
    }

    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public Card getCardById(int cardId){
        Optional<Card> card = cardRepository.findById(cardId);
        if(card.isPresent()){
            return card.get();
        }else{
            throw new RuntimeException("Card with id " + cardId + " not found");
        }
    }

    public String updateCard(int cardId, CardRequestDto cardRequestDto){
        Card card = getCardById(cardId);

        if(card != null){
            card.setCardStatus(cardRequestDto.getCardStatus());
            card.setExpiryDate(cardRequestDto.getExpiryDate());
            cardRepository.save(card);
            return "Card updated successfully";
        }

        throw new RuntimeException("Card with id " + cardId + " not found");
    }

    public String deleteCard(int cardId){
        Card card = getCardById(cardId);

        if(card != null){
            cardRepository.delete(card);
            return "Card deleted successfully";
        }

        throw new RuntimeException("Card with id " + cardId + " not found");
    }
}
