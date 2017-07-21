package it.arlanis.reply.hibernate;

import java.util.List;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * Hibernate Data access Object
 */
public interface CatalogueDao {

    /**
     * Save an object
     * @param o an object to persist
     * @throws Exception
     */
    void save(Object o) throws Exception;

    /**
     * Save in bulk
     * @param c the object class
     * @param b list of objects
     * @throws Exception
     */
    void bulkSave(Class c, List b) throws Exception;

    /**
     * Update an object
     * @param o an object to update
     * @throws Exception
     */
    void update(Object o) throws Exception;

    /**
     * Delete by id an object
     * @param c the object class
     * @param id the object id
     * @throws Exception
     */
    void delete(Class c, Long id) throws Exception;

    /**
     * Find all
     * @param c the object class
     * @return a list of results
     */
    List findAll(Class c);

    /**
     * Find an object by id
     * @param c the object class
     * @param id the object id
     * @return a single result
     */
    Object findById(Class c, Long id);
}