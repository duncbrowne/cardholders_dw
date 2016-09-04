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
public class CardholdersResource extends RESTResource
{
    private final CardholderDAO cardholderDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(CardholdersResource.class);
    static final MetricRegistry metrics = new MetricRegistry();
    private final Meter meterRequests = metrics.meter(MetricRegistry.name(CardholdersResource.class, "Meter"));

    /**
     * @param cardholderDAO This is the Data object that represents the cardholder table in the database.
     */
    public CardholdersResource(String id, String name, CardholderDAO cardholderDAO)
    {
        super(id, name);
        this.cardholderDAO = cardholderDAO;
    }

    /**
     * Brings back a list of cardholders.  The default is to bring back all cardholders in the table, but the range
     * can be specified also.
     * @param from This is the starting index of the records to be retrieved.
     * @param to This is the ending index of the records to be retrieved.
     * @return The list of carholders between the to and from indexes.
     */
//    @GET
    @Timed
    @UnitOfWork
    public List<Cardholder> items(@QueryParam("from") @DefaultValue("-1") int from , @QueryParam("to") @DefaultValue("-1") int to)
    {
        this.meterRequests.mark();
        List<Cardholder> cardholderList;
        if(from != -1 && to != -1)
            cardholderList = cardholderDAO.getByRange(from,to);
        else
            cardholderList = cardholderDAO.findAll();
        return cardholderList;
    }

    /**
     * Retrieve a specific cardholder record from the database, as specified by the cardholderID.
     * @param id the CardholderID of the record to be retrieved.
     * @return The Cardholder record associated with the given cardholderID.
     */
    @Timed
    @UnitOfWork
    public Cardholder getCardholder(@PathParam("id") Long id)
    {
        return findSafely(id);
    }

    /**
     * Creates a cardholder record in the associated database.
     * @param cardholder The cardholder record to be added to the database.
     * @return The Cardholder record that was added to the database.
     */
    @POST
    @Timed
    @UnitOfWork
    public Cardholder createCardholder(Cardholder cardholder)
    {
        return cardholderDAO.create(cardholder);
    }

    /**
     * Updates an existing Cardholder database record.
     * @param id The Id of the record to be updated.
     * @param newCardholder The Cardholder object that has the new values to be updated in the database.
     */
    @POST
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public void updateCardholder(@PathParam("id") Long id, Cardholder newCardholder)
    {
        // This line doesn't look necessary but it is!  Without it a new record will be created regardless
        // of the id.  This line gives a 404 exception which is what you want.
        findSafely(id.longValue());
        newCardholder.setId(id);
        cardholderDAO.update(newCardholder);
    }

    /**
     * Delete a Cardholder record from the database.
     * @param id The cardholderID of the Cardholder record that is to be deleted from the database.
     */
    @DELETE
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public void delete(@PathParam("id") Long id)
    {
        cardholderDAO.delete(id.longValue());
    }

    public int getCount()
    {
        return cardholderDAO.findAll().size();
    }

    protected Cardholder findSafely(long id)
    {
        final Optional<Cardholder> cardholder = cardholderDAO.findById(id);
        if (!cardholder.isPresent())
        {
            LOGGER.error(
                    appendEntries(
                            new ImmutableMap.Builder<String, Object>()
                                    .put("ID", id)
                                    .build()
                    ),
                    "Cardholder not found");

            throw new NotFoundException("No such cardholder.");
        }
        return cardholder.get();
    }
}
