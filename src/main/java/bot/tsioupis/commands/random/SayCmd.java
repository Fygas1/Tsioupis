package bot.tsioupis.commands.random;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class SayCmd extends Command {
    public SayCmd() {
        this.name = "say";
        this.aliases = new String[]{"speak", "s", "talk", "pe"};
        this.help = "na me se kofti";
    }

    @Override
    protected void execute(CommandEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        String message = event.getArgs();
        event.getMessage().delete().queue();
        channel.sendMessage(message).queue();
    }
}