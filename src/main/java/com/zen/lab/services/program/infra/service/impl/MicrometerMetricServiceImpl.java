package com.zen.lab.services.program.infra.service.impl;

import com.zen.lab.services.program.infra.service.MetricService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MicrometerMetricServiceImpl implements MetricService {

    private final MeterRegistry meterRegistry;

    @Autowired
    public MicrometerMetricServiceImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void recordLatency(String metricName, Duration duration, String... tags) {
        meterRegistry.timer(metricName).record(duration);
    }

    @Override
    public void increaseCounter(String metricName, String... tags) {
        meterRegistry.counter(metricName).increment();
    }

    @Override
    public void recordHistogram(String metricName, Duration duration, String... tags) {
        Timer timer = Timer.builder(metricName)
                .publishPercentiles(0.5, 0.9, 0.95, 0.99, 0.999)
                .publishPercentileHistogram()
                .minimumExpectedValue(Duration.ofMillis(1))
                .register(meterRegistry);
        timer.record(duration);
    }
}
