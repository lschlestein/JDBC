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
É importante que o nome de nossa Database rodando seja no Docker, ou configurada em nossa próspria máquina, seja igual, a Database utilizada em nossa aplicação Java.
Utilizaremos a seguinte tabela nesse exemplo:
``` sql
create table User
(
    UserID int auto_increment
        primary key,
    Name   varchar(100) null,
    Email  varchar(100) null
);
```

Se desejado podemos fazer algumas inserções na mesma;

```sql
INSERT INTO User(Name, Email) VALUES ('John Doe', 'doe@mail.com');
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
### Interfaces necessárias para manipular nossa base de dados:
![image](https://github.com/lschlestein/JDBC/assets/103784532/67c2de15-0a5d-4fc6-9ce1-a239b2342365)

Como visto acima, precisaremos de uma Connection, um Statement ou Prepared Statement, e em caso de buscas de dados, um ResultSet.
Esses componentes são interfaces que nos possibilitam acesso a nossa base de dados conforme veremos a seguir:

## Conexão
A primeira coisa a fazer é nos conectarmos a nossa base de dados utilizando a interface Connection:

```Java
String url = "jdbc:mysql://localhost:3306/COMPANY";
String user = "root";
String pass = "root";
try (Connection con = DriverManager.getConnection(url, user, pass)) {
         System.out.println("Connected");
} catch (SQLException e) {
         System.out.println("Não foi possível conectar: "+e.getMessage());
}
```
## Recuperando dados com JDBC
Já conectados, agora iremos buscar dados em nossa base de dados, utilizado um PreparedStatement e um ResultSet

```Java
PreparedStatement ps = con.prepareStatement("select * from User");
ResultSet rs = ps.executeQuery();
```
Aqui passamos qual a query SQL, que será executada em nossa database, e o resultado obtido ficara disponível na ResultSet.
```Java
while (rs.next()) {
         System.out.println(rs.getInt("UserID") + " " + rs.getString("Name") + " " + rs.getString("Email"));
}
```
Os métodos getInt ou getString recuperam os dados do ResultSet, já convertendo o mesmo para o tipo desejado, conforme o método chamado. O parâmetro passado é o nome da coluna contida no ResultSet.

É possíve também, passar o número da coluna, mas não recomendado a adição dessa prática pois, caso a ordem, ou o número de colunas de uma tabela mude, nossa aplicação pode passar a operar de forma indesejada.``
```java
while (rs.next()) {
         System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
}
```

Agora podemos criar uma entidade User em nossa aplicação, com seus respectivos métodos getters e setters, e armazenar os nossos usuários em uma lista de usuários.

```java
public class User {
    private int userID;
    private String name;
    private String email;

    public User(int userID, String nome, String email) {
        this.userID = userID;
        this.name = nome;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User() {
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}'+"\n";
    }
}
```
Populando uma lista de user:
```java
tmpUser = new User();
tmpUser.setUserID(rs.getInt("UserID"));
tmpUser.setEmail(rs.getString("Email"));
tmpUser.setName(rs.getString("Name"));
users.add(tmpUser);
```

Nosso código básico para busca de informações em nossa base de dados, ficará da seguinte forma:
```java
public class Principal {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/COMPANY";
        String user = "root";
        String pass = "root";
        User tmpUser = new User();
        List<User> users = new ArrayList<User>();

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected");
            PreparedStatement ps = con.prepareStatement("select * from User");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tmpUser = new User();
                tmpUser.setUserID(rs.getInt("UserID"));
                tmpUser.setEmail(rs.getString("Email"));
                tmpUser.setName(rs.getString("Name"));
                users.add(tmpUser);
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar: "+e.getMessage());
        }
        System.out.println(users);
    }
}
```

## Inserindo dados

Para inserir dados, também precisaremos estar conectados em nossa base de dados:
```java
public static int insertUser(User u) {
        try(Connection con =DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO User(Name,Email) VALUES (?,?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,u.getName());
            ps.setString(2,u.getEmail());
            return ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Erro na inserção: "+e.getMessage());
        }
    }
```


