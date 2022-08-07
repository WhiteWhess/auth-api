package ua.whess.authapi.api.utilities.impl;

import ua.whess.authapi.api.utilities.Utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomizeUtil extends Utility {
    private RandomizeUtil() {}

    private final static char[] DICT_SPACES = new char[]{
            '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2007', '\u2008', '\u2009', '\u200A', '\u200B', '\u200C', '\u200D', '\u200E', '\u200F'
    };

    private static final String alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String randomString(final int strLength) {
        StringBuilder stringBuilder = new StringBuilder(strLength);
        for (int i = 0; i < strLength; ++i) {
            stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        } return stringBuilder.toString();
    }

    public static String crazyString(final int len) {
        char[] buildString = new char[len];
        for (int i = 0; i < len; i++) {
            buildString[i] = DICT_SPACES[RANDOM.nextInt(DICT_SPACES.length)];
        } return new String(buildString);
    }

    public static String unicodeString(final int length, final int start, final int end) {
        if (start < 0)
            throw new IllegalArgumentException();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) RandomizeUtil.random(start, end));
        } return stringBuilder.toString();
    }

    static public int random (int range) {
        return RANDOM.nextInt(range + 1);
    }

    static public int random (int start, int end) {
        return start + RANDOM.nextInt(end - start + 1);
    }

    static public long random (long range) {
        return random(0L, range);
    }

    static public long random (long start, long end) {
        final long rand = RANDOM.nextLong();
        if (end < start) {
            long t = end;
            end = start;
            start = t;
        }
        long bound = end - start + 1L;
        final long randLow = rand & 0xFFFFFFFFL;
        final long boundLow = bound & 0xFFFFFFFFL;
        final long randHigh = (rand >>> 32);
        final long boundHigh = (bound >>> 32);
        return start + (randHigh * boundLow >>> 32) + (randLow * boundHigh >>> 32) + randHigh * boundHigh;
    }

    /** Returns a random boolean value. */
    static public boolean randomBoolean () {
        return RANDOM.nextBoolean();
    }

    static public boolean randomBoolean (float chance) {
        return random() < chance;
    }

    public static int randomNumber(int max, int min) {
        return Math.round(min + (float)Math.random() * (max - min));
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0F)
            return startInclusive;
        return (float)(startInclusive + (endInclusive - startInclusive) * Math.random());
    }

    public static double getRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1.0D);
    }

    public static float randomFloat(float min, float max) {
        return min + RANDOM.nextFloat() * (max - min);
    }

    public static double preciseRound(double value, double precision) {
        double scale = Math.pow(10.0D, precision);
        return Math.round(value * scale) / scale;
    }

    public static int getMiddle(int old, int newValue) {
        return (old + newValue) / 2;
    }

    public static double round(double num, double increment) {
        double v = (double) Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double randomize(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max)
            scaled = max;
        double shifted = scaled + min;
        if (shifted > max)
            shifted = max;
        return shifted;
    }

    public static float randomizeFloat(float min, float max) {
        return (float) (min + (max - min) * Math.random());
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static int randomize(int max, int min) {
        return -min + (int)(Math.random() * (max - -min + 1));
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0D / inc;
        return Math.round(val * one) / one;
    }

    static public float random () {
        return RANDOM.nextFloat();
    }

    static public float random (float range) {
        return RANDOM.nextFloat() * range;
    }

    static public float random (float start, float end) {
        return start + RANDOM.nextFloat() * (end - start);
    }

    static public int randomSign () {
        return 1 | (RANDOM.nextInt() >> 31);
    }

    public static float randomTriangular () {
        return RANDOM.nextFloat() - RANDOM.nextFloat();
    }

    public static float randomTriangular (float max) {
        return (RANDOM.nextFloat() - RANDOM.nextFloat()) * max;
    }

    public static float randomTriangular (float min, float max) {
        return randomTriangular(min, max, (min + max) * 0.5f);
    }

    public static float randomTriangular (float min, float max, float mode) {
        float u = RANDOM.nextFloat();
        float d = max - min;
        if (u <= (mode - min) / d) return min + (float)Math.sqrt(u * d * (mode - min));
        return max - (float)Math.sqrt((1 - u) * d * (max - mode));
    }

    public static int getRandomInRange(int max, int min) {
        return (int)(min + (max - min) * RANDOM.nextDouble());
    }

    public static double getRandomInRange(double max, double min) {
        return min + (max - min) * RANDOM.nextDouble();
    }
}
