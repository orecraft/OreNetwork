/*
 * Created on 2019/4/20
 * Author: MaHua_A
 * Copyright 2019 by OreCraft Studio
 * DO NOT MODIFY THESE WORDS
 */


package top.mahua_a.orenetwork.util;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Arrays;

public class ByteUtil {

    /**
     * Remove leading 0x00's from a byte array.
     */
    public static byte[] stripLeadingNullBytes(byte[] input) {
        byte[] result = Arrays.copyOf(input, input.length);
        while (result.length > 0 && result[0] == 0x00) {
            result = Arrays.copyOfRange(result, 1, result.length);
        }
        return result;
    }

    static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    /**
     * Convert a byte array into its hex string equivalent.
     */
    public static String toHexString(byte[] data) {
        char[] chars = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
            chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
        }
        return new String(chars).toLowerCase();
    }

    /**
     * Convert a hex string into its byte array equivalent.
     */
    public static byte[] toByteArray(String data) {
        if (data == null) {
            return new byte[] {};
        }

        if (data.length() == 0) {
            return new byte[] {};
        }

        while (data.length() < 2) {
            data = "0" + data;
        }

        if (data.substring(0, 2).toLowerCase().equals("0x")) {
            data = data.substring(2);
        }
        if (data.length() % 2 == 1) {
            data = "0" + data;
        }

        data = data.toUpperCase();

        byte[] bytes = new byte[data.length() / 2];
        String hexString = new String(HEX_DIGITS);
        for (int i = 0; i < bytes.length; i++) {
            int byteConv = hexString.indexOf(data.charAt(i * 2)) * 0x10;
            byteConv += hexString.indexOf(data.charAt(i * 2 + 1));
            bytes[i] = (byte) (byteConv & 0xFF);
        }
        return bytes;
    }

    /**
     * Reverse the endian-ness of a byte array.
     *
     * @param data Byte array to flip
     * @return Flipped array
     */
    public static byte[] flipEndian(byte[] data) {
        if (data == null) {
            return new byte[0];
        }
        byte[] newData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            newData[data.length - i - 1] = data[i];
        }

        return newData;
    }

    /**
     * Pad a byte array with leading bytes.
     *
     * @param data Data that needs padding
     * @param size The final desired size of the data.
     * @param pad The byte value to use in padding the data.
     * @return A padded array.
     */
    public static byte[] leftPad(byte[] data, int size, byte pad) {
        if (size <= data.length) {
            return data;
        }

        byte[] newData = new byte[size];
        for (int i = 0; i < size; i++) {
            newData[i] = pad;
        }
        for (int i = 0; i < data.length; i++) {
            newData[size - i - 1] = data[data.length - i - 1];
        }

        return newData;
    }

    /**
     * Reads a section of a byte array and returns it as its own byte array, not unlike a substring.
     *
     * @param data Byte array to read from.
     * @param start Starting position of the desired data.
     * @param size Size of the data.
     * @return Byte array containing the desired data.
     */
    public static byte[] readBytes(byte[] data, int start, int size) {
        if (data.length < start + size) {
            return new byte[0];
        }

        byte[] newData = Arrays.copyOfRange(data, start, start + size);

        return newData;
    }
    public static String ipToLong(String ipString) {
        if(ipString==null||ipString.equalsIgnoreCase("")){
            return null;
        }
        String[] ip=ipString.split("\\.");
        StringBuffer sb=new StringBuffer();
        for (String str : ip) {
            sb.append(String.format("%2s",Integer.toHexString(Integer.parseInt(str))).replace(" ","0"));
        }
        return sb.toString();
    }
    public static String bytesToip(byte[] bytes){
        if(bytes.length!=4){
            return "";
        }
        String ip="";
        for(byte b:bytes){
            ip=ip+(int)b+".";
        }
        ip=ip.substring(0,ip.length()-1);
        return ip;

    }
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
    public static int byteToInt(byte b){
        return (int)(b & 0xff);
    }
    public static String portTohex(int port){
        return String.format("%4s",Integer.toHexString(port)).replace(" ","0");
    }
    public static String countTohex(int count){
        return String.format("%2s",Integer.toHexString(count)).replace(" ","0");
    }

}
