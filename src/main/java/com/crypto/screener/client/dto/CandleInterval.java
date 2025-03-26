package com.crypto.screener.client.dto;

import java.time.Duration;
import java.util.Arrays;

public enum CandleInterval {
    H1(Duration.ofHours(1), "1h"),
    H4(Duration.ofHours(4), "4h"),
    D1(Duration.ofDays(1), "1d"),
    W1(Duration.ofDays(7), "1w");

    private final Duration duration;
    private final String code;

    CandleInterval(Duration duration, String code) {
        this.duration = duration;
        this.code = code;
    }

    public Duration duration() { return duration; }
    public String code() { return code; }

    public static CandleInterval from(Duration dur) {
        return Arrays.stream(values())
                .filter(i -> i.duration.equals(dur))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported interval: " + dur));
    }
}
