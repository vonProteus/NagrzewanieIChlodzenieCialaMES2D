/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data.my_typ;

/**
 *
 * @author proteus
 */
public class REOLO {

    private double A, m1, m2, m3, m4;
    //Si=A*exp(-m1*t)*Epsi**m2*Ei**m3*exp(-m4*Epsi)
    private int Kod_REO_MOD;
    private String marka_mat;
    private double C, R, K, Ct, Rt, Kt;

    //<editor-fold defaultstate="collapsed" desc="seters&geters">
    public double getA() {
	return A;
    }

    public void setA(double A) {
	this.A = A;
    }

    public double getC() {
	return C;
    }

    public void setC(double C) {
	this.C = C;
    }

    public double getCt() {
	return Ct;
    }

    public void setCt(double Ct) {
	this.Ct = Ct;
    }

    public double getK() {
	return K;
    }

    public void setK(double K) {
	this.K = K;
    }

    public int getKod_REO_MOD() {
	return Kod_REO_MOD;
    }

    public void setKod_REO_MOD(int Kod_REO_MOD) {
	this.Kod_REO_MOD = Kod_REO_MOD;
    }

    public double getKt() {
	return Kt;
    }

    public void setKt(double Kt) {
	this.Kt = Kt;
    }

    public double getR() {
	return R;
    }

    public void setR(double R) {
	this.R = R;
    }

    public double getRt() {
	return Rt;
    }

    public void setRt(double Rt) {
	this.Rt = Rt;
    }

    public double getM1() {
	return m1;
    }

    public void setM1(double m1) {
	this.m1 = m1;
    }

    public double getM2() {
	return m2;
    }

    public void setM2(double m2) {
	this.m2 = m2;
    }

    public double getM3() {
	return m3;
    }

    public void setM3(double m3) {
	this.m3 = m3;
    }

    public double getM4() {
	return m4;
    }

    public void setM4(double m4) {
	this.m4 = m4;
    }

    public String getMarka_mat() {
	return marka_mat;
    }

    public void setMarka_mat(String marka_mat) {
	this.marka_mat = marka_mat;
    }
    //</editor-fold>
}
