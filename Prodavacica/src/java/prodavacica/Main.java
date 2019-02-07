
package prodavacica;

import entiteti.Artikal;
import entiteti.DnevniPromet;
import entiteti.KupacRezervacija;
import entiteti.ProdArtikal;
import entiteti.ProdRezervacije;
import entiteti.Prodavnica;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    public static void main(String[] args) throws JMSException {
        int idprod=Integer.parseInt(args[0]);//Prodavnica u kojoj se radi.
        int idRad=Integer.parseInt(args[1]);//Radnik koji otvara prodavnicu.
        
        String idString = "Id" + idRad;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProdavacicaPU");
        EntityManager em=emf.createEntityManager();
        EntityTransaction et=em.getTransaction();
        java.util.Date utilDate=new java.util.Date();
        java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
       
       //Ubacivanje u bazu dnevnog prometa;
       String query="select p "
               + "from Prodavnica p "
               + "where p.idprodavnica=" + idprod;
       et.begin();
       TypedQuery<Prodavnica> prodQuery=(TypedQuery<Prodavnica>) em.createQuery(query);
       Prodavnica mojaProda=prodQuery.getSingleResult();
       et.commit();
       System.out.println("Otvorena prodavnica sa imenom " + mojaProda.getNaziv() + ".");
       
       
       
       
       query ="select count(dp.iddnevniPromet) "
               + "from DnevniPromet dp "
               + "where dp.idprodavnica.idprodavnica=" + idprod + " and dp.datum= '" + sqlDate +"'";
       
        et.begin();
        TypedQuery<Long> q=em.createQuery(query,Long.class);
        long list=q.getSingleResult();
        et.commit();
        if (list==0){ 
        et.begin();
        DnevniPromet dp=new DnevniPromet(1,sqlDate,0);
        dp.setIznos(0);
        dp.setIdprodavnica(mojaProda);
        em.persist(dp);
        et.commit();
     
       }

       //Ovaj gornji deo predstavlja unosenje podatka u dnevni promet za svaki radni dan.
       
       Scanner in=new Scanner(System.in);
       
       while(true){
           String tekst="===============================================\n"
               + "Unesite zeljenu opciju:\n"
               + "1.Pretrazivanje artikala po nazivu;\n"
               + "2.Pretrazivanje artikala po tipu;\n"
               + "3.Ispitivanje stanja upakovanosti nekog artikla.\n"
               + "4.Kupovina artikla u odredjenoj kolicini.\n"
               + "5.Kupovina rezervisanog artikla.\n";
       
       System.out.println(tekst);
       int zahtev;
       boolean zahtevPostoji=false;
       zahtev=in.nextInt();
       in.nextLine();
       switch(zahtev){
           
           case 1:
               System.out.println("Unesite naziv artikla.");
               String naziv=in.nextLine();
               query =("select a.naziv,p.cena,p.kolicina "
                       + "from ProdArtikal p ,Artikal a "
                       + "where p.idprod.idprodavnica=" + idprod + " and p.idartikal.idartikal=a.idartikal and a.naziv='" + naziv +"'");
              et.begin();
              TypedQuery<Object[]> q1=em.createQuery(query, Object[].class);
              List<Object[]> lista=q1.getResultList();
              if(lista.isEmpty()) {System.out.println("U prodavnici ne postoje artikli tog naziva");}
              else{
               System.out.println("Rezultat pretrage je sledeci :");     
               for(Object[] k:lista){
                   System.out.println("Ime artikla:" + k[0]);
                   System.out.println("Kolicina:" + k[1]);
                   System.out.println("Cena:" + k[2]);
               }
              }
               et.commit();
               break;
           case 2:
               System.out.println("Unesite tip artikla.");
               String tip=in.nextLine(); 
               et.begin();
               String query1=("select a.naziv,p.cena,p.kolicina,t.naziv "
                       + "from ProdArtikal p,Artikal a,TipArtikla t "
                       + "where p.idprod.idprodavnica=" + idprod + " and p.idartikal.idartikal=a.idartikal and a.idtipArtikla.idtipArtikla=t.idtipArtikla and t.naziv='" + tip +"'");
               TypedQuery<Object[]> nizTip=em.createQuery(query1, Object[].class);
               List<Object[]> listaTip=nizTip.getResultList();
               if(listaTip.isEmpty()) System.out.println("U prodavnici ne postoje artikli tog tipa.");
               else {
                   System.out.println("Rezultat pretrage je sledeci: ");
               for(Object[] v:listaTip){
                  System.out.println("Naziv: " + v[0] + " Cena: " + v[1] + " Kolicina:" + v[2]) ;
               }
               }
               et.commit();
               break;
           case 3:
               String sadrzaj;
               TextMessage textMessage;
               System.out.println("Unesite ime artikla:");
               String imeA=in.nextLine();
               query =("select a.naziv,p.cena,p.kolicina "
                       + "from ProdArtikal p ,Artikal a "
                       + "where p.idprod.idprodavnica=" + idprod + " and p.idartikal.idartikal=a.idartikal and a.naziv='" + imeA +"'");
              et.begin();
              TypedQuery<Object[]> q8=em.createQuery(query, Object[].class);
              List<Object[]> lista8=q8.getResultList();
               et.commit();
              if(lista8.isEmpty()) {System.out.println("U prodavnici ne postoje artikli tog naziva.");}
              else{
               et.begin();
               TypedQuery<Integer> query2=em.createQuery("select m.idmenadzer "
                       + "from Menadzer m "
                       + "where m.prod.idprodavnica=" + idprod ,Integer.class);
               List<Integer> res=query2.getResultList();
                et.commit();
               int idMen;
               if(res.isEmpty()) System.out.println("Za trenutno otvorenu prodavnicu ne postoji definisani menadzer.");
               else{
               idMen=res.get(0);
                  
               //int idMen=query2.getSingleResult();
              
               
               Destination destination = topicSpa;
               JMSContext context = connectionFactory.createContext();
               JMSProducer producer = context.createProducer();
               
               
                sadrzaj = "Informacija o spakovanosti.";
                textMessage = context.createTextMessage();
               textMessage.setText(sadrzaj);
               textMessage.setIntProperty("ID", idMen);
               textMessage.setIntProperty("Prodavac",idRad);
               
                   
               producer.send(destination, textMessage);
               
               
               System.out.println("Poslat je zahtev menadzeru za stanje artikla: " + imeA + ",gde je IDMen: " + idMen +" i idProdavca: " + idRad);
               JMSConsumer consumer = context.createConsumer(topicRecSpa,"Prodavac=" + idRad);
               
               Message message = consumer.receive();
               int result=0;
              
                 result=message.getIntProperty("result");
               
               if(result==1) 
                   System.out.println("Stanje artikla je neupakovano.");
               else 
                   System.out.println("Stanje artikla je upakovano.");
              }
            }
               break;
           case 4:
               System.out.println("Unesite ime artikla koje zelite da kupite.");
               String imeArtikla=in.nextLine();
               System.out.println("Unesti kolicinu koju zelite da kupite.");
               int kol=in.nextInt();
               et=em.getTransaction();
               et.begin();
               query=("select p.kolicina,a.idartikal,p.cena "
                       + "from ProdArtikal p,Artikal a "
                       + "where p.idprod.idprodavnica=" + idprod + " and p.idartikal.idartikal=a.idartikal and a.naziv='" + imeArtikla +"'");
              
              TypedQuery<Object[]> qe=em.createQuery(query, Object[].class);
              int kolicina=0;
              int idA=0;
              double cena=0;
              try{
              Object[] inArt=qe.getSingleResult();
               et.commit();
               kolicina= (int) inArt[0];
               idA=(int) inArt[1];
               cena= (double) inArt[2];
              }
              catch(NoResultException e){
                  kolicina=0;//Ako taj artikal uopste mozda ni ne postoji u prodavnici.A
              }
               if(kolicina<kol){
                    System.out.println("U prodavnici nema dovoljno artikala tog naziva.");
                    System.out.println("Da li zelite da pozovemo druge radnje i proverimo dostupnost artikla?\n"
                            + "1.Da\n"
                            + "2.Ne\n");
                    int zeliPro=in.nextInt();
                    if(zeliPro==1){
                        System.out.println("Da li zelite i ispitivanje samo odredjenog stanja artikla?"
                                + "1.Ne zelim\n"
                                + "2.Zelim:Zapakovan\n"
                                + "3.Zelim:Otpakovan\n");
                        int stanje=in.nextInt();
                        et.begin();
                        query="select pa.idprod.idprodavnica,pa.idprod.naziv,pa.kolicina,pa.cena "
                                + "from ProdArtikal pa "
                                + " where pa.kolicina >=" + kol +" and pa.idartikal.naziv= '" + imeArtikla + "'";
                         TypedQuery<Object[]> dostupniArtikli=em.createQuery(query, Object[].class);
                         List<Object[]> listaDostupni=dostupniArtikli.getResultList();
                         et.commit();
                         if(listaDostupni.isEmpty()) System.out.println("Ne postoje dostupni artikli u drugim prodavnicama.Dovidjenja.");
                         else {
                         if(stanje==1){
                             for(Object[] o: listaDostupni){
                             System.out.println("IdProdavnice: " + o[0] + " Naziv prodavnice: "  + o[1] + " Dostupna kolicina: " + o[2] + " Pojedinacna cena: " + o[3]) ; 
                             }
                             
                         }
                         else if(stanje==2 || stanje==3){
                             for(Object[] o:listaDostupni){
                                    int idProdaDos=(int) o[0];
                                    et.begin();
                                    TypedQuery<Integer> queryD=em.createQuery("select m.idmenadzer "
                                                                                  + "from Menadzer m "
                                                                                  + "where m.prod.idprodavnica=" + idProdaDos ,Integer.class);
                                    int idMenDos=queryD.getSingleResult();
                                    et.commit();
                                    Destination destinationInfo = topicSpa;
                                    JMSContext contextInfo = connectionFactory.createContext();
                                    JMSProducer producerInfo = contextInfo.createProducer();
                                     sadrzaj = "Informacija o spakovanosti.";
                                     textMessage = contextInfo.createTextMessage();
                                     textMessage.setText(sadrzaj);
                                     textMessage.setIntProperty("ID", idMenDos);
                                     textMessage.setIntProperty("Prodavac",idRad);
                                     producerInfo.send(destinationInfo, textMessage);


                                     JMSConsumer consumerInfo = contextInfo.createConsumer(topicRecSpa,"Prodavac=" + idRad);
                                     System.out.println("Poslata je poruka menadzeru sa ID " + idMenDos);
                                     Message messageInfo = consumerInfo.receive();
                                      int resultInfo=0;

                                      resultInfo=messageInfo.getIntProperty("result");
                                      
                                      if(resultInfo==0 && stanje==2 ) {
                                          zahtevPostoji=true;
                                         System.out.println("IdProdavnice: " + o[0] + " Naziv prodavnice: "  + o[1] + " Dostupna kolicina: " + o[2] + " Pojedinacna cena: " + o[3] + " Stanje artikla je upakovano.") ; 
                                      }
                                      else if (resultInfo==1 && stanje==3){
                                          zahtevPostoji=true;
                                         System.out.println("IdProdavnice: " + o[0] + " Naziv prodavnice: "  + o[1] + " Dostupna kolicina: " + o[2] + " Pojedinacna cena: " + o[3] + "Stanje artikla je neupakovano ") ; 
                                      }
                                      
                                      

                             }
                             if(zahtevPostoji==false){
                                  System.out.println("Ne postoji artikli u drugim prodavnicama koji zadovoljavaju zahteve kupca.Dovidjenja.");
                                   break;
                              }
                         }
                         
                         
                         System.out.println("Da li zelite da rezervisete artikal u nekoj od prodavnica?"
                                 + "1.Ne zelim."
                                 + "2.Zelim.");
                         int zRez=in.nextInt();
                         if(zRez==2){
                             System.out.println("Unesite idProd u kojoj zelite da rezervisete Vas artikal.");
                             int idPRez=in.nextInt();
                        
                              et.begin();
                                    TypedQuery<Integer> queryD=em.createQuery("select m.idmenadzer "
                                                                                  + "from Menadzer m "
                                                                                  + "where m.prod.idprodavnica=" + idPRez ,Integer.class);
                                    int idMenRez=queryD.getSingleResult();
                                    et.commit();
                                    Destination destinationRez = topicRez;
                                    JMSContext contextRez = connectionFactory.createContext();
                                    JMSProducer producerRez = contextRez.createProducer();
                                     sadrzaj = "Rezervacija.";
                                     textMessage = contextRez.createTextMessage();
                                     textMessage.setText(sadrzaj);
                                     textMessage.setIntProperty("ID", idMenRez);
                                     textMessage.setIntProperty("Prodavac",idRad);
                                     textMessage.setIntProperty("Kolicina",kol);
                                     textMessage.setStringProperty("IDartikal", imeArtikla);
                                     producerRez.send(destinationRez, textMessage);
                                     System.out.println("Poslat je zahtev za rezervaciju menadzeru.");
                                     
                                    JMSConsumer consumerRez = contextRez.createConsumer(topicRezReturn,"Prodavac=" + idRad);
               
                                    Message messageRez = consumerRez.receive();
                                    int reservation=messageRez.getIntProperty("reservation");
                                    System.out.println("Vas broj rezervacije je : "  + reservation);
                                    System.out.println("Da li zelite da artikal pokupite u nasoj radnji ili u radnji u kojoj ste rezervisali?"
                                            + "1.U vasoj radnji."
                                            + "2.U radnji u kojoj sam rezervisao.");
                                    int pok=in.nextInt();
                                    in.nextLine();
                                    if(pok==1){
                                        System.out.println("Unesite vase ime:");
                                        String ime=in.nextLine();
                                        System.out.println("Unesite vase prezime:");
                                        String prezime=in.nextLine();
                                        System.out.println("Unesite broj vaseg telefona:");
                                        int telefon=in.nextInt();
                                        et.begin();
                                        ProdRezervacije prodR = em.find(ProdRezervacije.class, reservation);
                                        et.commit();
                                        KupacRezervacija kr=new KupacRezervacija(1,telefon,ime,prezime);
                                        kr.setIdrez(prodR);
                                        et.begin();
                                        em.persist(kr);
                                        et.commit();
                                        System.out.println("Vasi podaci su sacuvani u bazi.Obavesticemo vas kada artikli budu stigli.Dovidjenja.");
                                        
                                    }
                                    else {
                                       System.out.println("Dovidjenja.");
                                    }
                                    
                                     
                            
                         }
                         else System.out.println("Dovidjenja.");
                        }
                    }
                    else{
                        System.out.println("Dovidjenja!");
                    }
               }
               else {
                   et.begin();
               int razlika=kolicina-kol;
               
                Query que=em.createQuery("update ProdArtikal p set p.kolicina=" + razlika 
                        + " where p.idartikal.idartikal=" + idA );
                que.executeUpdate();
                query ="select dp "
               + "from DnevniPromet dp "
               + "where dp.idprodavnica.idprodavnica=" + idprod + " and dp.datum= '" + sqlDate +"'";
       
                
               TypedQuery<DnevniPromet> dp=em.createQuery(query,DnevniPromet.class);
               DnevniPromet dpId=dp.getSingleResult();
               int pomK=dpId.getKolicina();
               int pomI=dpId.getIznos();
               pomK+=kol;
               pomI+=cena*kol;
               dpId.setKolicina(pomK);
               dpId.setIznos(pomI);
               et.commit();
               System.out.println("Iznos vaseg racuna je :" + cena*kol);
               System.out.println("Uspesno izvrsena kupovina artikla.");
                
              }
              break;
           case 5:
               System.out.println("Unesite broj vase rezervacije.");
               int brojR=in.nextInt();
               in.nextLine();
               String upitR="select kr "
                       + "from KupacRezervacija kr "
                       + "where kr.idrez.idprodRezervacije=" + brojR ;
               TypedQuery<KupacRezervacija> kr=em.createQuery(upitR, KupacRezervacija.class);
               et.begin();
               List<KupacRezervacija> listK=kr.getResultList();
               et.commit();
               if(listK.isEmpty() || listK==null) System.out.println("Ne postoji rezervacija sa tim brojem u bazi.");
               else {
                   et.begin();
                   ProdRezervacije pr=em.find(ProdRezervacije.class,brojR);
                   int koliko=pr.getKolicina();
                   Artikal a=pr.getIdArtik();
                   et.commit();
                   et.begin();
                   upitR="select pa "
                           + "from ProdArtikal pa "
                           + "where pa.idprod.idprodavnica = " + idprod +" and pa.idartikal.idartikal = " + a.getIdartikal();
                   TypedQuery<ProdArtikal> upitpa=em.createQuery(upitR, ProdArtikal.class);
                   
                   ProdArtikal pa=upitpa.getSingleResult();
                   et.commit();
                   double sold=koliko*pa.getCena();
                     query ="select dp "
               + "from DnevniPromet dp "
               + "where dp.idprodavnica.idprodavnica=" + idprod + " and dp.datum= '" + sqlDate +"'";
       
                
               TypedQuery<DnevniPromet> dp=em.createQuery(query,DnevniPromet.class);
               et.begin();
               DnevniPromet dpId=dp.getSingleResult();
               int pomK=dpId.getKolicina();
               int pomI=dpId.getIznos();
               pomK+=koliko;
               pomI+=sold;
               dpId.setKolicina(pomK);
               dpId.setIznos(pomI);
               et.commit();
               System.out.println("Iznos vaseg racuna je :" + sold);
               System.out.println("Uspesno izvrsena kupovina artikla.");
               et.begin();
               em.remove(pr);
               et.commit();
               et.begin();
               em.remove(listK.get(0));
               et.commit();
              
               }
               
               
               
       }
           
     } 
       

}
  
}
