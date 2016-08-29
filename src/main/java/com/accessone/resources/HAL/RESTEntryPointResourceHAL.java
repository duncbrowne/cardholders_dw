package com.accessone.resources.HAL;

import com.codahale.metrics.annotation.Timed;
import com.theoryinpractise.halbuilder.api.Representation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
@Path("/hal")
@Produces({"application/hal+json", "application/hal+xml"})
public class RESTEntryPointResourceHAL extends RESTResourceHAL {
    public RESTEntryPointResourceHAL(String id, String name) {
        super(id, name);
    }

    @GET
    @Timed
    public String entryPoint() {
        return this.getRepresentation().toString("application/hal+json");
    }

    public Representation getRepresentation() {
        return representationFactory.newRepresentation().withLink("self", this.generateURI()).withLink("components", this.generateURI("/components")).withLink("uis", this.generateURI("/uis")).withProperty("id", this.getId()).withProperty("name", this.getName()).withProperty("version", "1.0.0");
    }
}
