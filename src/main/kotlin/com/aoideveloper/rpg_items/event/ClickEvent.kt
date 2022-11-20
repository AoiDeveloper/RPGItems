package com.aoideveloper.rpg_items.event

import com.aoideveloper.rpg_items.RPGItems
import com.aoideveloper.rpg_items.item.ItemManager
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

class ClickEvent: Listener {
    @EventHandler
    fun onRightClickItem(event: PlayerInteractEvent) {
        if(event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        val itemId = event.item?.itemMeta?.persistentDataContainer?.get(NamespacedKey(RPGItems.plugin, "rpgItemID"), PersistentDataType.STRING) ?: return
        if(ItemManager.itemInfoList[itemId] == null) {
            event.player.sendMessage("そのアイテムは現在使用できません。")
        } else {
            if(ItemManager.itemInfoList[itemId]!!.execute != null) {
                val commands = ItemManager.itemInfoList[itemId]!!.execute!!
                commands.forEach {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute as ${event.player.name} at ${event.player.name} run $it")
                }
            }
        }
    }
}