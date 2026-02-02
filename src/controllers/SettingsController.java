package controllers;

import views.SystemViewResponsive;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;

/**
 *
 * @author jakim
 */
public class SettingsController implements MouseListener {

    private SystemViewResponsive views;

    public SettingsController(SystemViewResponsive views) {
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
        this.views.pnlBtnConfigGeneral.addMouseListener(this);
        this.views.pnlBtnCitasYAgendas.addMouseListener(this);
        this.views.pnlBtnReportes.addMouseListener(this);
        this.views.pnlBtnSistemas.addMouseListener(this);
        this.views.pnlBtnUsuariosYRoles.addMouseListener(this);
        this.views.pnlBtnVentasYFacturas.addMouseListener(this);
        this.views.pnlBtnSistemas1.addMouseListener(this);
        this.views.pnlBtnSistemas2.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == views.Usuario) {
//            views.JTabbedPane.setSelectedIndex(0);
            views.JTabbedPane.setSelectedComponent(views.pnlUsuarios);
        } else if (e.getSource() == views.Paciente) {
//            views.JTabbedPane.setSelectedIndex(1);
            views.JTabbedPane.setSelectedComponent(views.pnlPacientes);
        } else if (e.getSource() == views.Venta) {
//            views.JTabbedPane.setSelectedIndex(2);
            views.JTabbedPane.setSelectedComponent(views.pnlVentas);
        } else if (e.getSource() == views.Insumos) {
//            views.JTabbedPane.setSelectedIndex(3);
            views.JTabbedPane.setSelectedComponent(views.pnlInsumos);
        } else if (e.getSource() == views.Tratamiento) {
//            views.JTabbedPane.setSelectedIndex(4);
            views.JTabbedPane.setSelectedComponent(views.pnlTratamiento);
        } else if (e.getSource() == views.Clinicos) {
//            views.JTabbedPane.setSelectedIndex(5);
            views.JTabbedPane.setSelectedComponent(views.pnlCitas);
        } else if (e.getSource() == views.Capturas) {
//            views.JTabbedPane.setSelectedIndex(6);
            views.JTabbedPane.setSelectedComponent(views.pnlReportes);
        } else if (e.getSource() == views.Configuracion) {
//            views.JTabbedPane.setSelectedIndex(7);
            views.JTabbedPane.setSelectedComponent(views.pnlConfiguraciones);
        } else if (e.getSource() == views.pnlBtnConfigGeneral) {
            JDialog dialog = views.getConfiguración();
            dialog.pack();
            dialog.setLocationRelativeTo(views);
            dialog.setVisible(true);
        } else if (e.getSource() == views.pnlBtnReportes) {
            JDialog dialog = views.getDialogReporte();
            dialog.pack();
            dialog.setLocationRelativeTo(views);
            dialog.setVisible(true);
        } else if (e.getSource() == views.pnlBtnSistemas1) {
            JDialog dialog = views.getDialogCategorias();
            dialog.pack();
            dialog.setLocationRelativeTo(views);
            dialog.setVisible(true);
        } else if (e.getSource() == views.pnlBtnSistemas2) {
            JDialog dialog = views.getDialogTipoPrecio();
            dialog.pack();
            dialog.setLocationRelativeTo(views);
            dialog.setVisible(true);
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
            views.jPanelCitas.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Capturas) {
            views.jPanelReportes.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.Configuracion) {
            views.jPanelConfiguracion.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.jPanelBtnSalir || e.getSource() == views.btnSalir) {
            views.jPanelBtnSalir.setBackground(new Color(59, 130, 246));
        } else if (e.getSource() == views.pnlBtnConfigGeneral) {
            views.pnlBtnConfigGeneral.setBackground(new Color(220, 235, 247));
            views.pnlBtnConfigGeneralContent.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnReportes) {
            views.pnlBtnReportes.setBackground(new Color(220, 235, 247));
            views.pnlBtnReportesContent.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnCitasYAgendas) {
            views.pnlBtnCitasYAgendas.setBackground(new Color(220, 235, 247));
            views.pnlBtnCitasYAgendasContent.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnSistemas) {
            views.pnlBtnSistemas.setBackground(new Color(220, 235, 247));
            views.pnlBtnSistemasContent.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnUsuariosYRoles) {
            views.pnlBtnUsuariosYRoles.setBackground(new Color(220, 235, 247));
            views.pnlBtnUsuariosYRolesContent.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnVentasYFacturas) {
            views.pnlBtnVentasYFacturas.setBackground(new Color(220, 235, 247));
            views.pnlBtnVentasYFacturasContent.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnSistemas1) {
            views.pnlBtnSistemas1.setBackground(new Color(220, 235, 247));
            views.pnlBtnSistemasContent1.setBackground(new Color(220, 235, 247));
        } else if (e.getSource() == views.pnlBtnSistemas2) {
            views.pnlBtnSistemas2.setBackground(new Color(220, 235, 247));
            views.pnlBtnSistemasContent2.setBackground(new Color(220, 235, 247));
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
            views.jPanelCitas.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Capturas) {
            views.jPanelReportes.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.Configuracion) {
            views.jPanelConfiguracion.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.jPanelBtnSalir || e.getSource() == views.btnSalir) {
            views.jPanelBtnSalir.setBackground(new Color(30, 42, 74));
        } else if (e.getSource() == views.pnlBtnConfigGeneral) {
            views.pnlBtnConfigGeneral.setBackground(new Color(255, 255, 255));
            views.pnlBtnConfigGeneralContent.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnReportes) {
            views.pnlBtnReportes.setBackground(new Color(255, 255, 255));
            views.pnlBtnReportesContent.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnCitasYAgendas) {
            views.pnlBtnCitasYAgendas.setBackground(new Color(255, 255, 255));
            views.pnlBtnCitasYAgendasContent.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnSistemas) {
            views.pnlBtnSistemas.setBackground(new Color(255, 255, 255));
            views.pnlBtnSistemasContent.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnUsuariosYRoles) {
            views.pnlBtnUsuariosYRoles.setBackground(new Color(255, 255, 255));
            views.pnlBtnUsuariosYRolesContent.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnVentasYFacturas) {
            views.pnlBtnVentasYFacturas.setBackground(new Color(255, 255, 255));
            views.pnlBtnVentasYFacturasContent.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnSistemas1) {
            views.pnlBtnSistemas1.setBackground(new Color(255, 255, 255));
            views.pnlBtnSistemasContent1.setBackground(new Color(255, 255, 255));
        } else if (e.getSource() == views.pnlBtnSistemas2) {
            views.pnlBtnSistemas2.setBackground(new Color(255, 255, 255));
            views.pnlBtnSistemasContent2.setBackground(new Color(255, 255, 255));
        }
    }

}
