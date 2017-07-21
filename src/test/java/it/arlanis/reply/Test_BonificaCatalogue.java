package it.arlanis.reply;

import it.arlanis.reply.bonifica.BonificaCatalogue;
import it.arlanis.reply.hibernate.CatalogueService;
import it.arlanis.reply.models.Campaign;
import it.arlanis.reply.models.LogAccess;
import it.arlanis.reply.models.Material;
import it.arlanis.reply.models.User;
import it.arlanis.reply.models.enums.CampaignState;
import it.arlanis.reply.models.enums.CampaignType;
import it.arlanis.reply.models.enums.MarketType;
import it.arlanis.reply.models.enums.MaterialType;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * BonificaUtils test class
 */
@Transactional
public class Test_BonificaCatalogue {

    @Test
    @Rollback
    public void test_execBonifica() throws Exception {
        BonificaCatalogue b = new BonificaCatalogue();
        b.execBonifica();
        assertNotNull(b);
    }

    @Test
    @Rollback(true)
    public void test_invokeDelete() {
        Campaign cmp = new Campaign();
        cmp.setCode(String.valueOf(9999));
        cmp.setConvergenza(true);
        cmp.setDescription("Campagna di Test");
        cmp.setName("Campagna_Test");
        cmp.setState(CampaignState.Nuovo);
        cmp.setType(CampaignType.Abbonamento);
        cmp.setCreationDate(new Date());
        cmp.setCreator("admin");
        cmp.setModifier("admin");
        cmp.setLastUpdate(new Date());

        assertNotNull(cmp);
        assertEquals(cmp.getName(), "Campagna_Test");

        CatalogueService s = new CatalogueService();
        s.save(cmp);

        List<Campaign> usr = s.findAll(Campaign.class);
        Campaign c = usr.stream()                                           // Convert to steam
                .filter(x -> "Campagna_Test".equals(x.getName()))           // We want camp-40 only
                .findAny()                                                  // If 'findAny' then return found
                .orElse(null);                                        // If not found, return null

        if(c != null) {
            assertNotNull(c);
            s.delete(Campaign.class, c.getCampaignId());
        } else {
            assertEquals(c, null);
        }
    }

    @Test
    @Rollback
    public void test_invokeFindAll() {
        CatalogueService s = new CatalogueService();
        List mats = new ArrayList();
        for(int i = 0; i < 2; i++) {
            Material m = new Material();
            m.setDescription("test_material_" + i);
            m.setMarket(MarketType.Mobile);
            m.setStartDate(new Date());
            m.setCreationDate(new Date());
            m.setType(MaterialType.Device);
            m.setCreator("admin");
            m.setLastUpdate(new Date());
            m.setModifier("admin");
            m.setMaterialCode(String.valueOf(i));
            mats.add(m);
        }
        s.bulkSave(Material.class, mats);
        List u = s.findAll(Material.class);
        assertNotNull(u);
    }

    @Test
    @Rollback
    public void test_invokeFindById() throws UnknownHostException {
        CatalogueService s = new CatalogueService();
        User u = new User();
        u.setUsername("utenteTest1");
        u.setPassword("passwordTest1");
        u.setActive(true);
        u.setNome("testNome");
        u.setCognome("testCognome");
        u.setEmail("test_user@email.it");
        s.save(u);

        assertNotNull(u);

        LogAccess log = new LogAccess();
        log.setAccessDate(new Date());
        log.setDescription("test log");
        log.setIpAddress(Inet4Address.getLocalHost().getHostAddress().toString());
        log.setCreator("admin");
        log.setVersion(1);
        log.setModifier("admin");
        log.setCreationDate(new Date());
        log.setLastUpdate(new Date());
        log.setUser(u);
        log.setUserAgent("test_agent");
        s.save(log);

        assertNotNull(log);

        //Get by id
        List<LogAccess> logs = (List<LogAccess>) s.findAll(LogAccess.class);
        LogAccess l = logs.stream()                                         // Convert to steam
                .filter(x -> "test_agent".equals(x.getUserAgent()))         // We want "test_user@email.it" only
                .findAny()                                                  // If 'findAny' then return found
                .orElse(null);                                        // If not found, return null

        LogAccess res = (LogAccess) s.findById(LogAccess.class, l.getLogAccessId());
        assertNotNull(res);
    }
}
