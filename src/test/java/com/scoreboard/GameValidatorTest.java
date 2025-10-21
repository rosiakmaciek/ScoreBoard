package com.scoreboard;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameValidatorTest {

    @Test
    void shouldAllowValidGameStart() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        assertDoesNotThrow(() -> GameValidator.validateStartGame("Poland", "Germany", gamesMap));
    }

    @Test
    void shouldRejectInvalidHomeTeamStart() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateStartGame("Notacountry", "Poland", new HashMap<>())
        );

        assertTrue(ex.getMessage().contains("Invalid home team: Notacountry"));
    }

    @Test
    void shouldRejectInvalidAwayTeamStart() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateStartGame("Germany", "Notacountry", new HashMap<>())
        );

        assertTrue(ex.getMessage().contains("Invalid away team: Notacountry"));
    }

    @Test
    void shouldRejectSameTeam() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateStartGame("Uruguay", "Uruguay", new HashMap<>())
        );

        assertTrue(ex.getMessage().contains("Home and away team are the same: Uruguay"));
    }

    @Test
    void shouldRejectGameAlreadyStarted() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Italy", "Sweden"), new Game("Italy", "Sweden"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateStartGame("Italy", "Sweden", gamesMap)
        );

        assertTrue(ex.getMessage().contains("Game already started: Italy vs Sweden"));
    }

    @Test
    void shouldReturnMultipleErrorMessagesStart() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateStartGame("Notacountry", "Neverland", new HashMap<>())
        );

        assertTrue(ex.getMessage().contains("Invalid home team: Notacountry"));
        assertTrue(ex.getMessage().contains("Invalid away team: Neverland"));
    }

    @Test
    void shouldAllowValidGameFinish() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Poland", "Germany"), new Game("Poland", "Germany"));

        assertDoesNotThrow(() -> GameValidator.validateFinishGame("Poland", "Germany", gamesMap));
    }

    @Test
    void shouldNotFinishNotStartedGame() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Australia", "New Zealand"), new Game("Australia", "New Zealand"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateFinishGame("Russia", "China", gamesMap)
        );

        assertTrue(ex.getMessage().contains("Russia vs China is not on the score board - it cannot be removed"));
    }

    @Test
    void shouldAllowUpdateGame() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Australia", "New Zealand"), new Game("Australia", "New Zealand"));

        assertDoesNotThrow(() -> GameValidator.validateUpdateScore("Australia", "New Zealand", 1, 0, gamesMap));
    }

    @Test
    void shouldNotUpdateNotStartedGame() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Australia", "New Zealand"), new Game("Australia", "New Zealand"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateUpdateScore("Russia", "China", 1, 2, gamesMap)
        );

        assertTrue(ex.getMessage().contains("Russia vs China is not on the score board - it cannot be updated"));
    }

    @Test
    void shouldNotUpdateNegativeHomeScore() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Australia", "New Zealand"), new Game("Australia", "New Zealand"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateUpdateScore("Australia", "New Zealand", -1, 2, gamesMap)
        );

        assertTrue(ex.getMessage().contains("Home score is negative: -1"));
    }

    @Test
    void shouldNotUpdateNegativAwayScore() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Australia", "New Zealand"), new Game("Australia", "New Zealand"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateUpdateScore("Australia", "New Zealand", 2, -2, gamesMap)
        );

        assertTrue(ex.getMessage().contains("Away score is negative: -2"));
    }

    @Test
    void shouldReturnMultipleErrorMessagesUpdate() {
        Map<GameKey, Game> gamesMap = new HashMap<>();
        gamesMap.put(GameKey.of("Australia", "New Zealand"), new Game("Australia", "New Zealand"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> GameValidator.validateUpdateScore("China", "Russia", -1, -2, gamesMap)
        );

        assertTrue(ex.getMessage().contains("China vs Russia is not on the score board - it cannot be updated. Home score is negative: -1. Away score is negative: -2"));
    }
}