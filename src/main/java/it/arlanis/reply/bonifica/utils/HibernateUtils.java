package it.arlanis.reply.bonifica.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * Hibernate configuration utils
 */
public class HibernateUtils {
    private transient static final Logger logger = LoggerFactory.getLogger(HibernateUtils.class);
    private static SessionFactory factory;
    private static Map<String,String> cfgMap;
    private static Set<Class<? extends Object>> modelCls;

    private static void init() {
        cfgMap = new HashMap<>();
        modelCls = new HashSet<>();
        setPersistenceCls();
        setConfigsMap();
    }

    /**
     * Factory method
     * @return a SessionFactory object
     */
    public static SessionFactory getFactory() {
        if(factory == null)
            factory = buildSessionFactory();
        return factory;
    }

    public static void setFactory(SessionFactory factory) {
        HibernateUtils.factory = factory;
    }

    public static Map<String, String> getCfgMap() {
        return cfgMap;
    }

    public static void setCfgMap(Map<String, String> cfgMap) {
        HibernateUtils.cfgMap = cfgMap;
    }

    /**
     * Exec the SessionFactory build
     * @return the hibernate session
     */
    private static SessionFactory buildSessionFactory() {
        try {
            init();

            Configuration cfg = new Configuration();

            //Add persistence classes to Hibernate config
            for (Class<?> cls : modelCls) {
                cfg.addAnnotatedClass(cls);
            }

            //Add datasource properties to Hibernate config
            for (Map.Entry<String, String> entry : cfgMap.entrySet()) {
                cfg.setProperty(entry.getKey(), entry.getValue());
            }

            logger.info("*** Hibernate Configuration loaded");

            ServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build();

            //Build the session factory for queries
            factory = cfg.buildSessionFactory(registry);

            return factory;

        } catch(Throwable t) {

            logger.error("\n*** Initial SessionFactory creation failed");
            throw new ExceptionInInitializerError(t);
        }
    }

    /**
     * Build the Configuration object
     */
    private static void setConfigsMap() {
        try {
            Config cfgFactory = ConfigFactory.parseResources(BonificaUtils.DEV_CONFIG);

            ConfigObject configObject = cfgFactory.getObject("db.config");
            cfgMap.put("hibernate.connection.url", configObject.get("url").unwrapped().toString());
            cfgMap.put("hibernate.connection.username", configObject.get("username").unwrapped().toString());
            cfgMap.put("hibernate.connection.password", configObject.get("password").unwrapped().toString());
            cfgMap.put("hibernate.connection.driver_class", configObject.get("driver_class").unwrapped().toString());
            cfgMap.put("hibernate.dialect", configObject.get("hibernate_dialect").unwrapped().toString());
            cfgMap.put("hibernate.connection.pool_size", configObject.get("pool_size").unwrapped().toString());
            cfgMap.put("hibernate.show_sql", configObject.get("show_sql").unwrapped().toString());
            cfgMap.put("hibernate.current_session_context_class", configObject.get("session_ctx").unwrapped().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all classes in a package using Reflections API
     */
    private static void setPersistenceCls() {
        try {

            List<ClassLoader> classLoadersList = new LinkedList();
            classLoadersList.add(ClasspathHelper.contextClassLoader());
            classLoadersList.add(ClasspathHelper.staticClassLoader());

            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("it.arlanis.reply.models"))));

            Set<Class<?>> reflClass = reflections.getSubTypesOf(Object.class);


            reflClass.forEach(cls -> {

                logger.info("*** REFLECTION: Analyzed class " + cls.getSimpleName());

                if(!cls.getSimpleName().equalsIgnoreCase(BonificaUtils.CATALOGUE_INTERFACE)) {
                    modelCls.add(cls);
                }

            });

        } catch(Exception e) {

            e.printStackTrace();
        }
    }


}
