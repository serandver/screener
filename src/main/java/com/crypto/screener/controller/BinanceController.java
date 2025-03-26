package com.crypto.screener.controller;

import com.crypto.screener.client.MarketDataClient;
import com.crypto.screener.client.dto.Candle;
import com.crypto.screener.client.dto.CandleInterval;
import com.crypto.screener.client.dto.MarketTicker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/binance")
public class BinanceController {

    private final MarketDataClient marketClient;

    public BinanceController(@Qualifier("binanceClient") MarketDataClient marketClient) {
        this.marketClient = marketClient;
    }

    /**
     * GET all spot tickers (symbol, price, 24h change)
     */
    @GetMapping("/markets")
    public Flux<MarketTicker> getAllMarkets() {
        return marketClient.fetchAllTickers();
    }

    /**
     * GET historical candles for a symbol.
     * interval must be one of H1, H4, D1, W1
     */
    @GetMapping("/markets/{symbol}/candles")
    public Flux<Candle> getCandles(
            @PathVariable String symbol,
            @RequestParam CandleInterval interval
    ) {
        return marketClient.fetchCandles(symbol.toUpperCase(), interval);
    }
}
