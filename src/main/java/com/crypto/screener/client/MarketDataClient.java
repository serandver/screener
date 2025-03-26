package com.crypto.screener.client;

import com.crypto.screener.client.dto.Candle;
import com.crypto.screener.client.dto.CandleInterval;
import com.crypto.screener.client.dto.MarketTicker;
import reactor.core.publisher.Flux;
import java.time.Duration;

public interface MarketDataClient {
    /** Returns a stream of all tickers (symbol + price + change) */
    Flux<MarketTicker> fetchAllTickers();

    /** Returns OHLC “candles” for a single symbol */
    Flux<Candle> fetchCandles(String symbol, CandleInterval interval);
}
