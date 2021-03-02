package tetrdiscord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tetrnormal.Table;

public class EventListener extends ListenerAdapter {
    String intToEmoji(int n) {
        String x1 = "";
        switch (n) {
        case 0:
            x1 = "🟥";
            break;
        case 1:
            x1 = "🟧";
            break;
        case 2:
            x1 = "🟨";
            break;
        case 3:
            x1 = "🟩";
            break;
        case 4:
            x1 = "🟦";
            break;
        case 5:
            x1 = "🟫";
            break;
        case 6:
            x1 = "🟪";
            break;
        case 7:
            x1 = "⬛";
            break;
        case 8:
            x1 = "❎";
            break;
        case 16:
            x1 = "⬜";
            break;

        }
        return x1;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
        }
        // shamelessly copy pasted
        // Event specific information
        User author = e.getAuthor(); // The user that sent the message
        Message message = e.getMessage(); // The message that was received.
        MessageChannel channel = e.getChannel(); // This is the MessageChannel that the message was sent to.

        String msg = message.getContentDisplay(); // This returns a human readable version of the Message. Similar to

        if (e.isFromType(ChannelType.TEXT)) // If this message was sent to a Guild TextChannel
        {
            // Because we now know that this message was sent in a Guild, we can do guild
            // specific things
            // Note, if you don't check the ChannelType before using these methods, they
            // might return null due
            // the message possibly not being from a Guild!

            Guild guild = e.getGuild(); // The Guild that this message was sent in. (note, in the API, Guilds are
                                        // Servers)
            TextChannel textChannel = e.getTextChannel(); // The TextChannel that this message was sent to.
            Member member = e.getMember(); // This Member that sent the message. Contains Guild specific information
                                           // about the User!

            String name;
            if (message.isWebhookMessage()) {
                name = author.getName(); // If this is a Webhook message, then there is no Member associated
            } // with the User, thus we default to the author for name.
            else {
                name = member.getEffectiveName(); // This will either use the Member's nickname if they have one,
            } // otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
        }

        if (msg.equals("tetris")) {
            StringBuilder sb = new StringBuilder();
            int[][] stage = Main.table.getStage();
            for (int i = 24; i < Table.STAGESIZEY; i++) {
                for (int j = 0; j < Table.STAGESIZEX; j++) {
                    sb.append(intToEmoji(stage[i][j]));
                }
                sb.append("\n");
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription(sb.toString());
            eb.setColor(Table.intToColor(Main.table.getCurrentPieceInt()));
            eb.setAuthor("TETR v1.4", "https://www.spigotmc.org/resources/tetr.84269/", null);
            channel.sendMessage(eb.build()).queue();
        }

        if (msg.equalsIgnoreCase("tetr")) {

            new Thread() {
                @Override
                public void run() {
                    channel.sendMessage(new EmbedBuilder()
                            .setAuthor("TETR v1.4", "https://www.spigotmc.org/resources/tetr.84269/", null).build())
                            .queue(message -> {
                                while (!Main.table.getGameover()) {
                                    StringBuilder sb = new StringBuilder();
                                    int[][] stage = Main.table.getStage();
                                    for (int i = 24; i < Table.STAGESIZEY; i++) {
                                        for (int j = 0; j < Table.STAGESIZEX; j++) {
                                            sb.append(intToEmoji(stage[i][j]));
                                        }
                                        sb.append("\n");
                                    }
                                    EmbedBuilder eb = new EmbedBuilder();
                                    eb.setDescription(sb.toString());
                                    eb.setColor(Table.intToColor(Main.table.getCurrentPieceInt()));
                                    eb.setAuthor("TETR v1.4", "https://www.spigotmc.org/resources/tetr.84269/", null);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    message.editMessage(eb.build()).queue();
                                }
                                message.editMessage("GAME OVER").queue();
                            });
                }
            }.start();
        }

    }
}
