/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo;
import Modelo.Usuario;
import Vista.Administrador;
import Vista.VistaCliente;
import Modelo.Cliente;
import Vista.Login;
import Vista.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author groya
 */
public class ControladorLogin implements ActionListener{
    private Login vLogin;
    private Principal vPrincipal;
    private Modelo modelo;
    private Administrador vAdministrador;
    private ControladorAdministrador cAdministrador;
    private VistaCliente vCliente;
    private ControladorCliente cCliente;

   public ControladorLogin(Login vLogin, Principal vPrincipal, Modelo modelo) {
       this.vLogin = vLogin;
       this.vPrincipal = vPrincipal;
       this.modelo = modelo;
       
       vLogin.setControlador(this);
       vLogin.setModelo(modelo);
       
    }
    
   //Método para el ingreso de un Usuario al sistema
    private void Ingresar() {
        if(camposIngresoValidos()){
            String idUsuario = this.vLogin.getTxtUsuario().getText();
            String contrasenia = this.vLogin.getTxtContrasenia().getText();
            Usuario usuario = null;
            try{
                usuario =  modelo.consultarUsuario(idUsuario);
            }catch(Exception e){
                System.out.println("Error: Usuario no encontrado.");
            }
            //Verificar que coinciden los datos ingresados
            if(usuario != null){
                if((usuario.getIdUsuario().equalsIgnoreCase(idUsuario))&&(usuario.getContrasenia().equalsIgnoreCase(contrasenia) )){
                    if(usuario.getTipo() == 0){//Administrador
                        IngresarComoAdministrador(usuario);
                    }else if(usuario.getTipo() == 1){//Cliente
                        try{
                            Cliente cliente = modelo.consultarCliente(idUsuario);
                            if(cliente != null){
                                IngresarComoCliente(cliente);
                            }
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "El usuario no está inscrito como cliente.");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Tipo de usuario no válido.");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Identificación o usuario inválidos.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Datos ingresados no corresponden a ningún usuario.");
            }  
        }
    }
    
    private void IngresarComoAdministrador(Usuario administrador) {
        vAdministrador = new Administrador();
        cAdministrador = new ControladorAdministrador(vAdministrador, vLogin, modelo, administrador);
        vLogin.setVisible(false);
    }
    
    private void IngresarComoCliente(Cliente cliente) {
        vCliente = new VistaCliente();
        cCliente = new ControladorCliente(vCliente, vLogin, modelo, cliente);
        vLogin.setVisible(false);
        
        
    }
   
    //Método para regresar a la vista Principal
    public void Regresar(){
        this.vPrincipal.setVisible(true);
        
        this.vLogin.setVisible(false);
        this.vLogin.dispose();//Se liberan recursos al SO
    }
    //Valida que los textField del formulario no estén vacios
    public boolean camposIngresoValidos(){
        String usuarioIngresado = this.vLogin.getTxtUsuario().getText();
        String contraseniaIngresada = this.vLogin.getTxtContrasenia().getText();
        
        if(usuarioIngresado.isEmpty() || contraseniaIngresada.isEmpty()){
            JOptionPane.showMessageDialog(null, "Favor ingresar todos los datos.");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "Ingresar":
                Ingresar();
                break;
            
            case "Regresar":
                Regresar();
                break;
                
        }
    }



}
