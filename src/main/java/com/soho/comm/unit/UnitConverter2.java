package com.soho.comm.unit;

import java.text.NumberFormat;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UnitConverter2 {

    private static final Logger logger = LoggerFactory.getLogger(UnitConverter2.class);

    public static double convertUnit(String source) {
        // exp) 1 KB, 1 KB, 1MB , 10GB 등로 입력되면 byte로 연산 처리함

        // return 값
        double size = 0;

        // 입력값이 null 이나 공백일 경우 0으로 리턴
        if (null == source || "".equals(source)) {
            return size;
        }

        // 공백과 "," 제거
        source = source.trim().replace(" ", "").replaceAll(",", "");


        // 문자열이 있는지 확인
        boolean chkLetter = false;
        int beginIndex = 0;
        for (char c : source.toCharArray()) {
            if (Character.isLetter(c)) {
                chkLetter = true;
                break;
            }
            beginIndex++;
        }

        // size 부분 스트링 뽑기
        String sizeStr = source.substring(0, beginIndex).trim();
        if (sizeStr.length() > 0) {
            try {
                // long 값 추출
                NumberFormat nf = NumberFormat.getInstance();
                size = nf.parse(sizeStr).doubleValue();
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error(e.getMessage());
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                logger.error(e1.getMessage());
            }
        }

        // 입력값이 숫자로만 구성되었다면 숫자값만 리턴
        if (!chkLetter) {
            return size;
        }

        // 단위 라인 추출
        String unit = source.substring(beginIndex, source.length());
        //logger.debug("input  size : " + size + ", unit : " + unit);

        // 단위가 있으면 사이즈로 간주 해서 getByte 연산 진행
        if (checkByte(unit)) {
            size = getBytes(size, unit);
        }

        return size;
    }


    public static boolean checkByte(String unit) {
        // unit에 B 혹은 Byte가 존재 할 경우 byte로 처리
        boolean rtnChk = false;

        // unit입력 값이 null이거나 공백일 경우 false로 리턴
        if (null == unit || "".equals(unit)) {
            return false;
        }

        // 검사 로직
        unit = unit.toUpperCase();
        if (unit.contains("BYTE") || unit.contains("B")) {
            rtnChk = true;
        }

        return rtnChk;
    }


    public static double getBytes(double size, String unit) {

        // unit입력 값이 null이거나 공백일 경우 size를 리턴
        if (null == unit || "".equals(unit)) {
            return size;
        }

        // 압자리 추출
        unit = unit.trim().toUpperCase();
        char beginChar = unit.charAt(0);

        if (beginChar == 'B') {
            return size;
        }

        // 단위 기본값 선언
        char[] units = {'K', 'M', 'G', 'T', 'P', 'E'};
        int exp = 1;
        boolean byteChk = false;

        for (char ch : units) {
            if (beginChar == ch) {
                //logger.debug("output unit : " + String.valueOf(beginChar));
                byteChk = true;
                break;
            }
            exp++;
        }

        if (byteChk) {
            size = (double) size * Math.pow(1024, exp);
        }
        //logger.debug(String.valueOf(size));
        return size;
    }

}
