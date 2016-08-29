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
        Iterator var3 = objectCapabilities.iterator();

        while(var3.hasNext()) {
            Capability o = (Capability)var3.next();
            representation.withLink(o.getLink(), this.generateURI(o.getPath()));
        }

        representation.withProperty("capabilities", this.capabilities);
        representation.withProperty("count", Integer.valueOf(this.getCount()));
        return representation;
    }

    public Representation getRepresentation(List<?> objects) {
        Representation representation = representationFactory.newRepresentation();
        Optional objectCapability = this.capabilities.getCapability(Capability.OC_ALL_LOOKUP);
        if(objectCapability.isPresent()) {
            representation.withLink("self", this.generateURI(((Capability)objectCapability.get()).getPath()));
        }

        Range range = new Range(-1, -1);
        representation.withProperty("range", range);
        if(objects.size() > 0) {
            Iterator var5 = objects.iterator();

            while(var5.hasNext()) {
                Object object = var5.next();
                representation.withRepresentation("items", this.getRepresentation(object));
            }
        } else {
            representation.withRepresentation("items", representationFactory.newRepresentation());
        }

        return representation;
    }

    public Representation getRepresentation(Object object) {
        Representation representation = representationFactory.newRepresentation();
        this.withBean(object.getClass(), object, representation);
        representation.withLink("collection", this.generateURI());

        try {
            Method e = object.getClass().getMethod("getId", new Class[0]);
            representation.withLink("self", this.generateURI("/" + e.invoke(object, new Object[0])));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return representation;
    }

    public int getCount() {
        return -1;
    }
}