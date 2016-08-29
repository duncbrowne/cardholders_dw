package com.accessone.resources.HAL;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hal")
public class CardholdersServiceEntryPointResourceHAL extends RESTEntryPointResourceHAL {

    public CardholdersServiceEntryPointResourceHAL(String id, String name) {
        super(id, name);
    }

    @GET
    @Timed
    public String entryPoint() {
        return getRepresentation().toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public Representation getRepresentation() {
        return super.getRepresentation()
                .withLink("cardholders", generateURI("/cardholders"));
    }
}


