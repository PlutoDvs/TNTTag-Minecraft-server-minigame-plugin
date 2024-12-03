package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.gui.ArenaSelector;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"tnttag", "tt"})
public class JoinGUISubCommand {

    private final Tnttag plugin;

    public JoinGUISubCommand(Tnttag plugin) {
        this.plugin = plugin;
    }

    @Subcommand("joingui")
    @CommandPermission("tnttag.gui.join")
    public void onGUIJoin(Player player) {
        new ArenaSelector(plugin, player).open();
    }
}