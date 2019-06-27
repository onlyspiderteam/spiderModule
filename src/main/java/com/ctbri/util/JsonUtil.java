package com.ctbri.util;

import com.ctbri.pojo.PictureInfo;
import com.ctbri.pojo.UserInfoTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import java.text.SimpleDateFormat;



@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static{
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        //忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }



    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error",e);
            return null;
        }
    }



    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }



    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static void main(String[] args){
        String json1 = "{\"name\":\"Geely\",\"color\":\"blue\",\"id\":666}";
        UserInfoTest userInfoTest = JsonUtil.string2Obj(json1, UserInfoTest.class);

        String json = "{\"title\":\"Portrait of smiling girl lying in meadow under tree\",\"imageType\":\"JPEG\",\"tags\":[\"cat images\",\"cute\",\"flower\",\"flowers\",\"girls\",\"green\",\"kids\",\"pattern\",\"smile\",\"tree\"],\"downs\":[\"download0 url: https://visualhunt.com/photos/1/portrait-of-smiling-girl-lying-in-meadow-under-tree.jpg?s=t span: 314 x 200 (S)\",\"download1 url: https://visualhunt.com/photos/1/portrait-of-smiling-girl-lying-in-meadow-under-tree.jpg?s=m span: 600 x 382 (M)\",\"download2 url: https://visualhunt.com/photos/1/portrait-of-smiling-girl-lying-in-meadow-under-tree.jpg?s=l span: 800 x 509 (L)\",\"download3 url: https://visualhunt.com/photos/1/portrait-of-smiling-girl-lying-in-meadow-under-tree.jpg?s=xl span: 1600 x 1018 (XL)\",\"download4 url: https://visualhunt.com/photos/1/portrait-of-smiling-girl-lying-in-meadow-under-tree.jpg span: 2000 x 1272 (2XL)\"]}";
        PictureInfo pictureInfo = JsonUtil.string2Obj(json, PictureInfo.class);
    }

}
