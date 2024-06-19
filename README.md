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
Criando uma database MySQL no Docker:
```bash
docker run -d --name mysqlContainer -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=COMPANY -e MYSQL_USER=admin -e MYSQL_PASSWORD=root mysql
```
Criando uma database Postgres no Docker:
```bash
docker run --name postgresDB -p 5432:5432 -v /tmp/database:/var/lib/postgresql/data -e POSTGRES_PASSWORD=1234 -d postgres
```
Verifique se o container realmente está rodando, após dar o comando para sua criação
```bash
C:\Users\Lucas>docker ps
CONTAINER ID   IMAGE      COMMAND                  CREATED        STATUS        PORTS                               NAMES
42935d7e958e   postgres   "docker-entrypoint.s…"   14 hours ago   Up 14 hours   0.0.0.0:5432->5432/tcp              postgresDB
e57c1636e09c   mysql      "docker-entrypoint.s…"   15 hours ago   Up 15 hours   0.0.0.0:3306->3306/tcp, 33060/tcp   mysqlContainer
```


É importante que o nome de nossa Database rodando seja no Docker, ou configurada em nossa próspria máquina, seja igual, a Database utilizada em nossa aplicação Java.
Utilizaremos a seguinte tabela nesse exemplo:

MySQL
``` sql
create table Person
(   PersonID int auto_increment primary key,
    Name   varchar(100) null,
    Email  varchar(100) null
);
```

Postgres
``` sql
create table Person
(   PersonID SERIAL primary key,
    Name   varchar(100),
    Email  varchar(100));
```

Se desejado podemos fazer algumas inserções na mesma;

```sql
INSERT INTO Person(Name, Email) VALUES ('John Doe', 'doe@mail.com');
```

Abaixo está a configuração junto ao IntelliJ:
### MySQL
![image](https://github.com/lschlestein/JDBC/assets/103784532/ceb1e22a-74ce-41dd-8d92-9a598572c631)

### Postgres
![image](https://github.com/lschlestein/JDBC/assets/103784532/cd843d25-3dc7-46b0-90f0-58f62d38328d)

### Iniciando um Projeto Java com JDBC

Iremos criar um novo projeto Java no IntelliJ e configurar o arquivo pom.xml da seguinte forma:
Basicamente precisamos informar o driver que utilizaremos para trocar informações com a nossa base de dados.

### Configuração do Pom.xml para MySQL
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
### Configuração do Pom.xml Para Postgres
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
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.2</version>
        </dependency>
    </dependencies>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
</project>

```
Obs.: O driver configurado deve correposnder ao banco de dados o qual será acessado:

### Interfaces necessárias para manipular nossa base de dados:
![image](https://github.com/lschlestein/JDBC/assets/103784532/67c2de15-0a5d-4fc6-9ce1-a239b2342365)

Como visto acima, precisaremos de uma Connection, um Statement ou Prepared Statement, e em caso de buscas de dados, um ResultSet.
Esses componentes são interfaces que nos possibilitam acesso a nossa base de dados conforme veremos a seguir:

## Conexão
A primeira coisa a fazer é nos conectarmos a nossa base de dados utilizando a interface Connection:

### Conectando a um banco MySQL
```Java
String url = "jdbc:mysql://localhost:3306/COMPANY"; //Conectando a database padão COMPANY
String user = "root";                                //Utiliza o usuário root 
String pass = "root";                                //Utiliza a senha root
try (Connection con = DriverManager.getConnection(url, user, pass)) {
         System.out.println("Conectado ao Mysql");
} catch (SQLException e) {
         System.out.println("Não foi possível conectar: "+e.getMessage());
}
```
### Conectando a um banco Postgres
```Java
String url = "jdbc:postgresql://localhost:5432/postgres";     //Conectando a database padão postgres
String user = "postgres";                                      //Utiliza o usuário postgres
String pass = "1234";                                          //Senha configurada junto a database
try (Connection con = DriverManager.getConnection(url, user, pass)) {
         System.out.println("Conectado ao Postgres");
} catch (SQLException e) {
         System.out.println("Não foi possível conectar: "+e.getMessage());
}
```
## Recuperando dados com JDBC
Já conectados, agora iremos buscar dados em nossa base de dados, utilizado um PreparedStatement e um ResultSet

```Java
PreparedStatement ps = con.prepareStatement("select * from Person");
ResultSet rs = ps.executeQuery();
```
Aqui passamos qual a query SQL, que será executada em nossa database, e o resultado obtido ficara disponível na ResultSet.
```Java
while (rs.next()) {
         System.out.println(rs.getInt("PersonID") + " " + rs.getString("Name") + " " + rs.getString("Email"));
}
```
Os métodos getInt ou getString recuperam os dados do ResultSet, já convertendo o mesmo para o tipo desejado, conforme o método chamado. O parâmetro passado é o nome da coluna contida no ResultSet.

É possíve também, passar o número da coluna, mas não recomendado a adição dessa prática pois, caso a ordem, ou o número de colunas de uma tabela mude, nossa aplicação pode passar a operar de forma indesejada.``
```java
while (rs.next()) {
         System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
}
```

Agora podemos criar uma entidade Person em nossa aplicação, com seus respectivos métodos getters e setters, e armazenar os nossos usuários em uma lista de usuários.

```java
public class Person {
    private int personID;
    private String name;
    private String email;

    public Person(int personID, String nome, String email) {
        this.personID = personID;
        this.name = nome;
        this.email = email;
    }

    public int GetPersonID() {
        return personID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Person() {
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}'+"\n";
    }
}
```
Populando uma lista de person:
```java
tmpPerson = new Person();
tmpPerson.setPersonID(rs.getInt("PersonID"));
tmpPerson.setEmail(rs.getString("Email"));
tmpPerson.setName(rs.getString("Name"));
persons.add(tmpPerson);
```


Nosso código básico para busca de informações em nossa base de dados Postgres, ficará da seguinte forma:
```java
public class Main {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String user = "postgres";
    private static String password = "1234";
    
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<Person>();
        try (Connection con = DriverManager.getConnection(url, user, password);          //Conectando ao BD
             PreparedStatement ps = con.prepareStatement("select * from Person")) {    //Preparando query
            System.out.println("Conectado com sucesso!");
            ResultSet rs = ps.executeQuery();                                           //Executando query no BD
            while (rs.next()) {
                persons.add(new Person(rs.getInt("personid"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
        System.out.println(persons);
    }
}
```
Nosso código básico para busca de informações em nossa base de dados MySQL, ficará da seguinte forma:

```java
import Jdbc.model.Person;

public class Principal {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/COMPANY";
        String user = "root";
        String pass = "root";
        Person tmpPerson = new Person();
        List<Person> persons = new ArrayList<Person>();

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected");
            PreparedStatement ps = con.prepareStatement("select * from User");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tmpPerson = new Person();
                tmpPerson.setPersonID(rs.getInt("PersonID"));
                tmpPerson.setEmail(rs.getString("Email"));
                tmpPerson.setName(rs.getString("Name"));
                persons.add(tmpPerson);
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar: " + e.getMessage());
        }
        System.out.println(persons);
    }
}
```

## Inserindo dados

Para inserir dados, também precisaremos estar conectados em nossa base de dados:
```java
public int insertPerson(Person p) {
        try(Connection con =DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO Person(Name,Email) VALUES (?,?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,p.getName());
            ps.setString(2,p.getEmail());
            return ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Erro na inserção: "+e.getMessage());
        }
    }
```

### Deletando dados

Para exlcuir dados, também precisaremos estar conectados em nossa base de dados:
```java
 public int deletePerson(int id) {
        String query = "DELETE FROM Person WHERE PersonID = ?;";
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
```
### Alterando Dados

Para alterar dados:
```java
public int updatePerson(Person person) {
        String query = "UPDATE Person SET Name = ?, Email=? WHERE PersonID = ?;";
        try (Connection con = DbConnection.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setInt(3, person.getPersonId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
```

Como observamos acima, todos os métodos fazem uso do try com recursos. Isso só é possível devido a caracteristicas da interface AutoClosable, a qual as interfaces Connection, Statement e ResultSet são filhas.
Dessa forma, o fechamento da conexão, por exemplo, é gerenciado pelo próprio try.
É possível também utlizar o Statement ao invés do PreparedStatement, porém, assim nossa aplicação fica exposta a ataques do tipo [SQLInjection](https://www.w3schools.com/sql/sql_injection.asp)

Exemplo que utiliza o Statement ao invés do PreparedStatement:

```java
import Jdbc.model.Person;

public static void main(String[] args) {
    Person tmpPerson = new Person();
    List<Person> persons = new ArrayList<Person>();
    System.out.println(insertPerson(new Person(0, "Lucas", "lucas@mail.com")));
    try (Connection con = DriverManager.getConnection(url, user, pass);) {
        System.out.println("Connected");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from Person");
        while (rs.next()) {
            tmpPerson = new Person();
            tmpPerson.setPersonID(rs.getInt("PersonID"));
            tmpPerson.setEmail(rs.getString("Email"));
            tmpPerson.setName(rs.getString("Name"));
            persons.add(tmpPerson);
        }
    } catch (SQLException e) {
        System.out.println("Não foi possível conectar: " + e.getMessage());
    }
    System.out.println(persons);
}
```

Com o maior número de classe é importante organizarmos nossa aplicação em pacotes conforme segue:
![image](https://github.com/lschlestein/JDBC/assets/103784532/44aeab28-835b-4871-96d7-028de25f32f3)

O código completo para esse CRUD está diponível nesse mesmo [repositório](https://github.com/lschlestein/JDBC.git)

Referências:
[Processing SQL Statements with JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html)


