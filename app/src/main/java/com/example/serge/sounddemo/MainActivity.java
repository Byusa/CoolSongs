package com.example.serge.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;

    AudioManager audioManager; // helps  us to interact with the audio

    public void playAudio(View view){

        mplayer.start();

    }

    public void pauseAudio(View view) {

        mplayer.pause();

    }


    @Override//adding our codes to already existing codes
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer =  MediaPlayer.create(this, R.raw.hotnigga);
//////////////////////////////////////////////////////////////
//set max to be equated to the length of the audio file
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //alows us to work on volumes and audio in general
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//for the max volume
        int curVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //curVolume

        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar); //variable volumeControl
        volumeControl.setMax(maxVolume/20); //set the maxVolume to our maxVolume
        volumeControl.setProgress(curVolume); //set the seekbar value to the current volume
/////////////////////////////////////////////////////////////////////////////////        // //////////////////////////////////////////////////////////////////////

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ //added a seekbarChangeListener


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { //when the user starts tracking

            }

            @Override
           public void onStopTrackingTouch(SeekBar seekBar) { //when the user stops tracking

            }

            @Override                     //the seekBar    //the increase or decrease of the vol //true if the user increases it
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { //adding our own codes to an already existing method

               // Log.i("Seek value", Integer.toString(progress)); //progress has to be a string

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); // when the user update the seekbar then  it will update the (real) Stream volume
            }
        });
 //////////////////////////////////////////////////////////////////////////////////////////////////       ///////////////////////////////////////////////////////////
       // second seekbar
       final SeekBar scrubber = (SeekBar) findViewById(R.id.seekBar2);

        scrubber.setMax(mplayer.getDuration()); // with maxVolume which is equal to the duration of the audio. reduce the volume by 4

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //the codes here run everyafter each second(the scrubber will be moving )

                scrubber.setProgress(mplayer.getCurrentPosition());
            }
        }, 0, 1000); //at each second the code is going to be loaded (the volume increases at each second)
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////


                scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean FromUser) {

                        Log.i("Seek value", Integer.toString(progress)); //progress has to be a string //shown below
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); // when the user update the seekbar then  it will update the (real) Stream volume
                        mplayer.seekTo(progress); //to move the volume back and forward
                    }
                });


    }
}
