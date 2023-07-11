package bot.tsioupis.commands.random;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class PingCmd extends SlashCommand {

    public PingCmd() {
        this.name = "gay"; // This has to be lowercase
        this.help = "too many gays";
    }

    @Override
    public void execute(SlashCommandEvent event) {
        Member self=event.getGuild().getSelfMember();
        event.reply("poushti").queue();
    }
}
