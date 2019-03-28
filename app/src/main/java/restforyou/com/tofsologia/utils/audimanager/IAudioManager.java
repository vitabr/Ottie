package restforyou.com.tofsologia.utils.audimanager;

import android.net.Uri;

import java.util.List;

public interface IAudioManager {

    void play(List<String> files);

    void stop();
}
