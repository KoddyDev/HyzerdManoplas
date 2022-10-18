package me.koddydev.hyzerdmanopla.core

import me.koddydev.hyzerdmanopla.ManoplaMain
import me.koddydev.hyzerdmanopla.`object`.AtivadorObject
import me.koddydev.hyzerdmanopla.`object`.ManoplaObject
import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

object ManoplaSystem {

    val manoplas = mutableListOf<ManoplaObject>()
    val activePlayersEffects = mutableMapOf<String, MutableList<PotionEffectType>>()
    var ativador: AtivadorObject = AtivadorObject()

    fun load() {
        for (key in ManoplaMain.instance.config.getSection("Manoplas").keys) {
            val lore = mutableListOf<String>()

            ManoplaMain.instance.config.getStringList("Manoplas.${key}.Lore").forEach {
                lore.add(it.replace("&", "§"))
            }

            val item = ItemBuilder(ManoplaMain.instance.config["Manoplas.${key}.Material", Material::class.java])
            item.name(ManoplaMain.instance.config.getString("Manoplas.${key}.Name").replace("&", "§"))
            item.data(ManoplaMain.instance.config.getInt("Manoplas.${key}.Data"))

            manoplas.add(
                ManoplaObject(
                    key,
                    ManoplaMain.instance.config.getString("Manoplas.${key}.Display").replace("&", "§"),
                    item,
                    PotionEffectType.getByName(ManoplaMain.instance.config.getString("Manoplas.${key}.Effect")),
                    (ManoplaMain.instance.config.getInt("Manoplas.${key}.Nivel") - 1),
                    lore
                )
            )
        }

        val ativadorDisplay = ManoplaMain.instance.config.getString("Ativador.Display").replace("&", "§")
        val ativadorLore = mutableListOf<String>()
        ManoplaMain.instance.config.getStringList("Ativador.Lore").forEach { ativadorLore.add(it.replace("&", "§")) }
        val ativadorName = ManoplaMain.instance.config.getString("Ativador.Name").replace("&", "§")
        val ativadorItem = ItemBuilder(ManoplaMain.instance.config["Ativador.Material", Material::class.java])
        ativadorItem.name(ativadorDisplay)
        ativadorItem.data(ManoplaMain.instance.config.getInt("Ativador.Data"))
        ativadorItem.lore(ativadorLore)
        ativador = AtivadorObject(ativadorItem, ativadorName, ativadorDisplay, ativadorLore)
    }

    init {
        manoplas.clear()
        activePlayersEffects.clear()
    }

}