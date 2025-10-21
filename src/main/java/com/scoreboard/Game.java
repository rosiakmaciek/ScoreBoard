package com.scoreboard;

import java.time.LocalDateTime;

public class Game {

    private final String homeTeam;
    private final String awayTeam;

    private int homeScore;
    private int awayScore;

    private final LocalDateTime createdAt;

    public Game(final String homeTeam, final String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.homeScore = 0;
        this.awayScore = 0;

        createdAt = LocalDateTime.now();
    }

    public void setScore(final int homeScore, final int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return getHomeTeam() + " " + getHomeScore() + " - " + getAwayTeam() + " " + getAwayScore();
    }
}