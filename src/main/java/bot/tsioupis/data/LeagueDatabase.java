package bot.tsioupis.data;


import kotlin.contracts.Returns;

import java.util.HashMap;

public class LeagueDatabase {

    private static LeagueDatabase singleInstance=null;
    private static HashMap<String, String> countries;
    private static HashMap<Integer, String> league;
    private static HashMap<String, String > team;

    private LeagueDatabase(){
        countries=new HashMap<String, String>();
        countries.put("england","https://www.transfermarkt.com/premier-league/startseite/wettbewerb/GB1");
        countries.put("greece", "https://www.transfermarkt.com/super-league-1/startseite/wettbewerb/GR1");
        countries.put("cyprus", "https://www.transfermarkt.com/protathlima-cyta/startseite/wettbewerb/ZYP1");
        league=new HashMap<Integer, String>();
        team=new HashMap<String, String>();
    }

    public HashMap<String, String > getCountries(){
        return countries;
    }
    public HashMap<Integer, String > getLeague(){
        return league;
    }

    public HashMap<String, String> getTeam(){
        return team;
    }

    public static LeagueDatabase getInstance(){
        if (singleInstance==null) singleInstance=new LeagueDatabase();
        return singleInstance;
    }
}
