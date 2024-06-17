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
Pode ser utilizado outra base de dados, como postgres, só mudará a conexão junto a nossa aplicação.
Docker:
```bash
docker run -d --name mysqlContainer -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=dockerDatabase -e MYSQL_USER=admin -e MYSQL_PASSWORD=root mysql

```
Abaixo está a configuração junto ao IntelliJ:
![image](https://github.com/lschlestein/JDBC/assets/103784532/b2d2e5f0-4cae-47c8-ad02-7d9ccb3f7c4e)

### Iniciando um Projeto Java com JDBC

Iremos criar um novo projeto Java no IntelliJ e configurar o arquivo pom.xml da seguinte forma:
Basicamente precisamos informar o driver que utilizaremos para trocar informações com a nossa base de dados. Nesse caso inserimos o driver do MySql em nossa aplicação.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>Jdbc</groupId>
    <artifactId>JavaJdbc</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
</project>
```
Logo em seguida iremos configurar uma Connection para nos conectarmos a nossa base de dados:

```Java
String url = "jdbc:mysql://localhost:3306/COMPANY";
String user = "root";
String pass = "root";
try (Connection con = DriverManager.getConnection(url, user, pass);) {
         System.out.println("Connected");
} catch (SQLException e) {
         System.out.println("Não foi possível conectar: "+e.getMessage());
}
```



