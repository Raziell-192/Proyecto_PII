
/*package controllers;

import Views.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class SettingsControllesr implements MouseListener {
private SystemView Views;

public SettingsControllesr(SystemView Views){
    this.Views = Views;
    this.Views.jLabelPurcharse.addMouseListener(this);
    this.Views.jLabelEmployees.addMouseListener(this);
    this.Views.jLabelCustomers.addMouseListener(this);
    this.Views.jLabelReports.addMouseListener(this);
    this.Views.jLabelSettings.addMouseListener(this);
    this.Views.jLabel10.addMouseListener(this);
    this.Views.Products.addMouseListener(this);
    this.Views.Suppliers.addMouseListener(this);
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
        if (e.getSource() == Views.Products ){
          Views.Products.setBackground(new Color(152,202,63));
        }else if(e.getSource() == Views.jLabelPurcharse){
             Views.jLabelPurcharse.setBackground(new Color(152,202,63));
        }else if(e.getSource() == Views.Suppliers){
             Views.Suppliers.setBackground(new Color(152,202,63));
        }else if(e.getSource() == Views.jLabelReports){    
            Views.Suppliers.setBackground(new Color(152,202,63));
    }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}*/
