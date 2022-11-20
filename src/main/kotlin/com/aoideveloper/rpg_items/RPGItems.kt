package com.aoideveloper.rpg_items

import com.aoideveloper.rpg_items.item.ItemManager
import org.bukkit.plugin.java.JavaPlugin

class RPGItems: JavaPlugin() {
    companion object {
        lateinit var plugin: RPGItems
            private set
    }

    override fun onEnable() {
        println("run")
        plugin = this

        dataFolder.mkdir()
        ItemManager.loadItemInfo()
    }
}