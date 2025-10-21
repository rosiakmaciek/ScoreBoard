package com.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameValidator {
    public static void validateStartGame(final String homeTeam, final String awayTeam, final Map<GameKey, Game> scoreBoard) {
        List<String> errors = new ArrayList<>();

        if (!isCountryValid(homeTeam)) errors.add("Invalid home team: " + homeTeam);
        if (!isCountryValid(awayTeam)) errors.add("Invalid away team: " + awayTeam);
        if (homeTeam.equals(awayTeam)) errors.add("Home and away team are the same: " + homeTeam);
        if (scoreBoard.containsKey(GameKey.of(homeTeam, awayTeam)))
            errors.add("Game already started: " + homeTeam + " vs " + awayTeam);

        throwIfErrors(errors);
    }

    public static void validateFinishGame(final String homeTeam, final String awayTeam, final Map<GameKey, Game> scoreBoard) {
        if (!scoreBoard.containsKey(GameKey.of(homeTeam, awayTeam)))
            throw new IllegalArgumentException(homeTeam + " vs " + awayTeam + " is not on the score board - it cannot be removed");
    }

    public static void validateUpdateScore(final String homeTeam, final String awayTeam,
                                           final int homeScore, final int awayScore,
                                           final Map<GameKey, Game> scoreBoard) {
        List<String> errors = new ArrayList<>();

        if (!scoreBoard.containsKey(GameKey.of(homeTeam, awayTeam)))
            errors.add(homeTeam + " vs " + awayTeam + " is not on the score board - it cannot be updated");

        if (homeScore < 0) errors.add("Home score is negative: " + homeScore);
        if (awayScore < 0) errors.add("Away score is negative: " + awayScore);

        throwIfErrors(errors);
    }

    private static void throwIfErrors(List<String> errors) {
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(". ", errors));
        }
    }

    private static boolean isCountryValid(final String country) {
        return CountryUtils.countries.contains(country);
    }
}
