package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.managers.ArenaManager;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.IOException;

@Command({"tnttag", "tt"})
public class ReloadSubCommand {

    private final Tnttag plugin;
    private final ArenaManager arenaManager;

    public ReloadSubCommand(Tnttag plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @Subcommand("reload")
    @CommandPermission("tnttag.reload")
    public void onReload(Player player) throws IOException {
        Tnttag.customizationfile.reload();
        Tnttag.configfile.reload();
        Tnttag.scoreboardFile.reload();
        arenaManager.reload();
        plugin.getItemManager().reload();

        ChatUtils.sendMessage(player, "commands.files-reloaded");
    }
}