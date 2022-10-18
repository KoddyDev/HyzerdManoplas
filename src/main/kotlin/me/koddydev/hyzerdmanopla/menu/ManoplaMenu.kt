package me.koddydev.hyzerdmanopla.menu

import com.mikael.mkAPI.utils.soundNo
import com.mikael.mkAPI.utils.soundYes
import me.koddydev.hyzerdmanopla.ManoplaMain
import me.koddydev.hyzerdmanopla.core.ManoplaSystem
import me.koddydev.hyzerdmanopla.api.manoplaUser
import net.eduard.api.lib.game.ItemBuilder
import net.eduard.api.lib.kotlin.player
import net.eduard.api.lib.menu.ClickEffect
import net.eduard.api.lib.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.potion.PotionEffect

class ManoplaMenu(var player: Player) : Menu("Sua Manopla do Infinito", 5) {
    companion object {
        lateinit var instance: ManoplaMenu

        private val menus = mutableMapOf<Player, ManoplaMenu>()

        fun getMenu(player: Player): ManoplaMenu {
            if (menus.containsKey(player)) return menus[player]!!
            val playerMenu = ManoplaMenu(player)
            menus[player] = playerMenu
            playerMenu.registerMenu(ManoplaMain.instance)
            return playerMenu
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onQuit(e: PlayerQuitEvent) {
        if (e.player == player) {
            unregisterMenu()
            menus.remove(e.player)
        }
    }

    init {
        instance = this@ManoplaMenu
        isAutoAlignItems = true
        autoAlignSkipLines = listOf(1, 4)
        autoAlignSkipColumns = listOf(9,8,7,3,2,1)
        autoAlignPerLine = 3
        autoAlignPerPage = 3 * autoAlignPerLine
        update()
    }

    override fun update() {
        removeAllButtons()

        for (manopla in ManoplaSystem.manoplas) {
            button("manopla-${manopla.name}") {
                iconPerPlayer = {
                    val manoplaUser = this.manoplaUser
                    val item = manopla.item.clone()
                    if (!manoplaUser.hasManopla()) {
                        item.type(Material.STAINED_GLASS_PANE)
                        item.data(14)
                        item.name("§c???")
                        item.lore(listOf("§7Você deve obter esta pedra", "§7Para obter suas informações", "§7E utilizar os efeitos"))
                    } else {
                        item.lore(manopla.lore)
                        item.name(manopla.display)
                        item.glowed()

                        click = ClickEffect {
                            val activeList = ManoplaSystem.activePlayersEffects[this.name]!!

                            val ativo = activeList.firstOrNull {
                                it == manopla.effect
                            }

                            if(ativo == null) {
                                ManoplaMain.instance.log(manopla.effectLevel.toString())
                                val potion = PotionEffect(manopla.effect, Int.MAX_VALUE, manopla.effectLevel)
                                this.addPotionEffect(potion)
                                activeList.add(manopla.effect)
                                ManoplaSystem.activePlayersEffects[this.name] = activeList
                                this.sendMessage("§aVocê ativou poder da ${manopla.display} §acom sucesso!")
                            } else {
                                this.removePotionEffect(manopla.effect)
                                activeList.remove(manopla.effect)
                                ManoplaSystem.activePlayersEffects[this.name] = activeList
                                this.sendMessage("§cVocê desativou o poder da ${manopla.display} §ccom sucesso!")
                            }
                        }
                    }
                    item
                }

            }
        }

        button("button-ativador") {
            iconPerPlayer = {
                val item = ManoplaSystem.ativador.item.clone()
                if(!this.manoplaUser.hasManopla()) {
                    item.type(Material.INK_SACK)
                    item.data(1)
                    item.name("§c???")
                    item.lore(listOf("§7", "§7Você deve obter todos as joias", "§7para poder utilizar esta função", "§7"))
                    click = ClickEffect { }
                    setPosition(5, 5)
                    item
                } else {
                    click = ClickEffect {
                        if(this.inventory.contents.filter { it != null }.size >= 36) {
                            player.soundNo()
                            player.sendMessage("§cVocê não possue um slot livre em seu inventario para obter o ativador.") // Acabou
                        } else {
                            closeInventory()

                            ManoplaSystem.manoplas.forEach {
                                player.removePotionEffect(it.effect)
                            }
                            player.manoplaUser.removeManopla()
                            player.inventory.addItem(item)
                            player.soundYes()
                            player.sendMessage("§aVocê recebeu o ${ManoplaSystem.ativador.display}§a em seu inventario e teve suas permissões revogadas.") // Acabou
                        }
                    }
                    setPosition(5, 5)
                    item
                }

            }
        }
    }

}