package tetrdiscord;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import tetrnormal.Table;

public class Main {
    public static Table table = tetrnormal.Main.table;
    
    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        JDA jda = JDABuilder.createDefault("ODE1OTM3MTYxMDUyNDIyMTQ0.YDzqsg.LLtjokJOAS8uqiJPQzcAuIRhV18").setActivity(Activity.playing("being stupid")).build();
        jda.addEventListener(new EventListener());
        //launches actual app because current design is
        //sending image from app
        tetrnormal.Main.main(null);
    }

}
