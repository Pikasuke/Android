package com.example.catchit;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    private final int SOUND_POOL_MAX = 3;

    private static SoundPool soundPool;
    private static int coinSound, champSound, hitMissileSound, jumpSound, endGameSound, startGameSound;
    private static MediaPlayer mediaPlayer;

    public SoundPlayer(Context context) {

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            //SoundPool is deprecated since API 21 lolippop
            audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX).build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        coinSound = soundPool.load(context, R.raw.coin,1);
        champSound = soundPool.load(context, R.raw.champ,1);
        endGameSound = soundPool.load(context, R.raw.endgame,1);
        hitMissileSound = soundPool.load(context, R.raw.hit,1);
        jumpSound = soundPool.load(context, R.raw.jumps,1);
        startGameSound = soundPool.load(context, R.raw.startgame,1);

        mediaPlayer = MediaPlayer.create(context, R.raw.hurryup);
        mediaPlayer.setLooping(false);
    }

    public void playCoinSound(){
        soundPool.play(coinSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playChampSound(){
        soundPool.play(champSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playEndGameSound(){
        soundPool.play(endGameSound, 1.0f, 1.0f, 2, 0, 1.0f);
    }
    public void playHitMissileSound(){
        soundPool.play(hitMissileSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playJumpSound(){
        soundPool.play(jumpSound, 1.0f, 1.0f, 0, 0, 1.0f);
    }
    public void playStartGameSound(){
        soundPool.play(startGameSound, 1.0f, 1.0f, 3, 0, 1.0f);
    }

    public void playHurryUp(){
        mediaPlayer.start();
    }

    public void pauseHurryUp(){
        if(mediaPlayer !=null){
            mediaPlayer.pause();
        }
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
}
