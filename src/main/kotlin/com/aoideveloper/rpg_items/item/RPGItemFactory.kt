package com.aoideveloper.rpg_items.item

import com.aoideveloper.rpg_items.RPGItems
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object RPGItemFactory {
    /**
     * RPGItemを生成します。itemIdが無効な場合は、nullを返却します。
     */
    fun generateItem(itemId: String): ItemStack? {
        if(!ItemManager.itemInfoList.containsKey(itemId)) return null
        val itemInfo = ItemManager.itemInfoList[itemId]!!
        return generateItem(itemInfo)
    }
    
    /**
     * RPGItemを生成します。itemIdが無効な場合は、nullを返却します。
     */
    fun generateItem(itemInfo: ItemInfo): ItemStack {
        val item = ItemStack(itemInfo.material)
        item.itemMeta = Bukkit.getItemFactory().getItemMeta(itemInfo.material).apply {
            this?.persistentDataContainer?.set(NamespacedKey(RPGItems.plugin, "rpgItemID"), PersistentDataType.STRING, itemInfo.id)
            this?.setDisplayName(itemInfo.name)
            this?.lore = itemInfo.lore
        }
        return item
    }
}