package me.pluto.tnttag.subcommands;

import me.pluto.tnttag.Tnttag;
import me.pluto.tnttag.handlers.SetupCommandHandler;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"tnttag", "tt"})
public class CreateSubCommand {

    private final Tnttag plugin;

    public CreateSubCommand(Tnttag plugin) {
        this.plugin = plugin;
    }

    @Subcommand("create")
    @CommandPermission("tnttag.create")
    public void onCreate(Player player) {
        new SetupCommandHandler(plugin).start(player);
    }
}