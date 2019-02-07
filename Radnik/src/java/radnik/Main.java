
package radnik;

import entiteti.Artikal;
import java.util.List;


import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "queueNovaRoba")
    private static Queue queueNovaRoba;
    @Resource(lookup="topicCena")
    private static Topic topicCena;
    
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("RadnikPU");
       EntityManager em=emf.createEntityManager();
       EntityTransaction et=em.getTransaction();
       Scanner in=new Scanner(System.in);
       System.out.println("Dobrodosli na posao.");
       while(true){
           try {
              
               System.out.println("Unesite opciju koju zelite da izvrsite:"
                       + "1.Kreiranje novih artikala;"
                       + "2.Promena cene nekog artikla.");
              int izbor=in.nextInt();
              in.nextLine();
              if(izbor==1){
               System.out.println("Unesite naziv artikla koji zelite da proizvedete.");
               
               String nazivArtikla=in.nextLine();
               System.out.println("Unesite kolicinu koju zelite da proizvedete tog artikla.");
               int kolicina =in.nextInt();
               in.nextLine();
               String query="select a "
                       + "from Artikal a "
                       + "where a.naziv='" + nazivArtikla + "'" ;
               TypedQuery<Artikal> upit=em.createQuery(query, Artikal.class);
               et.begin();
               List<Artikal> artikalList=upit.getResultList();
               et.commit();
               if(artikalList.isEmpty() || artikalList==null) System.out.println("Nemamo informaciju u bazi o artiklu tog naziva. ");
               else{
               Artikal artikal=artikalList.get(0);
               int vreme=artikal.getVreme();
               Thread.sleep(vreme*1000);
      
               Destination destination = queueNovaRoba;
               JMSContext context = connectionFactory.createContext();
               JMSProducer producer = context.createProducer();
               
               
               String sadrzaj = "Prispela nova roba.";
               TextMessage textMessage = context.createTextMessage();
               
               textMessage.setText(sadrzaj);
             //  textMessage.setIntProperty("ID", men.getIdmenadzer());
               
               textMessage.setStringProperty("artikal", nazivArtikla);
               textMessage.setIntProperty("kolicina", kolicina);
               producer.send(destination, textMessage);
               System.out.println("Poslat je artikal : " + nazivArtikla + ", u kolicini: " + kolicina + " ." );
               
               }
              } 
              else if(izbor==2) {
                  System.out.println("Unesite naziv artikla ciju cenu zelite da promenite.");
                  String nazivA=in.nextLine();
                  System.out.println("Unestite novu cenu tog artikla.");
                  int novaCena=in.nextInt();
                  String query="select a "
                       + "from Artikal a "
                       + "where a.naziv='" + nazivA + "'" ;
                  TypedQuery<Artikal> upit=em.createQuery(query, Artikal.class);
                  et.begin();
                  List<Artikal> artikalList=upit.getResultList();
                  et.commit();
                  if(artikalList.isEmpty() || artikalList==null)
                      System.out.println("U bazi nemamo podatke o artiklu tog naziva.");
                  else{
                     
                  Artikal artikal=artikalList.get(0);
                  et.begin(); 
                  artikal.setCena(novaCena);
                  et.commit();
                  Destination destination = topicCena;
                  JMSContext context = connectionFactory.createContext();
                  JMSProducer producer = context.createProducer();
                  String sadrzaj = "Promenjena je cena artikla.";
                  TextMessage textMessage = context.createTextMessage();

                  textMessage.setText(sadrzaj);
                 
                  textMessage.setStringProperty("artikal",nazivA);
                  textMessage.setIntProperty("cena", novaCena);
                  producer.send(destination, textMessage);
                  System.out.println("Poslato je obavestenje svim menadzerima o promeni cene.");
                    
                  }  
              }
              
           } catch (InterruptedException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           } catch (JMSException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           
           
       }
    }
    
}
