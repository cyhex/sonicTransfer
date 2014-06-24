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

import java.io.ByteArrayOutputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onListenSound(View v) {
        AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_8BIT, 4096);
        recorder.startRecording();

    }

    public void onPlaySound(View v) {
        String binString = toBinaryString("a");
        final int sampleRate = 44100;
        final int numSamples = binString.length();
        ByteArrayOutputStream sample = new ByteArrayOutputStream();

        for (char c : binString.toCharArray()) {
            if (c == '0') {
                sample.write(1);
                sample.write(0);
                sample.write(0);
            } else {
                sample.write(1);
                sample.write(1);
                sample.write(0);
            }
        }

        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT, numSamples,
                AudioTrack.MODE_STATIC);

        audioTrack.write(sample.toByteArray(), 0, sample.size());
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
