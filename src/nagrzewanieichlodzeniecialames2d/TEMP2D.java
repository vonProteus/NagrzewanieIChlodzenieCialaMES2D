/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d;

import nagrzewanieichlodzeniecialames2d.data.my_typ.ELEM;
import nagrzewanieichlodzeniecialames2d.data.my_typ.Gr2d;

/**
 *
 * @author proteus
 */
public class TEMP2D {

    //<editor-fold defaultstate="collapsed" desc="GlobData">
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
    //</editor-fold>

    public void go() {
	double Asr, dTauMax, TauP;
	int iTau, n, Ntau, i, iErr;
	this.IniEL4();
	this.InpData();
	this.GenGrid2d(mH0, mB0, mNhH, mNhB, mGr);
	this.SetControlPoints();
	this.ALLOCATE_Matrix();

	this.WriteControlPointsBegin();


	Asr = mK / (mC * mR);
	mdTime = ((double) (mB0 / (mNhB))) * ((double) (mB0 / (mNhB))) / (0.5 * Asr);
	this.WriteControlPoints();

	Ntau = ((int) (mTime / mdTime)) + 1;
	mdTime = mTime / ((double) (Ntau));

	mTau = 0;

	for (n = 1; n  < Ntau;  ++n) {
	    mTau = mTau + mdTime;
	    this.SOLVER();
	    this.WriteControlPoints();
	}

    }

    private void IniEL4() {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void InpData() {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void GenGrid2d(double _mH0, double _mB0, int _mNhH, int _mNhB, Gr2d _mGr) {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void SetControlPoints() {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void ALLOCATE_Matrix() {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void WriteControlPointsBegin() {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void WriteControlPoints() {
	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void SOLVER() {
	throw new UnsupportedOperationException("Not yet implemented");
    }
}
