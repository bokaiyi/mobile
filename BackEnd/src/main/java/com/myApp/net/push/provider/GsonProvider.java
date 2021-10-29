package com.myApp.net.push.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * 用于设置Jersey的Json转换器
 * 用于替换JacksonJsonProvider
 * <p>
 * 该工具类完成了，把Http请求中的请求数据转换为Model实体，
 * 同时也实现了把返回的Model实体转换为Json字符串
 * 并输出到Http的返回体中。
 *
<<<<<<< HEAD
 * @param <T> 任意类型范型定义
=======
 * @param <T> any
>>>>>>> 16b0d4c (fix bugs-Final version)
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {
<<<<<<< HEAD
    // 共用一个全局的Gson
    private static final Gson gson;

    static {
        // Gson 初始化
        GsonBuilder builder = new GsonBuilder()
                // 序列化为null的字段
                .serializeNulls()
                // 仅仅处理带有@Expose注解的变量
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                // 支持Map
                .enableComplexMapKeySerialization();
        // 添加对Java8LocalDateTime时间类型的支持
=======
    // use a global Gson
    private static final Gson gson;

    static {
        // Gson init
        GsonBuilder builder = new GsonBuilder()
                // serialize to null
                .serializeNulls()
                
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                // support Map
                .enableComplexMapKeySerialization();
        // add Java8LocalDateTime support type
>>>>>>> 16b0d4c (fix bugs-Final version)
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter());
        gson = builder.create();
    }

    /**
<<<<<<< HEAD
     * 取得一个全局的Gson
=======
     * get a global Gson
>>>>>>> 16b0d4c (fix bugs-Final version)
     *
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }

    public GsonProvider() {
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    /**
<<<<<<< HEAD
     * 把Json的字符串数据, 转换为T类型的实例
=======
     * transfer Jason string to class <T>
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations,
                      MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                      InputStream entityStream) throws IOException, WebApplicationException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(entityStream, "UTF-8"))) {
            return gson.fromJson(reader, genericType);
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(T t, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    /**
<<<<<<< HEAD
     * 把一个T类的实例输出到Http输出流中
=======
     * write T to stream
>>>>>>> 16b0d4c (fix bugs-Final version)
     */
    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        //TypeAdapter<T> adapter = gson.getAdapter((TypeToken<T>) TypeToken.get(genericType));
        try (JsonWriter jsonWriter = gson.newJsonWriter(new OutputStreamWriter(entityStream, Charset.forName("UTF-8")))) {
            gson.toJson(t, genericType, jsonWriter);
            jsonWriter.close();
        }
    }
}