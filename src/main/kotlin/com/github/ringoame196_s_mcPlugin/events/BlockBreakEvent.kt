package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.DataConst
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class BlockBreakEvent(plugin: Plugin) : Listener {
    private val config = plugin.config

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val player = e.player
        val block = e.block
        val playerItem = player.inventory.itemInMainHand
        val maxSize = config.getInt(DataConst.MAX_SIZE_KEY) // 最大数
        val blockLocation = block.location

        if (!player.isSneaking) return
        if (block.type != Material.LADDER) return
        if (playerItem.type != Material.SHEARS) return

        var breakCount = 0

        while (breakCount < maxSize) {
            blockLocation.add(0.0, -1.0, 0.0)
            val breakBlock = blockLocation.block

            if (breakBlock.type == Material.LADDER) {
                breakBlock.type = Material.AIR
                breakCount++
            } else break
        }

        if (breakCount > 0) {
            // アイテムを取りやすくするための処理
            e.isCancelled = true
            block.type = Material.AIR

            val sound = Sound.BLOCK_SCAFFOLDING_BREAK
            val item = ItemStack(Material.LADDER, breakCount + 1)
            val message = "${ChatColor.AQUA} ${breakCount}個のはしごを回収しました"
            player.inventory.addItem(item)
            player.sendMessage(message)
            player.playSound(player, sound, 1f, 1f)
        }
    }
}
