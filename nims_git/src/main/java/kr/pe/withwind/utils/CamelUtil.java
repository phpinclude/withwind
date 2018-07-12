package kr.pe.withwind.utils;

import org.json.simple.JSONObject;

/**
 * 입력 문자열에 대하여 Camel 표기법으로 변환을 지원하는 Utility Class 이다.
 * @author 실행환경 개발팀 우병훈
 * @since 2009.02.06
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.02.06  우병훈          최초 생성
 * 
 * </pre>
 */
public class CamelUtil {

    /**
     * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등
     * 표기법 - 단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는
     * utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리
     * 함.)
     * @param underScore
     *        - '_' 가 포함된 변수명
     * @return Camel 표기법 변수명
     */
    public static String convert2CamelCase(String underScore, boolean nextUpper) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0
            && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
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
    
    public static String convert2CamelCase(String underScore) {
    	return convert2CamelCase(underScore, false);
    }
    
    public static String convert2CamelCase2(String underScore) {
    	return convert2CamelCase(underScore, true);
    }
    
    public static JSONObject toCamel(JSONObject jObj) {
    	
    	JSONObject reValue = new JSONObject();
    	
    	Object[] keys = jObj.keySet().toArray();

    	for (Object key : keys) {
    		String newKey = convert2CamelCase(key.toString());
    		reValue.put(newKey, jObj.get(key));
    	}
    	
    	return reValue;
    }
}