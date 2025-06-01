package ru.kosgos.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.MediaStyleNotificationHelper
import androidx.media3.ui.PlayerNotificationManager
import com.google.common.collect.ImmutableList
import ru.kosgos.firebase.MainActivity.Companion.CHANNEL_ID

class AudioPlayerService : MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private lateinit var player: ExoPlayer

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        player = ExoPlayer.Builder(this).build()

        val sessionCallback = object : MediaSession.Callback {}

        val uri =
            "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/62/0f/a4/620fa490-3ffb-3bf0-f850-62a1527b8c6c/mzaf_3111022950442747371.plus.aac.p.m4a"
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true


        mediaSession = MediaSession.Builder(this, player)
            .setId("audio_session")
            .setCallback(sessionCallback)

            .build()

        setMediaNotificationProvider(DefaultMediaNotificationProvider.Builder(this)
            .setChannelId(CHANNEL_ID)
            .setNotificationId(1)
            .build())
        val ms = mediaSession
        if (ms != null) {
            startForeground(1 , NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_ac_unit_24)
                // This is globally changed every time when
                // I add a new MediaItem from background service
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(MediaStyleNotificationHelper.MediaStyle(ms))
                .build())
        }

    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.release()
        player.release()
        super.onDestroy()
    }
}
