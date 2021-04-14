/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Modelo;
import Modelo.Ruta;
import Vista.Administrador;
import Vista.Principal;
import Vista.Rutas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jorge
 */
public class ControladorRutas implements ActionListener {

    private Rutas vRutas;
    private Administrador vAdministrador;
    private Modelo modelo;

    public ControladorRutas(Rutas vRutas, Administrador vAdministrador, Modelo modelo) {
        this.vRutas = vRutas;
        this.vAdministrador = vAdministrador;
        this.modelo = modelo;

        vRutas.setControlador(this);
        vRutas.setModelo(modelo);
    }

    public void Regresar() {
        this.vAdministrador.setVisible(true);
        this.vRutas.setVisible(false);
        this.vRutas.dispose();
    }

    private void RegistrarRuta() {
        if (!emptyFields()) {
            String numRuta = vRutas.getTxtNumRuta().getText();
            String origen = (String) vRutas.getCmbOrigen().getSelectedItem();
            String destino = (String) vRutas.getCmbDestino().getSelectedItem();
            String fechaSalida = vRutas.getTxtFechaSalida().getText();
            String fechaLlegada = vRutas.getTxtFechaLlegada().getText();
            //String diaSemana = vRutas.getTxtFechaSalida().get(Calendar.DAY_OF_WEEK));
            int duracionHora = Integer.parseInt(vRutas.getTxtDuracionHoras().getText());
            int duracionMin = Integer.parseInt(vRutas.getTxtDuracionMinutos().getText());

            //Ruta ruta = new Ruta();
            try {
                //modelo.insertarRuta(ruta);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Ruta registrado correctamente.");

        }//CIERRE IF
        else {
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }
    }

    public boolean emptyFields() {
        if (vRutas.getTxtNumRuta().getText().equals("")
                || vRutas.getCmbOrigen().toString().isEmpty()
                || vRutas.getCmbDestino().toString().isEmpty()
                || vRutas.getTxtDuracionHoras().getText().equals("")
                || vRutas.getTxtDuracionMinutos().getText().equals("")
                || vRutas.getTxtFechaSalida().getText().equals("")
                || vRutas.getTxtFechaLlegada().getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Agregar":
                RegistrarRuta();
                break;
            case "Regresar":
                Regresar();
                break;
            case "Modificar":

                break;
            case "Eliminar":

                break;

        }
    }

}
