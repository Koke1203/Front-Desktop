/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.DetalleVuelo;
import Modelo.Modelo;
import Vista.Principal;
import Vista.Login;
import Vista.RegistroCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author groya
 */
public class ControladorPrincipal implements ActionListener, KeyListener{
    
    private Modelo modelo;
    private Principal vPrincipal;
    private Login vLogin;
    private ControladorLogin cLogin;
    private RegistroCliente vRegistro;
    private ControladorRegistroCliente cRegistroCliente;
    
    public ControladorPrincipal(Modelo modelo, Principal vPrincipal){
        this.modelo = modelo;
        this.vPrincipal = vPrincipal;
        this.vPrincipal.setControlador(this);
        MostrarDetallesDeVuelos();
    }
    
    public void InciarSesion(){
        vLogin = new Login();
        cLogin = new ControladorLogin(vLogin, vPrincipal, modelo);
                
        this.vPrincipal.setVisible(false);
    }
    
    private void Registrarse() {
        vRegistro = new RegistroCliente();
        cRegistroCliente = new ControladorRegistroCliente(vRegistro, vPrincipal, modelo);
        this.vPrincipal.setVisible(false);
    }

    private void MostrarDetallesDeVuelos() {
        ArrayList<DetalleVuelo> detallesVuelo = modelo.listarDetalleVuelos();
        if ((detallesVuelo != null) && (!detallesVuelo.isEmpty())) {
            String[] nombreColumnas = {"ID Vuelo", "Origen", "Destino", "Fecha salida", "Fecha regreso", "Hora"};
            DefaultTableModel tableModel = new DefaultTableModel(null, nombreColumnas);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (DetalleVuelo detalle : detallesVuelo) {
                fila[0] = detalle.getIdVuelo();
                fila[1] = detalle.getOrigen();
                fila[2] = detalle.getDestino();
                fila[3] = FormatoFecha(detalle.getFechaSalida());
                fila[4] = FormatoFecha(detalle.getFechaRegreso());
                fila[5] = detalle.getHora();

                tableModel.addRow(fila);
                //System.out.println(detalle);
            }
            vPrincipal.getTableVuelos().setModel(tableModel);
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
    
    private void filter(String query){
        JTable tableDetalleVuelos = vPrincipal.getTableVuelos();
        DefaultTableModel dm = (DefaultTableModel) tableDetalleVuelos.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        tableDetalleVuelos.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        //tr.sort();
}
    
        @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "IniciarSesion":
                this.InciarSesion();
                break;
            case "Registrarse":
                this.Registrarse();
                break;                
        }
    }
    
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getSource() == vPrincipal.getTxtFilter()){
            String query = vPrincipal.getTxtFilter().getText();
            filter(query);
        }
    }
    
}
