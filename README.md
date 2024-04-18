Fajna zabawa się zapowiada ;)

Dobra, głowisz się pewnie jak to działa. Już spieszę z wyjaśnieniem.

Po pierwsze, musisz dodać adnotację do swojej klasy, którą chcesz mieć w bazie danych.
Do tego właśnie służy adnotacja **@DatabaseTable**
musisz podać kolumnę z kluczem głównym. 
Możesz chcieć posiadać jakieś pola w tej klasie tylko na potrzeby programu. 
Nie wszystkie chcesz zapisywać - proszę bardzo. Program będize brał pod uwagę tylko te pola
z adnotacją **@DatabaseField** gdzie musisz podać nazwę kolumny w bazie danych jak i możesz podać typ kolumny.

Jak na razie, trzeba przekazywać ręcznie Klasy dla których chcemy utwożyć encje w bazie danych.
Sprawdzamy, czy faktycznie klasa ma adnotację, jeśli tak,
sprawdzamy czy mamy już encję w naszym programie, jeśli tak, to po prostu tworzymy,
jeśli nie mieliśmy, najpierw tworzymy naszą Encję w programie. (addEntity)

Dla konkretnej klasy tworzymy obiekt EntityProperties, który przechowuje wszystkie parametry "bazodanowe" reprezentacji
tej klasy w bazie danych.

Jak na razie to tyle ;)


Konwencja nazewnicza:


|       SQL       |   Code   |
|:---------------:|:--------:|
|     SELECT      |   find   |
|     INSERT      |   add    |
| SELECT COUNT(*) |  count   |
|    DISTINCT     |  unique  |
|   DISTINCT ON   | uniqueOn |
|      JOIN       |   join   |
|   SELECT x.y    | find(Y)  |
|  WHERE x.y = ?  |   By?    |
|     INSERT      |   add    |

Sprawdzamy czy już mamy taką encję, jeśli tak, to nie dodajemy.
