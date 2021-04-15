/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ControladorAsientos;
import Modelo.Modelo;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author groya
 */
public final class VistaAsientos extends JFrame implements Observer{
    
    private ControladorAsientos controlador;
    private Modelo modelo;

    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JPanel panelAsientos;
    private JButton[][] matrizBotones;
    private JButton btnComprar;
    private JButton btnRegresar;
    
    public VistaAsientos() {
        configurarVentana();
        inicializarComponentes();
        
        
    }

    public void setControlador(ControladorAsientos controlador) {
        this.controlador = controlador;
        //Action listener
        this.btnComprar.addActionListener(controlador);
        this.btnRegresar.addActionListener(controlador);
    }
    
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
        this.modelo.addObserver(this);
    }
    
    private void configurarVentana() {
        this.setTitle("Reserva de asiento");
        this.setSize(600,500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelAsientos = new JPanel();
        
        btnComprar = new JButton("Comprar");
        btnComprar.setActionCommand("Comprar");
        
        btnRegresar = new JButton("Regresar");
        btnRegresar.setActionCommand("Regresar");
        
        panelBotones.add(btnComprar);
        panelBotones.add(btnRegresar);
        
        panelPrincipal.add(panelBotones,BorderLayout.PAGE_START);
        //panelPrincipal.add(btnComprar, BorderLayout.PAGE_START);
    }
    
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    public JPanel getPanelAsientos() {
        return panelAsientos;
    }

    public JButton[][] getMatrizBotones() {
        return matrizBotones;
    }
    
    public void setPanelAsientos(JPanel panelAsientos) {
        this.panelAsientos = panelAsientos;
    }

    public void setMatrizBotones(JButton[][] matrizBotones) {
        this.matrizBotones = matrizBotones;
    }
    
    /*private void inicializarComponentes() {
        panelAsientos = new JPanel();
        panelAsientos.setLayout(new GridLayout(6, 10));
        matrizBotones = new JButton[6][10];
        
        BotonAsiento boton;
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                boton = new BotonAsiento();
                boton.setText(abecedario[fila]+(columna + 1));
                boton.setBounds(75 * columna, 30 * fila, 75, 30);                
                matrizBotones[fila][columna] = boton;
                panelAsientos.add(matrizBotones[fila][columna]);
            }
        }
        this.setContentPane(panelAsientos);
        this.setVisible(true);
    }*/

    @Override
    public void update(Observable o, Object o1) {
       
    }
    
}
