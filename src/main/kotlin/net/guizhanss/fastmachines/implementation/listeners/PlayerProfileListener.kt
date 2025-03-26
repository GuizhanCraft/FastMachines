package net.guizhanss.fastmachines.implementation.listeners

import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile
import net.guizhanss.fastmachines.FastMachines
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerProfileListener(plugin: FastMachines) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        // Load the player profile when the player joins, so that we can use it later
        PlayerProfile.get(e.player) {}
    }
}
