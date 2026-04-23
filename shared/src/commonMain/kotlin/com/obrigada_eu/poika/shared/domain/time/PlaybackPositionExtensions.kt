package com.obrigada_eu.poika.shared.domain.time

fun Float.toPlaybackPositionMs() = (this * 1000).toLong()