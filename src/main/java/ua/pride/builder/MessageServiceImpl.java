package ua.pride.builder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.pride.model.GlobalSiegeStats;
import ua.pride.model.OperatorSiegeStats;
import ua.pride.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String MAIN_STATS = "Main stats ";
    private static final String CASUAL_STATS = "Casual stats ";
    private static final String RANKED_STATS = "Ranked stats ";
    private static final String TOP_OPERATORS = "Top operators ";
    private static final String WINRATE = "\n**Winrate: **";
    private static final String KD = "\n**K/D: **";
    private static final String HOURS = "\n**Hours: **";
    private static final String OVERALL = "Overall: ";
    private static final String SEASONAL = "Seasonal: ";
    private static final String PROFILE_IMAGE = "https://cdn.discordapp.com/avatars/374234758357712897/b35a81a27664a0da882d94a0df365788.png?size=128";
    private static final String OPERATOR_URL = "https://r6operators.marcopixel.eu/icons/png/";
    private static final String RANK_IMAGE_URL = "https://tabstats.com/images/r6/ranks/?rank=";
    private static final String IMAGE_FORMAT = "/default_146_146.png";

    @Value("${avatar.url}")
    private String AVATAR_URL;
    @Value("${r6.tab.url}")
    private String R6_TAB_URL;

    @Override
    public MessageEmbed buildMainStatsMessage(GlobalSiegeStats stats) {
        EmbedBuilder message = new EmbedBuilder();

        message.setThumbnail(RANK_IMAGE_URL + stats.getRankImageUrl());

        message.setAuthor(
                getAuthorName(stats.getPlayerName()),
                getProfileUrl(stats.getPlayerTabUrl()),
                getAvatarUrl(stats.getPlayerAvatarUrl()));

        message.addField(MAIN_STATS,
                "**Level: **" + stats.getLevel() +
                        "\n**Current rank: **" + stats.getCurrentEuRank() +
                        " (" + stats.getCurrentMmr() + " MMR) (±" + stats.getMmrChange() + ")" +
                        "\n**Max season rank: **" + stats.getMaxSeasonRank(), false);

        message.addField(RANKED_STATS,
                "**Total matches: **" + stats.getRankedTotalMatches() +
                        "\n**Season matches: **" + stats.getRankedSeasonMatches() +
                        HOURS + stats.getRankedTimePlayed() +
                        KD + stats.getRankedKd() +
                        WINRATE + stats.getRankedWinrate(), false);

        message.addField(CASUAL_STATS,
                "**Total matches: **" + stats.getCasualTotalMatches() +
                        HOURS + stats.getCasualTimePlayed() +
                        KD + stats.getCasualKd() +
                        WINRATE + stats.getCasualWinrate(), false);

        message.addField(TOP_OPERATORS,
                "**Attacker: **" + stats.getMainAttacker() +
                        "\n**Defender: **" + stats.getMainDefender(), false);
        message.setColor(stats.getFieldColor());
        message.setFooter("Want this bot to your channel go to /*URL*/", PROFILE_IMAGE);
        return message.build();
    }

    @Override
    public MessageEmbed buildOperatorMessage(GlobalSiegeStats stats, String operatorName) {
        EmbedBuilder message = new EmbedBuilder();
        String operatorNameLower = operatorName.toLowerCase();

        message.setThumbnail(OPERATOR_URL + operatorNameLower + ".png");

        message.setAuthor(
                getAuthorName(stats.getPlayerName()),
                getProfileUrl(stats.getPlayerTabUrl()),
                getAvatarUrl(stats.getPlayerAvatarUrl()));

        OperatorSiegeStats operator = stats.getOperators().get(operatorNameLower);

        message.addField(OVERALL,
                KD + operator.getOverallOperatorKd() +
                        HOURS + operator.getOverallOperatorHours() +
                        WINRATE + operator.getOverallWinrate(), true);

        message.addField(SEASONAL,
                KD + operator.getSeasonalOperatorKd() +
                        HOURS + operator.getSeasonalOperatorHours() +
                        WINRATE + operator.getSeasonalWinrate(), true);
        message.setFooter("Want this bot to your channel go to /*URL*/", PROFILE_IMAGE);
        return message.build();
    }

    @Override
    public EmbedBuilder buildRankMessage(String rank) {
        EmbedBuilder message = new EmbedBuilder();
        message.setAuthor("You got role according to your rank: " + rank);
        return message;
    }

    @Override
    public EmbedBuilder buildHelpMessage() {
        EmbedBuilder message = new EmbedBuilder();

        message.setAuthor("Click to contact with me",
                "https://steamcommunity.com/id/ArgentumPride/",
                PROFILE_IMAGE);

        message.addField("**Available commands**",
                "-6 stat [*uplayNickname*] - Main statistic about player\n" +
                        "-6 operator [*operatorName*] [*nickname*]- Particular operator statistic\n" +
                        "-6 register [*uplayNickname*] - Special feature" , false);
        message.setFooter("");
        return message;
    }

    private String getAuthorName(String playerName) {
        return playerName + "  (Click!)";
    }

    private String getProfileUrl(String profileUrl) {
        return R6_TAB_URL + profileUrl;
    }

    private String getAvatarUrl(String avatarUrl) {
        return AVATAR_URL + avatarUrl + IMAGE_FORMAT;
    }

}
