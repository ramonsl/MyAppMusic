package com.example.ramonsl.myappmusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private double mStartTime = 0;
    private double mFinalTime = 0;
    //linha 18
    private Handler myHandler = new Handler();
    private Button btnPause;
    private Button btnPlay;
    private Button btnStop;
    private TextView inicio;
    private TextView fim;
    private SeekBar seekbar;
    private MediaPlayer mediaPlayer;
    //Linha 26
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            mStartTime = mediaPlayer.getCurrentPosition();
            inicio.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) mStartTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) mStartTime)))
            );
            seekbar.setProgress((int) mStartTime);
            myHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        inicio = findViewById(R.id.txtIn);
        fim = findViewById(R.id.txtFim);
        seekbar = (SeekBar) findViewById(R.id.seek);
        seekbar.setClickable(false);
        //Linha 52
        mediaPlayer = MediaPlayer.create(this, R.raw.music);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

                mFinalTime = mediaPlayer.getDuration();
                mStartTime = mediaPlayer.getCurrentPosition();
                fim.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) mFinalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) mFinalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        mFinalTime)))
                );
                inicio.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) mStartTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        mStartTime)))
                );
                double tempo = mFinalTime;
                seekbar.setMax(Integer.valueOf((int) tempo));
                seekbar.setProgress((int) mStartTime);
                //linha 78
                myHandler.postDelayed(UpdateSongTime, 100);
                inicio.setText((String.valueOf(mediaPlayer.getCurrentPosition())));
            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });


    }


}
