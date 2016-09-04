package com.accessone.resources.HAL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import static net.logstash.logback.marker.Markers.appendEntries;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class HALRepresentationHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HALRepresentationHelper.class);

    private HALRepresentationHelper()
    {
    }

    public static void addPropertiesFromPOJO(Class<?> entity, Object cardholder, Representation representation)
    {
        Field[] declaredFields = entity.getDeclaredFields();
        int declaredFieldsLength = declaredFields.length;

        for (int i = 0; i < declaredFieldsLength; ++i)
        {
            Field field = declaredFields[i];
            if (!field.isAnnotationPresent(JsonIgnore.class))
            {
                try
                {
                    representation.withProperty(field.getName(), (new PropertyDescriptor(field.getName(),
                            cardholder.getClass())).getReadMethod().invoke(cardholder, new Object[0]));
                }
                catch (Exception e)
                {
                    LOGGER.error("exception thrown {} when attempting to get representation of property {}",
                            e.toString(), field.getName());
                }
            }
        }

    }
}
