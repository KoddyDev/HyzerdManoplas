package me.koddydev.hyzerdmanopla.`object`

import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.Material
import org.bukkit.potion.PotionEffectType

class ManoplaObject(
    var name: String = "Name",
    var display: String = "§aDisplay",
    var item: ItemBuilder = ItemBuilder(Material.INK_SACK),
    var effect: PotionEffectType = PotionEffectType.SPEED,
    var effectLevel: Int = 0,
    var lore: MutableList<String> = mutableListOf()
)