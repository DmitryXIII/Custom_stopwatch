package com.ineedyourcode.customstopwatch.domain

interface TimestampProvider {
    fun getMilliseconds(): Long
}