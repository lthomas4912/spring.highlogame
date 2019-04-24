package com.zipcodewilmington.casino.models.cardgames;


import com.zipcodewilmington.casino.models.Account;
import com.zipcodewilmington.casino.models.cardgames.utils.Hand;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@MappedSuperclass
public class CardPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne (cascade = CascadeType.ALL)
    private Hand hand;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    public CardPlayer() {
        this(null, new Hand(), new Account());
    }

    public CardPlayer(Long id, Hand hand, Account account) {
        this.id = id;
        this.hand = hand;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setName(String name){
        this.account.setName(name);
    }

    public String getName(){
        return account.getName();
    }

    @Override
    public String toString() {


        return "CardPlayer{" +
                "id=" + id +
                ", hand=" + Arrays.toString(hand.getCardList().toArray()) +
                ", account=" + account +
                '}';
    }
}
