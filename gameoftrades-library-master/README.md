# Game of Trades Library

Deze bibliotheek bevat het domein model en verschillende utility klassen voor de Game of Trades algoritme leer omgeving.

## Opzet

Dit project is opgezet als een Apache Maven project. De volgende broncode folders worden gebruikt:

* `src/main/java` - bevat de broncode van de bibliotheek. 
* `src/main/resources` - bevat de resources van de bibliotheek. 
* `src/test/java` - bevat de testen voor de bibliotheek.

In de broncode bestanden is een onderverdeling gemaakt in packages:

* `io.gameoftrades.model` - bevat het domein model van de handels wereld. Dit gebruik je in je algoritmen!
* `io.gameoftrades.util` - verschillende utilities.
* `io.gameoftrades.debug` - de debug interface zodat algoritmen zich kunnen visualiseren in de gebruikersinterface. 
* `io.gameoftrades.ui` - de gebruikersinterface voor het visualiseren en testen van de algoritmen.

## Gebruiken

Deze bibliotheek is al onderdeel van de gameoftrades-student-kit. 

Echter mocht je deze in een ander Maven project willen gebruiken dan kan dat met de volgende dependency:

```xml
<dependency>
  <groupId>io.gameoftrades</groupId>
  <artifactId>gameoftrades-library</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Bouwen

Met maven:
```
mvn clean install
```
