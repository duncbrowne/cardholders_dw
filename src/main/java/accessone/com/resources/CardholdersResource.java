package accessone.com.resources;

import accessone.com.core.Cardholder;
import accessone.com.db.CardholderDAO;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.hibernate.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Current Project cardholder_dropwizard
 * Created by duncan.browne on 21/08/2016.
 */
@Path("/cardholders")
@Produces(MediaType.APPLICATION_JSON)
public class CardholdersResource
{
    private final CardholderDAO cardholderDAO;
    private final AtomicLong counter;

    public CardholdersResource(CardholderDAO cardholderDAO)
    {
        this.cardholderDAO = cardholderDAO;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Cardholder> items(@QueryParam("from") @DefaultValue("-1") int from , @QueryParam("to") @DefaultValue("-1") int to)
    {
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
        //Cardholder cardholder = findSafely(cardholderID.longValue());
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
            throw new NotFoundException("No such cardholder.");
        }
        return cardholder.get();
    }
}
