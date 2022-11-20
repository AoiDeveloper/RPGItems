package com.aoideveloper.rpg_items.command

import com.aoideveloper.rpg_items.item.ItemManager
import com.aoideveloper.rpg_items.item.RPGItemFactory
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Item

class RPGItemCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) return false
        when(args[0]) {
            "give" -> {
                if(args.size != 3) return false
                val (player, itemId) = Pair(Bukkit.getServer().getPlayer(args[1]), args[2])
                if(player == null) {
                    sender.sendMessage("プレイヤー${args[1]}は見つかりませんでした")
                    return false
                }
                val item = RPGItemFactory.generateItem(itemId)
                player.inventory.addItem(item)
                sender.sendMessage("プレイヤー${player.name}に${ItemManager.itemInfoList[itemId]?.name}を与えました。")
            }
            "descript" -> {
                if(args.size != 2) return false
                if(!ItemManager.itemInfoList.containsKey(args[1])) {
                    sender.sendMessage("アイテム${args[1]}は見つかりませんでした")
                    return false
                } else {
                    sender.sendMessage(ItemManager.itemInfoList[args[1]]!!.description())
                }
            }
            "reload" -> {
                Bukkit.resetRecipes()
                ItemManager.loadItemInfo()
            }
        }
        return true
    }
}