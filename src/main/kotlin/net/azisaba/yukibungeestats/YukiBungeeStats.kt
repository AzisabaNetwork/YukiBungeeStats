package net.azisaba.yukibungeestats

import net.md_5.bungee.api.ServerPing
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.ProxyPingEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.event.EventHandler
import java.net.InetSocketAddress
import java.util.*

class YukiBungeeStats : Plugin(), Listener {

    private val pings = mutableMapOf<String, Int>().withDefault { 0 }
    private val connects = mutableMapOf<UUID, Int>().withDefault { 0 }

    override fun onEnable() {
        proxy.pluginManager.registerListener(this, this)
    }

    @EventHandler
    fun onPing(e: ProxyPingEvent) {
        val ip = (e.connection.socketAddress as InetSocketAddress).address.hostAddress
        pings[ip] = pings.getValue(ip).inc()
    }

    @EventHandler
    fun onConnect(e: PostLoginEvent) {
        val uuid = e.player.uniqueId
        connects[uuid] = connects.getValue(uuid).inc()
    }

    @EventHandler
    fun onPingForStats(e: ProxyPingEvent) {
        val sample = arrayOf(
            "Ping数: ${pings.size} IP (${pings.values.sum()} 回)",
            "接続数: ${connects.size} 人 (${connects.values.sum()} 回)"
        ).map { ServerPing.PlayerInfo(it, UUID.randomUUID()) }.toTypedArray()
        e.response.players.sample = sample
    }
}
