package me.koddydev.hyzerdmanopla.`object`

import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.Material

class AtivadorObject (
    val item: ItemBuilder = ItemBuilder(Material.SPONGE),
    val name: String = "Ativador",
    val display: String = "&6&lATIVADOR do INFINITO",
    val lore: MutableList<String> = mutableListOf()
)