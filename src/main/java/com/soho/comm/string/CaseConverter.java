package com.soho.comm.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseConverter {

    private static final Logger logger = LoggerFactory.getLogger(CaseConverter.class);

    public static String underScoreToCamelCase(String strUnderScore) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜

        if (strUnderScore.indexOf('_') < 0

                && Character.isLowerCase(strUnderScore.charAt(0))) {

            return strUnderScore;

        }

        StringBuilder result = new StringBuilder();

        boolean nextUpper = false;

        int len = strUnderScore.length();


        for (int i = 0; i < len; i++) {

            char currentChar = strUnderScore.charAt(i);

            if (currentChar == '_') {

                nextUpper = true;

            } else {

                if (nextUpper) {

                    result.append(Character.toUpperCase(currentChar));

                    nextUpper = false;

                } else {

                    result.append(Character.toLowerCase(currentChar));

                }

            }

        }
        return result.toString();

    }

    /**
     * camel 스타일의 데이터 클래스 멤버변수명 또는 화면오브젝트명을 DB컬럼명 스타일로 변환
     * FROM camel or pascal style TO db style using underscore
     * userName or UserName => USER_NAME
     *
     * @param strCamel
     * @return value
     */
    public static String camelToUnderScore(String strCamel) {

        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        String value = "";
        value = strCamel.replaceAll(regex, replacement).toUpperCase();
        return value;
    }


}

