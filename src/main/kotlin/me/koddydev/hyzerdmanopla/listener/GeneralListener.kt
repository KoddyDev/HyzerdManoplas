package me.koddydev.hyzerdmanopla.listener

import me.koddydev.hyzerdmanopla.api.*
import me.koddydev.hyzerdmanopla.core.ManoplaSystem
import me.koddydev.hyzerdmanopla.`object`.PlayerData
import net.eduard.api.lib.manager.EventsManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class GeneralListener : EventsManager() {

    @EventHandler(priority = EventPriority.LOW)
    fun onPreLogin(e: AsyncPlayerPreLoginEvent) {
        try {
            PlayerData.loadData(e.name)
            ManoplaSystem.activePlayersEffects[e.name] = mutableListOf()
        } catch (ex: Exception) {
            ex.printStackTrace()
            e.disallow(
                AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                "§cOcorreu um erro ao carregar seus dados de spawner."
            )
            e.kickMessage = "§cOcorreu um erro ao carregar seus dados de spawner."
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onJoin(e: PlayerJoinEvent) {
        e.joinMessage = null
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onQuit(e: PlayerQuitEvent) {
        e.quitMessage = null
        val player = e.player

        // Remover player do acche
        PlayerData.cache.remove(player.name.lowercase())
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onClick(e: PlayerInteractEvent) {
        val item = e.item
        val player = e.player
        val action = e.action

        if(item != null && item.itemMeta == ManoplaSystem.ativador.item.itemMeta) {
            if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                if(player.manoplaUser.hasManopla()) {
                    player.sendMessage("§aVocê ja possue a manopla do infinuto, verifique em /manopla.")
                } else {
                    player.inventory.remove(item)
                    player.manoplaUser.addManopla()
                    player.sendMessage("§aVocê ativou a sua manopla com sucesso. Digite /manopla para abrir o menu.")
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlace(e: BlockPlaceEvent) {
        val item = e.itemInHand
        if(item == ManoplaSystem.ativador.item) {
            e.setCancelled(true)
        }
    }
}