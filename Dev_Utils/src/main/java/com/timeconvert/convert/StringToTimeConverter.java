package com.timeconvert.convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: miao
 * @date 2022/1/11
 */
public class StringToTimeConverter implements Converter<String, Time> {
    public Time convert(String value) {
        Time time = null;
        if (StringUtils.isNotBlank(value)) {
            String strFormat = "HH:mm";
            int intMatches = StringUtils.countMatches(value, ":");
            if (intMatches == 2) {
                strFormat = "HH:mm:ss";
            }
            SimpleDateFormat format = new SimpleDateFormat(strFormat);
            Date date = null;
            try {
                date = format.parse(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            time = new Time(date.getTime());
        }
        return time;
    }

}