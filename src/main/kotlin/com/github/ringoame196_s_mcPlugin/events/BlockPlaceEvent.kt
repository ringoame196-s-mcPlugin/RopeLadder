package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.DataConst
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.plugin.Plugin

class BlockPlaceEvent(plugin: Plugin) : Listener {
    val config = plugin.config

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val player = e.player
        val block = e.block
        val maxSize = config.getInt(DataConst.MAX_SIZE_KEY)
    }
}