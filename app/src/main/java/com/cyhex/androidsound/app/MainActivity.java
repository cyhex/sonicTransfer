package com.cyhex.androidsound.app;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private EditText fq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fq = (EditText) findViewById(R.id.fq);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onListenSound(View v) {

    }

    public void onPlaySound(View v) {
        String binString = toBinaryString("a");
        final int sampleRate = 44100;
        final int freqOfTone = Integer.parseInt(fq.getText().toString());
        final int numSamples = sampleRate * 2;
        ByteArrayOutputStream sample = new ByteArrayOutputStream();

        int frameSize = (sampleRate/freqOfTone);
        double frameVal = 0;

        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            frameVal = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
            sample.write((byte) (frameVal * 127));

        }

        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT, numSamples,
                AudioTrack.MODE_STATIC);

        audioTrack.write(sample.toByteArray() , 0, sample.size());
        audioTrack.play();

    }

    private String toBinaryString(String str) {
        String r = "";
        byte[] bytes = str.getBytes();

        for (byte b : bytes) {
            r += String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
        }
        return r;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
