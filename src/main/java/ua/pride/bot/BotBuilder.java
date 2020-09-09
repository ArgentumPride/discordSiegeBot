package ua.pride.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.pride.listener.HelpListener;
import ua.pride.listener.SiegeListener;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.OnlineStatus.ONLINE;

@Component
public class BotBuilder {

    @Value("${bot.token}")
    private String BOT_TOKEN;

    private final SiegeListener siegeListener;
    private final HelpListener helpListener;

    public BotBuilder(SiegeListener siegeListener, HelpListener helpListener) {
        this.siegeListener = siegeListener;
        this.helpListener = helpListener;
    }

    @PostConstruct
    public void buildBot() throws LoginException {
        JDA jda = JDABuilder.createDefault(BOT_TOKEN)
                .setActivity(Activity.watching("your noob stat"))
                .setStatus(ONLINE)
                .addEventListeners(siegeListener, helpListener)
                .build();
    }
}
