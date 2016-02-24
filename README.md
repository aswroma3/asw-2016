# Architettura dei Sistemi Software a Roma Tre 

Benvenuti al repository del corso di [Architettura dei Sistemi Software](http://cabibbo.dia.uniroma3.it/asw/) 
a Roma Tre, 
tenuto dal prof. [Luca Cabibbo](http://cabibbo.dia.uniroma3.it/). 

Questo repository contiene il codice delle *esercitazioni* 
del corso di [Architettura dei Sistemi Software](http://cabibbo.dia.uniroma3.it/asw/), 
che sono relative a del semplice *software basato su middleware* e 
da eseguire in un *ambiente distribuito*: 
* il software è normalmente scritto in [Java](http://www.oracle.com/technetwork/java/index.html), 
  e costruito con [Gradle](http://gradle.org/); 
* ogni ambiente di esecuzione distribuito è costituito 
  da alcune macchine virtuali create con 
  [VirtualBox](https://www.virtualbox.org/)
  e [Vagrant](https://www.vagrantup.com/), 
  e accedute tramite [Git](https://git-scm.com/). 

## Software da installare 

### Per lo sviluppo del software 

* [Java SDK](http://www.oracle.com/technetwork/java/javase/) 
* [Java EE](http://www.oracle.com/technetwork/java/javaee/)
* [Gradle](http://gradle.org/) 
* [Git](https://git-scm.com/) 

### Per la gestione dell'ambiente di esecuzione  

* [VirtualBox](https://www.virtualbox.org/)
* [Vagrant](https://www.vagrantup.com/) 
* [Git](https://git-scm.com/) 

## Organizzazione del repository 

Questo repository è organizzato in diverse sezioni (cartelle): 
* **projects** contiene il codice del *software basato su middleware* - 
  con una sottosezione (sottocartella) per ciascuno degli argomenti del corso; 
* **environments** contiene il codice per la gestione degli *ambienti distribuiti* - 
  con una sottosezione (sottocartella) per ciascuno degli ambienti distribuiti 
  su cui poter eseguire il software distribuito sviluppato 

Attualmente non sono presenti tutti i progetti e nemmeno tutti gli ambienti. 
Verranno aggiunti a questo repository durante lo svolgimento del corso. 

Queste due sezioni non sono indipendenti, ma correlate (in modo non banale). 
Per esempio, il progetto **hello-rmi** (in **projects**)
va eseguito nell'ambiente **client-server** (in **environments**). 
