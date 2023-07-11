package bot.tsioupis;

import bot.tsioupis.commands.CommandManager;
import bot.tsioupis.news.alphanews;
import bot.tsioupis.news.kerkida;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;


import javax.security.auth.login.LoginException;
import java.io.*;


public class Run{

    private static String key=null;

    private static String getKey(){
        String text=null;
        BufferedReader brTest = null;
        try {
            brTest = new BufferedReader(new FileReader("/ULTRA_SECRET.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            text = brTest .readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        key=text;
        return key;
    }

    private static JDA start() {
        System.out.println("Loading");
        JDA bot = null;
        try {
            bot = JDABuilder.createDefault(getKey())
                    .enableCache(CacheFlag.VOICE_STATE)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES,GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new CommandManager().getClient())
                    .build().awaitReady();
            System.out.println("created instances");
        } catch (InterruptedException e) {
            System.out.println("JDA can't be found.");
        }
        return bot;
    }

    public static void main(String args[]) throws LoginException {
        JDA bot=start();
        //Message.suppressContentIntentWarning();
        kerkida kerkida=new kerkida(bot.getTextChannelById("994251916026597497")); //test
        //kerkida kerkida=new kerkida(bot.getTextChannelById("674334562574270546")); //main

        Thread t = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        kerkida.sendMessage();
                        Thread.sleep(10*60000);

                    } catch (InterruptedException ie) {
                    }
                }
            }
        };
        t.start();
        //System.out.println(new File(".").getAbsolutePath());
        //System.out.println("done.");
        //bot.getTextChannelById("994251916026597497").sendMessage("efsf").queue();
    }
}
