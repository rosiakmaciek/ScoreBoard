package com.scoreboard;

import java.util.*;

public class ScoreBoard {

    private final Map<GameKey, Game> gamesMap;

    public ScoreBoard() {
        gamesMap = new HashMap<>();
    }

    public void startGame(final String homeTeam, final String awayTeam) {
        GameValidator.validateStartGame(homeTeam, awayTeam, gamesMap);
        gamesMap.put(GameKey.of(homeTeam, awayTeam), new Game(homeTeam, awayTeam));

    }

    public void finishGame(final String homeTeam, final String awayTeam) {
        GameValidator.validateFinishGame(homeTeam, awayTeam, gamesMap);
        gamesMap.remove(GameKey.of(homeTeam, awayTeam));
    }

    public void updateScore(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
        GameValidator.validateUpdateScore(homeTeam, awayTeam, homeScore, awayScore, gamesMap);
        gamesMap.get(GameKey.of(homeTeam, awayTeam)).setScore(homeScore, awayScore);
    }

    public List<Game> getSummary() {
        return gamesMap.values().stream()
                .sorted(Comparator
                        .comparingInt((Game g) -> g.getHomeScore() + g.getAwayScore()).reversed()
                        .thenComparing(Game::getCreatedAt, Comparator.reverseOrder()))
                .toList();
    }
}