package com.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldStartGame() {
        scoreBoard.startGame("Norway", "Denmark");
        scoreBoard.startGame("Canada", "Sweden");

        assertEquals(2, scoreBoard.getSummary().size());
    }

    @Test
    void shouldEndGame() {
        scoreBoard.startGame("Romania", "Serbia");
        scoreBoard.startGame("Canada", "Sweden");

        assertEquals(2, scoreBoard.getSummary().size());

        scoreBoard.finishGame("Romania", "Serbia");
        assertEquals(1, scoreBoard.getSummary().size());
        assertEquals("Canada", scoreBoard.getSummary().getFirst().getHomeTeam());
        assertEquals("Sweden", scoreBoard.getSummary().getFirst().getAwayTeam());

        scoreBoard.finishGame("Canada", "Sweden");
        assertEquals(0, scoreBoard.getSummary().size());
    }

    @Test
    void shouldUpdateScore() {
        scoreBoard.startGame("Namibia", "Chad");

        scoreBoard.updateScore("Namibia", "Chad", 1, 2);
        assertEquals(1, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(2, scoreBoard.getSummary().getFirst().getAwayScore());

        scoreBoard.updateScore("Namibia", "Chad", 3, 5);
        assertEquals(3, scoreBoard.getSummary().getFirst().getHomeScore());
        assertEquals(5, scoreBoard.getSummary().getFirst().getAwayScore());
    }

    @Test
    void shouldProvideCorrectSummary() throws InterruptedException {
        scoreBoard.startGame("Belgium", "Greece");
        Thread.sleep(10);

        scoreBoard.startGame("Netherlands", "Spain");
        Thread.sleep(10);

        scoreBoard.startGame("Poland", "Russia");
        Thread.sleep(10);

        scoreBoard.startGame("Germany", "France");
        Thread.sleep(10);

        scoreBoard.updateScore("Germany", "France", 1, 3);
        scoreBoard.updateScore("Belgium", "Greece", 1, 0);

        scoreBoard.updateScore("Poland", "Russia", 0, 1);
        scoreBoard.updateScore("Poland", "Russia", 1, 1);
        scoreBoard.updateScore("Poland", "Russia", 2, 1);
        scoreBoard.updateScore("Poland", "Russia", 3, 1);
        scoreBoard.updateScore("Poland", "Russia", 4, 1);

        scoreBoard.updateScore("Netherlands", "Spain", 1, 4);

        List<Game> summary = scoreBoard.getSummary();

        assertEquals("Poland", summary.get(0).getHomeTeam());
        assertEquals("Russia", summary.get(0).getAwayTeam());
        assertEquals(4, summary.get(0).getHomeScore());
        assertEquals(1, summary.get(0).getAwayScore());

        assertEquals("Netherlands", summary.get(1).getHomeTeam());
        assertEquals("Spain", summary.get(1).getAwayTeam());
        assertEquals(1, summary.get(1).getHomeScore());
        assertEquals(4, summary.get(1).getAwayScore());

        assertEquals("Germany", summary.get(2).getHomeTeam());
        assertEquals("France", summary.get(2).getAwayTeam());
        assertEquals(1, summary.get(2).getHomeScore());
        assertEquals(3, summary.get(2).getAwayScore());

        assertEquals("Belgium", summary.get(3).getHomeTeam());
        assertEquals("Greece", summary.get(3).getAwayTeam());
        assertEquals(1, summary.get(3).getHomeScore());
        assertEquals(0, summary.get(3).getAwayScore());
    }

    @Test
    void shouldAllowScoreCorrection() throws InterruptedException {
        scoreBoard.startGame("Belgium", "Greece");
        Thread.sleep(10);

        scoreBoard.startGame("Italy", "Romania");
        Thread.sleep(10);

        scoreBoard.startGame("Netherlands", "Spain");
        Thread.sleep(10);

        scoreBoard.startGame("Poland", "Russia");
        Thread.sleep(10);

        scoreBoard.updateScore("Belgium", "Greece", 150, 150);
        scoreBoard.updateScore("Italy", "Romania", 100, 200);

        scoreBoard.updateScore("Poland", "Russia", 0, 1);
        scoreBoard.updateScore("Poland", "Russia", 1, 1);
        scoreBoard.updateScore("Poland", "Russia", 2, 1);
        scoreBoard.updateScore("Poland", "Russia", 2, 2);
        scoreBoard.updateScore("Poland", "Russia", 2, 3);
        scoreBoard.updateScore("Poland", "Russia", 0, 0);

        scoreBoard.updateScore("Netherlands", "Spain", 0, 1);

        List<Game> summary = scoreBoard.getSummary();

        assertEquals("Italy", summary.get(0).getHomeTeam());
        assertEquals("Romania", summary.get(0).getAwayTeam());
        assertEquals(300, summary.get(0).getHomeScore() + summary.get(0).getAwayScore());

        assertEquals("Belgium", summary.get(1).getHomeTeam());
        assertEquals("Greece", summary.get(1).getAwayTeam());
        assertEquals(300, summary.get(1).getHomeScore() + summary.get(1).getAwayScore());

        assertEquals("Netherlands", summary.get(2).getHomeTeam());
        assertEquals("Spain", summary.get(2).getAwayTeam());
        assertEquals(1, summary.get(2).getHomeScore() + summary.get(2).getAwayScore());

        assertEquals("Poland", summary.get(3).getHomeTeam());
        assertEquals("Russia", summary.get(3).getAwayTeam());
        assertEquals(0, summary.get(3).getHomeScore() + summary.get(3).getAwayScore());
    }

    @Test
    void shouldDisplayMatchesCorrectly() {
        scoreBoard.startGame("Peru", "Mexico");
        scoreBoard.startGame("Ireland", "Jamaica");

        scoreBoard.updateScore("Peru", "Mexico", 0, 5);
        scoreBoard.updateScore("Ireland", "Jamaica", 4, 3);

        List<Game> summary = scoreBoard.getSummary();

        assertEquals("Ireland 4 - Jamaica 3", summary.get(0).toString());
        assertEquals("Peru 0 - Mexico 5", summary.get(1).toString());
    }
}