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
z dokumentacji ;)
https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html


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

Ogólnie poprawne zapytania SQLowe
^SELECT(?:\s+\w+(?:\s*,\s*\w+)*)?\s+FROM\s+\w+(?:\s+JOIN\s+\w+\s+ON\s+\w+\s*=\s*\w+)*(?:\s+WHERE\s+\w+\s*[=<>!]+\s*\w+)?(?:\s+GROUP\s+BY\s+\w+)?(?:\s+ORDER\s+BY\s+\w+(?:\s+ASC|\s+DESC)?)?$

w pliku schemat.drawio jest rozpiska dokładniejsza.

Sprawdzamy czy już mamy taką encję, jeśli tak, to nie dodajemy.


RepositoryGenerator.generateRepositoryImpToFile
//TODO
// najważniejsza część tutaj się odbywa, a potem niżej będzie tylko dzielenie nazwy metody i dodawanie odpowiednich elementów do zapytania.

// Konwertujemy nasz typ na Classe zwykłą, uzykujemy dostęp do adnotacji, z adnotacji bierzemy nazwę tabeli.
// Następnie uzyskujemy dotęp do pól klasy, myClass.getDeclaredFields(); dla każdego pola sprawdzamy czy ma adnotację że jest to pola bazodanowe,
// dla każdego pobieramy nazwę kolumny w bazie danych, i
// Później robimy mapę, nazwa pola (nazwa w klasie tej encji) na odpowiednie nazwy pól w bazie danych. z adnotacji.
// Mając to wszystko możemy przytąpić do samego kompilowania / przetwarzania nazw metod.

Wiemy co ona zwraca, konwencja jest taka:
nazwy pol itp itd wszystko z małej, a nazwy wyróżnione z języka SQL są DUŻYMI,
tak aby można było łatwiej rozdzielać. tak naprawdę można połączyć diwe hashmapy, bo przecież
Nazwy wyróżnione w SQL będą zawsze inne od tych nazw usera.

Taki jest plan.


uyjemy coś w tym tylu:
String query = "SELECT DISTINCT ... WHERE x.lastname = ? AND x.firstname = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);

// Ustawiamy login jako String
preparedStatement.setString(1, login);

// Ustawiamy inne obiekty (np. Integer, Date, itp.)
preparedStatement.setObject(2, someObject);