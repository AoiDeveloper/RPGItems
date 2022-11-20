package com.aoideveloper.rpg_items

import org.bukkit.Bukkit

object Logger {
    fun broadcastMessageToOP(message: Array<String>) {
        Bukkit.getServer().onlinePlayers.filter{ it.isOp }.forEach { player ->
            message.forEach {
                player.sendMessage(it)
            }
        }
    }

    fun broadcastMessageToOP(message: String) {
        Bukkit.getServer().onlinePlayers.filter{ it.isOp }.forEach { player ->
            player.sendMessage(message)
        }
    }
}