package com.zen.lab.services.program.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Event {
    private Long fixtureId;
    private SportType sportType;
    private String home;
    private String away;
    private List<BetMarket> marketList = new ArrayList<>();
    private Group group;
    private Provider provider;
    private Instant startsAt;

    private Event() {}

    public long getFixtureId() {
        return fixtureId;
    }

    public SportType getSportType() {
        return sportType;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

    public List<BetMarket> getMarketList() {
        return marketList;
    }

    public Group getGroup() {
        return group;
    }

    public Provider getProvider() {
        return provider;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public static Builder builder() {
        return new Event.Builder();
    }

    public static class Builder {
        private Event instance = new Event();

        private Builder() {}

        public Builder ofSportType(SportType sportType) {
            this.instance.sportType = sportType;
            return this;
        }

        public Builder withParticipants(String home, String away) {
            this.instance.home = home;
            this.instance.away = away;
            return this;
        }

        public Builder ofGroup(Group group) {
            this.instance.group = group;
            return this;
        }

        public Builder fromProvider(Provider provider) {
            this.instance.provider = provider;
            return this;
        }

        public Builder whichStartsAt(Instant startsAt) {
            this.instance.startsAt = startsAt;
            return this;
        }

        public Builder withMarkets(List<BetMarket> markets) {
            this.instance.marketList = markets;
            for (BetMarket market :
                    markets) {
                market.register(instance);
            }
            return this;
        }

        public Event build() {
            return instance;
        }

    }

    @Override
    public String toString() {
        return "Event{" +
                "fixtureId=" + fixtureId +
                ", sportType=" + sportType +
                ", home='" + home + '\'' +
                ", away='" + away + '\'' +
                ", marketList=" + marketList +
                ", group=" + group +
                ", provider=" + provider +
                ", startsAt=" + startsAt +
                '}';
    }
}
