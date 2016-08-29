package com.accessone.resources;

import com.codahale.metrics.annotation.Timed;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Current Project cardholders_dw
 * Created by duncan.browne on 29/08/2016.
 */
@Path("/components")
@Produces({"application/json"})
public class RESTComponentResource extends RESTResource {
    Map<String, Component> components;

    public RESTComponentResource(Map<String, Component> components) {
        super("components", "components");
        this.components = components;
    }

    @GET
    public Component componentsService() {
        return (Component)this.components.get("root");
    }

    @GET
    @Timed
    @Path("/children/{id}")
    public List<Component> children(@PathParam("id") String id) {
        Component node = (Component)this.components.get(id);
        return node.getChildren();
    }

    @GET
    @Timed
    @Path("/items/{id}")
    public Component items(@PathParam("id") String id) {
        return (Component)this.components.get(id);
    }

    @GET
    @Timed
    @Path("/subscribe")
    public Response getSubscription(@QueryParam("clientURL") String clientURL, @Context HttpServletRequest request) {
        return Response.status(200).entity("true").build();
    }

    @POST
    @Timed
    @Path("/subscribe")
    public Response updateSubscription(@QueryParam("clientURL") String clientURL, @Context HttpServletRequest request) {
        return Response.status(201).build();
    }
}
