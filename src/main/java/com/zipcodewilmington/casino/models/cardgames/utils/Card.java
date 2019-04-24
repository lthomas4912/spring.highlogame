package com.zipcodewilmington.casino.models.cardgames.utils;

import javax.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Rank rank;
    private Suit suit;

    public Card() {
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Integer getPrimaryValue() {
        return rank.getPrimaryValue();
    }

    public Integer getSecondaryValue() {
        return rank.getPrimaryValue();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return getRank().name() + " of " + getSuit().getCardGraphic();
    }

    public boolean equals(Card card) {
        return rank.equals(card.rank);
    }
}
