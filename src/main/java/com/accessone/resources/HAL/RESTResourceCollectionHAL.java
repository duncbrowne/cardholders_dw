package com.accessone.resources.HAL;

import com.accessone.resources.Capability;
import com.accessone.resources.Range;
import com.theoryinpractise.halbuilder.api.Representation;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
public class RESTResourceCollectionHAL extends RESTResourceHAL {
    public RESTResourceCollectionHAL(String id, String name) {
        super(id, name);
        this.setType("Collection");
    }

    public Representation getRepresentation() {
        Representation representation = super.getRepresentation();
        List objectCapabilities = this.capabilities.getValue();
        Iterator iter = objectCapabilities.iterator();

        while(iter.hasNext()) {
            Capability o = (Capability)iter.next();
            representation.withLink(o.getLink(), this.generateURI(o.getPath()));
        }

        representation.withProperty("capabilities", this.capabilities);
        representation.withProperty("count", Integer.valueOf(this.getCount()));
        return representation;
    }

    public Representation getRepresentation(List<?> objects, Range range) {
        Representation representation = representationFactory.newRepresentation();
        Optional objectCapability = this.capabilities.getCapability(Capability.OC_ALL_LOOKUP);
        if(objectCapability.isPresent()) {
            representation.withLink("self", this.generateURI(((Capability)objectCapability.get()).getPath()));
        }

        representation.withProperty("range", range);
        if(objects.size() > 0) {
            Iterator iter = objects.iterator();

            while(iter.hasNext()) {
                Object object = iter.next();
                representation.withRepresentation("items", this.getRepresentation(object));
            }
        } else {
            representation.withRepresentation("items", representationFactory.newRepresentation());
        }

        return representation;
    }

    public Representation getRepresentation(List<?> objects) {
        Range range = new Range(-1, -1);
        return getRepresentation(objects, range);
    }

    public Representation getRepresentation(Object object) {
        Representation representation = representationFactory.newRepresentation();
        this.withBean(object.getClass(), object, representation);
        representation.withLink("collection", this.generateURI());

        try {
            Method e = object.getClass().getMethod("getId", new Class[0]);
            representation.withLink("self", this.generateURI("/" + e.invoke(object, new Object[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return representation;
    }

    public int getCount() {
        return -1;
    }
}