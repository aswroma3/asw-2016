# Progetti del corso di Architettura dei Sistemi Software 

Questa sezione del repository contiene il codice (codice sorgente) 
di alcune semplici *applicazioni software distribuite* e basate su *middleware*. 
Le diverse applicazioni distribuite sono organizzate in progetti 
(rappresentati da sottocartelle in questa sezione del repository), 
ciascuno dei quali è relativo a un argomento delle esercitazioni del corso. 

Attualmente non sono presenti tutti i progetti e tutte le applicazioni. 
Verranno aggiunti a questo repository durante lo svolgimento del corso. 

L'esecuzione delle applicazioni distribuite deve essere effettuata 
in un opportuno *ambiente di esecuzione*, 
definito nella cartella [environments/](../environments/) del repository. 

## Contenuto dei progetti 

Ciascun **progetto** contiene (come sotto-cartelle) una o più **applicazioni distribuite**. 
Ogni applicazione distribuita è composta da uno o più **moduli**, 
che danno luogo a uno o più **componenti eseguibili** dell'applicazione
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

1. usare una shell del proprio PC 

2. posizionarsi nella cartella dell'applicazione di interesse 

3. per compilare e assemblare l'applicazione, usare il comando `gradle build` 

In alternativa: 

1. collegarsi con `vagrant ssh` ad una macchina virtuale con Java SDK e Gradle 

2. posizionarsi nella cartella dell'applicazione di interesse 

3. per compilare e assemblare l'applicazione, usare il comando `gradle build` 

E' anche possibile: 

* ripulire la build, con il comando `gradle clean`


## Esecuzione 

Il risultato della costruzione di un'applicazione 
è in generale composto da uno o più **componenti eseguibili**, 
che dopo la costruzione sono disponibili nella cartella **dist** dell'applicazione, 
che in particolare conterrà una sotto-cartella per ciascun componente eseguibile dell'applicazione, 
contenente i file *jar* dell'applicazione, eventuali file di configurazione, 
nonché uno *script* per mandare in esecuzione il componente eseguibile. 

In generale, ciascun componente eseguibile va poi mandato in esecuzione 
nell'ambito di una opportuna macchina virtuale di un opportuno ambiente di esecuzione. 

La modalità specifica di esecuzione delle applicazioni distribuite 
varia in modo significativo da progetto a progetto, 
ed è descritta nell'ambito dei singoli progetti. 

## Progetti 

* [asw-000-ciao-mondo](asw-000-ciao-mondo/): un semplice progetto di prova 

* [asw-810-introduzione](asw-810-introduzione/): introduzione ai connettori  

## Libreria 

Questa sezione del repository contiene anche delle librerie usate 
da alcune di queste applicazioni. 

* [asw-libraries](asw-libraries/): il codice sorgente delle librerie

* [libraries](libraries/): i file jar delle librerie  
