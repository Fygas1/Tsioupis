package bot.tsioupis.commands.audio;

import bot.tsioupis.player.GuildMusicManager;
import bot.tsioupis.player.PlayerManager;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class SkipCmd extends SlashCommand {
    public SkipCmd() {
        this.name="skip";
        this.help="skip track";
    }

    @Override
    public List<OptionData> getOptions() {
        return super.getOptions();
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
            event.reply("Not in  acall");
            return;
        }

        if (selfVoiceState.getChannel() != memberVoiceState.getChannel()){
            event.reply("not in same channel");
        }

        GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
        guildMusicManager.getTrackScheduler().getPlayer().stopTrack();
        event.reply("Skipped").queue();
    }
}
