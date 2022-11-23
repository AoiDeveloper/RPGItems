package com.aoideveloper.rpg_items.command

import com.aoideveloper.rpg_items.item.ItemManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class RPGItemCommandTabCompleter: TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        when(args.size) {
            1 -> return mutableListOf("give", "descript", "reload")
            2 -> {
                when(args[0]) {
                    "give" -> return Bukkit.getServer().onlinePlayers.map{it.name}.toMutableList()
                    "descript" -> return ItemManager.itemInfoList.map{it.value.id}.toMutableList()
                    "reload" -> return mutableListOf()
                    else -> return mutableListOf()
                }
            }
            3 -> {
                return ItemManager.itemInfoList.map{it.value.id}.toMutableList()
            }
            else -> {
                return mutableListOf()
            }
        }
    }
}