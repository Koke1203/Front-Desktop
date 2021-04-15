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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
        MostrarDetalleRutas();
    }

    public void Regresar() {
        this.vAdministrador.setVisible(true);
        this.vRutas.setVisible(false);
        this.vRutas.dispose();
    }

    private void RegistrarRuta() {
        if (!emptyFields()) {
            String numRuta = vRutas.getTxtNumRuta().getText();
            String origen = vRutas.getTxtOrigen().getText();
            String destino = vRutas.getTxtDestino().getText();
            String duracionHoras = vRutas.getTxtDuracionHoras().getText();
            String duracionMinutos = vRutas.getTxtDuracionMinutos().getText();
            String dia = (String) vRutas.getCmbSemana().getSelectedItem();
            String horaSalida = vRutas.getTxtHoraSalida().getText();
            String minutosSalida = vRutas.getTxtMinutosSalida().getText();
            String fechaLlegada = "" + Integer.parseInt(duracionHoras) + Integer.parseInt(horaSalida) + ":"
                    + Integer.parseInt(duracionMinutos) + Integer.parseInt(minutosSalida);

            Ruta ruta = new Ruta(numRuta, origen, destino, Integer.parseInt(duracionHoras), Integer.parseInt(duracionMinutos), dia,
                    Integer.parseInt(horaSalida), Integer.parseInt(minutosSalida), fechaLlegada);
            try {
                modelo.insertarRuta(ruta);
                MostrarDetalleRutas();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Ruta registrado correctamente.");

        }//CIERRE IF
        else {
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }
    }

    private void ModificarRuta() {
        if (!emptyFields()) {
            String numRuta = vRutas.getTxtNumRuta().getText();
            String origen = vRutas.getTxtOrigen().getText();
            String destino = vRutas.getTxtDestino().getText();
            String duracionHoras = vRutas.getTxtDuracionHoras().getText();
            String duracionMinutos = vRutas.getTxtDuracionMinutos().getText();
            String dia = (String) vRutas.getCmbSemana().getSelectedItem();
            String horaSalida = vRutas.getTxtHoraSalida().getText();
            String minutosSalida = vRutas.getTxtMinutosSalida().getText();
            String fechaLlegada = "" + Integer.parseInt(duracionHoras) + Integer.parseInt(horaSalida) + ":"
                    + Integer.parseInt(duracionMinutos) + Integer.parseInt(minutosSalida);

            Ruta ruta = new Ruta(numRuta, origen, destino, Integer.parseInt(duracionHoras), Integer.parseInt(duracionMinutos), dia,
                    Integer.parseInt(horaSalida), Integer.parseInt(minutosSalida), fechaLlegada);
            try {
                modelo.modificarRuta(ruta);
                MostrarDetalleRutas();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Ruta modificada correctamente.");

        }//CIERRE IF
        else {
            JOptionPane.showMessageDialog(null, "Campos no válidos");
        }
    }

    private void EliminarRuta() {
        String numRuta = vRutas.getTxtNumRuta().getText();
        try {
            modelo.eliminarRuta(numRuta);
            MostrarDetalleRutas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean emptyFields() {
        if (vRutas.getTxtNumRuta().getText().equals("")
                || vRutas.getTxtOrigen().getText().equals("")
                || vRutas.getTxtDestino().getText().equals("")
                || vRutas.getTxtDuracionHoras().getText().equals("")
                || vRutas.getTxtDuracionMinutos().getText().equals("")
                || vRutas.getCmbSemana().toString().isEmpty()
                || vRutas.getTxtHoraSalida().getText().equals("")
                || vRutas.getTxtMinutosSalida().getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void MostrarDetalleRutas() {
        ArrayList<Ruta> detallesRutas = modelo.listarRutas();
        if ((detallesRutas != null) && (!detallesRutas.isEmpty())) {
            String[] nombreColumnas = {"#Ruta", "Origen", "Destino", "Duracion Horas", "Duracion Minutos", "Dia", "Hora Salida", "Minutos Salida",};
            DefaultTableModel tableModel = new DefaultTableModel(null, nombreColumnas);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (Ruta detalle : detallesRutas) {
                fila[0] = detalle.getIdRuta();
                fila[1] = detalle.getOrigen();
                fila[2] = detalle.getDestino();
                fila[3] = detalle.getDuracionHoras();
                fila[4] = detalle.getDuracionMinutos();
                fila[5] = detalle.getDiaSemana();
                fila[6] = detalle.getHora();
                fila[7] = detalle.getMinutos();
                tableModel.addRow(fila);
            }
            vRutas.getTableRutas().setModel(tableModel);
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
                ModificarRuta();
                break;
            case "Eliminar":
                EliminarRuta();
                break;

        }
    }

}
