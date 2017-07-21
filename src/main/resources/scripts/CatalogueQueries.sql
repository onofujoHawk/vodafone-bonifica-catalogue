/**
 * Query utili per Vodafone Eagle catalogue.
 * @author  Onofrio Falco
 * @date  Jul 2017
 */


/* show products with type */
Select * 
from catalogpre.product
where catalogpre.product.type = 'Optional' or catalogpre.product.type = 'Service'  
or catalogpre.product.type = 'Promo';
-- etc.


/* show offers in campaign and associated services */
Select *
from catalogpre.offer_in_campaign inner join catalogpre.product on catalogpre.offer_in_campaign.product = catalogpre.product.product_id
where catalogpre.offer_in_campaign.campaign = 48 and catalogpre.product.name like 'Diritto di%';


/* show offers in campaign, services and offers associated */
Select *
from catalogpre.offer_in_campaign inner join catalogpre.product on catalogpre.offer_in_campaign.product = catalogpre.product.product_id
inner join catalogpre.offer on catalogpre.offer_in_campaign.offer = catalogpre.offer.offer_id
where catalogpre.offer_in_campaign.campaign = 48;


/* show more info on relation offer-offer_in_campaign-product-campaign */
Select catalogpre.offer_in_campaign.offer_in_campaign_id as Offerte_in_Campagna_ID, 
catalogpre.offer_in_campaign.campaign as Campagna_ID,
catalogpre.campaign.name as Nome_Campagna,
catalogpre.offer.name as Nome_Offerta,
catalogpre.offer.offer_id as Offerta_ID, 
catalogpre.plan.plan_id as Piano_ID,
catalogpre.plan.name as Nome_Piano,
catalogpre.plan.market as Mercato_Piano,
catalogpre.plan.state as Stato_Piano,
catalogpre.product.name as Nome_Servizio, 
catalogpre.product.code as Codice_Servizio_SNCODE,
catalogpre.product.type as Tipo_Servizio
from catalogpre.offer_in_campaign 
inner join catalogpre.product on catalogpre.offer_in_campaign.product = catalogpre.product.product_id
inner join catalogpre.offer on catalogpre.offer_in_campaign.offer = catalogpre.offer.offer_id
inner join catalogpre.campaign on catalogpre.campaign.campaign_id = catalogpre.offer_in_campaign.campaign
inner join catalogpre.plan on catalogpre.plan.plan_id = catalogpre.offer_in_campaign.plan
where catalogpre.offer_in_campaign.campaign = 48;


/* update status of plan */
Update catalogpre.plan
set catalogpre.plan.state = 'Active'
where catalogpre.plan.plan_id = '19';


/* show plan-product relation */
Select catalogpre.plan_product_relation.plan, catalogpre.plan_product_relation.product, catalogpre.plan.plan_id, catalogpre.plan.name, catalogpre.product.name, catalogpre.product.type
from catalogpre.plan_product_relation inner join catalogpre.product 
on catalogpre.plan_product_relation.product = catalogpre.product.product_id
inner join catalogpre.plan 
on catalogpre.plan_product_relation.plan = catalogpre.plan.plan_id;


/* show services-products from offer in campaign ordered by type of service desc */
Select catalogdev.offer_in_campaign.offer_in_campaign_id as ID_Offer_In_Campaign, 
catalogdev.offer_in_campaign.offer as ID_Offer, 
catalogdev.offer.name as Nome_Offer,
catalogdev.product.product_id as ID_Service,
catalogdev.product.name as Nome_Service,
catalogdev.product.code as SNCode,
catalogdev.product.type as Tipo_Service
from catalogdev.offer_in_campaign inner join catalogdev.offer on catalogdev.offer_in_campaign.offer = catalogdev.offer.offer_id
inner join catalogdev.product on catalogdev.offer_in_campaign.product = catalogdev.product.product_id
where catalogdev.offer.state = 'Active'
order by catalogdev.product.type desc;


/* query for bonifica catalogue - AGCOM/Diritto di Ripensamento */
Select catalogdev.offer_in_campaign.offer_in_campaign_id, 
catalogdev.offer.offer_id,
catalogdev.offer.name, 
catalogdev.plan.plan_id,
catalogdev.plan.name, 
catalogdev.product.product_id,
catalogdev.product.name,
catalogdev.product.code,
catalogdev.product.type
from catalogdev.offer_in_campaign inner join catalogdev.offer on catalogdev.offer_in_campaign.offer = catalogdev.offer.offer_id
inner join catalogdev.plan on catalogdev.offer_in_campaign.plan = catalogdev.plan.plan_id
inner join catalogdev.product on catalogdev.offer_in_campaign.product = catalogdev.product.product_id
where catalogdev.offer_in_campaign.offer in 
(select catalogdev.offer.offer_id from catalogdev.offer where catalogdev.offer.market = 'SinglePlay' 
and catalogdev.offer.state = 'Active')
AND catalogdev.offer_in_campaign.product in 
(select catalogdev.product.product_id from catalogdev.product where catalogdev.product.type = 'Service' and catalogdev.product.state = 'Active')
AND catalogdev.offer_in_campaign.campaign in 
(select catalogdev.campaign.campaign_id from catalogdev.campaign where catalogdev.campaign.state = 'Esecuzione');


/* query for bonifica catalogue - AGCOM/Diritto di Ripensamento */
Select catalogdev.offer_in_campaign.offer_in_campaign_id, catalogdev.offer_in_campaign.offer, offer_in_campaign.product, product.type
from catalogdev.offer_in_campaign
inner join catalogdev.campaign on catalogdev.offer_in_campaign.campaign = catalogdev.campaign.campaign_id
inner join catalogdev.offer on catalogdev.offer_in_campaign.offer = catalogdev.offer.offer_id
inner join catalogdev.product on catalogdev.offer_in_campaign.product = catalogdev.product.product_id
where catalogdev.campaign.state = 'Esecuzione'
and catalogdev.offer.market = 'SinglePlay'
and catalogdev.product.code = 6979;


/* save a new Optional product if not exists */
INSERT INTO catalogdev.product (catalogdev.product.name,
                                catalogdev.product.code,
                                catalogdev.product.state,
                                catalogdev.product.type,
                                catalogdev.product.start_date,
                                catalogdev.product.market,
                                catalogdev.product.creator)
  Select * from (select 'Diritto di Ripensamento',
                   6983,
                   'Active',
                   'Optional',
                   CURDATE(),
                   'SinglePlay',
                   'administrator') AS temp
  where not exists(
      select catalogdev.product.product_id from catalogdev.product
      where catalogdev.product.code = 6983 and catalogdev.product.market = 'SinglePlay'
            and catalogdev.product.type = 'Optional')
LIMIT 1;


/* plan-product relation join table */
select * from catalogdev.plan_product_relation
  inner join catalogdev.product on catalogdev.plan_product_relation.product = catalogdev.product.product_id
  inner join catalogdev.plan on catalogdev.plan_product_relation.plan = catalogdev.plan.plan_id
where catalogdev.plan.market = 'SinglePlay' and catalogdev.plan.type = 'SIM'
      and catalogdev.product.type <> 'Optional'
group by catalogdev.plan_product_relation.plan;


/* product-in-offer join table */
Select catalogdev.offer.offer_id as id_offerta, catalogdev.offer.name as nome_offerta, catalogdev.product.product_id as id_servizio,
       catalogdev.product.type as tipo_servizio, catalogdev.plan.market as mercato_plan,
       catalogdev.plan.type as tipo_piano
from catalogdev.product_in_offer
  inner join catalogdev.product on catalogdev.product_in_offer.product = catalogdev.product.product_id
  inner join catalogdev.offer on catalogdev.product_in_offer.offer = catalogdev.offer.offer_id
  inner join catalogdev.plan on catalogdev.product_in_offer.plan = catalogdev.plan.plan_id
where catalogdev.plan.market = 'SinglePlay' and catalogdev.plan.type = 'SIM'  and catalogdev.offer.state = 'Active'
      and (catalogdev.product.type <> 'Optional' or catalogdev.product.type is null) and catalogdev.offer.market = 'SinglePlay'
group by catalogdev.product_in_offer.offer;


/* offer-in-campaign table */
Select *
from catalogdev.offer_in_campaign
  inner join catalogdev.product on catalogdev.offer_in_campaign.product = catalogdev.product.product_id
  inner join catalogdev.offer on catalogdev.offer_in_campaign.offer = catalogdev.offer.offer_id
  inner join catalogdev.plan on catalogdev.offer_in_campaign.plan = catalogdev.plan.plan_id
  inner join catalogdev.campaign on catalogdev.offer_in_campaign.campaign = catalogdev.campaign.campaign_id
where catalogdev.plan.market = 'SinglePlay' and catalogdev.plan.type = 'SIM'  and catalogdev.offer.state = 'Active'
      and (catalogdev.product.type <> 'Optional' or catalogdev.product.type is null) and catalogdev.offer.market = 'SinglePlay'
      and catalogdev.campaign.state = 'Esecuzione'
group by catalogdev.offer_in_campaign.offer
