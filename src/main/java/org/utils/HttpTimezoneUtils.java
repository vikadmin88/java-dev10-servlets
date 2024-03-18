package org.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class HttpTimezoneUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTimezoneUtils.class.getCanonicalName());
    private HttpTimezoneUtils() {
    }

    public static Map<String, List<String>> getParamsList(String queryStr) {
        LOGGER.info("Utils: perform params: {}", queryStr);
        Map<String, List<String>> params = new HashMap<>();
        String[] paramsPairs = queryStr.split("&");
        for (String paramsPair : paramsPairs) {
            String[] paramKeyVal = paramsPair.split("=");
            String paramKey = paramKeyVal[0];
            String paramVal = paramKeyVal[1];
            if (params.get(paramKey) == null) {
                params.put(paramKey,
                        new ArrayList<>(Collections.singletonList(paramVal)));
            } else {
                params.get(paramKey).add(paramVal);
            }
        }
        return params;
    }
}
