package restforyou.com.tofsologia.utils.audimanager;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class AudioManager implements IAudioManager, MediaPlayer.OnCompletionListener {

    private Context context;
    private MediaPlayer mediaPlayer;
    private int currentFile = 0;
    private List<String> list;

    public AudioManager(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void play(List<String> list) {
        this.list = list;
        if (currentFile < list.size()) {
            playFile(list.get(currentFile));
            currentFile++;
        }
    }

    public void playWelcomeLetter(String letter){
        ArrayList sounds = new ArrayList<String>();
        sounds.add("hello_kids.wav");
        sounds.add("Lets_write_the_letter.wav");
        sounds.add(letter +".wav");
        play(sounds);
    }

    public void playNextLetter(String letter){
        ArrayList sounds = new ArrayList<String>();
        sounds.add("Lets_write_the_letter.wav");
        sounds.add(letter +".wav");
        play(sounds);
    }

    public void playWelcomeWord(String word){

        ArrayList sounds = new ArrayList<String>();
        sounds.add("hello_kids.wav");
        sounds.add("Lets_write_the_words_2.wav");
        sounds.add(word + ".wav");
        play(sounds);
    }


    public void playNextWord(String word){

        ArrayList sounds = new ArrayList<String>();
        sounds.add("Lets_write_the_words_2.wav");
        sounds.add(word + ".wav");
        play(sounds);
    }

    public void playGongrats(){
        list = new ArrayList<>();
        list.add("great_job.wav");
    }

    private void playFile(String soundFileName) {
        try {
            AssetFileDescriptor descriptor = context.getAssets().openFd(soundFileName);
            mediaPlayer.reset();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        play(list);
    }
}
