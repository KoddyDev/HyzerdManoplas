package me.koddydev.hyzerdmanopla.command

import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player

class ManoplaAdminCommand : CommandManager("admin") {

    init {
        usage = "/admin"
        permission = "hyzerdmanopla.admin"
        register(ManoplaGiveAtivadorCommand())
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        player.sendMessage("§f")
        player.sendMessage("§a§lComandos Administrativos - Sistema de Manopla")
        player.sendMessage("§f")
        player.sendMessage("§b/manopla admin giveativador <player> - §fFaça o player ganhar um ativador de manopla.")
        player.sendMessage("§f")
    }

}