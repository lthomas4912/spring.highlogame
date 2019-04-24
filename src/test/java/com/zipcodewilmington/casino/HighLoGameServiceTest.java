package com.zipcodewilmington.casino;

import com.zipcodewilmington.casino.controllers.cardgames.highlo.PlayerChoice;
import com.zipcodewilmington.casino.models.cardgames.highlo.HighLoGame;
import com.zipcodewilmington.casino.models.cardgames.highlo.HighLoPlayer;
import com.zipcodewilmington.casino.models.cardgames.utils.*;
import com.zipcodewilmington.casino.repositories.cardgames.highlo.HighLoGameRepository;
import com.zipcodewilmington.casino.services.cardgames.highlo.HighLoGameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class HighLoGameServiceTest {
    private HighLoGameService service;
    private HighLoGameRepository repository;

    @Before
    public void setUp(){
        repository = mock(HighLoGameRepository.class);
        service = new HighLoGameService(repository);
    }

    @Test
    public void testMakeChoice_whenPlayerWinForHigh(){
        long player1Id = 19395325;
        long gameId = 1344;

        HighLoPlayer player = createPlayer(player1Id, new Card(Rank.TWO, Suit.DIAMONDS));

        System.out.println(player);

        Card nextCard = new Card(Rank.QUEEN, Suit.CLUBS);

        HighLoGame game = createGame(gameId, player, nextCard);

        Optional<HighLoGame> optionalGame = Optional.of(game);
        when(repository.findById(gameId)).thenReturn(optionalGame);

        // when
        PlayerChoice choice = new PlayerChoice(player1Id, "HI");
        HighLoResult actual = service.makeChoice(gameId, choice);

        String expected = "You win! Congratulations!";
        Assert.assertEquals(expected, actual.getResult(player));
    }

    @Test
    public void testMakeChoice_whenPlayerWinForLOW(){
        long player1Id = 19395325;
        long gameId = 1344;

        HighLoPlayer player = createPlayer(player1Id, new Card(Rank.QUEEN, Suit.DIAMONDS));
        Card nextCard = new Card(Rank.THREE, Suit.CLUBS);

        HighLoGame game = createGame(gameId, player, nextCard);

        Optional<HighLoGame> optionalGame = Optional.of(game);
        when(repository.findById(gameId)).thenReturn(optionalGame);

        // when
        PlayerChoice choice = new PlayerChoice(player1Id, "LO");
        HighLoResult actual = service.makeChoice(gameId, choice);

        String expected = "You win! Congratulations!";
        Assert.assertEquals(expected, actual.getResult(player));
    }

    @Test
    public void testMakeChoice_whenPlayerLoseForHigh(){
        long player1Id = 19395325;
        long gameId = 1344;

        HighLoPlayer player = createPlayer(player1Id, new Card(Rank.QUEEN, Suit.DIAMONDS));
        Card nextCard = new Card(Rank.FIVE, Suit.CLUBS);

        HighLoGame game = createGame(gameId, player, nextCard);

        Optional<HighLoGame> optionalGame = Optional.of(game);
        when(repository.findById(gameId)).thenReturn(optionalGame);

        // when
        PlayerChoice choice = new PlayerChoice(player1Id, "HI");
        HighLoResult actual = service.makeChoice(gameId, choice);

        String expected = "Whomps. You Lose";
        Assert.assertEquals(expected, actual.getResult(player));
    }

    @Test
    public void testMakeChoice_whenPlayerLoseForLOW(){
        long player1Id = 19395325;
        long gameId = 1344;

        HighLoPlayer player = createPlayer(player1Id, new Card(Rank.FIVE, Suit.DIAMONDS));
        Card nextCard = new Card(Rank.QUEEN, Suit.CLUBS);

        HighLoGame game = createGame(gameId, player, nextCard);

        Optional<HighLoGame> optionalGame = Optional.of(game);
        when(repository.findById(gameId)).thenReturn(optionalGame);

        // when
        PlayerChoice choice = new PlayerChoice(player1Id, "LO");
        HighLoResult actual = service.makeChoice(gameId, choice);

        String expected = "Whomps. You Lose";
        Assert.assertEquals(expected, actual.getResult(player));
    }

    @Test
    public void testMakeChoice_whenCardAreTheSame(){
        long player1Id = 19395325;
        long gameId = 1344;

        HighLoPlayer player = createPlayer(player1Id, new Card(Rank.FIVE, Suit.DIAMONDS));
        Card nextCard = new Card(Rank.FIVE, Suit.CLUBS);

        HighLoGame game = createGame(gameId, player, nextCard);

        Optional<HighLoGame> optionalGame = Optional.of(game);
        when(repository.findById(gameId)).thenReturn(optionalGame);

        // when
        PlayerChoice choice = new PlayerChoice(player1Id, "LO");
        HighLoResult actual = service.makeChoice(gameId, choice);

        String expected = "Draw";
        Assert.assertEquals(expected, actual.getResult(player));
    }

    private HighLoPlayer createPlayer(long playerId, Card card) {
        HighLoPlayer player = new HighLoPlayer();
        player.setId(playerId);
        player.getHand().addCard(card);

        return player;
    }

    private HighLoGame createGame(long gameId, HighLoPlayer player, Card nextCard) {

        List<Card> cards = new ArrayList<>();
        cards.add(nextCard);
        Deck deck = new Deck(cards);


        List<HighLoPlayer> players = new ArrayList<>();
        players.add(player);

        HighLoGame game = new HighLoGame(gameId, deck, players);

        return game;
    }


}
