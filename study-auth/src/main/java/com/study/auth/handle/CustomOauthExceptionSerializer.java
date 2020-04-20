package com.study.auth.handle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.studyframework.core.model.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {


    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        ApiResponse result = ApiResponse.error(String.valueOf(value.getHttpErrorCode()) , value.getMessage());
        gen.writeObject(result);
    }

}
