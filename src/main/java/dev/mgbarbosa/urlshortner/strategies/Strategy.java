package dev.mgbarbosa.urlshortner.strategies;

public interface Strategy<TOut> {
    TOut execute();
}
