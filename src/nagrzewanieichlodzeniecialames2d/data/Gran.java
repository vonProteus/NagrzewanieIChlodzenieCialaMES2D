/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data;

/**
 *
 * @author proteus
 */
public class Gran {

    private int N_p;
    private Cor_L[] P;
    private double[] W;
    private double[][] N1;
    private double[][] N2;
    private double[][] Nf;
    private int[] UZEL;

    //<editor-fold defaultstate="collapsed" desc="geters&seters">
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

    public int getN_p() {
	return N_p;
    }

    public void setN_p(int N_p) {
	this.N_p = N_p;
    }

    public double[][] getNf() {
	return Nf;
    }

    public void setNf(double[][] Nf) {
	this.Nf = Nf;
    }

    public Cor_L[] getP() {
	return P;
    }

    public void setP(Cor_L[] P) {
	this.P = P;
    }

    public int[] getUZEL() {
	return UZEL;
    }

    public void setUZEL(int[] UZEL) {
	this.UZEL = UZEL;
    }

    public double[] getW() {
	return W;
    }

    public void setW(double[] W) {
	this.W = W;
    }
    //</editor-fold>
}
