package me.pluto.tnttag.runnables;

import me.pluto.tnttag.Tnttag;

public class SignUpdateRunnable implements Runnable {
    private final Tnttag plugin;

    public SignUpdateRunnable(Tnttag plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getSignManager().updateSigns();
    }
}
