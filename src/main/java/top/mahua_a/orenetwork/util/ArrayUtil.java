package top.mahua_a.orenetwork.util;

public class ArrayUtil {
    public static void linkArray(Object[] source,Object[] d){
        System.arraycopy(source,0,d,d.length,source.length);
    }
}
