package me.pluto.tnttag.listeners;

import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.managers.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final ArenaManager arenaManager;

    public InventoryClickListener(Tnttag plugin) {
        this.arenaManager = plugin.getArenaManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (arenaManager.playerIsInArena(player)) {
            event.setCancelled(true);
        }
    }
}
