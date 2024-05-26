package com.monster.luv_cocktail.domain.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.monster.luv_cocktail.domain.enumeration.Role;
import java.io.IOException;

public class RoleDeserializer extends JsonDeserializer<Role> {
    public RoleDeserializer() {
    }

    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        return Role.valueOf(value);
    }
}

