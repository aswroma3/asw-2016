# asw-850-ejb

Questo progetto contiene alcune applicazioni distribuite 
formate da componenti **EJB** e dai relativi client: 

* **0-preparazione-server** 
  non è un'applicazione come le altre, ma contiene degli script 
  di preparazione del server Java EE *GlassFish*  

* **a-hello**
  un semplice *session bean* di tipo *stateless* **Hello** per saluti personalizzati, 
  con il relativo *application client* 

* **b-calculator** 
  un semplice *session bean* di tipo *stateless* **Calculator** 
  che implementa alcune operazioni matematiche, 
  con il relativo *application client* 

* **c-session-counter** 
  un semplice *session bean* di tipo *stateful* **SessionCounter** 
  per un contatore di sessione, 
  con il relativo *application client*   

* **d-stateless-counter** 
  variante *stateless* di un **Counter**, 
  con il relativo *application client*, 
  mostrato solo per confrontare la semantica dei session bean 
  *stateless* e *stateful*

* **e-asynchronous**
  un semplice *session bean* di tipo *stateless* con un'*operazione asincrona*, 
  con il relativo *application client* 
  
* **f-message-filter**
  un semplice *message-driven bean* che realizza un filtro, 
  che legge messaggi da una destinazione JMS e scrive messaggi 
  in un'altra destinazione JMS 
  
Le diverse applicazioni hanno una struttura simile, 
e la loro costruzione ed esecuzione è descritta qui di seguito. 

## Build  

Per la costruzione di ciascuna di queste applicazioni 
(tranne **0-preparazione-server**, che non richiede costruzione), 
vedere le istruzioni descritte nella sezione [projects/](../).

## Componenti eseguibili 

Ciascuna applicazione/progetto 
è composta da un componente *EJB* 
(che dopo la costruzione è presente 
nella cartella **dist/server** dell'applicazione) 
e da un componente eseguibile di tipo *appclient* 
(che dopo la costruzione è presente 
nella cartella **dist/client** dell'applicazione). 

## Ambiente di esecuzione 

Ciascuna applicazione va eseguita nell'ambiente 
[java-ee](../../environments/java-ee/). 
Gli script di **0-preparazione-server** vanno eseguiti sul nodo **server**. 
I componenti *EJB* vanno prima rilasciati sul nodo **server**. 
Gli *application client* vanno eseguiti sul nodo **client**.


## Preparazione ed Esecuzione 

La modalità di preparazione ed esecuzione delle diverse applicazioni è simile, 
ma ci sono anche delle differenze, 
per cui ciascuna è descritta separatamente. 

### Applicazioni **Hello**, **Calculator**, **SessionCounter**, **StatelessCounter** e **AsynchronousHello** 

Per eseguire un'applicazione, 
preliminarmente è necessario rilasciare il componente *EJB* dell'applicazione 
nell'application server *GlassFish*: 

* sul nodo **server**, nella cartella dell'applicazione  

   a. lo script `dist/server/deploy-ejb.sh` 
      rilascia il componente EJB dell'applicazione (insieme alle sue librerie) sull'application server *GlassFish*

   b. inoltre, lo script `dist/server/undeploy-ejb.sh` 
      rimuove il componente EJB (insieme alle sue librerie) dall'application server *GlassFish*

Per eseguire l'*application client* dell'applicazione: 

* sul nodo **client**, nella cartella dell'applicazione   

   a. eseguire lo script `dist/client/run-appclient.sh` 


### Applicazione **MessageFilter**

Per eseguire questa applicazione, 
preliminarmente è necessario configurare alcune *risorse JMS* 
e poi rilasciare il componente *EJB* dell'applicazione 
nell'application server *GlassFish*: 

* sul nodo **server**, nella cartella dell'applicazione  

   a. lo script `create-jms-resources.sh` consente di creare le risorse *JMS* necessarie alle diverse applicazioni 
      (questo script va eseguito una sola volta, e comunque prima di eseguire le applicazioni di questo progetto) 

   b. lo script `dist/server/deploy-ejb.sh` 
      rilascia il componente EJB dell'applicazione (insieme alle sue librerie) sull'application server *GlassFish*

   c. inoltre, lo script `dist/server/undeploy-ejb.sh` 
      rimuove il componente EJB (insieme alle sue librerie) dall'application server *GlassFish*

   d. infine. lo script `delete-jms-resources.sh` consente di cancellare le risorse *JMS*, se non sono più necessarie  

Per eseguire l'applicazione: 

* su un nodo **client**, nella cartella dell'applicazione

   a. eseguire lo script `dist/client/run-consumer.sh`, 
      che avvia un consumatore asincrono sul canale destinazione 
      dei messaggi filtrati 

* su un altro nodo **client**, nella cartella dell'applicazione

   a. eseguire lo script `dist/client/run-producer.sh`, 
      che avvia un produttore di messaggi sul canale destinazione 
      dei messaggi da filtrare 

I messaggi generati dal *produttore* verranno ricevuti dal *message-driven bean*, 
filtrati e trasmessi in un'altra coda, e da qui letti dal *consumatore* di messaggi. 


