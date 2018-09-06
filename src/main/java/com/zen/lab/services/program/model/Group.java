package com.zen.lab.services.program.model;

public class Group {
    private long id;
    private String name;
    private Group parent;
    private SportType sportType;
    private RegionType regionType;
    private CompetitionType competitionType;

    private Group() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Group getParent() {
        return parent;
    }

    public SportType getSportType() {
        return sportType;
    }

    public static Builder builder() {
        return new Group.Builder();
    }

    public static class Builder {
        private Group instance = new Group();

        private Builder() {}

        public Builder withId(long id) {
            this.instance.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public Builder fromParent(Group parent) {
            this.instance.parent = parent;
            return this;
        }

        public Builder ofSportType(SportType sportType) {
            this.instance.sportType = sportType;
            return this;
        }

        public Builder inRegion(RegionType regionType) {
            this.instance.regionType = regionType;
            return this;
        }

        public Builder inCompetition(CompetitionType competitionType) {
            this.instance.competitionType = competitionType;
            return this;
        }

        public Group build() {
            return instance;
        }
    }
}
