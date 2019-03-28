package restforyou.com.tofsologia.utils.audimanager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class AudioManager implements IAudioManager {

    private final Context context;
     private MediaPlayer mediaPlayer;

    public AudioManager(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();


    }

    @Override
    public void play(Uri file) {
        mediaPlayer = MediaPlayer.create(context, file);
    }

    @Override
    public void stop() {
        if (mediaPlayer  != null)
        mediaPlayer.stop();
    }
}
