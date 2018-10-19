package com.zen.lab.services.program.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.concurrent.atomic.AtomicLong;

public class BetMarket {
    private Long betmarketId;
    private BetMarketType betMarketType;
    @JsonIgnore
    private Event event;
    private BetMarketStatus betMarketStatus;

    private BetMarket() {}

    public static Builder builder() {
        return new BetMarket.Builder();
    }

    public long getBetmarketId() { return betmarketId; }

    public BetMarketType getBetMarketType() {
        return betMarketType;
    }

    public Event getEvent() {
        return event;
    }

    public void register(Event event) {
        this.event = event;
    }

    public static class Builder {

        private BetMarket instance = new BetMarket();

        private Builder() {}

        public Builder ofMarketType(BetMarketType type) {
            this.instance.betMarketType = type;
            return this;
        }

        public Builder withStatus(BetMarketStatus status) {
            this.instance.betMarketStatus = status;
            return this;
        }

        public Builder forEvent(Event event) {
            this.instance.event = event;
            return this;
        }

        public BetMarket build() {
            return instance;
        }
    }

    @Override
    public String toString() {
        return "BetMarket{" +
                "betmarketId=" + betmarketId +
                ", betMarketType=" + betMarketType +
                ", event=" + event +
                ", betMarketStatus=" + betMarketStatus +
                '}';
    }
}
