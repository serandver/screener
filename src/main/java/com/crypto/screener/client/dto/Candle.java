package com.crypto.screener.client.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record Candle(Instant openTime,
                     BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close,
                     long volume) {}
