/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Avion;
import Modelo.Modelo;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class ControladorAvion implements ActionListener {

    private Aviones vAviones;
    private Administrador vAdministrador;
    private Modelo modelo;

    public ControladorAvion(Aviones vAviones, Administrador vAdministrador, Modelo modelo) {
        this.vAviones = vAviones;
        this.vAdministrador = vAdministrador;
        this.modelo = modelo;
        vAviones.setControlador(this);
        vAviones.setModelo(modelo);
        MostrarDetallesAviones();
    }
    
    public void Regresar() {
        this.vAdministrador.setVisible(true);
        this.vAviones.setVisible(false);
        this.vAviones.dispose();
    }
    
    public void MostrarDetallesAviones() {
        ArrayList<Avion> detallesAvion = modelo.listarAviones();
        if ((detallesAvion != null) && (!detallesAvion.isEmpty())) {
            String[] nombreColumnas = {"ID Avion", "Marca", "Modelo", "Year", "#Pasajeros", "#Filas", "#Asientos"};
            DefaultTableModel tableModel = new DefaultTableModel(null, nombreColumnas);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (Avion detalle : detallesAvion) {
                fila[0] = detalle.getIdentificador();
                fila[1] = detalle.getMarca();
                fila[2] = detalle.getModelo();
                fila[3] = detalle.getAnio();
                fila[4] = detalle.getCantPasajeros();
                fila[5] = detalle.getCantFilas();
                fila[6] = detalle.getCantAsientos();
                tableModel.addRow(fila);
                //System.out.println(detalle);
            }
            vAviones.getTableAviones().setModel(tableModel);
        }
    }

    private void RegistrarAvion() {
        if (!emptyFields()) {
            String idAvion = vAviones.getTxtIdAvion().getText();
            int AnioAvion = Integer.parseInt(vAviones.getTxtAnioAvion().getText());
            String marcaAvion = vAviones.getTxtMarcaAvion().getText();
            String modeloAvion = vAviones.getTxtModeloAvion().getText();
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
    
    private void MoodificarAvion() {
        if (!emptyFields()) {
            String idAvion = vAviones.getTxtIdAvion().getText();
            int AnioAvion = Integer.parseInt(vAviones.getTxtAnioAvion().getText());
            String marcaAvion = vAviones.getTxtMarcaAvion().getText();
            String modeloAvion = vAviones.getTxtModeloAvion().getText();
            int numAsientos = Integer.parseInt(vAviones.getNumAsientos().getText());
            int numPasajeros = Integer.parseInt(vAviones.getNumPasajeros().getText());
            int numFilas = Integer.parseInt(vAviones.getNumFilas().getText());

            Avion aviones = new Avion(idAvion, AnioAvion, marcaAvion, modeloAvion, numPasajeros, numFilas, numAsientos);
            try {
                modelo.modificarAvion(aviones);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Avion modificado correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }

    }
    
    private void EliminarAvion(){
            String idAvion = vAviones.getTxtIdAvion().getText();
            try {
                modelo.eliminarAvion(idAvion);
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                MostrarDetallesAviones();
                
                break;
            case "Modificar":
                MoodificarAvion();
                break;
            case "Eliminar":
                EliminarAvion();
                break;
            case "Regresar":
                Regresar();
                break;
        }
    }
}
