package edu.uabc.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Collection;
import java.lang.StringBuilder;

public final class StringUtil {

    /**
     *
     */
    public static <T> String join(String delimiter, T items[]) {
        return join(delimiter, Arrays.asList(items));
    }

    /**
     */
    public static <T> String join(String delimiter, Collection<T> items) {
        StringBuilder sb = new StringBuilder();

        Iterator<T> iter= items.iterator();
        while(iter.hasNext())
            sb.append(iter.next().toString()).append(delimiter);

        return sb.length() > 0 ? sb.substring(0, sb.length() - delimiter.length()) : "";
    }

}
