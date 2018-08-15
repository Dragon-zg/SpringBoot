package com.web.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.web.model.ResultModel;
import com.web.utils.JsonUtils;
import org.springframework.data.domain.Page;

import java.io.IOException;

/**
 * 分页返回值处理器
 * @Author Dragon-zg
 * @Date 2018/8/15 13:15
 **/
public class JsonPageSerializer extends JsonSerializer<ResultModel<Page>> {

    @Override
    public void serialize(ResultModel<Page> resultModel, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        Page page = resultModel.getData();
        if (null == page) {
            return;
        }
        jsonGen.writeStartObject();
        jsonGen.writeFieldName("size");
        jsonGen.writeNumber(page.getSize());
        jsonGen.writeFieldName("number");
        jsonGen.writeNumber(page.getNumber());
        jsonGen.writeFieldName("totalElements");
        jsonGen.writeNumber(page.getTotalElements());
        jsonGen.writeFieldName("last");
        jsonGen.writeBoolean(page.isLast());
        jsonGen.writeFieldName("totalPages");
        jsonGen.writeNumber(page.getTotalPages());
        jsonGen.writeObjectField("sort", page.getSort());
        jsonGen.writeFieldName("first");
        jsonGen.writeBoolean(page.isFirst());
        jsonGen.writeFieldName("numberOfElements");
        jsonGen.writeNumber(page.getNumberOfElements());
        jsonGen.writeFieldName("content");
        jsonGen.writeRawValue(JsonUtils.toJson(page.getContent(), serializerProvider.getActiveView()));
        jsonGen.writeEndObject();
    }

}