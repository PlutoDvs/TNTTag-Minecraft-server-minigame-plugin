package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Arena;
import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.managers.ArenaManager;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"tnttag", "tt"})
public class JoinSubCommand {

    private final Tnttag plugin;
    private final ArenaManager arenaManager;

    public JoinSubCommand(Tnttag plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
    }

    @Subcommand("join")
    @CommandPermission("tnttag.join")
    public void onJoin(Player player, @Optional String arenaName) {
        if (arenaName == null && !Tnttag.configfile.getBoolean("global-lobby")) {
            player.sendMessage(ChatUtils.colorize(Tnttag.customizationfile.getString("general.specify-arena")));
            return;
        }

        if (!plugin.getLobbyManager().playerIsInLobby(player)) {
            if (!plugin.getLobbyManager().enterLobby(player, false)) return;
        } else if (arenaName == null) {
            ChatUtils.sendMessage(player, "player.already-in-lobby");
            return;
        }

        if (arenaName != null) {
            if (arenaManager.playerIsInArena(player)) {
                ChatUtils.sendMessage(player, "player.already-in-game");
                return;
            }

            Arena arena = arenaManager.getArena(arenaName);
            if (arena == null) {
                ChatUtils.sendMessage(player, "commands.invalid-arena");
                return;
            }

            if (!arena.getGameManager().isRunning()) {
                if (arena.getGameManager().playerManager.getPlayers().size() < arena.getMaxPlayers()) {
                    arena.getGameManager().playerManager.addPlayer(player);
                } else {
                    ChatUtils.sendMessage(player, "arena.full");
                }
            } else {
                ChatUtils.sendMessage(player, "arena.active");
            }
        }
    }
}