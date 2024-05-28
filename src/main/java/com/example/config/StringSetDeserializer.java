package com.example.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bodyzxy
 * @github https://github.com/bodyzxy
 * @date 2024/5/28 23:39
 */
public class StringSetDeserializer extends StdDeserializer<Set<String>> {

    public StringSetDeserializer() {
        this(null);
    }

    public StringSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Set<String> set = new HashSet<>();
        for (JsonNode jsonNode : node) {
            set.add(jsonNode.asText());
        }
        return set;
    }
}

