package ua.pride.service;

import com.google.gson.JsonElement;

public interface ApiRequesterService {
    JsonElement getSiegeStats(String username);
}
