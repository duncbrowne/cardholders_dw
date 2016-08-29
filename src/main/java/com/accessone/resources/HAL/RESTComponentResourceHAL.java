package com.accessone.resources.HAL;

import com.accessone.resources.Capability;
import com.accessone.resources.Component;
import com.accessone.resources.RESTComponentResource;
import com.codahale.metrics.annotation.Timed;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
@Path("/hal/components")
public class RESTComponentResourceHAL extends RESTResourceCollectionTreeHAL {
    RESTComponentResource resourceComponents;

    public RESTComponentResourceHAL(RESTComponentResource resourceComponents) {
        super(resourceComponents.getId(), resourceComponents.getName());
        this.resourceComponents = resourceComponents;
        this.addCapability(Capability.OC_ID_LOOKUP);
        this.addCapability(Capability.OC_SUBSCRIPTION);
    }

    @GET
    public String componentsService() {
        return this.getRepresentation().toString("application/hal+json");
    }

    @GET
    @Timed
    @Path("/items/{id}")
    public String items(@PathParam("id") String id) {
        return this.getRepresentation(this.resourceComponents.items(id)).toString("application/hal+json");
    }

    @GET
    @Timed
    @Path("/children/{id}")
    public String children(@PathParam("id") String id) {
        Component component = this.resourceComponents.items(id);
        return this.getRepresentation(component, component.getChildren()).toString("application/hal+json");
    }

    @POST
    @Timed
    @Path("/subscribe")
    public Response updateSubscription(@QueryParam("clientURL") String clientURL, @Context HttpServletRequest request) {
        return this.resourceComponents.updateSubscription(clientURL, request);
    }

    @GET
    @Timed
    @Path("/subscribe")
    public Response getSubscription(@QueryParam("clientURL") String clientURL, @Context HttpServletRequest request) {
        return this.resourceComponents.getSubscription(clientURL, request);
    }
}
