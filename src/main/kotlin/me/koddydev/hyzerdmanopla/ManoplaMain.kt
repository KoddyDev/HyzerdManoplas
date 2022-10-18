package me.koddydev.hyzerdmanopla

import com.mikael.mkAPI.api.MKPluginSystem
import com.mikael.mkAPI.spigot.api.apimanager
import me.koddydev.hyzerdmanopla.command.ManoplaCommand
import me.koddydev.hyzerdmanopla.core.ManoplaSystem
import me.koddydev.hyzerdmanopla.listener.GeneralListener
import me.koddydev.hyzerdmanopla.`object`.PlayerData
import net.eduard.api.lib.config.Config
import net.eduard.api.lib.modules.BukkitTimeHandler
import net.eduard.api.lib.plugin.IPluginInstance
import net.eduard.api.lib.storage.StorageAPI
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType
import java.io.File

class ManoplaMain : JavaPlugin(), IPluginInstance, BukkitTimeHandler {

    companion object {
        lateinit var instance: ManoplaMain
    }

    lateinit var config: Config

    override fun onEnable() {
        instance = this@ManoplaMain
        val start = System.currentTimeMillis()

        log("§eIniciando carregamento...")
        MKPluginSystem.loadedMKPlugins.add(this@ManoplaMain)

        log("§eCarregando diretórios...")
        config = Config(this@ManoplaMain, "config.yml")
        config.saveConfig()
        reloadConfigs() // x1
        reloadConfigs() // x2
        StorageAPI.updateReferences()

        log("§eCriando tabelas e referências...")

        apimanager.sqlManager.createTable<PlayerData>()
        apimanager.sqlManager.createReferences<PlayerData>()

        log("§eCarregando sistemas...")

        ManoplaSystem.load()

        // Commands
        ManoplaCommand().registerCommand(this)

        // Listeners
        GeneralListener().registerListener(this)

        val endTime = System.currentTimeMillis() - start
        log("§aPlugin ativado! (Tempo de carregamento: §f${endTime}ms§a)")
    }

    fun reloadConfigs() {

        val alma = mutableListOf<String>()
        val espaco = mutableListOf<String>()
        val mente = mutableListOf<String>()
        val poder = mutableListOf<String>()
        val realidade = mutableListOf<String>()
        val tempo = mutableListOf<String>()

        alma.add("&7Através dessa joia")
        alma.add("&7Você terá o efeito de regeneração III permanente.")
        espaco.add("&7Através dessa joia")
        espaco.add("&7Você terá o efeito de velocidade II permanente.")
        mente.add("&7Através dessa joia")
        mente.add("&7Você terá o efeito de pressa II permanente.")
        poder.add("&7Através dessa joia")
        poder.add("&7Você terá o efeito de resistência I permanente.")
        realidade.add("&7Através dessa joia")
        realidade.add("&7Você terá o efeito de força II permanente.")
        tempo.add("&7Através dessa joia")
        tempo.add("&7Você terá o efeito de super pulo II permanente.")

        config.add("Ativador.Material", Material.SPONGE, "Material do ativador")
        config.add("Ativador.Data", 0)
        config.add("Ativador.Name", "Ativador")
        config.add("Ativador.Display", "&6&lATIVADOR do INFINITO")
        config.add("Ativador.Lore", listOf("&f", "&7Atraves deste item você pode ativar", "&7todas as pedras do infinito e ganhar", "&7os seus devidos poderes"))

        config.add("Manoplas.Alma.Material", Material.INK_SACK, "Regeneração")
        config.add("Manoplas.Alma.Data", 14)
        config.add("Manoplas.Alma.Nivel", 3, "Nivel da Poção")
        config.add("Manoplas.Alma.Effect", PotionEffectType.REGENERATION.name, "Efeito da Poção")
        config.add("Manoplas.Alma.Name", "Alma")
        config.add("Manoplas.Alma.Display", "&fJoia da Alma")
        config.add("Manoplas.Alma.Lore", alma)
        config.add("Manoplas.Alma.Slot.Line", 2)
        config.add("Manoplas.Alma.Slot.Column", 3)

        config.add("Manoplas.Espaco.Material", Material.INK_SACK, "Velocidade")
        config.add("Manoplas.Espaco.Data", 14)
        config.add("Manoplas.Espaco.Nivel", 3, "Nivel da Poção")
        config.add("Manoplas.Espaco.Effect", PotionEffectType.SPEED.name, "Efeito da Poção")
        config.add("Manoplas.Espaco.Display", "&fJoia do Espaço")
        config.add("Manoplas.Espaco.Name", "Espaco")
        config.add("Manoplas.Espaco.Lore", espaco)
        config.add("Manoplas.Espaco.Slot.Line", 2)
        config.add("Manoplas.Espaco.Slot.Column", 4)

        config.add("Manoplas.Mente.Material", Material.INK_SACK, "Pressa")
        config.add("Manoplas.Mente.Data", 14)
        config.add("Manoplas.Mente.Nivel", 2, "Nivel da Poção")
        config.add("Manoplas.Mente.Effect", PotionEffectType.FAST_DIGGING.name, "Efeito da Poção")
        config.add("Manoplas.Mente.Display", "&fJoia da Mente")
        config.add("Manoplas.Mente.Name", "Mente")
        config.add("Manoplas.Mente.Lore", mente)
        config.add("Manoplas.Mente.Slot.Line", 2)
        config.add("Manoplas.Mente.Slot.Column", 5)

        config.add("Manoplas.Poder.Material", Material.INK_SACK, "Resistência")
        config.add("Manoplas.Poder.Data", 14)
        config.add("Manoplas.Poder.Nivel", 1, "Nivel da Poção")
        config.add("Manoplas.Poder.Effect", PotionEffectType.DAMAGE_RESISTANCE.name, "Efeito da Poção")
        config.add("Manoplas.Poder.Display", "&fJoia do Poder")
        config.add("Manoplas.Poder.Name", "Poder")
        config.add("Manoplas.Poder.Lore", poder)
        config.add("Manoplas.Poder.Slot.Line", 2)
        config.add("Manoplas.Poder.Slot.Column", 6)

        config.add("Manoplas.Realidade.Material", Material.INK_SACK, "Força")
        config.add("Manoplas.Realidade.Data", 14)
        config.add("Manoplas.Realidade.Nivel", 2, "Nivel da Poção")
        config.add("Manoplas.Realidade.Effect", PotionEffectType.INCREASE_DAMAGE.name, "Efeito da Poção")
        config.add("Manoplas.Realidade.Display", "&fJoia da Realidade")
        config.add("Manoplas.Realidade.Name", "Realidade")
        config.add("Manoplas.Realidade.Lore", realidade)
        config.add("Manoplas.Realidade.Slot.Line", 2)
        config.add("Manoplas.Realidade.Slot.Column", 7)

        config.add("Manoplas.Tempo.Material", Material.INK_SACK, "Super Pulo")
        config.add("Manoplas.Tempo.Data", 14)
        config.add("Manoplas.Tempo.Nivel", 2, "Nivel da Poção")
        config.add("Manoplas.Tempo.Effect", PotionEffectType.JUMP.name, "Efeito da Poção")
        config.add("Manoplas.Tempo.Display", "&fJoia do Tempo")
        config.add("Manoplas.Tempo.Name", "Tempo")
        config.add("Manoplas.Tempo.Lore", tempo)
        config.add("Manoplas.Tempo.Slot.Line", 3)
        config.add("Manoplas.Tempo.Slot.Column", 5)

        config.saveConfig()
    }

    fun log(msg: String) {
        Bukkit.getConsoleSender().sendMessage("§b[${systemName}] §f$msg")
    }

    override fun getPlugin(): Any {
        return this
    }

    override fun getSystemName(): String {
        return this.name
    }

    override fun getPluginFolder(): File {
        return this.dataFolder
    }

    override fun getPluginConnected(): Plugin {
        return this
    }
}