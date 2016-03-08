# asw-820-socket

Questo progetto contiene alcune applicazioni distribuite 
di tipo **client-server**
che esemplificano la comunicazione interprocesso 
basata su **socket**: 

* **a-client-server-udp** 
  una semplice applicazione client-server, 
  con un server *sequenziale* e *stateless* basato su *UDP* 

* **b-client-server-tcp**
  una semplice applicazione client-server, 
  con un server *concorrente* e *stateless* basato su *TCP* 

* **c-stateful-service** 
  una semplice applicazione client-server, 
  con un server *concorrente* e *stateful* basato su *TCP* 

Le diverse applicazioni hanno una struttura simile, 
e la loro costruzione ed esecuzione è descritta qui di seguito. 

## Build  

Per la costruzione di ciascuna di queste applicazioni, 
vedere le istruzioni descritte nella sezione [projects/](../). 

## Componenti eseguibili 

Ciascuna applicazione è composta da due componenti eseguibili (**server** e **client**) 
che dopo la costruzione sono presenti 
nelle cartelle **dist/server** e **dist/client** dell'applicazione. 

## Ambiente di esecuzione 

Ciascuna applicazione va eseguita nell'ambiente 
[client-server](../../environments/client-server/): 
il componente **server** sul nodo **server** e
il componente **client** sul nodo **client**.


## Esecuzione 

Per eseguire un'applicazione si proceda in questo modo: 

1. sul nodo **server** 

   a. posizionarsi nella cartella principale dell'applicazione 

   b. eseguire lo script `dist/server/run-server.sh` 
   
   c. il server può essere arrestato con CTRL-C 

2. sul nodo **client** 

   a. posizionarsi nella cartella principale dell'applicazione 

   b. eseguire lo script `dist/client/run-client.sh` 

