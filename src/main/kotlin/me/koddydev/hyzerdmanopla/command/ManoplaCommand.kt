package me.koddydev.hyzerdmanopla.command

import com.mikael.mkAPI.utils.soundClick
import com.mikael.mkAPI.utils.soundNo
import me.koddydev.hyzerdmanopla.menu.ManoplaMenu
import net.eduard.api.lib.manager.CommandManager
import org.bukkit.entity.Player

class ManoplaCommand : CommandManager("manopla") {

    init {
        usage = "/manopla"
        permission = "hyzerdmanopla.cmd.manopla"
        register(ManoplaAdminCommand())
    }

    override fun playerCommand(player: Player, args: Array<String>) {
        player.soundClick(2f, 1f)
        ManoplaMenu.getMenu(player).open(player)
    }

}