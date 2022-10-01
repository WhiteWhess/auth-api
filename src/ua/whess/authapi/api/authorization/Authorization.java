package ua.whess.authapi.api.authorization;

import ua.whess.authapi.api.utilities.impl.HardwareUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public final class Authorization {
    // PROTECTION ENTRY POINT
    public void entryPoint() {
        // PROTECTION THREAD INIT
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(
                                    "                                                                       \n" +
                                    "       █████╗ ██╗   ██╗████████╗██╗  ██╗       █████╗ ██████╗ ██╗      \n" +
                                    "      ██╔══██╗██║   ██║╚══██╔══╝██║  ██║      ██╔══██╗██╔══██╗██║      \n" +
                                    "      ███████║██║   ██║   ██║   ███████║█████╗███████║██████╔╝██║      \n" +
                                    "      ██╔══██║██║   ██║   ██║   ██╔══██║╚════╝██╔══██║██╔═══╝ ██║      \n" +
                                    "      ██║  ██║╚██████╔╝   ██║   ██║  ██║      ██║  ██║██║     ██║      \n" +
                                    "      ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝      ╚═╝  ╚═╝╚═╝     ╚═╝      \n" +
                                    "                                                                       \n");
                    System.out.println("Your HWID: " + HardwareUtil.getHWID());
                    System.out.print("Enter key: ");
                    String key = new Scanner(System.in).nextLine();
                    boolean auth = auth(key);
                    if (!auth) {
                        System.err.println("Invalid key or HWID");
                        Thread.currentThread().stop();
                    } // AUTH IS TRUE
                } catch (IOException ignored) {}
            }

            private boolean auth(String key) throws IOException {
                Scanner scanner = new Scanner(new URL("https://pastebin.com/raw/F37jjEzB").openStream());
                while (scanner.hasNext()) {
                    String [ ] splitted = scanner.nextLine().split(":");
                    if (key.equals(splitted[0]) && HardwareUtil.getHWID().equals(splitted[1]))
                        return true;
                } return false;
            }
        }.start();
    }
}
