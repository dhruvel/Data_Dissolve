package com.datadissolve.util;

import java.util.Arrays;
import java.security.SecureRandom;

/**
 * This class contains methods to sanitize data using the Gutmann, DoD, and Schneier methods.
 * The Gutmann method overwrites data 35 times with 8 different patterns.
 * The DoD method overwrites data 7 times with 4 different patterns.
 * The Schneier method overwrites data 7 times with 4 different patterns.
 * The Custom method overwrites data n times with m different patterns.
 */
public class DataSanitization {
    public static void wipeDataGutmann(byte[] data) {
        byte[][] patterns = new byte[35][];
        for (int i = 0; i < 4; i++) {
            patterns[i] = new byte[]{(byte) 0xFF};
        }
        for (int i = 4; i < 31; i++) {
            patterns[i] = new byte[1];
            new SecureRandom().nextBytes(patterns[i]);
        }
        for (int i = 31; i < 35; i++) {
            patterns[i] = new byte[]{(byte) 0x00};
        }

        for (byte[] pattern : patterns) {
            for (int j = 0; j < data.length; j += pattern.length) {
                for (int k = 0; k < pattern.length; k++) {
                    if (j + k < data.length) {
                        data[j + k] = pattern[k];
                    }
                }
            }
        }
        Arrays.fill(data, (byte) 0x00);
    }

    public static void wipeDataDoD(byte[] data) {
        byte[] pattern1 = {(byte) 0x00};
        byte[] pattern2 = {(byte) 0xFF};
        byte[] pattern3 = new byte[data.length];
        byte[] pattern4 = new byte[data.length];
        byte[] pattern5 = {(byte) 0xFF};
        byte[] pattern6 = {(byte) 0x00};
        byte[] pattern7 = new byte[data.length];

        new SecureRandom().nextBytes(pattern3);
        new SecureRandom().nextBytes(pattern4);
        new SecureRandom().nextBytes(pattern7);
        byte[][] patterns = {pattern1, pattern2, pattern3, pattern4, pattern5, pattern6, pattern7};

        for (byte[] pattern : patterns) {
            for (int j = 0; j < data.length; j += pattern.length) {
                for (int k = 0; k < pattern.length; k++) {
                    if (j + k < data.length) {
                        data[j + k] = pattern[k];
                    }
                }
            }
        }
        Arrays.fill(data, (byte) 0x00);
    }

    public static void wipeDataSchneier(byte[] data) {
        byte[] pattern1 = {(byte) 0x00};
        byte[] pattern2 = {(byte) 0xFF};
        byte[] pattern3 = new byte[data.length];
        byte[] pattern4 = new byte[pattern3.length];
        byte[] pattern5 = {(byte) 0x55, (byte) 0xAA};
        byte[] pattern6 = {(byte) 0xAA, (byte) 0x55};
        byte[] pattern7 = new byte[data.length];

        new SecureRandom().nextBytes(pattern3);
        new SecureRandom().nextBytes(pattern7);
        for (int i = 0; i < pattern3.length; i++) {pattern4[i] = (byte) ~pattern3[i];}
        byte[][] patterns = {pattern1, pattern2, pattern3, pattern4, pattern5, pattern6, pattern7};

        for (byte[] pattern : patterns) {
            for (int j = 0; j < data.length; j += pattern.length) {
                for (int k = 0; k < pattern.length; k++) {
                    if (j + k < data.length) {
                        data[j + k] = pattern[k];
                    }
                }
            }
        }
        Arrays.fill(data, (byte) 0x00);
    }

    public static void wipeDataCustom(byte[] data, int numPatterns, int numBits) {
        byte[][] patterns = new byte[numPatterns][numBits];
        for (int i = 0; i < numPatterns; i++) {
            new SecureRandom().nextBytes(patterns[i]);
        }
        for (byte[] pattern : patterns) {
            for (int j = 0; j < data.length; j += pattern.length) {
                for (int k = 0; k < pattern.length; k++) {
                    if (j + k < data.length) {
                        data[j + k] = pattern[k];
                    }
                }
            }
        }
        Arrays.fill(data, (byte) 0x00);
    }

}