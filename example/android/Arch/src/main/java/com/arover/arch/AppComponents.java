package com.arover.arch;

import android.view.Surface;

/**
 * @author arover
 * created at 8/11/21 1:55 AM
 */

public interface AppComponents {
    void playVideo(Surface surface, int width, int height);
    void stopVideo();
}
