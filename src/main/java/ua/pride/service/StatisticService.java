package ua.pride.service;

import ua.pride.model.AbstractSiegeStats;

public interface StatisticService {
    AbstractSiegeStats getGlobalStatistic(String uplayUsername);
}
