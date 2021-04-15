/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Avion;
import Modelo.Cliente;
import Modelo.DetalleAsiento;
import static Modelo.DetalleAsiento.abecedario;
import Modelo.HistoricoCompra;
import Modelo.Modelo;
import Modelo.Vuelo;
import Vista.BotonAsiento;
import Vista.ComprarVuelo;
import Vista.VistaAsientos;
import Vista.VistaCliente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author groya
 */
public class ControladorAsientos implements ActionListener{
    
    private Modelo modelo;
    private VistaAsientos vAsientos;
    
    //Otras vistas y controladores
    private ComprarVuelo vCompraVuelo;
    private VistaCliente vCliente;
            
    Cliente cliente;
    Vuelo vuelo;
    Avion avion;
    
    int filas, columnas;
    JButton ultimoBotonSeleccionado;
    
    public ControladorAsientos(Modelo modelo, VistaAsientos vAsientos, ComprarVuelo vCompraVuelo, VistaCliente vCliente , Cliente cliente, Vuelo vuelo, Avion avion){
        this.modelo = modelo;
        this.vAsientos = vAsientos;
        this.vAsientos.setControlador(this);
        this.vAsientos.setModelo(modelo);
        this.vCompraVuelo = vCompraVuelo;
        this.vCliente = vCliente;
        this.cliente = cliente;
        this.vuelo = vuelo;
        this.avion = avion;
        filas = avion.getCantFilas();
        columnas = avion.getCantAsientos();
        ultimoBotonSeleccionado = null;
        ConstruirMatrizAsientos();
        //MarcarAsientosReservados();
    }
    
    private void ConstruirMatrizAsientos(){
        
        JPanel panelAsientos = vAsientos.getPanelAsientos();//Panel para los botones
        panelAsientos.setLayout(new GridLayout(filas, columnas));//Layout en forma de grid de tamaño filasXcolumnas
        JButton[][] matrizBotones = new JButton[filas][columnas];//Matriz de botones
        ArrayList<DetalleAsiento> asientosReservados = modelo.listarDetalleAsientosVuelo(vuelo.getIdVuelo());//Se obtiene la lista con los asientos reservados
        boolean [][] boolMatrizAsientos = DetalleAsiento.matrizAsientosReservados(filas, columnas, asientosReservados);//Matriz booleana de asientos reservados
        
        BotonAsiento boton;
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                boton = new BotonAsiento();
                boton.setText(abecedario[fila]+(columna + 1));
                boton.setBounds(75 * columna, 30 * fila, 75, 30); 
                boton.addActionListener(this);
                if(boolMatrizAsientos[fila][columna]){
                    boton.setEnabled(false);
                }
                matrizBotones[fila][columna] = boton;

                panelAsientos.add(matrizBotones[fila][columna]);
            }
        }
        vAsientos.setPanelAsientos(panelAsientos);
        vAsientos.setMatrizBotones(matrizBotones);
        vAsientos.getPanelPrincipal().add(panelAsientos, BorderLayout.CENTER);
        vAsientos.setContentPane(vAsientos.getPanelPrincipal());
        vAsientos.setVisible(true);
    }
    
    private void Regresar() {
        vCompraVuelo.setVisible(true);
        
       vAsientos.setVisible(false);
       vAsientos.dispose();//Se liberan recursos al SO
    }
    
    private void RegresarInicioCliente(){
        //Vista historico compras
        vCliente.setVisible(true);
        //Vista actual
        vAsientos.setVisible(false);
        vAsientos.dispose();//Liberan recursos usados por la visa
        vCompraVuelo.dispose();//Liberan recursos usados por la visa
        //Actualiza datos de la tabla con hsitoricos de compra
        vCliente.getControlador().MostrarHistoricosVuelos();
        
        
        
    }
    
    private void EfectuarCompraVuelo(String asientoReservado){
        HistoricoCompra nuevoHistorico = new HistoricoCompra(vuelo.getIdVuelo(), cliente.getIdCliente());
        DetalleAsiento nuevoDetalleAsiento = new DetalleAsiento(vuelo.getIdVuelo(), asientoReservado);
        try {
            modelo.insertarHistoricoCompra(nuevoHistorico);
            modelo.insertarDetalleAsiento(nuevoDetalleAsiento);
        } catch (Exception ex) {
            Logger.getLogger(ControladorAsientos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getActionCommand().equals("Comprar")) {
            if(ultimoBotonSeleccionado != null){
                
                String asientoReservado = ultimoBotonSeleccionado.getText();
                EfectuarCompraVuelo(asientoReservado);
                JOptionPane.showMessageDialog(vAsientos, "Compra realizada exitosamente!","Mensaje",JOptionPane.INFORMATION_MESSAGE);
                
                RegresarInicioCliente();//Se retorna a la vista de incio del cliente
                
            }else{
                JOptionPane.showMessageDialog(vAsientos, "Se debe seleccionar un asiento!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }else if(ae.getActionCommand().equals("Regresar")){
            Regresar();
        } else {
            JButton botonEvento = (JButton) ae.getSource();
            if (ultimoBotonSeleccionado != null) {
                ultimoBotonSeleccionado.setBackground(null);
                ultimoBotonSeleccionado.setForeground(null);
                ultimoBotonSeleccionado = botonEvento;
            } else {
                ultimoBotonSeleccionado = botonEvento;
            }
            botonEvento.setBackground(Color.GREEN);
            botonEvento.setForeground(Color.WHITE);
            String text = botonEvento.getText();
            System.out.println(text);
        }
        

    }


    
}
