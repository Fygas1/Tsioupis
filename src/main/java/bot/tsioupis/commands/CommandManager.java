package bot.tsioupis.commands;

import bot.tsioupis.commands.audio.*;
import bot.tsioupis.commands.random.PingCmd;
import bot.tsioupis.commands.random.SayCmd;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class CommandManager extends ListenerAdapter {
    private CommandClientBuilder builder;
    private CommandClient client;

    public CommandManager(){
        this.builder=new CommandClientBuilder();
        this.builder.setActivity(Activity.playing("Giro mou foties")).setOwnerId("206865924110811136").setPrefix("!");
        this.builder.addCommand(new SayCmd());
        this.builder.addSlashCommand(new PingCmd());
        this.builder.addSlashCommand(new PlayCmd()).addSlashCommand(new SkipCmd()).addSlashCommand(new StopCmd()).addSlashCommand(new NowPlayingCmd()).addSlashCommand(new QueueCmd());
        this.builder.setHelpConsumer((event) -> {
            StringBuilder message = new StringBuilder("**"+event.getSelfUser().getName()+"** commands:\n");
            Command.Category category = null;
            for(Command command : this.client.getCommands()){
                if(!command.isHidden() && (!command.isOwnerCommand() || event.isOwner())){
                    if(!Objects.equals(category, command.getCategory())){
                        category = command.getCategory();
                        message.append("\n\n  __").append(category==null ? "No Category" : category.getName()).append("__:\n");
                    }
                    message.append("\n`").append(this.client.getPrefix()).append(command.getName())
                            .append(command.getArguments()==null ? "`" : " "+command.getArguments()+"`")
                            .append(" - ").append(command.getHelp());
                }
            }
            System.out.println("String: "+this.client.getOwnerId());
            System.out.println(event.getJDA().getUserById(this.client.getOwnerId()).getName());
            User owner = event.getJDA().getUserById(this.client.getOwnerId());
            message.append("\n\nGia voithia stile minima tou: **").append(owner.getName()).append("**#").append(owner.getDiscriminator());
            message.append(" https://media.discordapp.net/attachments/929015523495280691/991962133380202526/unknown.png");
            event.replyInDm(message.toString());
        });
        this.client=this.builder.build();
    }

    public CommandClient getClient(){
        return this.client;
    }

}

