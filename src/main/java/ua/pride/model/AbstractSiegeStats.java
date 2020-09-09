package ua.pride.model;

public abstract class AbstractSiegeStats {

    public boolean isEmpty() {
        return this instanceof EmptyGlobalSiegeStats;
    }
}
