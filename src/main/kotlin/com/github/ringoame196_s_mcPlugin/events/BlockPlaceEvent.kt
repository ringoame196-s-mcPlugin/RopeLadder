package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.DataConst
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

class BlockPlaceEvent(private val plugin: Plugin) : Listener {
    private val config = plugin.config

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val player = e.player
        val block = e.block
        val maxSize = config.getInt(DataConst.MAX_SIZE_KEY) // 最大数

        if (!player.isSneaking) return
        if (block.type != Material.LADDER) return

        val placeLocation = block.location
        val ladderItem = ItemStack(Material.LADDER)
        var placeCount = 1 // 設置ブロック数
        val sound = Sound.BLOCK_SCAFFOLDING_FALL

        object : BukkitRunnable() {
            override fun run() {
                val inventory = player.inventory
                placeLocation.add(0.0, -1.0, 0.0)
                val placeBlock = placeLocation.block // 設置予定ブロック

                if (canPlaceLadder(inventory, placeBlock) && placeCount != maxSize) {
                    placeLadder(placeBlock, block)
                    inventory.removeItem(ladderItem)
                    player.playSound(player, sound, 1f, 1f)
                    placeCount++
                } else {
                    sendEndMessage(player, placeCount)
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 8L)
    }

    private fun canPlaceLadder(inventory: Inventory, placeBlock: Block): Boolean {
        return placeBlock.type == Material.AIR && isHaveLadder(inventory)
    }

    private fun isHaveLadder(inventory: Inventory): Boolean {
        for (playerItem in inventory) {
            playerItem ?: continue
            if (playerItem.type == Material.LADDER) return true
        }
        return false
    }

    private fun placeLadder(placeBlock: Block, setupBlock: Block) {
        placeBlock.type = setupBlock.type
        placeBlock.blockData = setupBlock.blockData // 設置方向などを合わせるため
    }

    private fun sendEndMessage(player: Player, placeCount: Int) {
        // 設置終わり時のメッセージを出す
        val sound = Sound.BLOCK_ANVIL_USE
        val message = "${ChatColor.AQUA}${placeCount}ブロック分 縄梯子を設置しました"
        player.sendMessage(message)
        player.playSound(player, sound, 1f, 1f)
    }
}
