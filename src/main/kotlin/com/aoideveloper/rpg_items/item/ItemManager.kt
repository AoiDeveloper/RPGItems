package com.aoideveloper.rpg_items.item

import com.aoideveloper.rpg_items.Logger
import com.aoideveloper.rpg_items.RPGItems
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ShapedRecipe
import java.io.File

object ItemManager {
    var itemInfoList: MutableMap<String, ItemInfo> = mutableMapOf()
    private val itemConfigFolder by lazy {
        File(RPGItems.plugin.dataFolder, "item")
    }
    /**
     * Load item information from config files, and register its recipes.
     */
    fun loadItemInfo() {
        if(!itemConfigFolder.exists()) return
        itemInfoList = mutableMapOf()
        // プラグインのdataFolder/item下のフォルダを再帰的に列挙する
        itemConfigFolder.walkBottomUp().filter{ it.isFile }.filter{ (it.extension == "yml" || it.extension == "yaml") && !it.name.startsWith("-")}.forEach { fileName ->
            val itemConfig = YamlConfiguration.loadConfiguration(fileName)

            val itemId = itemConfig.getString("id")
            val itemName = itemConfig.getString("name")
            val itemLore = itemConfig.getStringList("lore")
            val itemMaterialName = itemConfig.getString("material")
            val itemClicked = itemConfig.getStringList("execute")
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
            if(itemInfoList.containsKey(itemId)) {
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にIDが重複しているアイテムが見つかりました。")
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 後に読み込まれた、${fileName}は無視されています。")
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
            if(itemMaterial == Material.AIR) {
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にマテリアルが不正なアイテムが見つかりました。マテリアルに空気を指定することはできません。")
                Logger.broadcastMessageToOP(  "${ChatColor.RED}[RPGItems] ファイル名: $fileName")
                return@forEach
            }

            val item = RPGItemFactory.generateItem(ItemInfo(itemId, itemName ?: "物体 X", itemLore, itemMaterial, itemClicked, null))
            val recipe = ShapedRecipe(NamespacedKey(RPGItems.plugin, itemId), item)

            // 今回は作業台のクラフトのみ対応するので、縦幅が3 横幅は特に指定しない
            if(recipeShape.size == 3) {
                var isValidRecipe = true
                if(recipeShape.any { it.length != 3 }) {
                    Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にレシピが不正なアイテムが見つかりました。レシピは3行3列で指定してください。")
                    Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] ファイル名: $fileName")
                } else {
                    recipe.shape(recipeShape[0], recipeShape[1], recipeShape[2])
                    recipeMap?.forEach recipeMap@{
                        if (it.value == null) return@recipeMap
                        if (it.key.length != 1) {
                            Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] レシピに使う記号は1文字のアルファベットである必要があります。")
                            Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] ファイル名: $fileName 記号: ${it.key}")
                            isValidRecipe = false
                            return@recipeMap
                        }
                        if (Material.getMaterial(it.value!!) == null) {
                            Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にレシピが不正なアイテムが見つかりました。")
                            Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] ファイル名: $fileName 記号: ${it.key}")
                            isValidRecipe = false
                            return@recipeMap
                        }
                        recipe.setIngredient(it.key[0], Material.getMaterial(it.value!!)!!)
                    }
                    if (isValidRecipe) Bukkit.addRecipe(recipe)
                }
            } else if (recipeShape.size != 0) {
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] 読み込み中にレシピが不正なアイテムが見つかりました。レシピは3行3列で指定してください。")
                Logger.broadcastMessageToOP("${ChatColor.RED}[RPGItems] ファイル名: $fileName")
            }
            val itemInfo = ItemInfo(itemId, itemName ?: "物体 X", itemLore, itemMaterial, itemClicked, recipe)
            itemInfoList[itemId] = itemInfo
        }
        Logger.broadcastMessageToOP("${ChatColor.GREEN}[RPGItems] 計${itemInfoList.size}個のアイテムを読み込みました。")
        itemInfoList.forEach {
            Logger.broadcastMessageToOP("${ChatColor.GREEN}[RPGItems] ID:${it.value.id} ${it.value.name}")
        }
    }
}
