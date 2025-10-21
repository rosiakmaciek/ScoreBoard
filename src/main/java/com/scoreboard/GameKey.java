package com.scoreboard;

public record GameKey(String homeTeam, String awayTeam) {
    public static GameKey of(final String home, final String away) {
        return new GameKey(home, away);
    }
}