package com.soho.comm.json;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Converts JSON to XML and makes sure the resulting XML
 * does not have invalid element names.
 */
public class JsonToXMLConverter {

    private static final Logger logger = LoggerFactory.getLogger(JsonToXMLConverter.class);


    private static final Pattern XML_TAG =
            Pattern.compile("(?m)(?s)(?i)(?<first><(/)?)(?<nonXml>.+?)(?<last>(/)?>)");

    private static final Pattern REMOVE_ILLEGAL_CHARS =
            Pattern.compile("(i?)([^\\s=\"'a-zA-Z0-9._-])|(xmlns=\"[^\"]*\")");

    private ObjectMapper mapper = new ObjectMapper();

    private XmlMapper xmlMapper = new XmlMapper();

    public String convertToXml(Object obj) {
        String s;
        try {
            s = xmlMapper.writeValueAsString(obj);
            return removeIllegalXmlChars(s);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String removeIllegalXmlChars(String s) {
        final Matcher matcher = XML_TAG.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String elementName = REMOVE_ILLEGAL_CHARS.matcher(matcher.group("nonXml"))
                    .replaceAll("").trim();
            matcher.appendReplacement(sb, "${first}" + elementName + "${last}");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private Map<String, Object> convertJson(String json) {
        try {
            return mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String convertJsonToXml(String json) {
        return convertToXml(convertJson(json));
    }


}

