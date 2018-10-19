package com.zen.lab.services.program.model;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class Outcome {
    private Long outcomeId;
    private BetMarket betMarket;
    private String name;
    private BigDecimal odds;

    private Outcome() {}

    public static Builder builder() {
        return new Outcome.Builder();
    }

    public static class Builder {
        private Outcome instance = new Outcome();

        private Builder() {}

        public Builder forBetMarket(BetMarket betMarket) {
            this.instance.betMarket = betMarket;
            return this;
        }

        public Builder withName(String name) {
            this.instance.name = name;
            return this;
        }

        public Builder withOdds(BigDecimal odds) {
            this.instance.odds = odds;
            return this;
        }

        public Builder withRandomOdds(double bound) {
            this.instance.odds = new BigDecimal(ThreadLocalRandom.current().nextDouble(20));
            return this;
        }

        public Outcome build() {
            return instance;
        }
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "outcomeId=" + outcomeId +
                ", betMarket=" + betMarket +
                ", name='" + name + '\'' +
                ", odds=" + odds +
                '}';
    }
}
