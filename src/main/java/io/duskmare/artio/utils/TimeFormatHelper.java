package io.duskmare.artio.utils;

import java.time.Duration;

public class TimeFormatHelper {
    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absoluteValueSeconds = Math.abs(seconds);

        String positive = String.format(
            "%d:%02d:%02d",
            absoluteValueSeconds / 3600,
            (absoluteValueSeconds % 3600) / 60,
            absoluteValueSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
