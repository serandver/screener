package com.crypto.screener.client.dto;

import java.math.BigDecimal;

public record MarketTicker(String symbol, BigDecimal lastPrice, BigDecimal priceChangePercent) {}
