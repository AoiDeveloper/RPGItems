package com.aoideveloper.rpg_items

import com.aoideveloper.rpg_items.command.RPGItemCommand
import com.aoideveloper.rpg_items.command.RPGItemCommandTabCompleter
import com.aoideveloper.rpg_items.event.ClickEvent
import com.aoideveloper.rpg_items.event.CraftEvent
import com.aoideveloper.rpg_items.item.ItemManager
import org.bukkit.plugin.java.JavaPlugin

class RPGItems: JavaPlugin() {
    companion object {
        lateinit var plugin: RPGItems
            private set
    }

    override fun onEnable() {
        plugin = this

        getCommand("rpgitems")?.apply {
            setExecutor(RPGItemCommand())
            tabCompleter = RPGItemCommandTabCompleter()
        }

        server.pluginManager.registerEvents(ClickEvent(), this)
        server.pluginManager.registerEvents(CraftEvent(), this)

        dataFolder.mkdir()
        ItemManager.loadItemInfo()
    }
}