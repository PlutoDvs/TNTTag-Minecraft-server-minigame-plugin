package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Arena;
import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.managers.ArenaManager;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.List;
import java.util.stream.Collectors;

@Command({"tnttag", "tt"})
public class ListSubCommand {

    private final ArenaManager arenaManager;

    public ListSubCommand(Tnttag plugin) {
        this.arenaManager = plugin.getArenaManager();
    }

    @Subcommand("list")
    @CommandPermission("tnttag.list")
    public void onList(Player player) {
        List<Arena> arenas = arenaManager.getArenaObjects();
        if (arenas.toArray().length == 0) {
            ChatUtils.sendMessage(player, "commands.no-available-arenas");
            return;
        }

        String arenaNames = arenas.stream()
                .map(Arena::getName)
                .collect(Collectors.joining());
        ChatUtils.sendCustomMessage(player, ChatUtils.getRaw("commands.available-arenas").replace("{arenas}", arenaNames));
    }
}