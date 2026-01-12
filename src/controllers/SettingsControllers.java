package controllers;

import Views.SystemViewResponsive ;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author jakim
 */
public class SettingsControllers implements MouseListener {

    private SystemViewResponsive  Views;

    public SettingsControllers(SystemViewResponsive  Views) {
        this.Views = Views;
        this.Views.Usuario.addMouseListener(this);
        this.Views.Estudiante.addMouseListener(this);
        this.Views.Profesores.addMouseListener(this);
        this.Views.Trabajadores.addMouseListener(this);
        this.Views.Administradores.addMouseListener(this);
        this.Views.Clinicos.addMouseListener(this);
        this.Views.Capturas.addMouseListener(this);
        this.Views.Configuracion.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == Views.Usuario) {
            Views.jPanelUsuarios.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Estudiante) {
            Views.jPanelEstudiantes.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Profesores) {
            Views.jPanelProfesores.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Trabajadores) {
            Views.jPanelTrabajadores.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Administradores) {
            Views.jPanelAdministradores.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Clinicos) {
            Views.jPanelAdministradoresClinicos.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Capturas) {
            Views.jPanelCapturasEstado.setBackground(new Color(152, 202, 63));
        } else if (e.getSource() == Views.Configuracion) {
            Views.jPanelConfiguracion.setBackground(new Color(152, 202, 63));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == Views.Usuario) {
            Views.jPanelUsuarios.setBackground(new Color(0, 131, 195));
        } else if (e.getSource() == Views.Estudiante) {
            Views.jPanelEstudiantes.setBackground(new Color(77,130,188));
        } else if (e.getSource() == Views.Profesores) {
            Views.jPanelProfesores.setBackground(new Color(77,130,188));
        } else if (e.getSource() == Views.Trabajadores) {
            Views.jPanelTrabajadores.setBackground(new Color(77,130,188));
        } else if (e.getSource() == Views.Administradores) {
            Views.jPanelAdministradores.setBackground(new Color(77,130,188));
        } else if (e.getSource() == Views.Clinicos) {
            Views.jPanelAdministradoresClinicos.setBackground(new Color(77,130,188));
        } else if (e.getSource() == Views.Capturas) {
            Views.jPanelCapturasEstado.setBackground(new Color(77,130,188));
        } else if (e.getSource() == Views.Configuracion) {
            Views.jPanelConfiguracion.setBackground(new Color(77,130,188));
        }
    }

}
