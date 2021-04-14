/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo;
import Modelo.Usuario;
import Vista.Administrador;
import Vista.RegistroAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author groya
 */
public class ControladorRegistroAdministrador implements ActionListener{
    
    private RegistroAdministrador vRegistroAdministrador;
    private Administrador vAdministrador;
    private Modelo modelo;
    private Usuario administrador;
    

    ControladorRegistroAdministrador(RegistroAdministrador vRegistroAdministrador, Administrador vAdministrador, Modelo modelo, Usuario administrador) {
        this.vRegistroAdministrador = vRegistroAdministrador;
        this.vAdministrador = vAdministrador;
        this.modelo = modelo;
        this.administrador = administrador;
        
        vRegistroAdministrador.setControlador(this);
        vRegistroAdministrador.setModelo(modelo);
        
        MostrarDatosAdministrador();
    }
    
    private void MostrarDatosAdministrador(){
        vRegistroAdministrador.getTxtIdAdmin().setText(administrador.getIdUsuario());
        vRegistroAdministrador.getTxtContraseniaAdmin().setText(administrador.getContrasenia());
    }
    
    private void Registrar() {
        JTextField txtIdentificacion = vRegistroAdministrador.getTxtIdentificacion();
        JTextField txtContrasenia = vRegistroAdministrador.getTxtContrasenia();
        
        if(!(txtIdentificacion.getText().isEmpty()) && !(txtContrasenia.getText().isEmpty())){
            String id = txtIdentificacion.getText();
            String pass = txtContrasenia.getText();
            Usuario usuarioBD = null;
                try{
                    usuarioBD = modelo.consultarUsuario(id);
                }catch(NullPointerException ex){
                    System.out.println("Se puede ingresar al usuario.");
                }
            if (usuarioBD == null) {//Caso de que el usuario no se haya registrado aún
                usuarioBD = new Usuario(id, "Administrador", pass, 0);
                try {
                    modelo.insertarUsuario(usuarioBD);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorRegistroAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(vRegistroAdministrador, "Administrador agregado correctamente.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                Regresar();
            }

        }else{
            JOptionPane.showMessageDialog(vRegistroAdministrador, "Campo vacío, favor ingresar una contraseña.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void Modificar() {
        if(ValidarCamposAdministrador()){
            String contrasenia = vRegistroAdministrador.getTxtContraseniaAdmin().getText();
            if (!contrasenia.equals(administrador.getContrasenia())) {
                administrador.setContrasenia(contrasenia);
                vAdministrador.getControlador().setAdministrador(administrador);
                modelo.modificarUsuario(administrador);
                JOptionPane.showMessageDialog(vAdministrador, "Administrador modificador correctamente!", "Modificación", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(vRegistroAdministrador, "Campos vacíos, favor completar los campos del registro.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean ValidarCamposAdministrador(){
        return !(vRegistroAdministrador.getTxtIdentificacion().getText().isEmpty() && vRegistroAdministrador.getTxtContraseniaAdmin().getText().isEmpty());
    }

    private void Regresar() {
        this.vAdministrador.setVisible(true);
        
        this.vRegistroAdministrador.setVisible(false);
        this.vRegistroAdministrador.dispose();//Se liberan recursos al SO
    }
    

    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "Registrar":
                Registrar();
                break;
            case "Modificar":
                Modificar();
                break;
            case "Regresar":
                Regresar();
                break;
        }
    }

    
}
