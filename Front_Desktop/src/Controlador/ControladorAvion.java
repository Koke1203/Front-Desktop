/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Avion;
import Modelo.Modelo;
import Vista.Aviones;
import Vista.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jorge
 */
public class ControladorAvion implements ActionListener {

    private Aviones vAviones;
    private Principal vPrincipal;
    private Modelo modelo;

    public ControladorAvion(Aviones vAviones, Principal vPrincipal, Modelo modelo) {
        this.vAviones = vAviones;
        this.vPrincipal = vPrincipal;
        this.modelo = modelo;
    }

    private void RegistrarAvion() {
        if (!emptyFields()) {
            String idAvion = vAviones.getTxtIdAvion().getText();
            int AnioAvion = Integer.parseInt(vAviones.getTxtIdAvion().getText());
            String marcaAvion = vAviones.getTxtIdAvion().getText();
            String modeloAvion = vAviones.getTxtIdAvion().getText();
            int numAsientos = Integer.parseInt(vAviones.getNumAsientos().getText());
            int numPasajeros = Integer.parseInt(vAviones.getNumPasajeros().getText());
            int numFilas = Integer.parseInt(vAviones.getNumFilas().getText());

            Avion aviones = new Avion(idAvion, AnioAvion, marcaAvion, modeloAvion, numPasajeros, numFilas, numAsientos);
            try {
                modelo.insertarAvion(aviones);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Avion registrado correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }

    }

    public boolean emptyFields() {
        if (vAviones.getTxtAnioAvion().getText().equals("")
                || vAviones.getTxtIdAvion().getText().equals("")
                || vAviones.getTxtMarcaAvion().getText().equals("")
                || vAviones.getTxtModeloAvion().getText().equals("")
                || vAviones.getNumAsientos().getText().equals("")
                || vAviones.getNumFilas().getText().equals("")
                || vAviones.getNumPasajeros().getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Agregar":
                RegistrarAvion();
                break;
            case "Modificar":

                break;
            case "Eliminar":

                break;
            case "Regresar":

                break;
        }
    }
}
