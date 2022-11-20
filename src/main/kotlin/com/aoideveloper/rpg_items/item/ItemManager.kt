package com.aoideveloper.rpg_items.item

import com.aoideveloper.rpg_items.Logger
import com.aoideveloper.rpg_items.RPGItems
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.persistence.PersistentDataType
import java.io.File

object ItemManager {
    private val itemInfoList: MutableMap<String, ItemInfo> = mutableMapOf()
    private val itemConfigFolder by lazy {
        File(RPGItems.plugin.dataFolder, "item")
    }
    /**
     * Load item information from config files, and register its recipes.
     */
    fun loadItemInfo() {
        if(!itemConfigFolder.exists()) return
        // プラグインのdataFolder/item下のフォルダを再帰的に列挙する
        itemConfigFolder.walkBottomUp().filter{ it.isFile }.filter{ it.extension == "yml" || it.extension == "yaml"}.forEach { fileName ->
            val itemConfig = YamlConfiguration.loadConfiguration(fileName)

            val itemId = itemConfig.getString("id")
            val itemName = itemConfig.getString("name")
            val itemLore = itemConfig.getStringList("lore")
            val itemMaterialName = itemConfig.getString("material")
            val itemClicked = itemConfig.getString("execute")
            // recipeMapはrecipeShape上の記号と素材の対応付けを表す
            val recipeMap = itemConfig.getConfigurationSection("recipe.mapping")?.getKeys(false)?.associate {
                it to itemConfig.getString("recipe.mapping.$it")
            }
            val recipeShape = itemConfig.getStringList("recipe.shape")

            if(itemId == null) {
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にIDが設定されていないアイテムが見つかりました。")
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] ファイル名: $fileName")
                return@forEach
            }
            if(itemMaterialName == null) {
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にマテリアルが設定されていないアイテムが見つかりました。")
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] ファイル名: $fileName")
                return@forEach
            }
            val itemMaterial = Material.getMaterial(itemMaterialName)
            if(itemMaterial == null) {
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にマテリアルが不正なアイテムが見つかりました。")
                Logger.broadcastMessageToOP(  "${ChatColor.RED}[RPGItems] ファイル名: $fileName")
                return@forEach
            }

            val item = ItemStack(itemMaterial)
            item.itemMeta = Bukkit.getItemFactory().getItemMeta(itemMaterial).apply {
                this?.setDisplayName(itemName)
                this?.persistentDataContainer?.set(NamespacedKey(RPGItems.plugin, "rpgItemID"), PersistentDataType.STRING, itemId)
            }
            println(item.itemMeta)
            val recipe = ShapedRecipe(NamespacedKey(RPGItems.plugin, itemId), item)

            // 今回は作業台のクラフトのみ対応するので、縦幅が3 横幅は特に指定しない
            if(recipeShape.size == 3) {
                recipe.shape(recipeShape[0], recipeShape[1], recipeShape[2])
                recipeMap?.forEach recipeMap@{
                    if(it.value == null) return@recipeMap
                    if(it.key.length != 1) {
                        Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] レシピに使う記号は1文字のアルファベットである必要があります。")
                        Logger.broadcastMessageToOP(  "${ChatColor.RED}[RPGItems] ファイル名: $fileName 記号: ${it.key}")
                    }
                    if(Material.getMaterial(it.value!!) == null) {
                        Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にレシピが不正なアイテムが見つかりました。")
                        Logger.broadcastMessageToOP(  "${ChatColor.RED}[RPGItems] ファイル名: $fileName 記号: ${it.key}")
                    }
                    recipe.setIngredient(it.key[0], Material.getMaterial(it.value!!)!!)
                }
                Bukkit.addRecipe(recipe)
            }
            val itemInfo = ItemInfo(itemId, itemName ?: "物体 X", itemLore.toTypedArray(), itemMaterial, itemClicked, recipe)
            itemInfoList[itemId] = itemInfo
        }
    }
}
