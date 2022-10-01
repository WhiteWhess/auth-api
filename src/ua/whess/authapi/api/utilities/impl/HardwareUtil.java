package ua.whess.authapi.api.utilities.impl;

import ua.whess.authapi.api.utilities.Utility;

public final class HardwareUtil extends Utility {
    private HardwareUtil() { }
    public static String getHWID() {
        String hw = EncodeUtil.SKCRYPT.encode(
                System.getProperty("os.name")           +
                System.getProperty("os.version")        +
                System.getProperty("os.arch")           +
                System.getenv("PROCESSOR_ARCHITECTURE") +
                System.getenv("PROCESSOR_ARCHITEW6432") +
                System.getenv("PROCESSOR_IDENTIFIER")   +
                System.getenv("NUMBER_OF_PROCESSORS")   +
                Runtime.getRuntime().availableProcessors()
        );
        return "AUTH_API-" + EncodeUtil.SHA224.hash(hw);
    }
}
