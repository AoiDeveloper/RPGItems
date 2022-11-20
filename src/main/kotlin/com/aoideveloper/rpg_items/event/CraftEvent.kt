package com.aoideveloper.rpg_items.event

import com.aoideveloper.rpg_items.RPGItems
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.persistence.PersistentDataType

/**
 * クラフトでのアイテム生成時、効果音を鳴らします。
 */
class CraftEvent: Listener {
    @EventHandler
    fun onCraft(event: CraftItemEvent) {
        if(event.currentItem?.itemMeta?.persistentDataContainer?.has(NamespacedKey(RPGItems.plugin, "rpgItemID"), PersistentDataType.STRING) == true) {
            event.whoClicked.world.playSound(event.whoClicked.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.5f)
        }
    }
}