package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.List;

@Command({"tnttag", "tt"})
public class HelpSubCommand {

    @Subcommand("help")
    @CommandPermission("tnttag.help")
    public void execute(Player player) {
        List<String> help_menu = Tnttag.customizationfile.getStringList("help-menu");

        for (String message : help_menu) {
            ChatUtils.sendCustomMessage(player, message);
        }
    }
}