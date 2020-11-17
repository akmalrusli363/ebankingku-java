# ebankingku-java
A Java demo project to demonstrate how to connect Java GUI project to MySQL via JDBC connection.

This project was built for BNCC Praetorian Training as a demonstration project how to connect Java Swing GUI to MySQL with the MySQL JDBC Connector.

## Requirement
- **JDK 8 or newer** - Required for executing and developing Java project 
- **MySQL** - For database server (manually or via [XAMPP](https://xampp.org))
- **MySQL JDBC Connector** - Connecting MySQL to Java Project

To use this project, you need **MySQL** and a latest **MySQL JDBC Connector** which you can download the Java Connector at (https://dev.mysql.com/downloads/connector/j/).

For MySQL Database, you can use [XAMPP](https://xampp.org) or install MySQL manually via (https://dev.mysql.com/downloads/)

## Instruction
1. Extract the `.zip` you've downloaded or clone this repository to your directory
2. Execute the query `ebankingku.sql` to your MySQL Database
3. Use or compile this project using Eclipse IDE or your favourite Java IDE (like Intellij IDEA or NetBeans)
> IMPORTANT: You need to include the MySQL JDBC Connector to your project before executing it!


## Basic way to connect to Database Server

### Three essential object
In a Java database connector, you need to import `java.sql` package for every Java SQL classes and methods.
```java
import java.sql.*; // required for every Java SQL classes and methods
```

After import `java.sql` package, create 3 Object in class definition (each from `java.sql.Connection`, `java.sql.ResultSet`, and `java.sql.Statement`)
```java
private Connection connectionPath; // path for MySQL connection
private ResultSet results; // obtained from executeQuery();
private Statement statement; // the data obtained from Database server
```

### Check availability & Define Java MySQL Connection
In a constructor, perform check for a class named `com.mysql.cj.jdbc.Driver` (varied between MySQL JDBC version)
```java
// Don't forget for try-catch if the class was not found
Class.forName("com.mysql.cj.jdbc.Driver");
```

After check for a class, create connection by obtain connection to your MySQL Database server by:
```java
String connection_url = "jdbc:mysql://yourMySQLpath/targetDatabase";
String username = "root";
String password = "";

// Perform connection to a MySQL Database server for example 'mysql://yourMySQLpath/targetDatabase' with user 'root' and no password
connect = DriverManager.getConnection(connection_url, username, password);
st = connect.createStatement(resultSetType, resultSetConcurrency);
```

> Don't forget for `try-catch` clause for class checking and SQL Connection attempts

**Notice:**
- resultSetType: `TYPE_FORWARD_ONLY(1003), TYPE_SCROLL_INSENSITIVE(1004), TYPE_SCROLL_SENSITIVE(1005)`
- resultSetConcurrency: `CONCUR_READ_ONLY(1007), CONCUR_UPDATABLE(1008)`

> Java SQL (`java.sql.ResultSet`) documentation - [ResultSet JavaDocs](https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html)

And finally, don't forget to close MySQL Database connection by `connection.close()` at a `closeDB()` method:
```java
public void close() throws SQLException {
  connect.close();
}
```

### Execute and update database query
To perform **selection** query, you need obtain result by `executeQuery(String query)` to `ResultSet` as results from the query. For executing the query that **modify, input, or delete** data from table, use `executeUpdate(String query)` to perform a query to update their table or database state. 

If you need to perform **execute** or **update** database query, use two following methods as follow:
```java
// Read and obtain data from database
public ResultSet execute(String query) throws SQLException {
  rs = st.executeQuery(query);
  return rs;
}

// Perform change, input, or delete data query for database
public void update(String query) throws SQLException {
  st.executeUpdate(query);
}
```

### Further documentation
- For MySQL JDBC Connector documentation or example - [Developer guide](https://dev.mysql.com/doc/connector-j/5.1/en/)
- For Java SQL (`java.sql`) documentation - [Package overview](https://docs.oracle.com/javase/7/docs/api/java/sql/package-summary.html)
