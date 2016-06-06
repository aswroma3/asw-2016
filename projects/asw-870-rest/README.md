# asw-870-rest

Questo progetto contiene alcune applicazioni distribuite 
formate da servizi e client **REST**: 

* **0-preparazione-server** 
  non è un'applicazione come le altre, ma contiene degli script 
  di preparazione del server Java EE *GlassFish*  

* **a1-hello-service**
  un semplice servizio REST **Hello** per saluti personalizzati  

* **a2-hello-client-curl** 
  un client *bash*/*CURL* per il servizio REST **Hello** 

* **a3-hello-client-java** 
  un client Java per il servizio REST **Hello** 
  
* **b1-async-service**
  un servizio REST *asincrono*  

* **b2-async-client** 
  un client Java per il servizio REST *asincrono* 
  
* **c1-product-manager-service** 
  un servizio REST per la *gestione di prodotti* 

* **c2-product-manager-client** 
  un client Java per il servizio REST per la *gestione di prodotti* 

Le diverse applicazioni hanno una struttura simile, 
e la loro costruzione ed esecuzione è descritta qui di seguito. 

## Build  

Per la costruzione di ciascuna di queste applicazioni 
(tranne **0-preparazione-server** e **a2-hello-client-curl**, 
che non richiedono costruzione), 
vedere le istruzioni descritte nella sezione [projects/](../). 


## Componenti eseguibili 

Le applicazioni per i servizi 
(**a1-hello-service**, **b1-async-service** e **c1-product-manager-service**) 
hanno un componente è che un servizio *REST* 
che va rilasciato in un application server *Java EE*, 
che dopo la costruzione è presente 
nella cartella **dist/server** dell'applicazione. 

I client Java per i servizi 
(**a3-hello-client-java**, **b2-async-client** e **c1-product-manager-service**) 
hanno un componente è che un'applicazione Java, 
che dopo la costruzione è presente 
nella cartella **dist/client** dell'applicazione. 


## Ambiente di esecuzione 

Ciascuna applicazione va eseguita nell'ambiente 
[java-ee](../../environments/java-ee/). 
Gli script di **0-preparazione-server** vanno eseguiti sul nodo **server**. 
Le applicazioni per i servizi vanno rilasciate sul nodo **server**. 
I client per i servizi vanno eseguiti sul nodo **client**.


## Preparazione ed Esecuzione 

La modalità di preparazione ed esecuzione dei tre servizi è simile, 
ma ci sono anche delle differenze, 
per cui ciascuna èd descritta separatamente. 

### Servizio **Hello** 

Preliminarmente è necessario rilasciare il servizio nell'application server *GlassFish*: 

* sul nodo **server**, nella cartella **a1-hello-service** 

   a. lo script `dist/server/deploy-rest-service.sh` 
      rilascia il servizio sull'application server *GlassFish*

   b. inoltre, lo script `dist/server/undeploy-rest-service.sh` 
      rimuove il servizio dall'application server *GlassFish*

Per l'esecuzione del client per il servizio: 

* sul nodo **client**  

   a. nella cartella **a2-hello-client-curl**, 
      lo script `run-curl-client.sh` è un client CURL del servizio 

   b. nella cartella **a3-hello-client-java**, 
      lo script `dist/client/run-rest-client.sh` avvia un client Java del servizio 


### Servizio *asincrono*

Simile al servizio **Hello**. 


### Servizio per la gestione dei prodotti 

Preliminarmente è necessario rilasciare il servizio nell'application server *GlassFish*: 

* sul nodo **server**, nella cartella **c1-product-manager-service** 

   a. lo script `dist/server/start-java-db.sh` 
      avvia il DBMS Java DB (necessario per l'esecuzione del servizio)

   b. lo script `dist/server/deploy-rest-service.sh` 
      crea la base di dati e rilascia il servizio sull'application server *GlassFish*

   c. inoltre, lo script `dist/server/undeploy-rest-service.sh` 
      rimuove il servizio dall'application server *GlassFish* e cancella la relativa base di dati 

   d. lo script `dist/server/stop-java-db.sh` 
      arresta il DBMS Java DB 

Per l'esecuzione del client per il servizio: 

* sul nodo **client**, nella cartella **c2-product-manager-client**: 

   a. lo script `dist/client/run-rest-client.sh` avvia un client Java del servizio 
      che effettua un accesso in sola lettura al servizio 
      
   b. invece, `dist/client/run-rest-client.sh update` avvia un client Java del servizio 
      che esegue anche degli inserimenti, degli aggiornamenti e delle cancellazioni 
      dal catalogo dei prodotti  
      
