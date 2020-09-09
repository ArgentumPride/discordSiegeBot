package ua.pride.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Map;

@Component
@Getter
@Setter
@Accessors(chain = true)
public class GlobalSiegeStats extends AbstractSiegeStats {
    private String level;
    private String rankImageUrl;
    private String playerName;
    private String playerTabUrl;
    private String playerAvatarUrl;
    private String currentEuRank;
    private String maxSeasonRank;
    private String currentMmr;
    private String rankedKd;
    private String rankedWinrate;
    private String rankedTotalMatches;
    private String rankedSeasonMatches;
    private String rankedTimePlayed;
    private String mmrChange;
    private String casualKd;
    private String casualWinrate;
    private String casualTimePlayed;
    private String casualTotalMatches;
    private String mainDefender;
    private String mainAttacker;
    private Color fieldColor;
    private Map<String, OperatorSiegeStats> operators;
}
