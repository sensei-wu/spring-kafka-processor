package com.zen.lab.services.program.model;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class Outcome {
    private static AtomicLong outcomeId = new AtomicLong(0);
    private BetMarket betMarket;
    private String name;
    private BigDecimal odds;

    private Outcome() {
        outcomeId.incrementAndGet();
    }

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
}
