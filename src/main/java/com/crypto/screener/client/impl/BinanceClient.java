package com.crypto.screener.client.impl;

import com.crypto.screener.client.MarketDataClient;
import com.crypto.screener.client.dto.Candle;
import com.crypto.screener.client.dto.CandleInterval;
import com.crypto.screener.client.dto.MarketTicker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component("binanceClient")
public class BinanceClient implements MarketDataClient {
    private final WebClient web = WebClient.create("https://api.binance.com");

    @Override
    public Flux<MarketTicker> fetchAllTickers() {
        return web.get()
                .uri("/api/v3/ticker/24hr")
                .retrieve()
                .bodyToFlux(BinanceTicker.class)
                .map(bt -> new MarketTicker(bt.symbol(), bt.lastPrice(), bt.priceChangePercent()));
    }

    @Override
    public Flux<Candle> fetchCandles(String symbol, CandleInterval interval) {
        return web.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval.code())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<List<Object>>>() {})
                .flatMapMany(Flux::fromIterable)
                .map(raw -> {
                    long openTime = Long.parseLong(raw.get(0).toString());
                    BigDecimal open     = new BigDecimal(raw.get(1).toString());
                    BigDecimal high     = new BigDecimal(raw.get(2).toString());
                    BigDecimal low      = new BigDecimal(raw.get(3).toString());
                    BigDecimal close    = new BigDecimal(raw.get(4).toString());
                    long volume         = new BigDecimal(raw.get(5).toString()).longValue();

                    return new Candle(
                            Instant.ofEpochMilli(openTime),
                            open, high, low, close, volume
                    );
                });
    }


    private record BinanceTicker(String symbol, BigDecimal lastPrice, BigDecimal priceChangePercent) {
    }
}
