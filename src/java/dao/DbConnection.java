package dao;

import java.sql.*;

public class DbConnection{
    String username;
    String password;
    String database;

    //SETTERS
    public void setUsername(String u){this.username=u;}
    public void setPassword(String p){this.password=p;}
    public void setDatabase(String d){this.database=d;}

    //GETTERS
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getDatabase(){return this.database;}

    //CONSTRUCTOR
    public DbConnection(){
        this.setUsername("postgres");
        this.setPassword("haingo");
        this.setDatabase("projet_nify");
    }
    //METHODS

    public Connection connectToOracle() throws Exception{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",this.getUsername(),this.getPassword());
        return con;
    }

    public Connection connectToMysql() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.getDatabase(),this.getUsername(),this.getPassword());
        return con;
    }

    public Connection connectToPostgres() throws Exception{
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+this.getDatabase(),this.getUsername(),this.getPassword());
        return con;
    }
}
