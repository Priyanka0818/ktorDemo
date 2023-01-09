package com.app.ktorcrud.utils

/**
 * Created by Priyanka.
 */
class ConnectionModel(type: Int, isConnected: Boolean) {
    private val type: Int
    private val isConnected: Boolean

    init {
        this.type = type
        this.isConnected = isConnected
    }

    fun getType(): Int {
        return type
    }

    fun getIsConnected(): Boolean {
        return isConnected
    }
}