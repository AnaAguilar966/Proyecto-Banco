package dao;

import java.sql.*;
import conexion.conexion;
import Entidades.cliente;

public class ClienteDAO {

    public void insertar(cliente c){
        String sql="INSERT INTO cliente(nombre,telefono,correo) VALUES(?,?,?)";

        try(Connection con = conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,c.getNombre());
            ps.setString(2,c.getTelefono());
            ps.setString(3,c.getCorreo());

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void actualizar(cliente c){
        String sql="UPDATE cliente SET nombre=?,telefono=?,correo=? WHERE id_cliente=?";

        try(Connection con = conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1,c.getNombre());
            ps.setString(2,c.getTelefono());
            ps.setString(3,c.getCorreo());
            ps.setInt(4,c.getIdCliente());

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void eliminar(int id){
        String sql="DELETE FROM cliente WHERE id_cliente=?";

        try(Connection con = conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1,id);
            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e);
        }
    }
}