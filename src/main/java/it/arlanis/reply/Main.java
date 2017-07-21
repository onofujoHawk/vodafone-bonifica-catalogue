package it.arlanis.reply;

import it.arlanis.reply.bonifica.BonificaCatalogue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * Esegue la bonifica del catalogo solo in PROD
 */
public class Main {
    private transient static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        exec();
    }

    /**
     * Exec bonifica
     */
    private static void exec() {
        try {
            BonificaCatalogue b = new BonificaCatalogue();
            b.execBonifica();
        } catch (Exception e) {
            logger.error("*** FATAL! Si è verificato un errore durante l'esecuzione della bonifica");
            e.printStackTrace();
        }
    }
}
