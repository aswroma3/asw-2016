# Progetti del corso di Architettura dei Sistemi Software 

Questa sezione del repository contiene il codice (codice sorgente) 
di alcune semplici *applicazioni software distribuite* e basate su *middleware*. 
Le diverse applicazioni distribuite sono organizzate in progetti 
(rappresentati da sottocartelle in questa sezione del repository), 
ciascuno dei quali � relativo a un argomento delle esercitazioni del corso. 

Attualmente non sono presenti tutti i progetti e tutte le applicazioni. 
Verranno aggiunti a questo repository durante lo svolgimento del corso. 

L'esecuzione delle applicazioni distribuite deve essere effettuata 
in un opportuno *ambiente di esecuzione*, 
definito nella cartella [environments/](../environments/) del repository. 

## Contenuto dei progetti 

Ciascun **progetto** contiene (come sotto-cartelle) una o pi� **applicazioni distribuite**. 
Ogni applicazione distribuita � composta da uno o pi� **moduli**, 
che danno luogo a uno o pi� **componenti eseguibili** dell'applicazione
(per esempio, un *client* e un *server*). 
In generale, ogni applicazione va eseguita in un **ambiente di esecuzione** opportuno, 
e in particolare ogni componente eseguibile dell'applicazione 
va eseguito in una macchina virtuale opportuna dell'ambiente. 

Queste informazioni sono descritte nell'ambito di ciascun progetto. 

## Build  

La costruzione (build, ovvero compilazione e assemblaggio) delle applicazioni 
va fatta applicazione per applicazione, 
utilizzando **Gradle**. 

Per compilare un'applicazione bisogna: 

1. collegarsi con `vagrant ssh` alla macchina virtuale **dev** 
   dell'ambiente [developer](../environments/developer/), su cui sono installati *Java SDK* e *Gradle* 

2. posizionarsi nella cartella principale dell'applicazione di interesse 

3. per compilare e assemblare l'applicazione, usare il comando `gradle build` 

In alternativa, � possibile compilare un'applicazione sul proprio PC 

1. installare e configurare *Java SDK* 

2. installare e configurare *Gradle* 

3. usare una shell del proprio PC 

4. posizionarsi nella cartella principale dell'applicazione di interesse 

5. per compilare e assemblare l'applicazione, usare il comando `gradle build` 

E' anche possibile: 

* ripulire la build di un'applicazione, con il comando `gradle clean`


## Esecuzione 

Il risultato della costruzione di un'applicazione 
� in generale composto da uno o pi� **componenti eseguibili**, 
che dopo la costruzione sono disponibili nella cartella **dist** dell'applicazione, 
che in particolare conterr� una sotto-cartella per ciascun componente eseguibile dell'applicazione, 
contenente i file *jar* dell'applicazione, eventuali file di configurazione, 
nonch� uno *script* per mandare in esecuzione il componente eseguibile. 

In generale, ciascun componente eseguibile va poi mandato in esecuzione 
nell'ambito di una opportuna macchina virtuale di un opportuno ambiente di esecuzione. 

La modalit� specifica di esecuzione delle applicazioni distribuite 
varia in modo significativo da progetto a progetto, 
ed � descritta nell'ambito dei singoli progetti. 

## Progetti 

* [asw-000-ciao-mondo](asw-000-ciao-mondo/): un semplice progetto di prova 

* [asw-810-introduzione](asw-810-introduzione/): introduzione ai connettori  

* [asw-820-socket](asw-820-socket/): comunicazione interprocesso e socket  

* [asw-830-rmi](asw-830-rmi/): oggetti distribuiti e invocazione remota 

* [asw-840-jms](asw-840-jms/): messaging con *JMS* 

* [asw-850-ejb](asw-850-ejb/): componenti *EJB* 

* [asw-860-ws](asw-860-ws/): servizi web *SOAP* 

* [asw-870-rest](asw-870-rest/): servizi *REST* 

## Libreria 

Questa sezione del repository contiene anche delle librerie usate 
da alcune di queste applicazioni. 

* [asw-libraries](asw-libraries/): il codice sorgente delle librerie

* [libraries](libraries/): i file jar delle librerie  
