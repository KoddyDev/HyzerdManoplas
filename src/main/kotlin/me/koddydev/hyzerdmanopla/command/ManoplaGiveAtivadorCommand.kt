package me.koddydev.hyzerdmanopla.command

import com.mikael.mkAPI.utils.soundNo
import com.mikael.mkAPI.utils.soundYes
import me.koddydev.hyzerdmanopla.core.ManoplaSystem
import me.koddydev.hyzerdmanopla.`object`.PlayerData
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class ManoplaGiveAtivadorCommand : CommandManager("giveativador") {

    init {
        usage = "/manopla admin giveativador <player>"
        permission = "hyzerdmanopla.admin"
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        try {
            if (args.isEmpty()) {
                sendUsage(player)
                return
            }
            val target = args[0]
            val targetUser = Bukkit.getPlayer(target)

            if(targetUser == null || !targetUser.isOnline) {
                player.soundNo()
                player.sendMessage("§cVocê tentou enviar o ${ManoplaSystem.ativador.display} §cpara o usuário §f${target}§c, mas ele não está online") // Acabou
            } else {

                if (targetUser.inventory.contents.filter { it != null }.size >= 36) {
                    player.soundNo()
                    player.sendMessage("§cVocê tentou enviar a ${ManoplaSystem.ativador.display} §cpara o usuário §f${target}§c, mas ele não possue um slot livre em seu inventario") // Acabou
                } else {
                    targetUser.inventory.addItem(ManoplaSystem.ativador.item.clone())

                    player.soundYes()
                    player.sendMessage("§aVocê enviou a ${ManoplaSystem.ativador.display} §apara o usuário §f${target}§a com sucesso!") // Acabou
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            player.soundNo()
            player.sendMessage("§cOcorreu um erro interno ao executar este comando.")
        }
    }
}