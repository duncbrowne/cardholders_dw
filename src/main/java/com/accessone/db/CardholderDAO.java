package com.accessone.db;

import com.accessone.core.Cardholder;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Current Project cardholder_dropwizard
 * Created by duncan.browne on 21/08/2016.
 */
public class CardholderDAO extends AbstractDAO<Cardholder>{
    public CardholderDAO(SessionFactory factory)
    {
        super(factory);
    }

    public Optional<Cardholder> findByCardholderID(long cardholderID)
    {
        return Optional.fromNullable(get(cardholderID));
    }

    public Cardholder create(Cardholder cardholder)
    {
        DateTime now = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = fmt.print(now);
        cardholder.setDateCreated(nowAsString);
        cardholder.setLastModified(nowAsString);

        return persist(cardholder);
    }

    public void update(Cardholder cardholder)
    {
        currentSession().merge(cardholder);
    }

    public List<Cardholder> findAll()
    {
        Query query = currentSession().createQuery("from Cardholder");
        return query.list();
    }

    public List<Cardholder> getByRange(int from, int to)
    {
        Query query = currentSession().createQuery("from Cardholder b order by b.CardholderID").
                setFirstResult(from).setMaxResults((to - from) + 1);
        return query.list();
    }

    public void delete(long cardholderID)
    {
        final Optional<Cardholder> cardholder = findByCardholderID(cardholderID);
        if (cardholder.isPresent())
        {
            currentSession().delete(cardholder.get());
        }
        else
        {
            throw new NotFoundException("No such cardholder");
        }
    }
}
