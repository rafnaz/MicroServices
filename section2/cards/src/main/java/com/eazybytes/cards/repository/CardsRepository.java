package com.eazybytes.cards.repository;

import com.eazybytes.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

    /**
     * @param mobileNumber
     * @return Cards
     */
    Optional<Cards> findCardByMobileNumber(String mobileNumber);

    Optional<Cards> findCardByCardNumber(String cardNumber);
}
