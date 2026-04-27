package com.obrigada_eu.poika.desktop.fake

import com.obrigada_eu.poika.shared.domain.audio.AudioService
import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateUpdater
import com.obrigada_eu.poika.shared.domain.session.PlayerSessionWriter

class DesktopFakeAudioService(
    private val progressTracker: ProgressStateUpdater,
    private val playerSessionWriter: PlayerSessionWriter,
) : AudioService {

    private var playersMap: Map<String, FakePlayer> = mapOf()
    private var playerIsReady = false
    private var isPlaying: Boolean = false
    private var currentSpeed = 1f


    override fun loadTracks(songMetaData: SongMetaData) {
        println("song: ${songMetaData.artist} - ${songMetaData.title}")
        println("tracks: ${songMetaData.tracks.joinToString(", ") { it.name }}")

        val tracks = songMetaData.tracks
        val durationMs = 134500L

        playersMap = tracks.map { it.name }.associateWith { part -> FakePlayer(
            id = part,
            volume = 1f,
            durationMs = durationMs
        ) }

        updateDuration(durationMs)

        playerSessionWriter.setCurrentSong(songMetaData)
        playerSessionWriter.updateParts(playersMap.mapValues { (_, player) -> player.volume })

    }

    override fun togglePlayPause() {
        println("play/pause")
        if (playerIsReady) {
            if (isPlaying) {
                playerSessionWriter.setIsPlaying(false)
            } else {
                playerSessionWriter.setIsPlaying(true)
            }
            isPlaying = !isPlaying
        }
    }

    override fun stop() {
        println("stop")

        seekTo(0)
        isPlaying = false
        playerSessionWriter.setIsPlaying(false)
    }

    override fun rewind(durationMs: Long) {
        println("rewind $durationMs")
        (getCurrentPosition() + durationMs)
            .coerceIn(0L..getDuration())
            .let { position ->
                playersMap.values.forEach { player -> player.seekTo(position) }
                updateProgressTracker(currentPosition = position)
            }
    }

    override fun changeSpeed(speedDif: Float) {
        println("speedDif $speedDif")
        val speed = (currentSpeed + speedDif).coerceIn(0.5f..2f)
        currentSpeed = speed
        setSpeed(speed)
    }

    private fun setSpeed(speed: Float) {
        playersMap.values.forEach { player ->
            player.setSpeed(speed)
        }
        playerSessionWriter.setSpeed(speed)
    }

    override fun setVolume(part: String, volume: Float) {
        println("volume $part = $volume")
        playersMap[part]?.volume = volume
        playerSessionWriter.setTrackVolume(part, volume)
    }

    override fun seekTo(positionMs: Long) {
        println("seek $positionMs")
        playersMap.values.forEach { player -> player.seekTo(positionMs) }
        updateProgressTracker(currentPosition = positionMs)
    }

    private fun getCurrentPosition(): Long {
        return playersMap.values.firstOrNull()?.currentPosition ?: 0L
    }

    private fun getDuration(): Long {
        return playersMap.values.firstOrNull()?.durationMs ?: 0L
    }

    private fun updateProgressTracker(currentPosition: Long) {
        progressTracker.update(progressTracker.currentState().copy(currentPosition = currentPosition))
    }

    private fun updateDuration(duration: Long) {
        progressTracker.update(progressTracker.currentState().copy(duration = duration))
    }
}

data class FakePlayer(
    val id: String,
    var volume: Float = 1f,
    var currentPosition: Long = 0,
    var durationMs: Long,
    var playbackSpeed: Float = 1f
) {
    fun setSpeed(speed: Float) { playbackSpeed = speed }
    fun seekTo(positionMs: Long) { currentPosition = positionMs }
}