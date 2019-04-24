package com.zipcodewilmington.casino.services.cardgames.highlo;

import com.zipcodewilmington.casino.controllers.cardgames.highlo.PlayerChoice;
import com.zipcodewilmington.casino.models.cardgames.highlo.HighLoGame;
import com.zipcodewilmington.casino.models.cardgames.highlo.HighLoPlayer;
import com.zipcodewilmington.casino.models.cardgames.utils.Card;
import com.zipcodewilmington.casino.models.cardgames.utils.Deck;
import com.zipcodewilmington.casino.models.cardgames.utils.HighLoResult;
import com.zipcodewilmington.casino.repositories.cardgames.highlo.HighLoGameRepository;
import com.zipcodewilmington.springutils.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HighLoGameService extends AbstractService<HighLoGame, Long> {


    @Autowired
    public HighLoGameService(HighLoGameRepository repository) {
        super(repository);
    }

    @Override
    public HighLoGame update(Long gameId, HighLoGame newHighLoGame) {
        HighLoGame originalHighLoGame = super.read(gameId);
        originalHighLoGame.setId(newHighLoGame.getId());
        originalHighLoGame.setDeck(newHighLoGame.getDeck());
        return super.repository.save(originalHighLoGame);
    }

    public HighLoGame create() {
        HighLoGame game = new HighLoGame();
        game.getDeck().shuffle();
        super.create(game);
        return game;
    }

    public HighLoGame dealCard(Long id) {
        HighLoGame game = repository.findById(id).get();

        Deck deck = game.getDeck();

        for(HighLoPlayer player : game.getPlayerList()) {
            player.getHand().addCard(deck.pop());
        }

        repository.save(game);

        return game;
    }

    /**
     * This method compares the first card and second card value
     *
     * @param firstCard It takes the value of the firstCard
     * @param secondCard It takes the value of the secondCard
     * @return true if the second card is less than the first one
     */

    public boolean isLess(Card firstCard, Card secondCard) {
//        HighLoGame game = repository.findById(id).get();
//
//        for(HighLoPlayer p: game.getPlayerList()){
//            while(p.getHand() != null && p.getHand().getCardList().size() == 2)
//           firstCard = p.getHand().getCardList().get(0);
//           secondCard = p.getHand().getCardList().get(1);
//        }

        Integer firstCardValue = firstCard.getRank().getPrimaryValue();
        Integer secondCardValue = secondCard.getRank().getPrimaryValue();

        return firstCardValue < secondCardValue;
    }



    public boolean isMore(Card firstCard, Card secondCard) {
        return !isLess(firstCard, secondCard);
    }
//
//    public void winOrLose(Long id, String input) {
//        /*
//        for(Player p : Game){
//        if(choiceMethod equals High && isMore(id,firstCard,secondCard) ||
//            (choiceMethod equals Low && isLess(id,firstCard,secondCard)){
//            sout("Winner");
//            } else {
//            sout("Loser");
//            }
//            clear Hand;
//            }
//
//           */
//
//    }
//
//    public void resultOfGame( Long id, Card firstCard, Card secondCard){
//        HighLoGame game = repository.findById(id).get();
//        System.out.println("HI or LO: Choose one");
//        Scanner scanner = new Scanner(System.in);
//        String userInput = scanner.nextLine();
//
//        Integer firstCardValue = firstCard.getRank().getPrimaryValue();
//        Integer secondCardValue = secondCard.getRank().getPrimaryValue();
//
//
//        if(userInput.toUpperCase().equals("HI") ||userInput.toUpperCase().equals("LO") && isLess(id,firstCard, secondCard)){
//            System.out.println("You win! Congratulations!");
//        } else {
//            isMore(id, firstCard, secondCard);
//            System.out.println("Whomps. You Lose");
//        }
//
//    }

    public HighLoResult makeChoice(Long gameId, PlayerChoice... choices) {
        HighLoGame game = repository.findById(gameId).get();
        Card nextCard = game.getDeck().pop();
        Map<Long, String> choiceMap = Arrays.stream(choices)
                                        .collect(Collectors.toMap(PlayerChoice::getPlayerId,
                                                                  PlayerChoice::getChoice));

        HighLoResult result = new HighLoResult(nextCard);
        for(HighLoPlayer player : game.getPlayerList()){
            String playerChoice = choiceMap.get(player.getId());
            String resultString = getResultString(playerChoice, player, nextCard);
            result.addResult(player, resultString);
        }

        repository.save(game);
        return result;
    }

    private String getResultString(String choice, HighLoPlayer player, Card nextCard) {
        List<Card> hand = player.getHand().getCardList();
        Card playerCard = hand.get(hand.size() - 1);

        String resultString = null;
        if (playerCard.equals(nextCard)) {
            resultString = "Draw";
        } else if (isLess(playerCard, nextCard) && "HI".equalsIgnoreCase(choice)) {
            resultString = "You win! Congratulations!";
        } else if (isLess(playerCard, nextCard) && "LO".equalsIgnoreCase(choice)) {
            resultString = "Whomps. You Lose";
        } else if (isMore(playerCard, nextCard) && "HI".equalsIgnoreCase(choice)) {
            resultString = "Whomps. You Lose";
        } else {
            resultString = "You win! Congratulations!";
        }
        return resultString;
    }

}
