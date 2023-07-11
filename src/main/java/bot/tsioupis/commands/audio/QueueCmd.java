package bot.tsioupis.commands.audio;

import bot.tsioupis.player.GuildMusicManager;
import bot.tsioupis.player.PlayerManager;
import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class QueueCmd extends SlashCommand {
    public QueueCmd(){
        this.name="queue";
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

        GuildMusicManager guildMusicManager= PlayerManager.get().getGuildMusicManager(event.getGuild());
        List<AudioTrack> queue=new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());

        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setTitle("Queue");
        if (queue.isEmpty()){
            embedBuilder.setDescription("eMPTY");
        }
        for (AudioTrack track: queue){
            AudioTrackInfo info =track.getInfo();
            embedBuilder.addField("1",info.title, false);
        }
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
