package com.zipcodewilmington.casino.controllers.cardgames.highlo;


import com.zipcodewilmington.casino.models.cardgames.highlo.HighLoGame;
import com.zipcodewilmington.casino.models.cardgames.highlo.HighLoPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "games/highlo")
public class GamePlayerController {

    @Autowired
    private GamePlayerService gameService;

    @PutMapping(value = "/{gameId}/players")
    public ResponseEntity<HighLoGame> addPlayers(@PathVariable long gameId, @RequestBody List<Long> playerIds) {
        return new ResponseEntity<>(gameService.addPlayers(gameId, playerIds), HttpStatus.OK);

    }

    @PostMapping(value = "/{gameId}/players/new")
    public ResponseEntity<HighLoGame> createPlayers(@PathVariable long gameId, @RequestBody HighLoPlayer player) {
        return new ResponseEntity<>(gameService.createPlayer(gameId, player), HttpStatus.CREATED);

    }

}
