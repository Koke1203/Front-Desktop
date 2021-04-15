/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ControladorAsientos;
import javax.swing.JButton;

/**
 *
 * @author groya
 */
public class BotonAsiento extends JButton{
    
    
    
    public void setControlador(ControladorAsientos controlador){
        this.addActionListener(controlador);
    }
}
