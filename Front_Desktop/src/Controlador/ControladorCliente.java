/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.DetalleHistoricoCompra;
import Modelo.Modelo;
import Vista.ComprarVuelo;
import Vista.Login;
import Vista.VistaCliente;
import Vista.VistaPerfilCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class ControladorCliente implements ActionListener{
    
    private VistaCliente vCliente;
    private Login vLogin;
    private Modelo modelo;
    private Cliente cliente;
    private VistaPerfilCliente vPerfil;
    private ControladorPerfilCliente cPerfilCliente;
    private ComprarVuelo vCompraVuelo;
    private ControladorCompraVuelo cCompraVuelo;

    public ControladorCliente(VistaCliente vCliente, Login vLogin, Modelo modelo, Cliente cliente) {
        this.vCliente = vCliente;
        this.vLogin = vLogin;
        this.modelo = modelo;
        this.cliente = cliente;
        
        this.vCliente.setControlador(this);
        this.vCliente.setModelo(modelo);
        
        MostrarHistoricosVuelos();
    }
    
    public void MostrarHistoricosVuelos() {
        ArrayList<DetalleHistoricoCompra> detalleHistoricoCompras = modelo.listarDetalleHistoricoComprasCliente(cliente.getIdCliente());
        if ((detalleHistoricoCompras != null) && (!detalleHistoricoCompras.isEmpty())) {
            String[] nombreColumnas = {"ID Vuelo", "Origen", "Destino", "Precio", "Fecha"};
            DefaultTableModel tableModel = new DefaultTableModel(null, nombreColumnas);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (DetalleHistoricoCompra detalle : detalleHistoricoCompras) {
                fila[0] = detalle.getIdVuelo();
                fila[1] = detalle.getOrigen();
                fila[2] = detalle.getDestino();
                fila[3] = detalle.getPrecio();
                fila[4] = detalle.getFecha();

                tableModel.addRow(fila);
            }
            vCliente.getTableHistorialCompras().setModel(tableModel);
        }
    }
    
    private void CerrarSesion() {
        this.vCliente.setVisible(false);
        this.vLogin.getControlador().Regresar();
        this.vCliente.dispose();
    }

    private void MostrarPerfil() {
        vPerfil = new VistaPerfilCliente();
        cPerfilCliente = new ControladorPerfilCliente(vPerfil, vCliente, modelo, cliente);
        vCliente.setVisible(false);
        vPerfil.setVisible(true);
        
    }
    
    private void MostrarVentanaCompras() {
        vCompraVuelo = new ComprarVuelo();
        cCompraVuelo = new ControladorCompraVuelo(vCompraVuelo, vCliente, modelo, cliente);
        vCliente.setVisible(false);
        vCompraVuelo.setVisible(true);
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
        
    public Cliente getCliente() {
        return cliente;
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "Comprar":
                MostrarVentanaCompras();
                break;
            case "Cerrar":
                CerrarSesion();
                break;
            case "Perfil":
                MostrarPerfil();
                break;
        }
    }
    
}
