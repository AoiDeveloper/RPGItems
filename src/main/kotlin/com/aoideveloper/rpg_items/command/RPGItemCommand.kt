package com.aoideveloper.rpg_items.command

import com.aoideveloper.rpg_items.item.ItemManager
import com.aoideveloper.rpg_items.item.RPGItemFactory
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class RPGItemCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp) return false
        if(args.isEmpty()) return false

        when(args[0]) {
            // give: RPGアイテムをプレイヤーに与える。 /rpgitems give <player> <itemId> <amount?>
            "give" -> {
                when(args.size) {
                    3, 4 -> {
                        val (player, itemId) = Pair(Bukkit.getServer().getPlayer(args[1]), args[2])
                        val itemCount = args.getOrNull(3)?.toIntOrNull() ?: 1 // 4つ目の引数が設定されていなければ、個数を1に設定する

                        if(player == null) {
                            sender.sendMessage("プレイヤー${args[1]}は見つかりませんでした")
                            return false
                        }

                        val item = RPGItemFactory.generateItem(itemId, itemCount)
                        if(item == null) {
                            sender.sendMessage("アイテム${args[2]}は見つかりませんでした")
                            return false
                        }
                        player.inventory.addItem(item)
                        sender.sendMessage("プレイヤー${player.name}に${itemCount}個の${ItemManager.itemInfoList[itemId]?.name}を与えました。")
                    }
                    else -> {
                        return false
                    }
                }
            }
            // descript: RPGアイテムの説明を表示する。 /rpgitems descript <itemId>
            "descript" -> {
                if(args.size != 2) return false
                sender.sendMessage(ItemManager.itemInfoList[args[1]]?.description() ?: "アイテム${args[1]}は見つかりませんでした")
            }
            // reload: RPGアイテムのコンフィグをすべて再度読み込む。
            "reload" -> {
                Bukkit.resetRecipes()
                ItemManager.loadItemInfo()
            }
        }
        return true
    }
}