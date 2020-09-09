package ua.pride.service;

import com.google.gson.JsonElement;
import ua.pride.model.GlobalSiegeStats;

public interface JsonToModelService {

    GlobalSiegeStats getGlobalStats(JsonElement stats);
}
