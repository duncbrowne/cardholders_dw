package com.accessone.resources;

import com.accessone.core.Cardholder;
import com.accessone.db.CardholderDAO;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static net.logstash.logback.marker.Markers.appendEntries;

/**
 * Current Project cardholder_dropwizard
 * Created by duncan.browne on 21/08/2016.
 */
@Path("/cardholders")
@Produces(MediaType.APPLICATION_JSON)
public class CardholdersResource
{
    private final CardholderDAO cardholderDAO;private static final Logger LOGGER =
        LoggerFactory.getLogger(CardholdersResource.class);
    static final MetricRegistry metrics = new MetricRegistry();
    private final Meter meterRequests = metrics.meter(MetricRegistry.name(CardholdersResource.class, "Meter"));
//    final Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
//    final GraphiteReporter reporter = GraphiteReporter.forRegistry(metrics)
//            .prefixedWith("CardholdersResource")
//            .convertRatesTo(TimeUnit.SECONDS)
//            .convertDurationsTo(TimeUnit.MILLISECONDS)
//            .filter(MetricFilter.ALL)
//            .build(graphite);

    public CardholdersResource(CardholderDAO cardholderDAO)
    {
        this.cardholderDAO = cardholderDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Cardholder> items(@QueryParam("from") @DefaultValue("-1") int from , @QueryParam("to") @DefaultValue("-1") int to)
    {
        this.meterRequests.mark();
        List<Cardholder> cardholderList = null;
        if(from != -1 && to != -1)
            cardholderList = cardholderDAO.getByRange(from,to);
        else
            cardholderList = cardholderDAO.findAll();
        return cardholderList;
    }

    @GET
    @Timed
    @Path("/{cardholderid}")
    @UnitOfWork
    public Cardholder getCardholder(@PathParam("cardholderid") Long cardholderID)
    {
        return findSafely(cardholderID);
    }

    @POST
    @Timed
    @UnitOfWork
    public Cardholder createCardholder(Cardholder cardholder)
    {
        return cardholderDAO.create(cardholder);
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/{cardholderid}")
    public void updateCardholder(@PathParam("cardholderid") Long cardholderID, Cardholder newCardholder)
    {
        // This line doesn't look necessary but it is!  Without it a new record will be created regardless
        // of the cardholderID.  This line gives a 404 exception which is what you want.
        Cardholder cardholder = findSafely(cardholderID.longValue());
        newCardholder.setCardholderID(cardholderID);
        cardholderDAO.update(newCardholder);
    }

    @DELETE
    @Timed
    @UnitOfWork
    @Path("/{cardholderid}")
    public void delete(@PathParam("cardholderid") Long cardholderID)
    {
        cardholderDAO.delete(cardholderID.longValue());
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/subscribe")
    public String subscribe(@QueryParam("clientURL") String clientURL, @Context HttpServletRequest request)
    {
        return "ok";
    }

    public int getCount()
    {
        return cardholderDAO.findAll().size();
    }

    protected Cardholder findSafely(long cardholderID)
    {
        final Optional<Cardholder> cardholder = cardholderDAO.findByCardholderID(cardholderID);
        if (!cardholder.isPresent())
        {
            LOGGER.error(
                    appendEntries(
                            new ImmutableMap.Builder<String, Object>()
                                    .put("cardholderID", cardholderID)
                                    .build()
                    ),
                    "Cardholder not found");

            throw new NotFoundException("No such cardholder.");
        }
        return cardholder.get();
    }
}
