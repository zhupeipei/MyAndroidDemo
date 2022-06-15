package com.aire.android.util;

import androidx.annotation.NonNull;

/**
 * @author ZhuPeipei
 * @date 2022/6/15 15:30
 */
public class TraceUtil {

    public static String getTrace(@NonNull Thread thread) {
        StackTraceElement[] traceElements = thread.getStackTrace();
        if (traceElements == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder("\ntrace:\n");
        int size = traceElements.length;
        StackTraceElement element;
        for (int i = 0; i < size; i++) {
            element = traceElements[i];
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
