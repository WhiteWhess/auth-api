package ua.whess.authapi.api.utilities.impl;

import ua.whess.authapi.api.utilities.Utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class EncodeUtil extends Utility {
    private EncodeUtil() { }
    public static final class SKCRYPT {
        private SKCRYPT() { }
        public static byte[] encode(byte[] bytes, int key) {
            for (int i = 0; i < bytes.length; ++i) {
                bytes[i] ^= ((key - i) % (i + 16));
            } return bytes;
        }

        public static byte[] encode(byte[] bytes) {
            return encode(bytes, Integer.MAX_VALUE);
        }
        public static String encode(String string, int key) {
            return new String(encode(string.getBytes(StandardCharsets.UTF_8), key));
        }

        public static String encode(String string) {
            return new String(encode(string.getBytes(StandardCharsets.UTF_8), string.length()));
        }

        public static byte[] decode(byte[] bytes, int key) {
            for (int i = 0; i < bytes.length; ++i) {
                bytes[i] ^= ((key - i) % (i + 16));
            } return bytes;
        }

        public static byte[] decode(byte[] bytes) {
            return decode(bytes, Integer.MAX_VALUE);
        }

        public static String decode(String string, int key) {
            return new String(decode(string.getBytes(StandardCharsets.UTF_8), key));
        }

        public static String decode(String string) {
            return new String(decode(string.getBytes(StandardCharsets.UTF_8), string.length()));
        }
    }

    public static final class XOR {
        private XOR() { }
        public static byte[] encode(byte[] bytes) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] ^= i;
            } return bytes;
        }

        public static byte[] decode(byte[] bytes) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] ^= i;
            } return bytes;
        }

        public static String encode(String string) {
            return new String(encode(string.getBytes(StandardCharsets.UTF_8)));
        }

        public static String decode(String string) {
            return new String(decode(string.getBytes(StandardCharsets.UTF_8)));
        }
    }

    public static final class SHA224 {
        private SHA224() {}
        private static final MessageDigest hasher;

        static {
            try {
                hasher = MessageDigest.getInstance("SHA-224");
            } catch (Exception ignored) {
                throw new RuntimeException();
            }
        }

        public static String hash(String str) {
            return new String(hash(str.getBytes(StandardCharsets.UTF_8)));
        }

        public static byte[] hash(byte[] bytes) {
            hasher.update(bytes);
            final StringBuilder hash = new StringBuilder();
            for (final byte b : hasher.digest()) {
                hash.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
            }
            return hash.toString().getBytes(StandardCharsets.UTF_8);
        }
    }
}
