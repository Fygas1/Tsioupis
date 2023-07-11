package bot.tsioupis.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

    private TrackScheduler trackScheduler;
    private AudioForwarder audioForwarder;

    public GuildMusicManager(AudioPlayerManager manager){
        AudioPlayer player=manager.createPlayer();
        this.trackScheduler=new TrackScheduler(player);
        player.addListener(this.trackScheduler);
        this.audioForwarder=new AudioForwarder(player);
    }

    public TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    public AudioForwarder getAudioForwarder() {
        return audioForwarder;
    }
}
