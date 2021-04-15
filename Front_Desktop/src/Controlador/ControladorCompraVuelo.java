/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Avion;
import Modelo.Cliente;
import Modelo.DetalleVuelo;
import Modelo.Modelo;
import Modelo.Vuelo;
import Vista.ComprarVuelo;
import Vista.VistaAsientos;
import Vista.VistaCliente;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jorge
 */
public class ControladorCompraVuelo implements ActionListener, KeyListener{
  
    private ComprarVuelo vCompraVuelo;
    private Modelo modelo;
    
    private Cliente cliente;
    
    //Otras vistas y controladores
    private VistaCliente vCliente;
    private VistaAsientos vAsientos;
    private ControladorAsientos cAsientos;

    public ControladorCompraVuelo(ComprarVuelo vCompraVuelo, VistaCliente vCliente, Modelo modelo, Cliente cliente) {
        this.vCompraVuelo = vCompraVuelo;
        this.vCliente = vCliente;
        this.modelo = modelo;
        this.cliente = cliente;
        
        this.vCompraVuelo.setControlador(this);
        this.vCompraVuelo.setModelo(modelo);
        MostrarDetallesDeVuelos();
    }
    
    private void Comprar() {
        JTable tablaVuelos = vCompraVuelo.getTableVuelos();
        int filaSeleccionada = tablaVuelos.getSelectedRow();
        if(filaSeleccionada != -1){
            String idVueloSeleccionado = (String) tablaVuelos.getValueAt(filaSeleccionada, 0);
            Vuelo vuelo = modelo.consultarVuelo(idVueloSeleccionado);
            Avion avion = modelo.consultarAvion(vuelo.getAvion());
            
            MostrarAsientos(vuelo, avion);

            //JOptionPane.showMessageDialog(vCompraVuelo, "Vuelo " + idVueloSeleccionado + " seleccionado correctamente.","Mensaje",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(vCompraVuelo, "Seleccione el vuelo que desea comprar!.","Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void Regresar() {
        this.vCliente.setVisible(true);
        
        this.vCompraVuelo.setVisible(false);
        this.vCompraVuelo.dispose();//Se liberan recursos al SO
    }
    
    private void MostrarAsientos(Vuelo vuelo, Avion avion) {
        vAsientos = new VistaAsientos();
        cAsientos = new ControladorAsientos(modelo, vAsientos, vCompraVuelo, vCliente, cliente, vuelo, avion);
        vCompraVuelo.setVisible(false);
    }
    

    private void MostrarDetallesDeVuelos() {
        ArrayList<DetalleVuelo> detallesVuelo = modelo.listarDetalleVuelos();
        if ((detallesVuelo != null) && (!detallesVuelo.isEmpty())) {
            String[] nombreColumnas = {"ID Vuelo", "Origen", "Destino", "Fecha salida", "Hora", "Precio"};
            DefaultTableModel tableModel = new DefaultTableModel(null, nombreColumnas);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (DetalleVuelo detalle : detallesVuelo) {
                fila[0] = detalle.getIdVuelo();
                fila[1] = detalle.getOrigen();
                fila[2] = detalle.getDestino();
                fila[3] = FormatoFecha(detalle.getFechaSalida());
                fila[4] = detalle.getHora();
                fila[5] = detalle.getPrecio();

                tableModel.addRow(fila);
            }
            vCompraVuelo.getTableVuelos().setModel(tableModel);
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
        JTable tableDetalleVuelos = vCompraVuelo.getTableVuelos();
        DefaultTableModel dm = (DefaultTableModel) tableDetalleVuelos.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        tableDetalleVuelos.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            
            case "Comprar":
                Comprar();
                break;
            case "Regresar":
                Regresar();
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
        if (ke.getSource() == vCompraVuelo.getTxtFiltroTable()) {
            String query = vCompraVuelo.getTxtFiltroTable().getText();
            filter(query);
        }
    }
    
}
