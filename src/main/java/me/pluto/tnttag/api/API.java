package me.pluto.tnttag.api;

import me.pluto.tnttag.enums.PlayerType;
import me.pluto.tnttag.managers.ArenaManager;
import me.pluto.tnttag.objects.PlayerData;
import me.pluto.tnttag.Tnttag;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class API {

    private static ArenaManager arenaManager = null;

    public API(Tnttag plugin) {
        arenaManager = plugin.getArenaManager();
    }

    public int getTimesTagged(UUID playerUUID) {
        PlayerData data = new PlayerData(playerUUID);
        return data.getTimesTagged();
    }

    public int getWins(UUID playerUUID) {
        PlayerData data = new PlayerData(playerUUID);
        return data.getWins();
    }

    public int getTags(UUID playerUUID) {
        PlayerData data = new PlayerData(playerUUID);
        return data.getTags();
    }

    public void setTimesTagged(UUID playerUUID, int value) {
        PlayerData data = new PlayerData(playerUUID);
        data.setTimesTagged(value);
    }

    public void setWins(UUID playerUUID, int value) {
        PlayerData data = new PlayerData(playerUUID);
        data.setWins(value);
    }

    public void setTags(UUID playerUUID, int value) {
        PlayerData data = new PlayerData(playerUUID);
        data.setTags(value);
    }

    public boolean arenaExists(String arenaName) {
        return arenaManager.getArena(arenaName) != null;
    }

    public HashMap<Player, PlayerType> getPlayers(String arenaName) {
        if (arenaExists(arenaName)) {
            return arenaManager.getArena(arenaName).getGameManager().playerManager.getPlayers();
        }
        return null;
    }

    public String getArenaState(String arenaName) {
        if (arenaExists(arenaName)) {
            return arenaManager.getArena(arenaName).getGameManager().getCustomizedState();
        }
        return "Unknown arena";
    }

    public TreeMap<UUID, Integer> getWinsData() {
        TreeMap<UUID, Integer> winsData = new TreeMap<>();
        for (String route : Tnttag.playerdatafile.getRoutesAsStrings(false)) {
            if (Tnttag.playerdatafile.getInt(route + ".wins") != null) {
                int wins = Tnttag.playerdatafile.getInt(route + ".wins");
                winsData.put(UUID.fromString(route), wins);
            }
        }
        return winsData;
    }

    public TreeMap<UUID, Integer> getTimesTaggedData() {
        TreeMap<UUID, Integer> timesTaggedData = new TreeMap<>();
        for (String route : Tnttag.playerdatafile.getRoutesAsStrings(false)) {
            if (Tnttag.playerdatafile.getInt(route + ".timestagged") != null) {
                int timesTagged = Tnttag.playerdatafile.getInt(route + ".timestagged");
                timesTaggedData.put(UUID.fromString(route), timesTagged);
            }
        }
        return timesTaggedData;
    }

    public TreeMap<UUID, Integer> getTagsData() {
        TreeMap<UUID, Integer> tagsData = new TreeMap<>();
        for (String route : Tnttag.playerdatafile.getRoutesAsStrings(false)) {
            if (Tnttag.playerdatafile.getInt(route + ".tags") != null) {
                int tags = Tnttag.playerdatafile.getInt(route + ".tags");
                tagsData.put(UUID.fromString(route), tags);
            }
        }
        return tagsData;
    }

    public TreeMap<UUID, Integer> getWinstreakData() {
        TreeMap<UUID, Integer> winstreakData = new TreeMap<>();
        for (String route : Tnttag.playerdatafile.getRoutesAsStrings(false)) {
            if (Tnttag.playerdatafile.getInt(route + ".winstreak") != null) {
                int winstreak = Tnttag.playerdatafile.getInt(route + ".winstreak");
                winstreakData.put(UUID.fromString(route), winstreak);
            }
        }
        return winstreakData;
    }
}
