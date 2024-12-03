package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Arena;
import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.managers.ArenaManager;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"tnttag", "tt"})
public class RandomJoinSubCommand {

    private final Tnttag plugin;
    private final ArenaManager arenaManager;

    public RandomJoinSubCommand(Tnttag plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @Subcommand({"randomjoin", "autojoin"})
    @CommandPermission("tnttag.join")
    public void onJoin(Player player) {
        if (!Tnttag.configfile.getBoolean("global-lobby") && !plugin.getLobbyManager().playerIsInLobby(player)) {
            if (!plugin.getLobbyManager().enterLobby(player, false)) return;
        }

        if (arenaManager.playerIsInArena(player)) {
            ChatUtils.sendMessage(player, "player.already-in-game");
            return;
        }

        for (Arena randomArena : arenaManager.getArenaObjects()) {
            if (!randomArena.getGameManager().isRunning()) {
                if (randomArena.getGameManager().playerManager.getPlayers().size() < randomArena.getMaxPlayers()) {
                    randomArena.getGameManager().playerManager.addPlayer(player);
                    return;
                }
            }
        }

        ChatUtils.sendMessage(player, "commands.no-available-arenas");
    }
}