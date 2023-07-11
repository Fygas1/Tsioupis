package bot.tsioupis.commands.audio;

import bot.tsioupis.player.PlayerManager;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayCmd extends SlashCommand{

    public PlayCmd(){
        this.name="play";
        this.help="h manasou";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options=new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "name", "name of the song to play", true));
        return options;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        Member member=event.getMember();
        GuildVoiceState memberVoiceState=member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()){
            event.reply("empa call prota").queue();
            return;
        }
        Member self=event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState= self.getVoiceState();

        if (!selfVoiceState.inAudioChannel()){
            //join
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        }
        else {
            if (selfVoiceState.getChannel()!=memberVoiceState.getChannel()){
                event.reply("fdsfad").queue();
                return;
            }
        }
        PlayerManager playerManager =PlayerManager.get();
        event.reply("Playing").queue();
        playerManager.play(event.getGuild(), event.getOption("name").getAsString());
    }
}
