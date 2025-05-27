package net.guizhanss.fastmachines.core.services

import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.guizhanlib.kt.slimefun.config.ConfigField
import net.guizhanss.guizhanlib.kt.slimefun.config.addonConfig

class ConfigService(plugin: FastMachines) {

    lateinit var autoUpdate: ConfigField<Boolean>
    lateinit var debug: ConfigField<Boolean>
    lateinit var lang: ConfigField<String>
    lateinit var enableResearches: ConfigField<Boolean>

    // fast machines options
    lateinit var fmTickRate: ConfigField<Int>
    lateinit var fmUseEnergy: ConfigField<Boolean>
    lateinit var fmRequireSfResearch: ConfigField<Boolean>

    private val config = addonConfig(plugin, "config.yml") {
        autoUpdate = boolean("auto-update", true)
        debug = boolean("debug", false)
        lang = string("lang", FastMachines.DEFAULT_LANG)
        enableResearches = boolean("enable-researches", false)
        fmTickRate = int("fast-machines.tick-rate", 10, 5, 600)
        fmUseEnergy = boolean("fast-machines.use-energy", true)
        fmRequireSfResearch = boolean("fast-machines.require-sf-research", false)
    }

    init {
        reload()
    }

    fun reload() {
        config.reload()
    }
}
