package utils;

import config.ConfigManager;

public class TimeoutUtil {
    public static int getDefaultTimeout() {
        return ConfigManager.getInt("timeout");
    }
}
