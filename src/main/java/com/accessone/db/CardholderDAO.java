package com.accessone.db;

import com.accessone.core.Cardholder;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Current Project cardholder_dropwizard
 * Created by duncan.browne on 21/08/2016.
 */
public class CardholderDAO extends AbstractDAO<Cardholder>{

    /**
     * This is the constructor of the CardholderDAO class.
     * @param factory The factory from which the DAO is created.
     */
    public CardholderDAO(SessionFactory factory)
    {
        super(factory);
    }

    public Optional<Cardholder> findById(long id)
    {
        return Optional.fromNullable(get(id));
    }

    /**
     * Creates a DAO object from a Cardholder object
     * @param cardholder The Cardholder object from which the DAO is to be created.
     * @return The Cardholder object that represents the DAO.
     */
    public Cardholder create(Cardholder cardholder)
    {
        return persist(cardholder);
    }

    /**
     * Updates the DAO object with the values stored in a Cardholder object.
     * @param cardholder The Cardholder object that is to provide the updated values to be applied to the DAO object.
     */
    public void update(Cardholder cardholder)
    {
        currentSession().merge(cardholder);
    }

    /**
     * Finds all of the records in the Cardholder table.
     * @return A list of Cardholder records that represent all of the records in the Cardholder table.
     */
    public List<Cardholder> findAll()
    {
        Query query = currentSession().createQuery("from Cardholder");
        return query.list();
    }

    /**
     * Finds all of the records in the Cardholder table between a certain range of table indexes.
     * @param from The starting table index.
     * @param to The ending table index.
     * @return A list of Cardholder objects that represent the records in the Cardholder table between the "to" and
     * "from" indexes.
     */
    public List<Cardholder> getByRange(int from, int to)
    {
        Query query = currentSession().createQuery("from Cardholder b order by b.id").
                setFirstResult(from).setMaxResults((to - from) + 1);
        return query.list();
    }

    /**
     * Deletes a Cardholder record in the table.
     * @param id The CardholderID value of the record to be deleted.
     * @throws NotFoundException If no cardholder exists with the given CardholderID
     */
    public void delete(long id)
    {
        final Optional<Cardholder> cardholder = findById(id);
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
