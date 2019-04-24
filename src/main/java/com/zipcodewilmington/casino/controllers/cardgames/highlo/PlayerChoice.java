package com.zipcodewilmington.casino.controllers.cardgames.highlo;

public class PlayerChoice {
    private String choice;
    private long playerId;

    public PlayerChoice(long playerId, String choice) {
        this.playerId = playerId;
        this.choice = choice;
    }

    public PlayerChoice() {
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
