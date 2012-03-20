/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data.my_typ;

/**
 *
 * @author proteus
 */
public class ReturnFromJacob_2d {

    double[][] J_;
    double[][] J_inv;
    int p;
    int N_p;
    int NBN;
    double[][] N1;
    double[][] N2;
    double[] X;
    double[] Y;
    double DetJ;
    int i;
    
    //<editor-fold defaultstate="collapsed" desc="geters&seters">
     public int getI() {
	return i;
    }

    public void setI(int i) {
	this.i = i;
    }
    
    public double getDetJ() {
	return DetJ;
    }

    public void setDetJ(double DetJ) {
	this.DetJ = DetJ;
    }

    public double[][] getJ_() {
	return J_;
    }

    public void setJ_(double[][] J_) {
	this.J_ = J_;
    }

    public double[][] getJ_inv() {
	return J_inv;
    }

    public void setJ_inv(double[][] J_inv) {
	this.J_inv = J_inv;
    }

    public double[][] getN1() {
	return N1;
    }

    public void setN1(double[][] N1) {
	this.N1 = N1;
    }

    public double[][] getN2() {
	return N2;
    }

    public void setN2(double[][] N2) {
	this.N2 = N2;
    }

    public int getNBN() {
	return NBN;
    }

    public void setNBN(int NBN) {
	this.NBN = NBN;
    }

    public int getN_p() {
	return N_p;
    }

    public void setN_p(int N_p) {
	this.N_p = N_p;
    }

    public double[] getX() {
	return X;
    }

    public void setX(double[] X) {
	this.X = X;
    }

    public double[] getY() {
	return Y;
    }

    public void setY(double[] Y) {
	this.Y = Y;
    }

    public int getP() {
	return p;
    }

    public void setP(int p) {
	this.p = p;
    }
    //</editor-fold>
}
