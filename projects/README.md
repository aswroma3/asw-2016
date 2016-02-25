# Progetti del corso di Architettura dei Sistemi Software 

Questa sezione del repository contiene il codice (codice sorgente) 
di alcune semplici *applicazioni software distribuite* e basate su *middleware*. 
Ogni sottosezione (sottocartella) è relativa a un argomento delle esercitazioni del corso. 

Attualmente non sono presenti tutti i progetti. 
Verranno aggiunti a questo repository durante lo svolgimento del corso. 

L'esecuzione dei progetti in questa sezione deve essere fatta 
in un opportuno *ambiente di esecuzione*, 
definito nella cartella [environments/](../environments/) del repository. 

## Contenuto dei progetti 

Ciascun **progetto** contiene (come sotto-cartelle) una o più **applicazioni distribuite**. 
Ogni applicazione distribuite contiene uno o più **elementi** 
(per esempio, un *client* e un *server*). 
In generale, ogni applicazione va eseguita in un **ambiente di esecuzione** 
opportuno, e ogni elemento dell'applicazione va eseguito in una  
macchina virtuale opportuna dell'ambiente. 

Queste informazioni sono descritte nell'ambito di ciascun progetto. 

## Build  

La compilazione e assemblaggio (build) delle applicazioni va fatta applicazione per applicazione, 
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

La modalità di esecuzione delle applicazioni distribuite 
varia in modo significativo da progetto a progetto, 
e per questo è descritta solo nell'ambito dei singoli progetti. 

