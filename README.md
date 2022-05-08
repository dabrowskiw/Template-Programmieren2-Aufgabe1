# Aufgabe zu Woche 3

In dieser Woche beginnen wir mit der Implementation eines Tools zur personalisierten HIV-Medizin.

Damit verständlich ist, was eigentlich mit diesem Projekt bezweckt wird, beginnen wir mit einer (sehr vereinfachten - das Modul heißt ja Programmierung und nicht Virologie) Beschreibung des Problems. Eine grundlegende Einarbeitung in eine neue Fachlichkeit ist grundsätzlich neben der Programmierung auch ein wichtiger Bestandteil der Arbeit in der Informatik: Nur, wenn man die Fragestellung und die Sprache der späteren Anwender_innen kennt, ist man in der Lage, nützliche Software zu schreiben. Wo immer Sie mal arbeiten werden: Wenn Sie Ihren Job gut machen wollen, werden Sie nicht drum herum kommen, auch ein wenig Expertise in dem Gebiet zu werden, für welches Sie Software entwickeln.  


## Fachlicher Hintergrund

Das Humane Immundefizienz-Virus (HIV) ist ein RNA-Virus, welches sich im Körper von Infizierten rasch vermehrt: Das Virus kapert den Stoffwechsel der infizierten Zelle und missbraucht ihn, um viele Kopien von sich selber zu erstellen. Ist dieser Vorgang abgeschlossen, wird die infizierte Zelle zerstört. Dadurch werden die neuen Viruspartikel freigesetzt und können wieder neue Zellen infizieren.

Bei diesem Prozess mutiert das Virus schnell: Beim Erzeugen der Viruskopien wird auch das Genom des ursprünglichen Viruspartikels kopiert, wobei allerdings Fehler passieren. Somit unterscheiden sich die Genome der neu erzeugten Viren von dem Genom des ursprünglichen Virus - es entstehen mutierte Virusvarianten. 

Medikamente gegen das Virus verabreicht, verlangsamen oder verhindern diesen Prozess. Es können allerdings mutierte Varianten auftauchen, deren Vermehrungsmechanismus durch die Mutation so verändert ist, dass das Medikament nicht mehr wirkt.

Da die heutigen Medikamente die Vermehrung des Virus extrem verlangsamen, ist die Wahrscheinlichkeit, dass eine Mutation nach Einsetzen der Behandlung auftaucht, recht gering. Es sollte allerdings unbedingt vermieden werden, ein Medikament zu verabreichen, falls bereits gegen dieses Medikament resistente Mutationen im Körper vorhanden sind - in diesem Fall würden einfach diese Varianten sich ungehindert weiter vermehren und die Behandlung würde fehlschlagen.

Entsprechend werden vor der Behandlung Untersuchungen durchgeführt, bei denen für eine große Anzahl an aus dem Blut isolierten Viruspartikeln die Genomsequenzen ermittelt werden. Diese werden dann mit Listen von bekannten Mutationen abgeglichen, um zu ermitteln, gegen welche möglichen Medikamente Mutationen bereits vorliegen.

Wichtig ist dabei zu beachten, dass das HIV-Genom drei Proteine codiert, gegen deren Funktionen Medikamente verabreicht werden: Die Protease (P), die Integrase (IN) und die Reverse Transkriptase (RT). Entsprechend gibt es die Medikamentgruppen Protease-Inhibitoren (PI), der Integrase-Inhibitoren (INI) und der Reverse Transkriptase-Inhibitoren (mit zwei Untertypen: NRTI und NNRTI).

## Ausgangsdaten

In diesem Projekt verwenden Sie Daten der [HIV drug resistance database der Stanford University](https://hivdb.stanford.edu/) sowie simulierte Patientendaten.

### Referenz-Sequenz und FASTA-Format

Um die bekannten Mutationen des HIV sowie ihre Auswirkung darauf, wie gut Medikamente funktionieren, zu beschreiben, werden diese als Abweichungen von einer sogenannten Referenzsequenz beschrieben. Hierbei handelt es sich um die Abfolge von Aminosäuren in dem Protein (ein Protein ist im Endeffekt nur eine Kette von Aminosäuren genannten Bausteinen), welches von dem betroffenen Genomabschnitt kodiert wird. Der Einfachheit halber überspringen wir hier die dreckigen Details der Übersetzung von Genomschnipseln zu Aminosäuresequenzen und arbeiten direkt mit der letzteren.

Aminosäuresequenzen werden in sogenannten FASTA-Dateien gespeichert. Diese sind wie folgt aufgebaut:

```text
> Sequenzname 1
PQVTLWRRPIV
LLDTYADDTVL
...
> Sequenzname 2
TIKIGGQLKEA
EEMSLPGKWKP
...
...
> Sequenzname n
SEQUENZ_N
```

Jede Sequenz beginnt mit einem Header: Einer Zeile, deren erstes Zeichen ein ```>``` ist und von dem Sequenznamen gefolgt ist. Danach kommen beliebig viele (aber mindestens eine) Zeilen mit der Aminosäuresequenz, wobei jeder Buchstabe eine der 20 möglichen Aminosäuren entsprechend des [IUPAC Amino Acid Code](http://bioinformatics.org/sms2/iupac.html) darstellt.

Es können in einer FASTA-Datei beliebig viele Sequenzen hintereinander vorkommen. Wichtig ist zu beachten, dass es keine Kommentarzeichen o.Ä. gibt - jede Zeile ist entweder ein Sequenzname oder eine Sequenz.

Die Referenzsequenz für Protease (das Zielprotein für Medikamente aus der Klasse der Proteaseinhibitoren, kurz PI) ist unter [data/protease_reference.fasta](data/protease_reference.fasta) zu finden.

### Mutationsmuster

Mutationsmuster werden als Abweichung(en) von der Referenzsequenz beschrieben. Dabei wird die Position in der Referenzsequenz sowie die dort zu findende Aminosäure angegeben. Lautet die Referenzsequenz beispielsweise:

```PQVTLWQRPI```

so ergibt sich beispielsweise aus dem Mutationsmuster ```3P``` (an der dritten Position ist abweichend von der Referenzsequenz die Aminosäure P, also Prolin) die Sequenz:

```PQPTLWQRPI```

Ein Mutationsmuster kann auch mehrere Mutationen enthalten. Bezogen auf die oben genannte Referenzsequenz würde beispielsweise das Mutationsmuster ```2L,5Q``` die folgende Sequenz beschreiben: 

```PLVTQWQRPI```

Es können die Effekte unterschiedlicher Mutationsmuster auf die Medikamentenresistenz von Viren untersucht werden. Die Ergebnisse dieser Untersuchungen sind in Tabellen wie ["PI Mutation Pattern & Susceptibility"](https://hivdb.stanford.edu/pages/phenoSummary/Pheno.PI.Simple.html) (als csv zu finden unter [data/HIVMutationPatternsPI.csv](data/HIVMutationPatternsPI.csv)) zusammengefasst. Dabei beschreibt jede Zeile ein Mutationsmuster. In der ersten Spalte ist das Mutationsmuster selber genannt, in der zweiten Spalte die Anzahl der untersuchten Sequenzen mit diesem Mutationsmuster, und die folgenden Spalten beschreiben den Einfluss des Mutationsmusters auf die Resistenz gegen unterschiedliche Medikamente. Dabei wird jeweils angegeben, um welchen Faktor sich die Resistenz verändert. Beispielsweise kann man aus dem folgenden Ausschnitt der o.g. Tabelle:

![Mutationen](Bilder/mutationpatterns.png)

ablesen, dass die Mutation ```30N``` zu einer 40-fachen Erhöhung der Resistenz gegen das Medikament NFV führt, aber dafür die Empfindlichkeit gegen das Medikament FPV ein wenig erhöht (FPV foldn=0.8).

### Simulierte Patientendaten

In der Datei [data/protease_sequences.fasta](data/protease_sequences.fasta) sind simulierte Sequenzen von Viren aus dem Blut eines Patienten im FASTA-Format abgespeichert. Diese sind in dieser Woche noch nicht relevant. Perspektivisch sollen in diesem Projekt diese Sequenzen mit den Mutationsmustern abgeglichen werden, um zu ermitteln, welches Medikament (bzw. welche Medikamentenkombination) in diesem konkreten Fall am besten verwendet werden sollte.

## Programmieraufgabe

In dieser Aufgabe legen Sie den Grundstein für ein Tool, welches die oben genannten Daten analysiert und ein Medikament (oder eine Medikamentenkombination) vorschlägt. In der ersten Ausbaustufe bauen wir ein Tool, das sich nur eine Medikamentenklasse anschaut (also nur von einem Protein die Sequenzen anschaut), später erweitern wir die Funktionalität dann auf mehrere Medikamentenklassen/Referenzsequenzen.

### FASTA-Datei parsen

Implementieren Sie eine Klasse SeqFile, die sich um das Einlesen und die Verwaltung von FASTA-Dateien kümmert. Diese sollte folgende Methoden haben:

* ```public SeqFile(String filename)```: Liest die FASTA-Datei ein.
  ```public boolean isValid()```: Gibt ```true``` zurück, wenn die FASTA-Datei erfolgreich eingelesen wurde, sonst (z.B. weil die Datei nicht existiert oder das Format falsch ist) ```false``` 
* ```public int getNumberOfSequences()```: Gibt die Anzahl der eingelesenen Sequenzen zurück (0, falls die Datei nicht eingelesen werden konnte)
* ```public HashSet<String> getSequences()```: Gibt die Sequenzen als HashSet zurück (leers HashSet, falls die Datei nicht eingelesen werden konnte)
* ```public String getFirstSequence()```: Utility-Methode, um einfach an die erste (im Fall der Referenzsequenz: einzige) Sequenz zu kommen. Gibt die erste Sequenz zurück (leerer String, falls die Datei nicht eingelesen werden konnte)

### Optionen parsen

Implementieren Sie die Methode ```public static CommandLine parseOptions(String[] args)``` in der Klasse ```HIVDiagnostics```. Dabei sollen die folgenden Optionen unterstützt werden:

* -m, --mutationfiles: Pfad zu CSV-Datei mit Mutationspattern. Muss angegeben werden.
* -d, --drugnames: Name des Medikaments. Muss angegeben werden.
* -r, --references: Pfad zu FASTA-Datei mit der Referenzsequenz. Muss angegeben werden.
* -p, --patientseqs: Pfad zu FASTA-Datei mit Patientensequenzen. Muss angegeben werden.

Die Methode soll mittels eines HelpFormatters eine Fehlermeldung aus- und ```null``` zurückgeben, falls eine der erforderlichen Optionen nicht übergeben wurde.
