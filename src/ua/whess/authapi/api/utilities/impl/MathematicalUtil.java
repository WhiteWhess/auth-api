package ua.whess.authapi.api.utilities.impl;

import ua.whess.authapi.api.utilities.Utility;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class MathematicalUtil extends Utility {
    private MathematicalUtil() {}

    static public final float nanoToSec = 1 / 1000000000f;

    static public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
    static public final float PI = (float)Math.PI;
    static public final float PI2 = PI * 2;
    static public final float HALF_PI = PI / 2;

    static public final float E = (float)Math.E;

    static private final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
    static private final int SIN_MASK = ~(-1 << SIN_BITS);
    static private final int SIN_COUNT = SIN_MASK + 1;

    static private final float radFull = PI2;
    static private final float degFull = 360;
    static private final float radToIndex = SIN_COUNT / radFull;
    static private final float degToIndex = SIN_COUNT / degFull;

    static public final float radiansToDegrees = 180f / PI;
    static public final float radDeg = radiansToDegrees;
    static public final float degreesToRadians = PI / 180;
    static public final float degRad = degreesToRadians;

    static private class Sin {
        static final float[] table = new float[SIN_COUNT];

        static {
            for (int i = 0; i < SIN_COUNT; i++)
                table[i] = (float)Math.sin((i + 0.5f) / SIN_COUNT * radFull);
            table[0] = 0f;
            table[(int)(90 * degToIndex) & SIN_MASK] = 1f;
            table[(int)(180 * degToIndex) & SIN_MASK] = 0f;
            table[(int)(270 * degToIndex) & SIN_MASK] = -1f;
        }
    }

    static public float sin (float radians) {
        return Sin.table[(int)(radians * radToIndex) & SIN_MASK];
    }

    static public float cos (float radians) {
        return Sin.table[(int)((radians + HALF_PI) * radToIndex) & SIN_MASK];
    }

    static public float sinDeg (float degrees) {
        return Sin.table[(int)(degrees * degToIndex) & SIN_MASK];
    }

    static public float cosDeg (float degrees) {
        return Sin.table[(int)((degrees + 90) * degToIndex) & SIN_MASK];
    }

    public static float atanUnchecked (double i) {
        double n = Math.abs(i);
        double c = (n - 1.0) / (n + 1.0);
        double c2 = c * c;
        double c3 = c * c2;
        double c5 = c3 * c2;
        double c7 = c5 * c2;
        double c9 = c7 * c2;
        double c11 = c9 * c2;
        return (float)Math.copySign((Math.PI * 0.25)
                + (0.99997726 * c - 0.33262347 * c3 + 0.19354346 * c5 - 0.11643287 * c7 + 0.05265332 * c9 - 0.0117212 * c11), i);
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue){
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).floatValue();
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
    }

    public static float atan2 (final float y, float x) {
        float n = y / x;
        if (n != n)
            n = (y == x ? 1f : -1f);
        else if (n - n != n - n) x = 0f;
        if (x > 0)
            return atanUnchecked(n);
        else if (x < 0) {
            if (y >= 0) return atanUnchecked(n) + PI;
            return atanUnchecked(n) - PI;
        } else if (y > 0)
            return x + HALF_PI;
        else if (y < 0) return x - HALF_PI;
        return x + y;
    }

    static public float acos (float a) {
        float a2 = a * a;
        float a3 = a * a2;
        if (a >= 0f) {
            return (float)Math.sqrt(1f - a) * (1.5707288f - 0.2121144f * a + 0.0742610f * a2 - 0.0187293f * a3);
        }
        return 3.14159265358979323846f
                - (float)Math.sqrt(1f + a) * (1.5707288f + 0.2121144f * a + 0.0742610f * a2 + 0.0187293f * a3);
    }

    static public float asin (float a) {
        float a2 = a * a;
        float a3 = a * a2;
        if (a >= 0f) {
            return 1.5707963267948966f
                    - (float)Math.sqrt(1f - a) * (1.5707288f - 0.2121144f * a + 0.0742610f * a2 - 0.0187293f * a3);
        }
        return -1.5707963267948966f + (float)Math.sqrt(1f + a) * (1.5707288f + 0.2121144f * a + 0.0742610f * a2 + 0.0187293f * a3);
    }

    public static float atan (float i) {
        double n = Math.min(Math.abs(i), Double.MAX_VALUE);
        double c = (n - 1.0) / (n + 1.0);
        double c2 = c * c;
        double c3 = c * c2;
        double c5 = c3 * c2;
        double c7 = c5 * c2;
        double c9 = c7 * c2;
        double c11 = c9 * c2;
        return (float)Math.copySign((Math.PI * 0.25)
                + (0.99997726 * c - 0.33262347 * c3 + 0.19354346 * c5 - 0.11643287 * c7 + 0.05265332 * c9 - 0.0117212 * c11), i);
    }

    static public int nextPowerOfTwo (int value) {
        if (value == 0) return 1;
        value--;
        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;
        return value + 1;
    }

    static public boolean isPowerOfTwo (int value) {
        return value != 0 && (value & value - 1) == 0;
    }

    static public short clamp (short value, short min, short max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public int clamp (int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public long clamp (long value, long min, long max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public float clamp (float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public double clamp (double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    static public float lerp (float fromValue, float toValue, float progress) {
        return fromValue + (toValue - fromValue) * progress;
    }

    static public float norm (float rangeStart, float rangeEnd, float value) {
        return (value - rangeStart) / (rangeEnd - rangeStart);
    }

    static public float map (float inRangeStart, float inRangeEnd, float outRangeStart, float outRangeEnd, float value) {
        return outRangeStart + (value - inRangeStart) * (outRangeEnd - outRangeStart) / (inRangeEnd - inRangeStart);
    }

    public static float lerpAngle (float fromRadians, float toRadians, float progress) {
        float delta = ((toRadians - fromRadians + PI2 + PI) % PI2) - PI;
        return (fromRadians + delta * progress + PI2) % PI2;
    }

    public static float lerpAngleDeg (float fromDegrees, float toDegrees, float progress) {
        float delta = ((toDegrees - fromDegrees + 360 + 180) % 360) - 180;
        return (fromDegrees + delta * progress + 360) % 360;
    }

    public static BigDecimal round(float f, int times) {
        BigDecimal bd = new BigDecimal(Float.toString(f));
        bd = bd.setScale(times, 4);
        return bd;
    }

    public static boolean isEven(int number) {
        return (number % 2 == 0);
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isInteger(Double variable) {
        return (variable.doubleValue() == Math.floor(variable.doubleValue()) && !Double.isInfinite(variable.doubleValue()));
    }

    public static float[] constrainAngle(float[] vector) {
        vector[0] = vector[0] % 360.0F;
        vector[1] = vector[1] % 360.0F;
        while (vector[0] <= -180.0F)
            vector[0] = vector[0] + 360.0F;
        while (vector[1] <= -180.0F)
            vector[1] = vector[1] + 360.0F;
        while (vector[0] > 180.0F)
            vector[0] = vector[0] - 360.0F;
        while (vector[1] > 180.0F)
            vector[1] = vector[1] - 360.0F;
        return vector;
    }

    public static double roundToDecimalPlace(double value, double inc) {
        double halfOfInc = inc / 2.0D;
        double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc)
            return (new BigDecimal(Math.ceil(value / inc) * inc, MathContext.DECIMAL64))
                    .stripTrailingZeros()
                    .doubleValue();
        return (new BigDecimal(floored, MathContext.DECIMAL64))
                .stripTrailingZeros()
                .doubleValue();
    }

    static private final int BIG_ENOUGH_INT = 16 * 1024;
    static private final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
    static private final double CEIL = 0.9999999;
    static private final double BIG_ENOUGH_CEIL = 16384.999999999996;
    static private final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;

    static public int floor (float value) {
        return (int)(value + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;
    }

    static public int floorPositive (float value) {
        return (int)value;
    }

    static public int ceil (float value) {
        return BIG_ENOUGH_INT - (int)(BIG_ENOUGH_FLOOR - value);
    }

    static public int ceilPositive (float value) {
        return (int)(value + CEIL);
    }

    public static float wrapDegrees(float value)
    {
        value = value % 360.0F;

        if (value >= 180.0F)
        {
            value -= 360.0F;
        }

        if (value < -180.0F)
        {
            value += 360.0F;
        }

        return value;
    }

    public static double wrapDegrees(double value)
    {
        value = value % 360.0D;

        if (value >= 180.0D)
        {
            value -= 360.0D;
        }

        if (value < -180.0D)
        {
            value += 360.0D;
        }

        return value;
    }

    public static int clampAngle(int angle)
    {
        angle = angle % 360;

        if (angle >= 180)
        {
            angle -= 360;
        }

        if (angle < -180)
        {
            angle += 360;
        }

        return angle;
    }

    static public int round (float value) {
        return (int)(value + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;
    }

    static public int roundPositive (float value) {
        return (int)(value + 0.5f);
    }

    static public boolean isZero (float value) {
        return Math.abs(value) <= FLOAT_ROUNDING_ERROR;
    }

    static public boolean isZero (float value, float tolerance) {
        return Math.abs(value) <= tolerance;
    }

    static public boolean isEqual (float a, float b) {
        return Math.abs(a - b) <= FLOAT_ROUNDING_ERROR;
    }

    static public boolean isEqual (float a, float b, float tolerance) {
        return Math.abs(a - b) <= tolerance;
    }

    static public float log (float a, float value) {
        return (float)(Math.log(value) / Math.log(a));
    }

    static public float log2 (float value) {
        return log(2, value);
    }
}
