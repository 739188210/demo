package com.develop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可：
 * <p>
 * 创建CloseableHttpClient对象。
 * 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
 * 如果需要发送请求参数，可可调用setEntity(HttpEntity entity)方法来设置请求参数。setParams方法已过时（4.4.1版本）。
 * 调用HttpGet、HttpPost对象的setHeader(String name, String value)方法设置header信息，或者调用setHeaders(Header[] headers)设置一组header信息。
 * 调用CloseableHttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个CloseableHttpResponse。
 * 调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容；调用CloseableHttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头。
 * 释放连接。无论执行方法是否成功，都必须释放连接
 *
 * @author: miao
 * @date 2022/3/30
 */

public class HttpReqeustUtils {

    public static void main(String[] args) throws NoSuchMethodException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        Student student = new Student();
        student.setName("lili");
        student.setAge(11);
        getObj(student);

        getFieldValue(student, "openid");

        setFieldValue(student, Student.class, "companyName", String.class, "飞度");
    }


    /**
     * @param entity T
     */
    public static <T> Object getFieldValue(T entity, String filedName) throws NoSuchMethodException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        Object obj = null;
        Class<?> tClass = entity.getClass();
        Method[] declaredMethods = tClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String name = declaredMethod.getName();
            if (("get" + filedName).toLowerCase().equals(name.toLowerCase())) {
                obj = declaredMethod.invoke(entity);
            }
        }
        return obj;
    }

    public static <T> void setFieldValue(T obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
//            Method method = clazz.getDeclaredMethod(methodName, new Class[] { typeClass });
//            method.invoke(obj, new Object[] { getClassTypeValue(typeClass, value) });
            Method declaredMethod = clazz.getDeclaredMethod(methodName, typeClass);
            declaredMethod.invoke(obj, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }

    /**
     * 处理字符串 如： abc_dex ---> abcDex
     *
     * @param str
     * @return
     */
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }


    //  public static

    /**
     * 拼接某属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    private static String preGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

    public static <T> T getObj(T obj) throws NoSuchMethodException {
        Class<?> aClass = obj.getClass();
        Constructor<?> constructor = aClass.getConstructor();
        constructor.setAccessible(true);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            //    System.out.println(declaredMethod.getName());
        }
        return null;
    }


    /**
     * @param url 地址
     * @param map 参数
     */
    public static void getHTTPReqeust(String url, Map<String, String> map) {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create().build();

            //创建URLBuilder
            URIBuilder uriBuilder = new URIBuilder(url);
            //设置参数
            map.forEach(uriBuilder::addParameter);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            response = client.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获取响应体。相应数据是一个基于http协议标准字符串封装的对象
                //响应头和响应体都是封装的htto协议数据。 直接使用存在乱码或解析错误 需要解译
                HttpEntity entity = response.getEntity();
                String entityStr = EntityUtils.toString(entity, "UTF-8");
                System.out.println(entityStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void postHttpRequestByBody() {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create().build();
            // post 请求体传递参数， 需要定义请求体格式，默认表单格式

            HttpPost httpPost = new HttpPost();
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 超时配置
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);

            // 获取响应体。相应数据是一个基于http协议标准字符串封装的对象
            // 响应头和响应体都是封装的htto协议数据。 直接使用存在乱码或解析错误 需要解译
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";

            // json -> string
            Student student = objectMapper.readValue(jsonString, Student.class);
            // string -> json
            String s = objectMapper.writeValueAsString(student);

            StringEntity entityStr = new StringEntity(jsonString, "UTF-8");
//            entityStr.setContentType("application/json"); <==> httpPost.setHeader("Content-Type", "application/json");

            entityStr.setContentEncoding("UTF-8");

            httpPost.setEntity(entityStr);

            response = client.execute(httpPost);

            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Student {
    private String name;
    private int age;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student [ name: " + name + ", age: " + age + " ]";
    }

}
