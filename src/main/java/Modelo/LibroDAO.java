package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Controlador.Conexion;


public class LibroDAO {
	
	Conexion cnn= new Conexion();
	Connection con=cnn.Conecta();
    PreparedStatement ps=null;
    ResultSet res=null;
    
    public boolean Inserta_Libro(LibroDTO lib) {
    	
    	boolean resul=false;
    	try {
    	String sql="insert into libro values(?,?,?,?,?)";
    	ps=con.prepareStatement(sql);
    	ps.setString(1, lib.getIsbn());
    	ps.setString(2, lib.getTitulo());
    	ps.setString(3, lib.getEditorial());
    	ps.setString(4, lib.getAutor());
    	ps.setInt(5, lib.getNo_paginas());
    	resul=ps.executeUpdate()>0;
    	}catch(SQLException ex) {
    	 JOptionPane.showMessageDialog(null,"No se Registro el Libro"+ ex);	
    	}
    	
    	return resul;
    }
    

    public LibroDTO Buscar_Libro(String isbn) {
  	  
  	  LibroDTO lib=null;
  	  try {
  		String sql="select * from libro where isbn=?";
  		ps=con.prepareStatement(sql);
  		ps.setString(1, isbn);
  		res=ps.executeQuery();
  		while(res.next()) {
  			lib= new LibroDTO(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5));
  		}
  	  }catch(SQLException ex) {
  		  JOptionPane.showMessageDialog(null,"Error al Consultar Libro" +ex);
  	  }
  	  
  	  return lib;
    }
    
    public boolean Eliminar_Libro(String isbn) {
	    boolean resul=false;
		try {
		String sql="delete from libro where isbn=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, isbn);
		resul=ps.executeUpdate()>0;	
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null,"error al eliminar: "+ex);
		}
		return resul;
	}

	public boolean Actualizar_Libro(LibroDTO lib) {
	    boolean resul=false;
		try {
		String sql="update libro set titulo=?, editorial=?, autor=?, No_paginas=? where isbn=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, lib.getTitulo());
		ps.setString(2, lib.getEditorial());
		ps.setString(3, lib.getAutor());
		ps.setInt(4, lib.getNo_paginas());
		ps.setString(5, lib.getIsbn());
		resul=ps.executeUpdate()>0;	
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null,"error al actualizar: "+ex);
		}
		return resul;
	}
	
	public boolean Cargar_Libros(String URL) {
		
		boolean resul=false;
		try {
			
		String sql="load data infile '"+URL+"' into table libro fields terminated by ',' lines terminated by '\r\n'";
		ps = con.prepareStatement(sql);
		resul=ps.executeUpdate()>0;
		}catch(SQLException ex) {
			JOptionPane.showMessageDialog(null,"Error al registrar los Libros: "+ ex);
		}
		return resul;
	}
	
	
	
	
	
	
	
}
