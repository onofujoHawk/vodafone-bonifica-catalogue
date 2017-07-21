package it.arlanis.reply;

import it.arlanis.reply.bonifica.utils.BonificaUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * BonificaUtils test class
 */
public class Test_BonificaUtils {

    @Test
    public void test_camelToSnakeCaseConversion() {
        String res = BonificaUtils.camelToSnakeCase("onofrioFalco");
        assertEquals(res, "onofrio_falco");
    }

    @Test
    public void test_getClasses() throws IOException, ClassNotFoundException {
        Class[] res = BonificaUtils.getClasses("it.arlanis.reply.hibernate");
        assertNotNull(res);
    }
}
