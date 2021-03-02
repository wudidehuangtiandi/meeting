package com.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*自定义转换类*/
public class StringToDateConverter implements Converter<String,Date> {
    @Nullable
    @Override
    public Date convert(String source) {
        if(source == null){
            throw new  RuntimeException("缺少参数");
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = df.parse(source);
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException("转换异常");
        }

    }
}
