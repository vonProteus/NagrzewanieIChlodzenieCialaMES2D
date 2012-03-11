/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data;

import nagrzewanieichlodzeniecialames2d.data.my_typ.ELEM;
import nagrzewanieichlodzeniecialames2d.data.my_typ.Gr2d;

/**
 *
 * @author proteus
 */
public class GlobData {

    private double mTbegin;
    private double mTime;
    private double mdTime;
    private double mTau;
    private double mT_otoczenia;
    private double mAlfa;
    private double mH0;
    private double mB0;
    private int mNhH;
    private int mNhB;
    private int mLDA;
    private double mC;
    private double mK;
    private double mR;
    private Gr2d mGr;
    private ELEM mEL4;
    private int[] mContrPoints = new int[9];
    private double[] mcpX = new double[9];
    private double[] mcpY = new double[9];
    private double[][] est = new double[4][4];
    private double[] r = new double[4];
    private double[] mB;
    private double[][] mA;
    private double[] mX;

    //<editor-fold defaultstate="collapsed" desc="getter&setters">
    public double[][] getEst() {
	return est;
    }

    public void setEst(double[][] est) {
	this.est = est;
    }

    public double[][] getmA() {
	return mA;
    }

    public void setmA(double[][] mA) {
	this.mA = mA;
    }

    public double getmAlfa() {
	return mAlfa;
    }

    public void setmAlfa(double mAlfa) {
	this.mAlfa = mAlfa;
    }

    public double[] getmB() {
	return mB;
    }

    public void setmB(double[] mB) {
	this.mB = mB;
    }

    public double getmB0() {
	return mB0;
    }

    public void setmB0(double mB0) {
	this.mB0 = mB0;
    }

    public double getmC() {
	return mC;
    }

    public void setmC(double mC) {
	this.mC = mC;
    }

    public int[] getmContrPoints() {
	return mContrPoints;
    }

    public void setmContrPoints(int[] mContrPoints) {
	this.mContrPoints = mContrPoints;
    }

    public ELEM getmEL4() {
	return mEL4;
    }

    public void setmEL4(ELEM mEL4) {
	this.mEL4 = mEL4;
    }

    public Gr2d getmGr() {
	return mGr;
    }

    public void setmGr(Gr2d mGr) {
	this.mGr = mGr;
    }

    public double getmH0() {
	return mH0;
    }

    public void setmH0(double mH0) {
	this.mH0 = mH0;
    }

    public double getmK() {
	return mK;
    }

    public void setmK(double mK) {
	this.mK = mK;
    }

    public int getmLDA() {
	return mLDA;
    }

    public void setmLDA(int mLDA) {
	this.mLDA = mLDA;
    }

    public int getmNhB() {
	return mNhB;
    }

    public void setmNhB(int mNhB) {
	this.mNhB = mNhB;
    }

    public int getmNhH() {
	return mNhH;
    }

    public void setmNhH(int mNhH) {
	this.mNhH = mNhH;
    }

    public double getmR() {
	return mR;
    }

    public void setmR(double mR) {
	this.mR = mR;
    }

    public double getmT_otoczenia() {
	return mT_otoczenia;
    }

    public void setmT_otoczenia(double mT_otoczenia) {
	this.mT_otoczenia = mT_otoczenia;
    }

    public double getmTau() {
	return mTau;
    }

    public void setmTau(double mTau) {
	this.mTau = mTau;
    }

    public double getmTbegin() {
	return mTbegin;
    }

    public void setmTbegin(double mTbegin) {
	this.mTbegin = mTbegin;
    }

    public double getmTime() {
	return mTime;
    }

    public void setmTime(double mTime) {
	this.mTime = mTime;
    }

    public double[] getmX() {
	return mX;
    }

    public void setmX(double[] mX) {
	this.mX = mX;
    }

    public double[] getMcpX() {
	return mcpX;
    }

    public void setMcpX(double[] mcpX) {
	this.mcpX = mcpX;
    }

    public double[] getMcpY() {
	return mcpY;
    }

    public void setMcpY(double[] mcpY) {
	this.mcpY = mcpY;
    }

    public double getMdTime() {
	return mdTime;
    }

    public void setMdTime(double mdTime) {
	this.mdTime = mdTime;
    }

    public double[] getR() {
	return r;
    }

    public void setR(double[] r) {
	this.r = r;
    }
    //</editor-fold>
}
