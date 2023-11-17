package com.example.data_dissolve;

import java.util.Arrays;
import java.security.SecureRandom;


public class DataSanitization {
    public static void wipeDataGuttman(byte[] data) {
        byte[] pattern1 = {(byte) 0x55, (byte) 0xAA};
        byte[] pattern2 = {(byte) 0x92, (byte) 0x49};
        byte[] pattern3 = {(byte) 0x49, (byte) 0x92};
        byte[] pattern4 = {(byte) 0x00, (byte) 0xFF};
        byte[] pattern5 = {(byte) 0xFF, (byte) 0x00};
        byte[] pattern6 = {(byte) 0x6D, (byte) 0xB6};
        byte[] pattern7 = {(byte) 0xB6, (byte) 0x6D};
        byte[] pattern8 = {(byte) 0x00, (byte) 0x00};

        byte[][] patterns = {pattern1, pattern2, pattern3, pattern4, pattern5, pattern6, pattern7, pattern8};

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

    public static void wipeDataSchneider(byte[] data) {
        byte[] pattern1 = {(byte) 0x00};
        byte[] pattern2 = {(byte) 0xFF};
        byte[] pattern3 = new byte[data.length];
        new SecureRandom().nextBytes(pattern3);
        byte[] pattern4 = new byte[pattern3.length];
        for (int i = 0; i < pattern3.length; i++) {
            pattern4[i] = (byte) ~pattern3[i];
        }
        byte[] pattern5 = {(byte) 0x55, (byte) 0xAA};
        byte[] pattern6 = {(byte) 0xAA, (byte) 0x55};
        byte[] pattern7 = new byte[data.length];
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

}
