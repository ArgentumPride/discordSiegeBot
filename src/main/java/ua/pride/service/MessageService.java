package ua.pride.service;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import ua.pride.model.GlobalSiegeStats;

public interface MessageService {

    MessageEmbed buildMainStatsMessage(GlobalSiegeStats stats);

    MessageEmbed buildOperatorMessage(GlobalSiegeStats stats, String operatorName);

    EmbedBuilder buildHelpMessage();

    EmbedBuilder buildRankMessage(String rank);
}
