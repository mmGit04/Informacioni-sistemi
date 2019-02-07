/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzer;

import entiteti.Artikal;
import entiteti.ProdArtikal;
import entiteti.ProdRezervacije;
import entiteti.Prodavnica;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Minjoza
 */
public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "MyTopicSendSpa")
    private static Topic topicSpa;
    @Resource(lookup="topicRezervacije")
    private static Topic topicRez;
    @Resource(lookup="topicRezReturn")
    private static Topic topicRezReturn;
    @Resource(lookup="MyTopicRecSpa")
    private static Topic topicRecSpa;
    @Resource(lookup="queueNovaRoba")
    private static Queue queueNovaRoba;
    @Resource(lookup="topicCena")
    private static Topic topicCena;
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MenadzerPU");
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        int idprod=Integer.parseInt(args[0]);
        int idMen=Integer.parseInt(args[1]);
        String idString = "Id" + idMen;
        System.out.println("Radi menadzer sa ID: " + idMen);
        JMSContext context = connectionFactory.createContext();
       
        JMSConsumer consumerStanje = context.createConsumer(topicSpa,"ID=" + idMen);
        
        JMSConsumer consumerRezervacije=context.createConsumer(topicRez,"ID=" + idMen);
        
        JMSConsumer consumerNovaRoba=context.createConsumer(queueNovaRoba);
        
        JMSConsumer consumerNovaCena=context.createConsumer(topicCena);
        
        JMSProducer producer=context.createProducer();
        consumerNovaCena.setMessageListener((Message message)->{
            try {
                String nazivA=message.getStringProperty("artikal");
                int novaCena=message.getIntProperty("cena");
                System.out.println("Nova cena artikla naziva : " + nazivA + " je : " + novaCena);
                et.begin();
                TypedQuery<Artikal> upit=em.createQuery("select a "
                        + " from Artikal a "
                        + "where a.naziv = '" + nazivA + "'", Artikal.class);
                Artikal a=upit.getSingleResult();
                et.commit();
                et.begin();
                double cena=novaCena;
                a.setCena(cena);
                et.commit();
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
              
        
       });
      
        
        consumerNovaRoba.setMessageListener((Message message)->{
            try {
                String nazivArtikla=message.getStringProperty("artikal"); 
                int kolicina=message.getIntProperty("kolicina");
                String query="select a "
                       + "from Artikal a "
                       + "where a.naziv='" + nazivArtikla + "'" ;
               TypedQuery<Artikal> upit=em.createQuery(query, Artikal.class);
               et.begin();
               Artikal artikal=upit.getSingleResult();
               et.commit();
               System.out.println("Pristigla je roba sa sifrom :" + nazivArtikla + " , u kolicini " + kolicina + ".");
               et.begin();
               TypedQuery<ProdArtikal> upitpa=em.createQuery("select pa "
                       + "from ProdArtikal pa "
                       + "where pa.idprod.idprodavnica=" + idprod + " and pa.idartikal.idartikal = " + artikal.getIdartikal(), ProdArtikal.class);
              ProdArtikal pa=upitpa.getSingleResult();
               et.commit();
               et.begin();
               int kol=pa.getKolicina();
               et.commit();
               kol+=kolicina;
               et.begin();
               pa.setKolicina(kol);
               et.commit();
               System.out.println("Uspesno je izmenjeno stanje artikla u radnji .");
                        
                        
                        } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        });
        
        consumerRezervacije.setMessageListener((Message message)->{
           
            try {
                int Rad=message.getIntProperty("Prodavac");
                System.out.println("Stigao je zahtev za rezervaciju od radnika: " + Rad);
                int kolicina=message.getIntProperty("Kolicina");
                String idArtikal=message.getStringProperty("IDartikal");
                ProdRezervacije pr=new ProdRezervacije(1,kolicina);
                et.begin();
                TypedQuery<Artikal> query=em.createQuery("select a "
                                          + "from Artikal a "
                                          + "where a.naziv='" + idArtikal + "'" ,Artikal.class);
                 Artikal artikal=query.getSingleResult();
                et.commit();
                pr.setIdArtik(artikal);
                et.begin();
                Prodavnica prodavnica=em.find(Prodavnica.class, idprod);
                et.commit();
                pr.setIdprodav(prodavnica);
                et.begin();
                em.persist(pr);
                et.commit();
                et.begin();
                em.refresh(pr);
                et.commit();
                int reservation=pr.getIdprodRezervacije();
                String que="select pa"
                        + " from ProdArtikal pa"
                        + " where pa.idprod.idprodavnica=" + idprod +" and pa.idartikal.idartikal = " + artikal.getIdartikal();
                TypedQuery<ProdArtikal> upit=em.createQuery(que, ProdArtikal.class);
                et.begin();
                ProdArtikal pa=upit.getSingleResult();
                et.commit();
                int trenutnaKol=pa.getKolicina();
                trenutnaKol-=kolicina;
                et.begin();
                pa.setKolicina(trenutnaKol);
                et.commit();
                String sadrzaj="Uspesna rezervacija.";
                TextMessage textMessage=context.createTextMessage();
                textMessage.setIntProperty("reservation", reservation);
                textMessage.setIntProperty("Prodavac",Rad);
                producer.send(topicRezReturn,textMessage);
                System.out.println("Poslata je poruka o rezervaciji radniku :" + Rad + "sa brojem rezervacije: " + reservation);
                
                
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        consumerStanje.setMessageListener((Message message) -> {
            
                try {
                    int Rad=message.getIntProperty("Prodavac");
                    System.out.println("Stigla je poruka za info o spakovanosti od radnika: " + Rad );
                   
                    int vr=(int) Math.random();
                    int result=0;
                    if(vr>0.5)  result=1;
                    else result=0;
                    String sadrzaj="Stanje spakovanosti";
                    TextMessage textMessage=context.createTextMessage();
                    textMessage.setIntProperty("result", result);
                    textMessage.setIntProperty("Prodavac",Rad);
                    producer.send(topicRecSpa, textMessage);
                    System.out.println("Poslao sam poruku o spakovanosti sa rezultatom:" + result + ",prodavcu sa id :" + Rad);
                            } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        });
        while(true){
            
        }
    }
    
}
