/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Modelo;
import Modelo.Usuario;
import Vista.VistaCliente;
import Vista.VistaPerfilCliente;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author groya
 */
public class ControladorPerfilCliente implements ActionListener{
    
    private VistaPerfilCliente vPerfilCliente;
    private VistaCliente vCliente;
    private Modelo modelo;
    private Cliente cliente;
    
    //Arreglo con JTextField de la vista registro
    private ArrayList<JTextField> camposTexto;
    
    public ControladorPerfilCliente(VistaPerfilCliente vPerfilCliente, VistaCliente vCliente, Modelo modelo, Cliente cliente){
        this.vPerfilCliente = vPerfilCliente;
        this.vCliente = vCliente;
        this.modelo = modelo;
        this.cliente = cliente;
        
        this.vPerfilCliente.setControlador(this);
        this.vPerfilCliente.setModelo(modelo);
        
        MostrarDatosCliente();
    }
    
    private void MostrarDatosCliente(){
        vPerfilCliente.getTxtIdentificacion().setText(cliente.getIdCliente());
        vPerfilCliente.getTxtNombre().setText(cliente.getNombre());
        vPerfilCliente.getTxtPrimerApellido().setText(cliente.getPrimerApellido());
        vPerfilCliente.getTxtSegundoApellido().setText(cliente.getSegundoApellido());
        vPerfilCliente.getTxtCorreo().setText(cliente.getCorreo());
        vPerfilCliente.getTxtDireccion().setText(cliente.getDireccion());
        vPerfilCliente.getTxtTelefono().setText(cliente.getTelefono());
        vPerfilCliente.getTxtCelular().setText(cliente.getCelular());
        vPerfilCliente.getTxtContrasenia().setText(cliente.getContrasenia());

        String strDate = cliente.getFechaNacimiento();
        Date date = FormatoFechaDateChooser(strDate);
        vPerfilCliente.getDateNacimiento().setDate(date);
    }
    
    private Date FormatoFechaDateChooser(String strDate){
        SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoNormal = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatoBD.parse(strDate);
        } catch (ParseException e) {
            try {
                date = formatoNormal.parse(strDate);
            } catch (ParseException ex) {
                System.out.println("Error al realizar conversión de fechas (FormatoFechaDateChooser)");
            }
        }
        return date;
    }
    
    public boolean camposRegistroVacios(){
        camposTexto = getComponentesTextField();
        
        Iterator<JTextField> iter = camposTexto.iterator();
        while (iter.hasNext()) {
            if(iter.next().getText().isEmpty()){
                return true;
            }
        }
        
        JDateChooser campoFechaNacimiento = vPerfilCliente.getDateNacimiento();
        if(campoFechaNacimiento.getDate() == null){
            return true;
        }
        
        
       return false;
    }
    
    public ArrayList<JTextField> getComponentesTextField(){
        ArrayList<JTextField> jTxtComponentes = new ArrayList<>();
        for (Component component : vPerfilCliente.getPanelPerfilCliente().getComponents()) {
            if (component instanceof JTextField) {
                jTxtComponentes.add((JTextField) component);
            }
        }
        return jTxtComponentes;
    }
    
    private void Modificar() {
        if(!camposRegistroVacios()){
            String idCliente = vPerfilCliente.getTxtIdentificacion().getText();
            String nombre = vPerfilCliente.getTxtNombre().getText();
            String primApellido = vPerfilCliente.getTxtPrimerApellido().getText();
            String segundoApellido = vPerfilCliente.getTxtSegundoApellido().getText();
            Date fechaNac = vPerfilCliente.getDateNacimiento().getDate();
            String correo = vPerfilCliente.getTxtCorreo().getText();
            String direccion = vPerfilCliente.getTxtDireccion().getText();
            String telefono = vPerfilCliente.getTxtTelefono().getText();
            String celular = vPerfilCliente.getTxtCelular().getText();
            String contrasenia = vPerfilCliente.getTxtContrasenia().getText();          
            
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            String nacimiento = dFormat.format(fechaNac);

            Usuario modUsuario = new Usuario(idCliente,"usuario",contrasenia,1);
            Cliente modCliente = new Cliente(idCliente,nombre,primApellido,segundoApellido,nacimiento,correo, direccion, telefono, celular, contrasenia);
            
            try{
                modelo.modificarUsuario(modUsuario);
                modelo.modificarCliente(modCliente);
                
                vCliente.getControlador().setCliente(modCliente);
                Regresar();
            }catch(Exception ex){
                  JOptionPane.showMessageDialog(null, "Error al realizar la modificación del cliente.","Error",JOptionPane.ERROR_MESSAGE);
                  Regresar();
            }
        }else{
             JOptionPane.showMessageDialog(null, "Campos vacíos, favor completar todos los campos.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void Regresar() {        
        vPerfilCliente.setVisible(false);
        vCliente.setVisible(true);
        vPerfilCliente.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "Regresar":
                Regresar();
                break;
            case "Modificar":
                Modificar();
                break;
        }        
    }   
}
