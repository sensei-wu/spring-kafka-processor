package com.zen.lab.services.program.model;

public enum CompetitionType {
    BUNDESLIGA ("Bundesliga", SportType.FOOTBALL, RegionType.GERMANY),
    SECOND_BUNDESLIGA ("Second Bundesliga", SportType.FOOTBALL, RegionType.GERMANY),
    DFB_CUP ("DFB Cup", SportType.FOOTBALL, RegionType.GERMANY),
    SEASON_BET ("Season Bets", SportType.FOOTBALL, RegionType.CHAMPIONS_LEAGUE),
    PREMIER_LEAGUE ("Premier League", SportType.FOOTBALL, RegionType.ENGLAND),
    LEAGUE_ONE ("League One", SportType.FOOTBALL, RegionType.ENGLAND),
    LEAGUE_TWO ("League Two", SportType.FOOTBALL, RegionType.ENGLAND),
    DEL ("DEL", SportType.ICEHOCKEY, RegionType.GERMANY),
    NHL ("NHL", SportType.ICEHOCKEY, RegionType.USA),
    GRAND_SLAM ("Grand Slam", SportType.TENNIS, RegionType.ATP);

    private String name;
    private RegionType regionType;
    private SportType sportType;

    CompetitionType(String name, SportType sportType, RegionType regionType) {
        this.name = name;
        this.sportType = sportType;
        this.regionType = regionType;
    }

    public String getName() {
        return name;
    }

    public SportType getSportType() {
        return sportType;
    }

    public RegionType getRegionType() {
        return regionType;
    }
}
