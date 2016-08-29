package com.accessone.resources.HAL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class HALRepresentationHelper
{
    private static RepresentationFactory representationFactory = new JsonRepresentationFactory();

    private HALRepresentationHelper()
    {
    }

    public static void addPropertiesFromPOJO(Class<?> entity, Object demo, Representation representation)
    {
        Field[] var3 = entity.getDeclaredFields();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            Field field = var3[var5];
            if (!field.isAnnotationPresent(JsonIgnore.class))
            {
                try
                {
                    representation.withProperty(field.getName(), (new PropertyDescriptor(field.getName(), demo.getClass())).getReadMethod().invoke(demo, new Object[0]));
                }
                catch (Exception var8)
                {
                    ;
                }
            }
        }

    }

    public static ReadableRepresentation getRepresentation(String json)
    {
        return representationFactory.readRepresentation("application/hal+json", new InputStreamReader(new ByteArrayInputStream(json.getBytes())));
    }

    public static boolean equals(String jsonObject1, String jsonObject2)
    {
        ReadableRepresentation repObject1 = getRepresentation(jsonObject1);
        ReadableRepresentation repObject2 = getRepresentation(jsonObject2);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeObject1 = null;
        JsonNode jsonNodeObject2 = null;

        try
        {
            jsonNodeObject1 = mapper.readTree(repObject1.toString("application/hal+json"));
            jsonNodeObject2 = mapper.readTree(repObject2.toString("application/hal+json"));
        }
        catch (IOException var8)
        {
            var8.printStackTrace();
        }

        return jsonNodeObject1.equals(jsonNodeObject2);
    }
}
