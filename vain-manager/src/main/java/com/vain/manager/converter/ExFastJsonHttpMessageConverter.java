package com.vain.manager.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author vain
 * @description: 自定义的json扩展
 * @date 2017/8/31 11:56
 */
public class ExFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final Logger logger = LoggerFactory.getLogger(ExFastJsonHttpMessageConverter.class);

    // fastjson特性参数
    private SerializerFeature[] serializerFeature;

    public void setSerializerFeature(SerializerFeature[] serializerFeature) {
        this.serializerFeature = serializerFeature;
    }

    public ExFastJsonHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        // JavaType javaType = getJavaType(clazz);
        // return this.objectMapper.canDeserialize(javaType) &&
        // canRead(mediaType);
        return true;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        // return this.objectMapper.canSerialize(clazz) && canWrite(mediaType);
        return true;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // should not be called, since we override canRead/Write instead
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((i = inputMessage.getBody().read()) != -1) {
                baos.write(i);
            }

            if (baos.size() > 0) {

                byte[] data = baos.toByteArray();

                String json = new String(data, DEFAULT_CHARSET);
                logger.info(json);

                return JSON.parseObject(json, clazz);
            }
        } catch (Exception e) {
            logger.error("read http message exception, ", e);
        }

        return null;

    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        String jsonString = o == null ? "" : (o instanceof String) ? o.toString() : JSON.toJSONString(o,
                serializerFeature);

        logger.info(jsonString);

        byte[] data = jsonString.getBytes(DEFAULT_CHARSET);

        OutputStream out = outputMessage.getBody();
        out.write(data);
        out.flush();
    }

}