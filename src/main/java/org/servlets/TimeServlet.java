package org.servlets;

import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utils.HttpTimezoneUtils;

@WebServlet(name = "time-servlet", value = "/time")
public class TimeServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeServlet.class.getCanonicalName());
    private static final DateTimeFormatter DT_OUTPUT_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String PATTERN_EXT = " UTC";
    private static final String PATTERN_PLUS_EXT = " UTC+";
    private static final String PATTERN_MINUS_EXT = " UTC-";
    private String datetime;

    public void init() {
        LOGGER.info("Init servlet...");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String timeZoneParam = "timezone";
        resp.setContentType("text/html; charset=utf-8");
        datetime = getZonedDateTime(0);

        String queryStr = req.getQueryString();
        LOGGER.info("Query string: {}", queryStr);

        Map<String, List<String>> params = HttpTimezoneUtils.getParamsList(queryStr);
        LOGGER.info("List all params: {}", params);
        if (params.containsKey(timeZoneParam)) {
            String tz = params.get(timeZoneParam)
                    .get(0)
                    .replaceFirst("UTC", "");
            LOGGER.info("Parsed timezone: {}", tz);

            int tzInt = 0;
            try {
                tzInt = Integer.parseInt(tz);
                datetime = getZonedDateTime(tzInt);
            } catch (NumberFormatException e) {
                LOGGER.error("Timezone is not integer: {}", e.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + datetime + "</h1>");
        out.println("</body></html>");
    }

    private String getZonedDateTime(int zoneOffset) {
        Instant now = Instant.now();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(now, ZoneId.of("UTC"));

        if (zoneOffset >= -12 && zoneOffset <= 14) {
            LOGGER.info("Zone is in range [-12...14]: {}", zoneOffset);
            if (zoneOffset > 0) {
                return zdt.plusHours(zoneOffset).format(DT_OUTPUT_PATTERN) + PATTERN_PLUS_EXT + zoneOffset;
            } else if (zoneOffset < 0) {
                zoneOffset = zoneOffset * -1;
                return zdt.minusHours(zoneOffset).format(DT_OUTPUT_PATTERN) + PATTERN_MINUS_EXT + zoneOffset;
            }
        }
        return zdt.format(DT_OUTPUT_PATTERN) + PATTERN_EXT;
    }

    public void destroy() {
        LOGGER.info("Destroy servlet...");
        datetime = null;
    }
}