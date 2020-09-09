package ua.pride.listener;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;
import ua.pride.exception.NoUsernameException;
import ua.pride.exception.NotImplementedCommandException;
import ua.pride.exception.WrongUsernameException;
import ua.pride.model.AbstractSiegeStats;
import ua.pride.model.GlobalSiegeStats;
import ua.pride.service.MessageService;
import ua.pride.service.RankService;
import ua.pride.service.StatisticService;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SiegeListener extends ListenerAdapter {

    private static final String SIEGE_PREFIX = "-6 ";
    private static final List<String> commands = Arrays.asList("stat", "operator", "register", "help");

    public static final List<String> siegeOperators = Arrays.asList("ace", "melusi", "oryx", "iana", "wamai", "kali", "amaru", "goyo", "nokk", "warden", "mozzie", "gridlock", "nomad", "kaid",
            "clash", "maverick", "maestro", "alibi", "lion", "finka", "vigil", "dokkaebi", "zofia", "ela", "ying", "lesion", "mira", "jackal",
            "hibana", "echo", "caveira", "capitao", "blackbeard", "valkyrie", "buck", "frost", "mute", "sledge", "smoke", "thatcher", "ash", "castle", "pulse",
            "thermite", "montagne", "twitch", "doc", "rook", "jager", "bandit", "blitz", "iq", "fuze", "glaz", "tachanka", "kapkan");

    private final MessageService messageService;
    private final RankService rankService;
    private final StatisticService statisticService;

    public SiegeListener(StatisticService statisticService, MessageService messageService, RankService rankService) {
        this.messageService = messageService;
        this.rankService = rankService;
        this.statisticService = statisticService;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(SIEGE_PREFIX)) {
            String[] message = event.getMessage().getContentRaw().split("\\s");
            if (message[2] == null) {
                event.getChannel().sendMessage("Type username plz").queue();
                throw new NoUsernameException();
            }
            if (commands.contains(message[1])) {
                AbstractSiegeStats abstractSiegeStats = statisticService.getGlobalStatistic(message[2]);
                if (abstractSiegeStats.isEmpty()) {
                    event.getChannel().sendMessage("Wrong Uplay username").queue();
                    throw new WrongUsernameException();
                }
                GlobalSiegeStats globalSiegeStats = (GlobalSiegeStats) abstractSiegeStats;
                MessageEmbed playerStats = messageService.buildMainStatsMessage(globalSiegeStats);
                if (message[1].equals("stat")) {
                    event.getChannel().sendMessage(playerStats).queue();
                } else if (message[1].equals("register")) {
                    String userTag = Objects.requireNonNull(event.getMember()).getUser().getAsTag();
                    boolean success = rankService.registerNewUser(userTag.substring(userTag.indexOf('#') + 1), message[2], globalSiegeStats.getCurrentEuRank());
                    if (success)
                        event.getChannel().sendMessage("Congratulations, now you are registered!").queue();
                    else
                        event.getChannel().sendMessage("You already registered").queue();
                } else if (message[1].equals("operator") || siegeOperators.contains(message[3].toLowerCase())) {
                    MessageEmbed operatorStats = messageService.buildOperatorMessage(globalSiegeStats, message[3]);
                    event.getChannel().sendMessage(operatorStats).queue();
                }
            } else {
                event.getChannel().sendMessage("Unknown command").queue();
                throw new NotImplementedCommandException();
            }
        }
    }
}
