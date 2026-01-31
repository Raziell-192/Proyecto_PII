package controllers;

import views.SystemViewResponsive;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author jakim
 */
public class SettingsControllers implements MouseListener {

    private SystemViewResponsive views;

    public SettingsControllers(SystemViewResponsive views) {
        this.views = views;
        this.views.Usuario.addMouseListener(this);
        this.views.Paciente.addMouseListener(this);
        this.views.Venta.addMouseListener(this);
        this.views.Insumos.addMouseListener(this);
        this.views.Tratamiento.addMouseListener(this);
        this.views.Clinicos.addMouseListener(this);
        this.views.Capturas.addMouseListener(this);
        this.views.Configuracion.addMouseListener(this);
        this.views.jPanelBtnSalir.addMouseListener(this);
        this.views.btnSalir.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == views.Usuario) {
            views.JTabbedPane.setSelectedIndex(0);

        } else if (e.getSource() == views.Paciente) {
            views.JTabbedPane.setSelectedIndex(1);

        } else if (e.getSource() == views.Venta) {
            views.JTabbedPane.setSelectedIndex(2);

        } else if (e.getSource() == views.Insumos) {
            views.JTabbedPane.setSelectedIndex(3);

        } else if (e.getSource() == views.Tratamiento) {
            views.JTabbedPane.setSelectedIndex(4);

        } else if (e.getSource() == views.Clinicos) {
            views.JTabbedPane.setSelectedIndex(5);

        } else if (e.getSource() == views.Capturas) {
            views.JTabbedPane.setSelectedIndex(6);

        } else if (e.getSource() == views.Configuracion) {
            views.JTabbedPane.setSelectedIndex(7);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == views.Usuario) {
            views.jPanelUsuarios.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Paciente) {
            views.jPanelPacientes.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Venta) {
            views.jPanelVenta.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Insumos) {
            views.jPanelInsumos.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Tratamiento) {
            views.jPanelTratamiento.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Clinicos) {
            views.jPanelAdministradoresClinicos.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Capturas) {
            views.jPanelCapturasEstado.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Configuracion) {
            views.jPanelConfiguracion.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.jPanelBtnSalir || e.getSource() == views.btnSalir) {
            views.jPanelBtnSalir.setBackground(new Color(59, 130, 246));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == views.Usuario) {
            views.jPanelUsuarios.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Paciente) {
            views.jPanelPacientes.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Venta) {
            views.jPanelVenta.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Insumos) {
            views.jPanelInsumos.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Tratamiento) {
            views.jPanelTratamiento.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Clinicos) {
            views.jPanelAdministradoresClinicos.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Capturas) {
            views.jPanelCapturasEstado.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Configuracion) {
            views.jPanelConfiguracion.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.jPanelBtnSalir || e.getSource() == views.btnSalir) {
            views.jPanelBtnSalir.setBackground(new Color(30, 42, 74));
        }
    }

}
