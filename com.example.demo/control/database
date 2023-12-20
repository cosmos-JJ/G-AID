package com.example.demo.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public class database {

	Statement data(){
		try {Class.forName("oracle.jdbc.driver.OracleDriver");
			try {Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.23:1521:xe","C##AID","1234");
				try {Statement stat = con.createStatement();
				return stat;
			}
			catch(SQLSyntaxErrorException e) {System.out.print(e.getMessage());}
		}
		catch(SQLException e) {System.out.print(e.getMessage());}
	}
	catch(ClassNotFoundException e) {System.out.print(e.getMessage());}
	
	return null;
	}	
}
