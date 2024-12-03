package me.pluto.tnttag.listeners;

import me.pluto.tnttag.Arena;
import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.enums.GameState;
import me.pluto.tnttag.enums.PlayerType;
import me.pluto.tnttag.managers.GameManager;
import me.pluto.tnttag.utils.ChatUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityDamageByEntityListener implements Listener {

    private final Tnttag plugin;
    public final Map<Player, Long> cooldowns = new HashMap<>();

    public EntityDamageByEntityListener(Tnttag plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player victim = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        Arena damagerArena = plugin.getArenaManager().getPlayerArena(damager);
        Arena victimArena = plugin.getArenaManager().getPlayerArena(victim);

        if (victimArena != null && damagerArena == victimArena) {
            if (damagerArena.getGameManager().playerManager.getPlayerType(damager) == PlayerType.SPECTATOR || victimArena.getGameManager().playerManager.getPlayerType(victim) == PlayerType.SPECTATOR) {
                event.setCancelled(true);
                return;
            }

            GameManager gameManager = damagerArena.getGameManager();


            if (gameManager.state == GameState.INGAME) {
                PlayerType damagerType = gameManager.playerManager.getPlayerType(damager);

                // Check if the damager is not a tagger
                if (damagerType != PlayerType.TAGGER) {
                    if (!Tnttag.configfile.getBoolean("game-combat")) {
                        event.setCancelled(true);
                    } else if (gameManager.isRunning()) {
                        event.setDamage(0);
                    }
                    return;
                }

                PlayerType victimType = gameManager.playerManager.getPlayerType(victim);
                if (victimType == PlayerType.TAGGER) return;

                if (Tnttag.configfile.getBoolean("cooldown.enabled")) {
                    long currentMilliSeconds = System.currentTimeMillis();
                    long cooldownEnd = cooldowns.getOrDefault(damager, 0L) + (Tnttag.configfile.getInt("cooldown.duration") * 1000);

                    if (cooldownEnd > currentMilliSeconds) {
                        long secondsRemaining = (cooldownEnd - currentMilliSeconds) / 1000;

                        ChatUtils.sendCustomMessage(damager, Tnttag.customizationfile.getString("player.cooldown")
                                .replace("%seconds%", String.valueOf(secondsRemaining)));
                        event.setCancelled(true);
                        return;
                    }

                    cooldowns.put(damager, currentMilliSeconds);
                }

                event.setDamage(0);

                // Switch player types if the damager is a tagger and the victim is a survivor
                gameManager.playerManager.setPlayerType(damager, PlayerType.SURVIVOR);
                gameManager.playerManager.setPlayerType(victim, PlayerType.TAGGER);

                if (!ChatUtils.getRaw("player.tagged").isEmpty()) ChatUtils.sendCustomMessage(victim, ChatUtils.getRaw("player.tagged").replace("{tagger}", damager.getName()));

                victim.playSound(victim.getLocation(), Sound.valueOf(ChatUtils.getRaw("sounds.tagged").toUpperCase()), 1, 1);
                damager.playSound(damager.getLocation(), Sound.valueOf(ChatUtils.getRaw("sounds.untagged").toUpperCase()), 1, 1);
            }
        }
    }
}