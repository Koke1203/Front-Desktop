/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.DetalleVuelo;
import Modelo.Modelo;
import Vista.ComprarVuelo;
import Vista.VistaCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class ControladorCompraVuelo implements ActionListener{
  
    private ComprarVuelo vCompraVuelo;
    private VistaCliente vCliente;
    private Modelo modelo;
    private Cliente cliente;

    public ControladorCompraVuelo(ComprarVuelo vCompraVuelo, VistaCliente vCliente, Modelo modelo, Cliente cliente) {
        this.vCompraVuelo = vCompraVuelo;
        this.vCliente = vCliente;
        this.modelo = modelo;
        this.cliente = cliente;
        
        this.vCompraVuelo.setControlador(this);
        this.vCompraVuelo.setModelo(modelo);
    }
    
    private void Comprar() {
        
    }
    
    private void Regresar() {
        this.vCliente.setVisible(true);
        
        this.vCompraVuelo.setVisible(false);
        this.vCompraVuelo.dispose();//Se liberan recursos al SO
    }
    
    /*
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
                fila[4] = FormatoFecha(detalle.getFechaRegreso());
                fila[5] = detalle.getHora();

                tableModel.addRow(fila);
                //System.out.println(detalle);
            }
            vPrincipal.getTableVuelos().setModel(tableModel);
        }
    }*/
    
    
    
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
    
}
