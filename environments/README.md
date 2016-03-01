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

Molti ambienti di esecuzione richiedono di scaricare 
il file di installazione di Oracle Java SDK per Linux 
dal sito di Java: 
[www.oracle.com/technetwork/java/javase/](http://www.oracle.com/technetwork/java/javase/downloads/index.html). 
In particolare, bisogna scaricare **JDK** 
per **Linux x64**, 
ovvero il file **jdk-8u73-linux-x64.tar.gz**. 

Questo file va scaricato e copiato nella cartella **environments/shared/resources** 
prima di procedere con le attività successive. 

Se si usa una versione di Java SDK diversa dalla versione 8u73, 
è necessario anche modificare le prime righe dello script 
**environments/shared/scripts/setup-java.sh**, 
indicando il numero della versione che è stata scaricata. 

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
