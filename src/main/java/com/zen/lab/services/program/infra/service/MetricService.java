package com.zen.lab.services.program.infra.service;

import java.time.Duration;

public interface MetricService {
    void recordLatency(String metricName, Duration duration, String... tags);
    void increaseCounter(String metricName, String... tags);
    void recordHistogram(String metricName, Duration duration, String... tags);
}
