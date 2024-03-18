package org.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utils.HttpTimezoneUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@WebFilter(urlPatterns = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimezoneValidateFilter.class.getCanonicalName());
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String timeZoneParam = "timezone";
        res.setContentType("text/html; charset=utf-8");

        String queryStr = req.getQueryString();
        LOGGER.info("Filter: Query string: {}", queryStr);

        if (queryStr != null) {
            Map<String, List<String>> params = HttpTimezoneUtils.getParamsList(queryStr);
            LOGGER.info("Filter: List all params: {}", params);
            if (params.containsKey(timeZoneParam)) {
                String tz = params.get(timeZoneParam)
                        .get(0)
                        .replaceFirst("UTC", "GMT");
                String tzCmp = TimeZone.getTimeZone(tz).getID();
                if (tzCmp.matches("GMT([-+]+)([0-9]+){2}:([0-9]+){2}")) {
                    LOGGER.info("Filter: TimeZone valid: {}", tzCmp);
                    super.doFilter(req, res, chain);
                } else {
                    LOGGER.error("Filter: Timezone is not correct: {}", tz);
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    res.getWriter().write("Invalid timezone");
                }
            }
        }
    }
}
