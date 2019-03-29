package restforyou.com.tofsologia.utils.audimanager;

import android.net.Uri;

import java.util.List;

public interface IAudioManager {

    void play(List<String> files);

    void stop();

    void playWelcomeLetter(String letter);
    void playNextLetter(String letter);
    void playWelcomeWord(String word);
    void playNextWord(String word);
}
