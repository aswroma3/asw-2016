# asw-810-introduzione

Questo progetto contiene alcune semplici applicazioni 
per introdurre l'uso dei **connettori**: 

* **a-chiamata-locale**

* **b-factory**

* **c-iniezione-delle-dipendenze** 

* **d-application-context** 

* **e-spring** 

* **f-proxy**

* **g-client-server** 

Solo l'ultima applicazione è un'applicazione distribuita. 
Tutte le altre sono applicazioni non distribuite. 
Ne descriviamo l'uso separatamente 

## Applicazioni non distribuite (tutte tranne **g-client-server**) 

### Build  

Per la costruzione di ciascuna applicazione, vedere le istruzioni 
descritte nella sezione [projects/](../). 

### Componenti eseguibili 

Ciascuna di queste applicazioni è composta da un unico componente eseguibile, 
che dopo la costruzione è presente nella cartella **dist** dell'applicazione. 

### Ambiente di esecuzione 

Ciascuna di queste applicazioni può essere eseguita nell'ambiente 
[standalone](../../environments/standalone/), 
sul nodo **node**. 

### Esecuzione 

Per eseguire un'applicazione: 

1. posizionarsi nella cartella **dist** dell'applicazione 

2. eseguire lo script `../run-app.sh` 

In alternativa: 

1. posizionarsi nella cartella principale dell'applicazione 

2. usare il comando `gradle run` 


## L'applicazione distribuita **g-client-server** 

### Build  

Per la costruzione dell'applicazione, vedere le istruzioni 
descritta nella sezione [projects/](../). 

### Componenti eseguibili 

Questa applicazione è composta da due componenti eseguibili (**server** e **client**) 
che dopo la costruzione sono presenti 
nelle cartelle **dist/server** e **dist/client** dell'applicazione. 

### Ambiente di esecuzione 

Questa applicazione va eseguita nell'ambiente 
[client-server](../../environments/client-server/): 
il componente **server** sul nodo **server** e
il componente **client** sul nodo **client**.


### Esecuzione 

Per eseguire questa applicazione si proceda in questo modo: 

1. sul nodo **server** 

   a. posizionarsi nella cartella principale dell'applicazione 

   b. eseguire lo script `dist/server/run-server.sh` 
   
   c. il server può essere arrestato con CTRL-C 

2. sul nodo **client** 

   a. posizionarsi nella cartella principale dell'applicazione 

   b. eseguire lo script `dist/client/run-client.sh` 

