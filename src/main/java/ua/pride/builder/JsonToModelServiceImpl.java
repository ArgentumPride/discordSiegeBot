package ua.pride.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.pride.listener.SiegeListener;
import ua.pride.model.GlobalSiegeStats;
import ua.pride.model.OperatorSiegeStats;
import ua.pride.service.JsonToModelService;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static ua.pride.util.JsonUtils.getDoubleFromJsonObject;
import static ua.pride.util.JsonUtils.getStringFromJsonObject;

@Service
public class JsonToModelServiceImpl implements JsonToModelService {

    @Override
    public GlobalSiegeStats getGlobalStats(JsonElement stats) {
        GlobalSiegeStats globalSiegeStats = new GlobalSiegeStats();
        JsonObject statsJsonObject = stats.getAsJsonObject();

        globalSiegeStats.setOperators(getOperatorStats(stats));

        return globalSiegeStats
                .setRankImageUrl(getStringFromJsonObject(statsJsonObject.getAsJsonObject("ranked"), "NA_rank") + "&champ=")
                .setCurrentEuRank(getStringFromJsonObject(statsJsonObject.getAsJsonObject("ranked"), "rankname"))
                .setMaxSeasonRank(getStringFromJsonObject(statsJsonObject.getAsJsonObject("ranked"), "maxrankname"))
                .setCurrentMmr(getStringFromJsonObject(statsJsonObject.getAsJsonObject("ranked"), "EU_actualmmr"))
                .setMmrChange(getStringFromJsonObject(statsJsonObject.getAsJsonObject("ranked"), "EU_mmrchange"))
                .setRankedSeasonMatches(getStringFromJsonObject(statsJsonObject.getAsJsonObject("ranked"), "EU_matches"))

                .setPlayerName(getStringFromJsonObject(statsJsonObject.getAsJsonObject("player"), "p_name"))
                .setPlayerTabUrl(getStringFromJsonObject(statsJsonObject.getAsJsonObject("player"), "p_id"))
                .setPlayerAvatarUrl(getStringFromJsonObject(statsJsonObject.getAsJsonObject("player"), "p_user"))

                .setLevel(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "level"))
                .setRankedKd(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "rankedpvp_kd"))
                .setRankedWinrate(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "rankedpvp_wl"))
                .setRankedTimePlayed(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "rankedpvp_hoursplayed"))
                .setRankedTotalMatches(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "rankedpvp_matches"))
                .setCasualKd(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "casualpvp_kd"))
                .setCasualWinrate(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "casualpvp_wl"))
                .setCasualTimePlayed(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "casualpvp_hoursplayed"))
                .setCasualTotalMatches(getStringFromJsonObject(statsJsonObject.getAsJsonObject("stats"), "casualpvp_matches"))

                .setMainAttacker(getStringFromJsonObject(statsJsonObject.getAsJsonObject("op_main"), "attacker"))
                .setMainDefender(getStringFromJsonObject(statsJsonObject.getAsJsonObject("op_main"), "defender"))

                .setFieldColor(getColor(globalSiegeStats.getCurrentEuRank()));
    }

    private Map<String, OperatorSiegeStats> getOperatorStats(JsonElement stats) {
        JsonObject statsObject = stats.getAsJsonObject();

        Map<String, OperatorSiegeStats> operatorStatsMap = new HashMap<>();

        SiegeListener.siegeOperators
                .forEach(operator -> operatorStatsMap.put(operator, getParticularOperator(operator, statsObject)));
        return operatorStatsMap;
    }

    private OperatorSiegeStats getParticularOperator(String operator, JsonObject stats) {
        JsonObject overall = stats.getAsJsonObject("operators").getAsJsonObject(StringUtils.capitalize(operator)).getAsJsonObject("overall");
        JsonObject seasonal = stats.getAsJsonObject("operators").getAsJsonObject(StringUtils.capitalize(operator)).getAsJsonObject("seasonal");

        return new OperatorSiegeStats()
                .setOverallOperatorKd(getStringFromJsonObject(overall, "kd"))
                .setOverallOperatorHours(secondsToHours(getDoubleFromJsonObject(overall, "timeplayed")))
                .setOverallWinrate(getStringFromJsonObject(overall, "winrate") + "%")
                .setSeasonalOperatorKd(getStringFromJsonObject(seasonal, "kd"))
                .setSeasonalOperatorHours(secondsToHours(getDoubleFromJsonObject(seasonal, "timeplayed")))
                .setSeasonalWinrate(getStringFromJsonObject(seasonal, "winrate") + "%");
    }

    private String secondsToHours(Double seconds) {
        return new DecimalFormat("####.#").format(seconds / 3600);
    }

    private Color getColor(String rank) {
        if (rank.startsWith("Cooper")) {
            return new Color(117, 15, 8);
        } else if (rank.startsWith("Bronze")) {
            return new Color(180, 127, 50);
        } else if (rank.startsWith("Silver")) {
            return Color.LIGHT_GRAY;
        } else if (rank.startsWith("Gold")) {
            return Color.YELLOW;
        } else if (rank.startsWith("Platinum")) {
            return new Color(70, 140, 255);
        } else if (rank.equals("Diamond")) {
            return new Color(107, 0, 183);
        } else if (rank.startsWith("Champion"))
            return Color.RED;
        else
            return Color.DARK_GRAY;
    }

}
