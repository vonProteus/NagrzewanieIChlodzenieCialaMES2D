/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data.my_typ;

/**
 *
 * @author proteus
 */
public class ELEM {

    private int nbn;
    private int nbnp;
    private int N_p;
    private double[][] N1;
    private double[][] N2;
    private double[][] Nf;
    private Cor_L[] P;
    private Cor_L[] L;
    private double[] W;
    private Gran[] Sf = new Gran[4];

    //<editor-fold defaultstate="collapsed" desc="seters&geters">
    public Cor_L[] getL() {
	return L;
    }

    public void setL(Cor_L[] L) {
	this.L = L;
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

    public Gran[] getSf() {
	return Sf;
    }

    public void setSf(Gran[] Sf) {
	this.Sf = Sf;
    }

    public double[] getW() {
	return W;
    }

    public void setW(double[] W) {
	this.W = W;
    }

    public int getNbn() {
	return nbn;
    }

    public void setNbn(int nbn) {
	this.nbn = nbn;
    }

    public int getNbnp() {
	return nbnp;
    }

    public void setNbnp(int nbnp) {
	this.nbnp = nbnp;
    }
    //</editor-fold>
}
