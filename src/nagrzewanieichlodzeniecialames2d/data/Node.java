/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data;

/**
 *
 * @author proteus
 */
public class Node {
    private double x;
    private double y;
    private double t;
    private double CR;
    
    private int status;
    
    //<editor-fold defaultstate="collapsed" desc="seters&geters">
    
   
    
    public double getCR() {
	return CR;
    }

    public void setCR(double CR) {
	this.CR = CR;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public double getT() {
	return t;
    }

    public void setT(double t) {
	this.t = t;
    }

    public double getX() {
	return x;
    }

    public void setX(double x) {
	this.x = x;
    }

    public double getY() {
	return y;
    }

    public void setY(double y) {
	this.y = y;
    }
    
     //</editor-fold>
}
