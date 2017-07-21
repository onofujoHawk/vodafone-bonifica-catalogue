package it.arlanis.reply;

import it.arlanis.reply.bonifica.utils.HibernateUtils;
import org.hibernate.Session;
import org.junit.Test;


import static org.junit.Assert.assertNotNull;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * HibernateUtils test class
 */
public class Test_HibernateUtils {

    /**
     * Per eseguire questo test bisogna avviare con puTTy
     * una session SSH con il database in qualsiasi ambiente (DEV-PRE-PROD) ed
     * eseguire una query di lettura per verificare che si è connessi al catalogo
     */
    @Test
    public void test_getFactory() {
        Session sx = HibernateUtils.getFactory().getCurrentSession();
        assertNotNull(sx);
    }
}
