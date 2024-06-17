# JDBC (Java Database Connectivity)
## O Que é JDBC

JDBC é similar ao ODBC, e há alguns anos utilizava justamente ODBC para se conectar ao banco de dados. Uma porção de código nativo do Java, era capaz de “conversar” com qualquer banco de dados que tivesse um driver ODBC., assim,  popularizando o JDBC no mercado.
O JDBC utiliza drivers, os quais são responsáveis  pela conexão com o banco, e pela execução das instruções SQL.

### Drivers JDBC

Os drivers JDBC são responsáveis pela conexão e execução das consultas ao mesmo.
No dia-a-dia encontraremos os mesmos em forma de arquivos JAR (Java ARchive) pelo fabricante do banco de dados ou terceiros.
Ex. de Drivers:

[MySQL:](https://www.mysql.com/products/connector/)

[Firebird:](https://firebirdsql.org/en/jdbc-driver/)

[Microsoft SQL Server:](https://docs.microsoft.com/pt-br/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15)

[PostgreSQL:](https://jdbc.postgresql.org/)

### Configurando o banco de dados:

Nesse exemplo utilizaremos o Docker junto a uma Database:
Abaixo está a configuração junto ao IntelliJ:
![image](https://github.com/lschlestein/JDBC/assets/103784532/b2d2e5f0-4cae-47c8-ad02-7d9ccb3f7c4e)




