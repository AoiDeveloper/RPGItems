package com.aoideveloper.rpg_items.item

import org.bukkit.Material
import org.bukkit.inventory.ShapedRecipe

data class ItemInfo(val id: String, val name: String, val lore: Array<String>?, val material: Material, val execute: String?, val recipe: ShapedRecipe?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemInfo

        // Array<String>の比較が冗長なので、idが一緒であれば同じと判定する。
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}