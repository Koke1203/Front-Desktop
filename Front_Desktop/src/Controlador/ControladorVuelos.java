/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Excepciones.GlobalException;
import Modelo.Avion;
import Modelo.Modelo;
import Modelo.Vuelo;
import Vista.Administrador;
import Vista.Vuelos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author groya
 */
public class ControladorVuelos implements ActionListener {

    private Vuelos vVuelos;
    private Administrador vAdministrador;
    private Modelo modelo;

    public ControladorVuelos(Vuelos vVuelos, Administrador vAdministrador, Modelo modelo) {
        this.vVuelos = vVuelos;
        this.vAdministrador = vAdministrador;
        this.modelo = modelo;
        
        vVuelos.setControlador(this);
        vVuelos.setModelo(this.modelo);
        MostrarVuelos();
    }
    
    public void Regresar() {
        vAdministrador.setVisible(true);
        vVuelos.setVisible(false);
        vVuelos.dispose();
    }
    
    private void MostrarVuelos() {
        ArrayList<Vuelo> vuelos = modelo.listarVuelos();
        if ((vuelos != null) && (!vuelos.isEmpty())) {
            String[] nombreColumnas = {"ID Vuelo", "Precio", "Fecha ida", "Fecha regreso", "ID Avion", "ID Ruta"};
            DefaultTableModel tableModel = new DefaultTableModel(null, nombreColumnas);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (Vuelo vuelo : vuelos) {
                fila[0] = vuelo.getIdVuelo();
                fila[1] = vuelo.getPrecio();
                fila[2] = FormatoFecha(vuelo.getFechaIda());
                fila[3] = FormatoFecha(vuelo.getFechaRegreso());
                fila[4] = vuelo.getAvion();
                fila[5] = vuelo.getRuta();

                tableModel.addRow(fila);
            }
            vVuelos.getTableVuelos().setModel(tableModel);
        }
    }
    
    public String FormatoFecha(String strFecha) {
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date date = inputFormat.parse(strFecha);
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            String outputString = outputFormat.format(date);
            return outputString;
        } catch (ParseException ex) {
            Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            return strFecha;
        }
    }
    
    private void RegistrarVuelo() {
        if (!emptyFields()) {
            String idVuelo = vVuelos.getTxtIdVuelo().getText();
            String strPrecio = vVuelos.getTxtPrecio().getText();
            Date dateFechaIda =  vVuelos.getDateFechaIda().getDate();
            Date dateFechaRegreso =  vVuelos.getDateFechaRegreso().getDate();
            String idAvion = vVuelos.getTxtIdAvion().getText();
            String idRuta = vVuelos.getTxtIdRuta().getText();
            
            Avion avion = modelo.consultarAvion(idAvion);
            Double precio = Double.parseDouble(strPrecio);
            
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaIda = dFormat.format(dateFechaIda);
            String fechaRegreso = dFormat.format(dateFechaRegreso);
            
            
            Vuelo vuelo = new Vuelo(idVuelo, fechaIda, fechaRegreso, avion.getCantAsientos() , precio ,idAvion, idRuta);
            try {
                modelo.insertarVuelo(vuelo);
                MostrarVuelos();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Vuelo registrado correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }

    }
    
    private void ModificarVuelo() {
        if (!emptyFields()) {
            String idVuelo = vVuelos.getTxtIdVuelo().getText();
            String strPrecio = vVuelos.getTxtPrecio().getText();
            Date dateFechaIda =  vVuelos.getDateFechaIda().getDate();
            Date dateFechaRegreso =  vVuelos.getDateFechaRegreso().getDate();
            String idAvion = vVuelos.getTxtIdAvion().getText();
            String idRuta = vVuelos.getTxtIdRuta().getText();
            
            Avion avion = modelo.consultarAvion(idAvion);
            Double precio = Double.parseDouble(strPrecio);
            
            SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaIda = dFormat.format(dateFechaIda);
            String fechaRegreso = dFormat.format(dateFechaRegreso);
            
            
            Vuelo vuelo = new Vuelo(idVuelo, fechaIda, fechaRegreso, avion.getCantAsientos() , precio ,idAvion, idRuta);
            try {
                modelo.modificarVuelo(vuelo);
                MostrarVuelos();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Vuelo modificado correctamente.");
        }else{
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }
    }
    
    private void EliminarVuelo(){
        if(vVuelos.getTxtIdVuelo().getText().equals("")){
            JOptionPane.showMessageDialog(vVuelos,"Error" ,"Ingrese el identificador del vuelo a eliminar!",JOptionPane.ERROR_MESSAGE);
        }else{
            String idVuelo = vVuelos.getTxtIdVuelo().getText();
            try {
                modelo.eliminarVuelo(idVuelo);
                MostrarVuelos();
            } catch (GlobalException ex) {
                Logger.getLogger(ControladorVuelos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean emptyFields() {
        
        
        if (vVuelos.getTxtIdVuelo().getText().equals("")
                || vVuelos.getTxtPrecio().getText().equals("")
                || vVuelos.getDateFechaIda().getDate() == null
                || vVuelos.getDateFechaRegreso().getDate() == null
                || vVuelos.getTxtIdAvion().getText().equals("")
                || vVuelos.getTxtIdRuta().getText().equals("")) {
            return true;
        }

        
        else {
            return false;
        }

    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Agregar":
                RegistrarVuelo();
                break;
            case "Modificar":
                ModificarVuelo();
                break;
            case "Eliminar":
                EliminarVuelo();
                break;
            case "Regresar":
                Regresar();
                break;
            default:
                break;
        }
    }
    
}
