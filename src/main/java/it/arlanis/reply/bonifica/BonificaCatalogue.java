package it.arlanis.reply.bonifica;

import it.arlanis.reply.bonifica.utils.BonificaUtils;
import it.arlanis.reply.hibernate.CatalogueService;
import it.arlanis.reply.models.*;
import it.arlanis.reply.models.enums.ComponentState;
import it.arlanis.reply.models.enums.MarketType;
import it.arlanis.reply.models.enums.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Onofrio Falco
 * @date 17/07/2017.
 *
 * Esegue la bonifica del catalogo solo in PROD:
 *
 * a) Inserimento anagrafica servizio opzionale (6979 – Diritto di Ripensamento);
 * b) Bonifica dei piani con Mercato = SINGLE PLAY e tipo = SIM per aggiungere il suddetto servizio;
 * c) Bonifica di tutte le offerte con Mercato = SINGLE PLAY, stato = Attivo e aventi come Piano SIM uno di quelli del punto precedente;
 * d) Bonifica di tutte le offer in campaign all’interno delle campagne con stato = Esecuzione, al fine di aggiungere il servizio dal punto precedente;
 *
 */
public class BonificaCatalogue {
    private transient static final Logger logger = LoggerFactory.getLogger(BonificaCatalogue.class);
    private CatalogueService catalogueService;

    /**
     * Public constructor
     */
    public BonificaCatalogue() {
        catalogueService = new CatalogueService();
    }

    public void execBonifica() throws Exception {
        logger.info("\n*** BONIFICA Catalogue: start");

        //AGCOM - Servizio Opzionale/Diritto di Ripensamento
        Product optional;

        //List of DB objects
        List<Plan> plans = new ArrayList<>();
        List<PlanProductRelation> planProdRel = new ArrayList<>();
        List<Offer> offs = new ArrayList<>();
        List<OfferInCampaign> offsCmpgn = new ArrayList<>();
        List<Campaign> cmpgns = new ArrayList<>();
        List<ProductInOffer> prodInOff = new ArrayList<>();

        //Temp lists to Persist
        List<PlanProductRelation> prPlToSave = new ArrayList<>(0);
        List<ProductInOffer> prInOffToSave = new ArrayList<>(0);
        List<OfferInCampaign> offInCmpToSave = new ArrayList<>(0);

        //Steps
        boolean step_A = false;
        boolean step_B = false;
        boolean step_C = false;
        boolean step_D = false;
        boolean finish = false;

        //Open Hibernate session
        catalogueService.openSessionFactory();


        //TODO: exec A)
        if (!catalogueService.countOptionalServices()) {
            //SAVE NEW
            optional = new Product();
            optional.setCode((long) BonificaUtils.OPTIONAL_SERVICE_CODE);
            optional.setMarket(MarketType.SinglePlay);
            optional.setStartDate(BonificaUtils.CURRENT_DATE);
            optional.setName(BonificaUtils.OPTIONAL_SERVICE_NAME);
            optional.setType(ProductType.valueOf("Optional"));
            optional.setState(ComponentState.Active);
            optional.setCreator(BonificaUtils.SERVICE_OWNER);
            optional.setVersion(BonificaUtils.SERVICE_VERSION);
            optional.setCreationDate(BonificaUtils.CURRENT_DATE);
            optional.setLastUpdate(BonificaUtils.CURRENT_DATE);
            optional.setModifier(BonificaUtils.SERVICE_OWNER);

            //SAVE - Product.class
            catalogueService.save(optional);
            step_A = true;

            logger.info("*** SUCCESS! Nuova anagrafica \"" + optional.getType().getNome() + "\" creata in product");

        } else {
            //GET EXISTING SERVICE
            optional = (Product) catalogueService.findProductsByCode((long) BonificaUtils.OPTIONAL_SERVICE_CODE).get(0);
            if(!optional.getState().equals(ComponentState.Active)) {
                //Set state as Active
                optional.setState(ComponentState.Active);
                catalogueService.update(optional);
            }

            logger.info("*** SUCCESS! Selezionata la nuova anagrafica \""+ optional.getType().getNome() +"\" dalla tabella Product");
            step_A = true;
        }

        catalogueService.setDirRip(optional); //Set AGCOM/Diritto Ripensamento for Query


        if(step_A) {
            plans = catalogueService.findPlanByMarketAndType();
            if(plans.size() > 0) {

                planProdRel = catalogueService.findPlanProductRelationsByPlans(plans);
                int countPlanProdRel = planProdRel.size();
                int countPlanProd_Bonificate = catalogueService.countPlanProductRelations_Bonificate(plans);

                if (!planProdRel.isEmpty() && planProdRel != null) {
                    //Verifica che ci siano relazioni da bonificare
                    if (countPlanProdRel != countPlanProd_Bonificate) {
                        for (PlanProductRelation rel : planProdRel) {
                            if (rel.getProduct() == null || rel.getProduct().getCode().intValue() != BonificaUtils.OPTIONAL_SERVICE_CODE) {

                                PlanProductRelation ppr = new PlanProductRelation();
                                ppr.setIncluded(rel.getIncluded() != null ? rel.getIncluded() : false);
                                ppr.setNewValue(rel.getNewValue() != null ? rel.getNewValue() : false);
                                ppr.setParameter(rel.getParameter());
                                ppr.setPlan(rel.getPlan()); //FK

                                //SET THE OPTIONAL SERVICE
                                ppr.setProduct(optional); //FK

                                ppr.setValue(rel.getValue()); //FK
                                ppr.setCreationDate(BonificaUtils.CURRENT_DATE);
                                ppr.setCreator(BonificaUtils.SERVICE_OWNER);
                                ppr.setLastUpdate(BonificaUtils.CURRENT_DATE);
                                ppr.setModifier(BonificaUtils.SERVICE_OWNER);
                                ppr.setVersion(rel.getVersion());

                                //Save in temp list
                                prPlToSave.add(ppr);
                            }
                        }

                        if(!prPlToSave.isEmpty()) {
                            logger.info("*** SUCCESS! Aggiunto ad ogni plan il nuovo Servizio Opzionale");
                            step_B = true;
                        }
                    } else {
                        logger.error("*** WARNING! Tutte le relazioni tra plan e product già BONIFICATE");
                        step_B = true;
                    }
                } else {
                    logger.error("*** WARNING! Non ci sono a DB relazioni tra plan e product da bonificare");
                }
            } else {
                logger.error("*** WARNING! Non sono presenti a DB piani con type SIM e market SinglePlay");
            }

        }


        //TODO exec B)
        if(step_B) {

            //BULK SAVE - PlanProductRelation.class
            catalogueService.bulkSave(PlanProductRelation.class, prPlToSave);

            offs = catalogueService.findActiveOffersByMarket();
            if (offs.size() > 0) {
                prodInOff = catalogueService.findProductsInOfferByPlansAndOffers(plans, offs);
                int countProdInOff = prodInOff.size();
                int countProdInOff_Bonificate = catalogueService.countProductsInOffer_Bonificati(plans, offs);

                if(!prodInOff.isEmpty() && prodInOff != null) {
                    //Verifica che ci siano relazioni da bonificare
                    if(countProdInOff != countProdInOff_Bonificate) {
                        prodInOff.forEach(p -> {

                            if (p.getProduct() == null || p.getProduct().getCode().intValue() != BonificaUtils.OPTIONAL_SERVICE_CODE) {
                                ProductInOffer prInOf = new ProductInOffer();
                                prInOf.setIncluded(p.getIncluded() != null ? p.getIncluded() : false);
                                prInOf.setIsNewValue(p.getIsNewValue() != null ? p.getIsNewValue() : false);
                                prInOf.setMaterials(p.getMaterials());
                                prInOf.setOffer(p.getOffer());//FK

                                //SET THE OPTIONAL SERVICE
                                prInOf.setProduct(optional);//FK

                                prInOf.setParameter(p.getParameter());
                                prInOf.setPlan(p.getPlan());//FK
                                prInOf.setValue(p.getValue());
                                prInOf.setCreationDate(BonificaUtils.CURRENT_DATE);
                                prInOf.setCreator(BonificaUtils.SERVICE_OWNER);
                                prInOf.setLastUpdate(BonificaUtils.CURRENT_DATE);
                                prInOf.setVersion(p.getVersion());
                                prInOf.setModifier(BonificaUtils.SERVICE_OWNER);

                                //Save in temp list
                                prInOffToSave.add(prInOf);
                            }
                        });

                        if(!prInOffToSave.isEmpty()) {
                            logger.info("*** SUCCESS! Aggiornate le offer con il nuovo Servizio");
                            step_C = true;
                        }
                    } else {
                        logger.error("*** WARNING! Tutti i record product in offer sono già stati BONIFICATI");
                        step_C = true;
                    }
                } else {
                    logger.error("*** WARNING! Non ci sono a DB product in offer da bonificare");
                }
            } else {
                logger.error("*** WARNING! Sono sono presenti a DB offer Attive con market SinglePlay");
            }

        }


        //TODO exec C)
        if(step_C) {
            //BULK SAVE - ProductInOffer.class
            catalogueService.bulkSave(ProductInOffer.class, prInOffToSave);

            cmpgns = catalogueService.findCampaignsInEsecuzione();
            if (cmpgns.size() > 0) {

                offsCmpgn = catalogueService.findOffersInCampaignByAllForeignKeys(plans, offs, cmpgns);
                int countOffsCmpgn = offsCmpgn.size();
                int countOffsCmpgn_Bonificate = catalogueService.countOffersInCampaign_Bonificate(plans, offs, cmpgns);

                if (!offsCmpgn.isEmpty() && offsCmpgn != null) {
                    //Verifica che ci siano relazioni da bonificare
                    if(countOffsCmpgn != countOffsCmpgn_Bonificate) {
                        offsCmpgn.forEach(cmp -> {

                            if(cmp.getProduct() == null || cmp.getProduct().getCode() != BonificaUtils.OPTIONAL_SERVICE_CODE) {
                                OfferInCampaign offInCmpgn = new OfferInCampaign();
                                offInCmpgn.setCampaign(cmp.getCampaign()); //FK
                                offInCmpgn.setIncluded(cmp.getIncluded() != null ? cmp.getIncluded() : false);
                                offInCmpgn.setNewValue(cmp.getNewValue() != null ? cmp.getNewValue() : false);
                                offInCmpgn.setMaterial(cmp.getMaterial());
                                offInCmpgn.setOffer(cmp.getOffer()); //FK
                                offInCmpgn.setParameter(cmp.getParameter());
                                offInCmpgn.setPayment(cmp.getPayment());

                                //SET THE OPTIONAL SERVICE
                                offInCmpgn.setProduct(optional); //FK

                                offInCmpgn.setPlan(cmp.getPlan()); //FK
                                offInCmpgn.setValue(cmp.getValue());
                                offInCmpgn.setCreationDate(BonificaUtils.CURRENT_DATE);
                                offInCmpgn.setCreator(BonificaUtils.SERVICE_OWNER);
                                offInCmpgn.setLastUpdate(BonificaUtils.CURRENT_DATE);
                                offInCmpgn.setModifier(BonificaUtils.SERVICE_OWNER);

                                //Save in temp list
                                offInCmpToSave.add(offInCmpgn);
                            }
                        });

                        if(!offInCmpToSave.isEmpty()) {
                            logger.info("*** SUCCESS! Aggiornate tutte le offer in campaign con il nuovo Servizio");
                            step_D = true;
                        }
                    } else {
                        logger.error("*** WARNING! Tutti i record offer in campaign sono stati già BONIFICATI");
                    }
                } else {
                    logger.error("*** WARNING! Non sono presenti a DB offer in campaign da bonificare");
                }
            } else {
                logger.error("*** WARNING! Non sono presenti a DB offer in campaign con campagne in Esecuzione");
            }
        }


        //TODO exec D)
        if(step_D) {
            catalogueService.bulkSave(OfferInCampaign.class, offInCmpToSave);
            finish = true;

            if(finish) {
                logger.info("\n*** SUCCESS! Bonifica Catalogue completata");
            }

        }

        //Close hibernate session factory
        catalogueService.closeSessionFactory();


        logger.info("\n*** BONIFICA Catalogue: stop");
    }

}
