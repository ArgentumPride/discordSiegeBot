package ua.pride.listener;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;
import ua.pride.service.MessageService;

import javax.annotation.Nonnull;

@Service
public class HelpListener extends ListenerAdapter {

    public static final String HELP_PREFIX = "-6 help";

    private final MessageService messageService;

    public HelpListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equals(HELP_PREFIX)) {
            event.getChannel()
                    .sendMessage(messageService.buildHelpMessage().build()).queue();
        }
    }
}
