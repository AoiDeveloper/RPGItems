package com.aoideveloper.rpg_items

import org.bukkit.Bukkit

object Logger {
    /**
     * すべてのOPとサーバーログに対して一斉にメッセージを配信する
     */
    fun broadcastMessageToOP(message: String) {
        Bukkit.getServer().onlinePlayers.filter{ it.isOp }.forEach { player ->
            player.sendMessage(message)
        }
        RPGItems.plugin.server.logger.info(message) // 装飾は消した上で送信する
    }
}