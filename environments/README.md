# Ambienti di esecuzione del corso di Architettura dei Sistemi Software 

Questa sezione del repository contiene il codice (*infrastructure-as-code*) 
di alcuni *ambienti di esecuzione* distribuiti virtuali. 
Ogni sottosezione (sottocartella) è relativa a un diverso ambiente di esecuzione. 

Attualmente non sono presenti tutti gli ambienti. 
Verranno aggiunti a questo repository durante lo svolgimento del corso. 

Questi ambienti di esecuzione possono essere utilizzati per 
eseguire i progetti delle applicazioni distribuite 
definiti nella cartella [projects/](../projects/) del repository. 

## Preparazione 

Per usare delle versioni più recenti del software di sviluppo 
(come *JDK* e *Gradle*) 
è necessario modificare le prime righe dei relativi script di installazione 
(per esempio, **environments/shared/scripts/setup-java.sh** per *JDK*), 
indicando il numero della versione da utilizzare. 

## Utilizzo degli ambienti di esecuzione 

Ogni ambiente di esecuzione è composto da uno o più macchine virtuali, 
collegate in una rete privata. 

Ogni ambiente è rappresentato da una diversa cartella di questa sezione del repository. 
Si veda il file **README.md** di una cartella per la descrizione del relativo ambiente. 

Gli ambienti vengono creati con **Vagrant**, 
e possono essere tutti gestiti allo stesso modo. 

Per gestire un ambiente bisogna: 

1. usare una shell (per esempio, Git) del proprio PC 

2. posizionarsi nella cartella dell'ambiente di interesse 

3. per avviare o creare l'ambiente di esecuzione, usare il comando `vagrant up` 

4. per collegarsi con SSH a una macchina virtuale *VM* dell'ambiente, usare il comando `vagrant ssh VM`
    
E' anche possibile: 

* arrestare l'ambiente di esecuzione, con il comando `vagrant halt`

* distruggere l'ambiente di esecuzione, con il comando `vagrant destroy -f`  

## Ambienti 

* [developer](developer/)
  per la compilazione e l'assemblaggio (build) dei progetti  
  definiti nella cartella [projects/](../projects/) del repository 

* [standalone](standalone/)
  per l'esecuzione di applicazioni Java non distribuite   

* [client-server](client-server/) 
  per l'esecuzione di applicazioni Java distribuite di tipo client-server 

* [java-ee](java-ee/) 
  per l'esecuzione di applicazioni Java EE con l'application server *GlassFish*  
