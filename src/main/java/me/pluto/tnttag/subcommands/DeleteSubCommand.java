package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Arena;
import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.managers.ArenaManager;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.IOException;

@Command({"tnttag", "tt"})
public class DeleteSubCommand {

    private final Tnttag plugin;
    private final ArenaManager arenaManager;

    public DeleteSubCommand(Tnttag plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @Subcommand("delete")
    @CommandPermission("tnttag.delete")
    public void onDelete(Player player, Arena arena) throws IOException {
        arenaManager.deleteArena(arena.getName());
        ChatUtils.sendMessage(arena, player, "commands.arena-deleted");
    }
}