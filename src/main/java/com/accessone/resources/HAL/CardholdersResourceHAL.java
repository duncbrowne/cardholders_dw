package com.accessone.resources.HAL;

import com.accessone.resources.Capability;
import com.accessone.resources.CardholdersResource;
import com.accessone.resources.Range;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/cardholders")
public class CardholdersResourceHAL extends RESTResourceCollectionHAL {

    CardholdersResource cardholdersResource;

    public CardholdersResourceHAL(CardholdersResource cardholdersResource) {
        super(cardholdersResource.getId(),cardholdersResource.getName());
        this.cardholdersResource = cardholdersResource;

        addCapability(Capability.OC_ID_LOOKUP);
        addCapability(Capability.OC_ALL_LOOKUP);
        addCapability(Capability.OC_POS_RANGE_LOOKUP);
    }

    @GET
    @UnitOfWork
    @Path("/capabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public String resourceCollectionRepresentation()
    {
        return getRepresentation().toString(RepresentationFactory.HAL_JSON);
    }

    @GET
    @UnitOfWork
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCardholdersHAL(@PathParam("id") Long id) {
        return getRepresentation(cardholdersResource.getCardholder(id.longValue())).
                toString(RepresentationFactory.HAL_JSON);
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public String items(@QueryParam("from") @DefaultValue("-1") int from , @QueryParam("to") @DefaultValue("-1") int to) {
        Range range = new Range(from, to);
        return getRepresentation(cardholdersResource.items(from, to), range).toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public int getCount()
    {
        return cardholdersResource.getCount();
    }
}
