package me.koddydev.hyzerdmanopla.api

import me.koddydev.hyzerdmanopla.`object`.PlayerData
import net.eduard.api.lib.game.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

val Player.manoplaUser get() = PlayerData.cache[this.name.lowercase()]!!