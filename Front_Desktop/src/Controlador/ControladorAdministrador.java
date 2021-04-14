/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo;
import Modelo.Usuario;
import Vista.Administrador;
import Vista.Aviones;
import Vista.Login;
import Vista.RegistroAdministrador;
import Vista.Rutas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class ControladorAdministrador implements ActionListener {
    private Modelo modelo;
    private Usuario administrador;
    private Administrador vAdministrador;
    private ControladorRutas cRuta;
    private Login vLogin;
    private Rutas vRutas;
    private Aviones vAviones;
    private RegistroAdministrador vRegistroAdministrador;
    private ControladorRegistroAdministrador cRegistroAdministrador;
    
 ControladorAdministrador(Administrador vAdministrador, Login vLogin, Modelo modelo, Usuario administrador) {
        this.modelo = modelo;
        this.administrador = administrador;
        this.vLogin = vLogin;
        this.vAdministrador = vAdministrador;
        this.vAdministrador.setControlador(this);
        this.vAdministrador.setModelo(modelo);
    }
    
    
    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
    }

    //private Vuelos vVuelos;
    public void IniciarVuelos(){}
    public void IniciarAviones(){}
    
    public void IniciarRutas(){
    vRutas = new Rutas();
    this.vAdministrador.setVisible(false);
    cRuta = new ControladorRutas(vRutas, vAdministrador, modelo);
    }
    
    public void IniciarEditarAdmin(){}
    public void IniciarRegistroAdmin(){}
    


    
    
    private void RegistrarAdministrador(){
        vRegistroAdministrador = new RegistroAdministrador();
        cRegistroAdministrador = new ControladorRegistroAdministrador(vRegistroAdministrador, vAdministrador, modelo, administrador);
        vAdministrador.setVisible(false);
    }
    
   
    
    private void CerrarSesion() {
        this.vAdministrador.setVisible(false);
        this.vLogin.getControlador().Regresar();
        this.vAdministrador.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "Vuelos":
                
                break;
            case "Rutas":
                this.IniciarRutas();
                break;
            case "Aviones":
                break;
            case "Registros":
                RegistrarAdministrador();
                break;
            case "Editar":
                break;
            case "Cerrar":
                CerrarSesion();
                break;
        }
    }


    
}
