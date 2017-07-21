package it.arlanis.reply.hibernate;

import it.arlanis.reply.bonifica.utils.BonificaUtils;
import it.arlanis.reply.bonifica.utils.HibernateUtils;
import it.arlanis.reply.models.*;
import it.arlanis.reply.models.enums.*;
import javassist.Modifier;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.lang.Object;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * Hibernate DAO implementation
 */
public class CatalogueService implements CatalogueDao {
    private transient static final Logger logger = LoggerFactory.getLogger(CatalogueService.class);
    private static Session session;
    private static Transaction tx;
    private Product dirRip;

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        CatalogueService.session = session;
    }

    public Product getDirRip() {
        return dirRip;
    }

    public void setDirRip(Product dirRip) {
        this.dirRip = dirRip;
    }

    /**
     * Hibernate save
     * @param o
     */
    public void save(Object o) {
        Long id = null;
        openSession();
        try {
            id = (Long) session.save(o);
            session.flush();
        } catch (Exception e) {
            try {
                rollback();
            } catch(RuntimeException rbe){
                logger.error("Couldn’t roll back transaction ", rbe);
            }
            throw e;
        } finally {
            logger.info("*** Salvato un record a DB con id " + id);
            closeSession();

        }
    }

    /**
     * Hibernate bulk save
     * @param c
     * @param b
     */
    @Transactional
    public void bulkSave(Class c, List b) {
        Set<Long> saveIds = new HashSet();
        openSession();
        try {
            long counter = 0;
            Iterator cursor = b.iterator();
            logger.info("*** Saving " + b.size() + " records for " + c.getSimpleName());

            while(cursor.hasNext()) {
                java.lang.Object object = cursor.next();
                Long id = (Long) session.save(object);
                saveIds.add(id);
                if (counter % 20 == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
        } catch (Exception e) {
            try {
                rollback();
            } catch(RuntimeException rbe){
                logger.error("Couldn’t roll back transaction ", rbe);
            }
            throw e;

        } finally {
            logger.info("*** Salvati a DB " + saveIds.size() + " records");
            logger.info("*** List of Ids = " + saveIds.toString());
            closeSession();
        }
    }

    /**
     * Hibernate update
     * @param o
     */
    public void update(Object o) {
        openSession();
        try {
            session.update(o);
        } catch (Exception e) {
            try {
                rollback();
            } catch(RuntimeException rbe){
                logger.error("Couldn’t roll back transaction ", rbe);
            }
            throw e;
        } finally {
            session.flush();
            closeSession();
        }
    }

    /**
     * Hibernate delete
     * @param id
     * @param c
     */
    public void delete(Class c, Long id) {
        openSession();
        try {
            List<Field> fields = new ArrayList();
            Field f = c.getDeclaredFields()[0];
            if (Modifier.isPrivate(f.getModifiers())) {
                fields.add(f);
            }

            String modelId = BonificaUtils.camelToSnakeCase(fields.get(0).getName());

            Object o = session.createCriteria(c)
                    .add(Restrictions.eq(modelId, id))
                    .uniqueResult();

            session.delete(o);
            session.flush();

        } catch(Exception e) {
            try {
                rollback();
            } catch(RuntimeException rbe){
                logger.error("Couldn’t roll back transaction ", rbe);
            }
            throw e;
        } finally {
            closeSession();
        }
    }

    /**
     * Hibernate get all
     * @param c
     * @return
     */
    public List findAll(Class c) {
        openSession();
        List res = session.createCriteria(c).list();
        closeSession();
        return res;
    }

    /**
     * Hibernate get by id
     * @param id
     * @param c
     * @return
     */
    public java.lang.Object findById(Class c, Long id) {
        openSession();
        Criteria criteria = session.createCriteria(c);
        List<Field> fields = new ArrayList();
        Field f = c.getDeclaredFields()[0];
        if (Modifier.isPrivate(f.getModifiers())) {
            fields.add(f);
        }

        String modelId = BonificaUtils.camelToSnakeCase(fields.get(0).getName());

        criteria.add(Restrictions.eq(modelId, id));
        java.lang.Object res = criteria.uniqueResult();
        closeSession();
        return res;
    }

    /**
     * Count all "Servizio Opzionale"
     * @return a boolean
     */
    public boolean countOptionalServices() {
        openSession();
        Number count = (Number) session
                .createCriteria(Product.class)
                .setProjection(Projections.rowCount())
                .add(Restrictions.eq("type", ProductType.Optional))
                .add(Restrictions.eq("state", ComponentState.Active))
                .uniqueResult();

        closeSession();

        if (count != null && count.intValue() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Find a Service by code
     * @param c a code
     * @return a list of services
     */
    public List findProductsByCode(long c) {
        openSession();
        Criteria criteria = session.createCriteria(Product.class);
        criteria.add(Restrictions.eq("code", c));
        criteria.add(Restrictions.eq("state", ComponentState.Active));
        List<Product> res = criteria.list();
        closeSession();
        return res;
    }

    /**
     * Get a list of plans by market and type
     * @return a list of plans
     */
    public List findPlanByMarketAndType() {
        openSession();
        Criteria criteria = session.createCriteria(Plan.class);
        criteria.add(Restrictions.eq("market", MarketType.SinglePlay));
        criteria.add(Restrictions.eq("type", PlanType.SIM));
        List<Plan> res = criteria.list();
        closeSession();
        return res;
    }

    /**
     * Get an "Object" by his name
     * @param n the name
     * @return an "Object" result
     */
    public it.arlanis.reply.models.Object findObjectByNome(String n) {
        openSession();
        Criteria criteria = session.createCriteria(it.arlanis.reply.models.Object.class);
        criteria.add(Restrictions.eq("nome", n));
        List<it.arlanis.reply.models.Object> list = criteria.list();
        closeSession();
        if (list.size() == 1)
            return list.get(0);
        else
            return null;
    }

    /**
     * Find a plan-product relation by plan ids
     * @param plans list of plans
     * @return a list of results
     */
    public List findPlanProductRelationsByPlans(List plans) {
        openSession();
        Criteria criteria = session.createCriteria(PlanProductRelation.class);
        criteria.add(Restrictions.in("plan", plans));

        //And product IS NULL or <> Diritto di ripensamento
        Criterion notEqual = Restrictions.ne("product", dirRip);
        Criterion isNull = Restrictions.isNull("product");
        criteria.add(Restrictions.or(notEqual, isNull));

        //Group by
        criteria.setProjection(Projections
                .projectionList()
                .add(Projections.groupProperty("plan").as("plan"))
                .add(Projections.property("product").as("product"))
                .add(Projections.property("parameter").as("parameter"))
                .add(Projections.property("value").as("value"))
                .add(Projections.property("newValue").as("newValue"))
                .add(Projections.property("included").as("included")))
                .setResultTransformer(Transformers.aliasToBean(PlanProductRelation.class));

        logger.info("*** Result after GROUP BY= " + criteria.list().toString());


        List res = criteria.list();
        closeSession();
        return res;
    }

    /**
     * Find all campaigns in "Esecuzione" state
     * @return a list
     */
    public List findCampaignsInEsecuzione() {
        openSession();
        Criteria criteria = session.createCriteria(Campaign.class);
        criteria.add(Restrictions.eq("state", CampaignState.Esecuzione));
        List res = criteria.list();
        closeSession();
        return res;
    }

    /**
     * Get all active offers by market type
     * @return all active offers
     */
    public List findActiveOffersByMarket() {
        openSession();
        Criteria criteria = session.createCriteria(Offer.class);

        criteria.add(Restrictions.eq("market", MarketType.SinglePlay));
        criteria.add(Restrictions.eq("state", ComponentState.Active));
        List res = criteria.list();
        closeSession();
        return res;
    }

    /**
     * Get products in offer by foreign keys
     * @param plans list of plans
     * @param offers list of offers
     * @return multiple results
     */
    public List findProductsInOfferByPlansAndOffers(List plans, List offers) {
        openSession();
        Criteria criteria = session.createCriteria(ProductInOffer.class);
        criteria.add(Restrictions.in("plan", plans));
        criteria.add(Restrictions.in("offer", offers));

        //And product IS NULL or <> Diritto di ripensamento
        Criterion notEqual = Restrictions.ne("product", dirRip);
        Criterion isNull = Restrictions.isNull("product");
        criteria.add(Restrictions.or(notEqual, isNull));

        //Group by
        criteria.setProjection(Projections
                .projectionList()
                .add(Projections.groupProperty("offer").as("offer"))
                .add(Projections.property("plan").as("plan"))
                .add(Projections.property("product").as("product"))
                .add(Projections.property("parameter").as("parameter"))
                .add(Projections.property("value").as("value"))
                .add(Projections.property("isNewValue"))
                .add(Projections.property("included").as("included")))
                .setResultTransformer(Transformers.aliasToBean(ProductInOffer.class));

        logger.info("*** Result after GROUP BY= " + criteria.list().toString());


        List res = criteria.list();
        closeSession();
        return res;
    }

    /**
     * Find all offer in campaign by plans, offers and campaigns
     * @param plans plans list
     * @param offers offers list
     * @param cmpgns a list of campaigns
     * @return offers in campaign
     */
    public List findOffersInCampaignByAllForeignKeys(List plans, List offers, List cmpgns) {
        openSession();
        Criteria criteria = session.createCriteria(OfferInCampaign.class);

        criteria.add(Restrictions.in("plan", plans));
        criteria.add(Restrictions.in("offer", offers));
        criteria.add(Restrictions.in("campaign", cmpgns));

        //And product IS NULL or <> AGCOM/Diritto di ripensamento
        Criterion notEqual = Restrictions.ne("product", dirRip);
        Criterion isNull = Restrictions.isNull("product");
        criteria.add(Restrictions.or(notEqual, isNull));

        //Group by
        criteria.setProjection(Projections
                .projectionList()
                .add(Projections.groupProperty("offer").as("offer"))
                .add(Projections.property("campaign").as("campaign"))
                .add(Projections.property("plan").as("plan"))
                .add(Projections.property("product").as("product"))
                .add(Projections.property("offer").as("offer"))
                .add(Projections.property("parameter").as("parameter"))
                .add(Projections.property("material").as("material"))
                .add(Projections.property("value").as("value"))
                .add(Projections.property("payment").as("payment"))
                .add(Projections.property("included").as("included"))
                .add(Projections.property("newValue").as("newValue")))
                .setResultTransformer(Transformers.aliasToBean(OfferInCampaign.class));

        logger.info("*** Result after GROUP BY= " + criteria.list().toString());


        List res = criteria.list();
        closeSession();
        return res;
    }


    /**
     * DAO: per la verifica di record da bonificare a DB
     */
    public int countPlanProductRelations_Bonificate(List plans) {
        openSession();

        List plProdRel = session
                .createCriteria(PlanProductRelation.class)
                .setProjection(Projections.rowCount())
                .add(Restrictions.in("plan", plans))
                .add(Restrictions.eq("product", dirRip))
                .setProjection(Projections
                        .projectionList()
                        .add(Projections.groupProperty("plan").as("plan"))
                        .add(Projections.property("product").as("product"))
                        .add(Projections.property("parameter").as("parameter"))
                        .add(Projections.property("value").as("value"))
                        .add(Projections.property("newValue").as("newValue"))
                        .add(Projections.property("included").as("included")))
                .setResultTransformer(Transformers.aliasToBean(PlanProductRelation.class))
                .list();

        closeSession();
        if(plProdRel == null)
            return -1;
        else
            return plProdRel.size();
    }

    public int countProductsInOffer_Bonificati(List plans, List offers) {
        openSession();

        List countProdsInOff = session
                .createCriteria(ProductInOffer.class)
                .setProjection(Projections.rowCount())
                .add(Restrictions.in("plan", plans))
                .add(Restrictions.in("offer", offers))
                .add(Restrictions.eq("product", dirRip))
                .setProjection(Projections
                        .projectionList()
                        .add(Projections.groupProperty("offer").as("offer"))
                        .add(Projections.property("plan").as("plan"))
                        .add(Projections.property("product").as("product"))
                        .add(Projections.property("parameter").as("parameter"))
                        .add(Projections.property("value").as("value"))
                        .add(Projections.property("isNewValue"))
                        .add(Projections.property("included").as("included")))
                .setResultTransformer(Transformers.aliasToBean(ProductInOffer.class))
                .list();

        closeSession();
        if(countProdsInOff == null)
            return -1;
        else
            return countProdsInOff.size();
    }

    public int countOffersInCampaign_Bonificate(List plans, List offers, List cmpgns) {
        openSession();

        List countOffsInCmpgn = session
                .createCriteria(OfferInCampaign.class)
                .setProjection(Projections.rowCount())
                .add(Restrictions.in("offer", offers))
                .add(Restrictions.in("plan", plans))
                .add(Restrictions.in("campaign", cmpgns))
                .add(Restrictions.eq("product", dirRip))
                .setProjection(Projections
                        .projectionList()
                        .add(Projections.groupProperty("offer").as("offer"))
                        .add(Projections.property("campaign").as("campaign"))
                        .add(Projections.property("plan").as("plan"))
                        .add(Projections.property("product").as("product"))
                        .add(Projections.property("offer").as("offer"))
                        .add(Projections.property("parameter").as("parameter"))
                        .add(Projections.property("material").as("material"))
                        .add(Projections.property("value").as("value"))
                        .add(Projections.property("payment").as("payment"))
                        .add(Projections.property("included").as("included"))
                        .add(Projections.property("newValue").as("newValue")))
                .setResultTransformer(Transformers.aliasToBean(OfferInCampaign.class))
                .list();

        closeSession();
        if(countOffsInCmpgn == null)
            return -1;
        else
            return countOffsInCmpgn.size();
    }


    /**
     * DAO Utils
     */
    public static void openSession() {
        session = null;
        tx = null;

        session = HibernateUtils.getFactory().getCurrentSession();
        if (!session.isOpen()) {
            session = HibernateUtils.getFactory().openSession();
        }

        beginTransaction();

    }

    public static void closeSession() {

        if(tx.isActive()) {
            commit();
        }
        if (session.isConnected()) {
            session.close();
        }
    }

    public static void openSessionFactory() {
        session = HibernateUtils.getFactory().getCurrentSession();
    }

    public static void closeSessionFactory() {
        if (tx.isActive()) {
            commit();
        }
        HibernateUtils.getFactory().close();
    }

    public static void beginTransaction() {
        tx = session.beginTransaction();
        tx.setTimeout(5);
    }

    public static void commit() {
        tx.commit();
    }

    public static void rollback() {
        tx.rollback();
    }

}
