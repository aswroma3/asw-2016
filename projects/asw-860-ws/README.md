# asw-860-ws

Questo progetto contiene alcune applicazioni distribuite 
formate da servizi web e client **SOAP**: 

* **0-preparazione-server** 
  non è un'applicazione come le altre, ma contiene degli script 
  di preparazione del server Java EE *GlassFish*  

* **a1-calculator-service**
  un semplice servizio web SOAP **CalculatorService** per operazioni matematiche

* **a2-calculator-client** 
  un client *Java* per il servizio web **CalculatorService** 
  (da eseguire come *application client*)

* **a3-calculator-client-unmanaged** 
  un client *Java* per il servizio web **CalculatorService** 
  (da eseguire come semplice *applicazione Java*)
  
* **b-temperature-converter-client**
  un client *Java* per il servizio web 
  [TempConvert](http://www.w3schools.com/xml/tempconvert.asmx)
  
Le diverse applicazioni hanno una struttura simile, 
e la loro costruzione ed esecuzione è descritta qui di seguito. 

## Build  

Per la costruzione di ciascuna di queste applicazioni 
(tranne **0-preparazione-server**, 
che non richiede costruzione), 
vedere le istruzioni descritte nella sezione [projects/](../). 

In particolare, per la costruzione delle applicazioni client 
**a2-calculator-client** e **a3-calculator-client-unmanaged**, 
è necessario che il servizio web **a1-calculator-service** 
sia stato *prima* costruito e rilasciato sull'application server GlassFish. 


## Componenti eseguibili 

Le applicazioni per i servizi 
(**a1-calculator-service**) 
hanno un componente è che un servizio web *SOAP* 
che va rilasciato in un application server *Java EE*, 
che dopo la costruzione è presente 
nella cartella **dist/server** dell'applicazione. 

Il client Java **a2-calculator-client** per il servizio **CalculatorService** 
ha un componente che + un application client Java EE, 
che dopo la costruzione è presente 
nella cartella **dist/client** dell'applicazione. 

I client Java **a3-calculator-client-unmanaged** per il servizio **CalculatorService** 
e **b-temperature-converter-client** per il servizio **TempCovert** 
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

La modalità di preparazione ed esecuzione è simile nei diversi casi, 
ma ci sono anche delle differenze, 
per cui ciascuna èd descritta separatamente. 

### Servizio **CalculatorService** 

Preliminarmente è necessario rilasciare il servizio nell'application server *GlassFish*: 

* sul nodo **server**, nella cartella **a1-calculator-service** 

   a. lo script `dist/server/deploy-ws.sh` 
      rilascia il servizio web sull'application server *GlassFish*

   b. inoltre, lo script `dist/server/undeploy-ws.sh` 
      rimuove il servizio web dall'application server *GlassFish*

Per l'esecuzione del client per il servizio: 

* sul nodo **client**  

   a. nella cartella **a2-calculator-client**, 
      lo script `dist/client/run-ws-client.sh` avvia un application client Java del servizio 

   b. nella cartella **a3-calculator-client-unmanaged**, 
      lo script `dist/client/run-ws-client.sh` avvia un client Java del servizio 

### Servizio **TempConvert** 

Questo servizio non va rilasciato, poiché è un servizio web esterno. 

Per l'esecuzione del client per il servizio: 

* sul nodo **client**  

   a. nella cartella **b-temperature-converter-client**, 
      lo script `dist/client/run-ws-client.sh` avvia un application client Java del servizio 

