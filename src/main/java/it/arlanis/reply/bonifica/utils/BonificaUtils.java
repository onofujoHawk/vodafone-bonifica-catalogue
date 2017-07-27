package it.arlanis.reply.bonifica.utils;

import com.google.common.base.CaseFormat;
import it.arlanis.reply.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * Classe di utils per la Bonifica catalogo
 */
public class BonificaUtils {

    public static final String OPTIONAL_SERVICE_NAME = "Diritto di Ripensamento";
    public static final int OPTIONAL_SERVICE_CODE = 9679;
    public static final Date CURRENT_DATE = new Date();
    public static final String SERVICE_OWNER = "administrator";
    public static final int SERVICE_VERSION = 1;
    public static final String DEV_CONFIG = "configs/dev.conf";
    //public static final String PRE_CONFIG = "configs/pre.conf";
    //public static final String PROD_CONFIG = "configs/prod.conf";
    public static final String CATALOGUE_INTERFACE = "EagleCatalogueObject";

    //Hibernate persistence classes
    public static final Class[] persistenceClasses = new Class[] {Campaign.class, Product.class, Plan.class, PlanProductRelation.class,
                Offer.class, OfferInCampaign.class, ProductInOffer.class};

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * Conversion from camel-case to snake-case
     * This method convert a primary-key member
     * in a model class from camel to snake-case
     *
     * @param f the camel-case string
     * @return a snake-case string
     */
    public static final String camelToSnakeCase(String f) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, f);
    }

}
