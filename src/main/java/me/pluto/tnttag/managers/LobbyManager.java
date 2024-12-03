package me.pluto.tnttag.managers;

import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.objects.PlayerInformation;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class LobbyManager {

    private final Tnttag plugin;
    private final ItemManager itemManager;
    private final ArrayList<Player> players = new ArrayList<>();
    private final HashMap<Player, PlayerInformation> playerInformationMap;
    private Location globalLobbyLocation;

    public LobbyManager(Tnttag plugin) {
        this.plugin = plugin;
        this.itemManager = plugin.getItemManager();
        this.playerInformationMap = new HashMap<>();
    }

    public boolean enterLobby(Player player, boolean toggledOn) {
        if (globalLobbyLocation == null) {
            ChatUtils.sendMessage(player, "player.global-lobby-not-set");
            return false;
        }

        if (toggledOn) player.teleport(globalLobbyLocation);

        players.add(player);
        // This should be done first because the PlayerInformation constructor clears the inventory too.
        playerInformationMap.put(player, new PlayerInformation(plugin, player));
        itemManager.giveGlobalLobbyItems(player);
        if (toggledOn) ChatUtils.sendMessage(player, "player.joined-lobby");
        if (toggledOn) player.playSound(player.getLocation(), Sound.valueOf(ChatUtils.getRaw("sounds.lobby-join").toUpperCase()), 1, 1);
        return true;
    }

    public void leaveLobby(Player player) {
        itemManager.clearInv(player);
        players.remove(player);
        PlayerInformation playerInfo = playerInformationMap.remove(player);
        if (playerInfo != null) {
            playerInfo.restore();
        }
        ChatUtils.sendMessage(player, "player.leaved-lobby");
        player.playSound(player.getLocation(), Sound.valueOf(ChatUtils.getRaw("sounds.lobby-leave").toUpperCase()), 1, 1);
    }

    public boolean playerIsInLobby(Player player) {
        return players.contains(player);
    }

    public void teleportToLobby(Player player) {
        player.teleport(globalLobbyLocation);
    }

    public void load() {
        String[] parts = Tnttag.configfile.getString("globalLobby").split(",");
        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);

        this.globalLobbyLocation = new Location(world, x, y, z);
        globalLobbyLocation.setYaw(Float.parseFloat(parts[4]));
        globalLobbyLocation.setPitch(Float.parseFloat(parts[5]));
    }

    public HashMap<Player, PlayerInformation> getPlayerInformationMap() {
        return playerInformationMap;
    }
}
