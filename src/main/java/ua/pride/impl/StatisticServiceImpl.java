package ua.pride.impl;

import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;
import ua.pride.model.AbstractSiegeStats;
import ua.pride.model.EmptyGlobalSiegeStats;
import ua.pride.service.ApiRequesterService;
import ua.pride.service.JsonToModelService;
import ua.pride.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final ApiRequesterService apiRequesterService;
    private final JsonToModelService jsonToModelService;

    public StatisticServiceImpl(ApiRequesterService apiRequesterService, JsonToModelService jsonToModelService) {
        this.apiRequesterService = apiRequesterService;
        this.jsonToModelService = jsonToModelService;
    }

    @Override
    public AbstractSiegeStats getGlobalStatistic(String uplayUsername) {
        JsonElement globalStats = apiRequesterService.getSiegeStats(uplayUsername);
        if (!globalStats.isJsonNull())
            return jsonToModelService.getGlobalStats(globalStats);
        else
            return EmptyGlobalSiegeStats.INSTANCE;
    }
}
