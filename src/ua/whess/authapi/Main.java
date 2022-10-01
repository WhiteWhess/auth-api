package ua.whess.authapi;

import ua.whess.authapi.api.authorization.Authorization;

public final class Main {
    // LOADER INITIALIZATION
    private static final Authorization auth = new Authorization();

    // MAIN METHOD
    public static void main(String[] args) {
        new Thread(auth::entryPoint).start();
    }
}
