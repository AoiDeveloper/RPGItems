package com.aoideveloper.rpg_items.item

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ShapedRecipe

/**
 * @param id アイテムのID Unique
 * @param name アイテムのdisplayName
 * @param lore アイテムのlore
 * @param material アイテムの見かけ
 * @param execute 右クリックされた際に実行されるコマンド
 * @param recipe クラフトレシピ
 */
data class ItemInfo(val id: String, val name: String, val lore: List<String>?, val material: Material, val execute: List<String>?, val recipe: ShapedRecipe?) {
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

    fun description():String {
        return """
            |${ChatColor.RESET}${ChatColor.GRAY}【アイテム名】
            |$name
            |${ChatColor.RESET}${ChatColor.GRAY}【説明文】
            |${lore?.joinToString("\n") ?: "なし"}
            |${ChatColor.RESET}${ChatColor.GRAY}【見た目】
            |$material
            |${ChatColor.RESET}${ChatColor.GRAY}【実行されるコマンド】
            |${execute?.joinToString("\n") ?: "なし"}
        """.trimMargin()
    }
}