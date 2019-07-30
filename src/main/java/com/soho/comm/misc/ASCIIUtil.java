package com.soho.comm.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soho.comm.json.JsonToXMLConverter;

public class ASCIIUtil {

    private static final Logger logger = LoggerFactory.getLogger(ASCIIUtil.class);

    public static void asciiTableGenerator() {
        for (char c = 0; c <= 255; c++) {
            System.out.format("%c = 0x%02X (%3d)%n", c, (int) c, (int) c);

            //String.valueOf('\u003f')
        }
    }


}

