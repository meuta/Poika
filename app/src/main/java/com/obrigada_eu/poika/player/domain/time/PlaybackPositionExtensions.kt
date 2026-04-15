package com.obrigada_eu.poika.player.domain.time

fun Float.toPlaybackPositionMs() = (this * 1000).toLong()