package com.accessone.resources.HAL;

import com.accessone.resources.Capability;
import com.accessone.resources.CardholdersResource;
import com.accessone.resources.Range;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

@Path("/hal/cardholders")
public class CardholdersResourceHAL extends RESTResourceCollectionHAL {

    CardholdersResource cardholdersResource;

    public CardholdersResourceHAL(CardholdersResource cardholdersResource) {
        super(cardholdersResource.getId(),cardholdersResource.getName());
        this.cardholdersResource = cardholdersResource;

        addCapability(Capability.OC_ID_LOOKUP);
        addCapability(Capability.OC_ALL_LOOKUP);
        addCapability(Capability.OC_POS_RANGE_LOOKUP);
        addCapability(Capability.OC_SUBSCRIPTION);
    }

//    @GET
//    @UnitOfWork
//    public String resourceCollectionRepresentation()
//    {
//        return getRepresentation().toString(RepresentationFactory.HAL_JSON);
//    }

    @GET
    @UnitOfWork
    @Path("/{cardholderid}")
    public String getCardholdersHAL(@PathParam("cardholderid") Long cardholderId) {
        return getRepresentation(cardholdersResource.getCardholder(cardholderId.longValue())).toString(RepresentationFactory.HAL_JSON);
    }

    @GET
    @UnitOfWork
    public String items(@QueryParam("from") @DefaultValue("-1") int from , @QueryParam("to") @DefaultValue("-1") int to) {
        Range range = new Range(from, to);
        return getRepresentation(cardholdersResource.items(from, to), range).toString(RepresentationFactory.HAL_JSON);
    }

    @POST
    @UnitOfWork
    @Path("/subscribe")
    public String subscribe(@QueryParam("clientURL") String clientURL, @Context HttpServletRequest request) {
        return cardholdersResource.subscribe(clientURL, request);
    }

    @Override
    public int getCount()
    {
        return cardholdersResource.getCount();
    }
}
