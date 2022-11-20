package com.aoideveloper.rpg_items

import org.bukkit.Bukkit

object Logger {
    /**
     * OPに対して一斉にメッセージを配信する
     */
    fun broadcastMessageToOP(message: String) {
        Bukkit.getServer().onlinePlayers.filter{ it.isOp }.forEach { player ->
            player.sendMessage(message)
        }
    }
}