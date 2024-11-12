package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.BlockPlaceEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        val plugin = this
        saveDefaultConfig()
        server.pluginManager.registerEvents(BlockPlaceEvent(plugin), plugin)
        server.pluginManager.registerEvents(BlockBreakEvent(plugin), plugin)
    }
}
