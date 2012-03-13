/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d;

import java.io.File;
import java.io.FileInputStream;
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
	mEL4 = new ELEM();
	double Alfa, L1, L2, e, n, SN;
	int iP, i;

	Alfa = 1 / (Math.sqrt(3.0));
	mEL4.setNbn(4); 
	mEL4.setN_p(4);
	
	mEL4.allocateN12fPWL();
	
	
	for (int a = 0; a < mEL4.getW().length; ++a) {
	    mEL4.getW()[a] = 1.0;
	}

	
	
	mEL4.getP(1).setE(-Alfa);
	mEL4.getP(1).setN(-Alfa);
	mEL4.getP(2).setE(Alfa);
	mEL4.getP(2).setN(-Alfa);
	mEL4.getP(3).setE(Alfa);
	mEL4.getP(3).setN(Alfa);
	mEL4.getP(4).setE(-Alfa);
	mEL4.getP(4).setN(Alfa);

	mEL4.getL(1).setE(-1);
	mEL4.getL(1).setN(-1);
	mEL4.getL(2).setE(1);
	mEL4.getL(2).setN(-1);
	mEL4.getL(3).setE(1);
	mEL4.getL(3).setN(1);
	mEL4.getL(4).setE(-1);
	mEL4.getL(4).setN(1);

	for (iP = 1; iP <= mEL4.getN_p(); ++iP) {
	    L1 = mEL4.getP(iP).getE();
	    L2 = mEL4.getP(iP).getN();

	    mEL4.setNf(1, iP, 0.25 * (1 - L1) * (1 - L2));
	    mEL4.setN1(1, iP, -0.25 * (1 - L2));
	    mEL4.setN2(1, iP, -0.25 * (1 - L1));

	    mEL4.setNf(2, iP, 0.25 * (1 + L1) * (1 - L2));
	    mEL4.setN1(2, iP, 0.25 * (1 - L2));
	    mEL4.setN2(2, iP, -0.25 * (1 + L1));

	    mEL4.setNf(3, iP, 0.25 * (1 + L1) * (1 + L2));
	    mEL4.setN1(3, iP, 0.25 * (1 + L2));
	    mEL4.setN2(3, iP, 0.25 * (1 + L1));

	    mEL4.setNf(4, iP, 0.25 * (1 - L1) * (1 + L2));
	    mEL4.setN1(4, iP, -0.25 * (1 + L2));
	    mEL4.setN2(4, iP, 0.25 * (1 - L1));
	}


	for (i = 1; i <= 4; ++i) {
	    mEL4.getSf(i).setN_p(2);
	    mEL4.getSf(i).allocatePW();

	    for (int a = 0; a < mEL4.getSf(i).getW().length; ++a) {
		mEL4.getSf(i).getW()[a] = 1;
//		    System.out.print("i "+i+" a "+ a +" : "+mEL4.getSf(i).getW()[a]+" \n");
	    }

	    mEL4.getSf(i).allocateN12dUzel(mEL4.getNbn(), 2);
	}

	mEL4.getSf(1).setUZEL(1, 4);
	mEL4.getSf(1).setUZEL(2, 1);
	
	mEL4.getSf(2).setUZEL(1, 1);
	mEL4.getSf(2).setUZEL(2, 2);
	
	mEL4.getSf(3).setUZEL(1, 2);
	mEL4.getSf(3).setUZEL(2, 3);
	
	mEL4.getSf(4).setUZEL(1, 3);
	mEL4.getSf(4).setUZEL(2, 4);

	
	mEL4.getSf(1).getP(1).setN(Alfa);
	mEL4.getSf(1).getP(1).setE(-1);
	mEL4.getSf(1).getP(2).setN(-Alfa);
	mEL4.getSf(1).getP(2).setE(-1);
	mEL4.getSf(2).getP(1).setN(-1);
	mEL4.getSf(2).getP(1).setE(-Alfa);
	mEL4.getSf(2).getP(2).setN(-1);
	mEL4.getSf(2).getP(2).setE(Alfa);
	mEL4.getSf(3).getP(1).setN(-Alfa);
	mEL4.getSf(3).getP(1).setE(1);
	mEL4.getSf(3).getP(2).setN(Alfa);
	mEL4.getSf(3).getP(2).setE(1);
	mEL4.getSf(4).getP(1).setN(1);
	mEL4.getSf(4).getP(1).setE(Alfa);
	mEL4.getSf(4).getP(2).setN(1);
	mEL4.getSf(4).getP(2).setE(-Alfa);
	
	
	
	for (i = 1; i <= 4; ++i) {
	    for (iP = 1; iP <= 2; ++iP) {
		e = mEL4.getSf(i).getP(iP).getE();
		n = mEL4.getSf(i).getP(iP).getN();

		// tu zmineione ip na iP
		mEL4.getSf(i).setNf(1, iP, 0.25 * (1 - e) * (1 - n));
		mEL4.getSf(i).setNf(2, iP, 0.25 * (1 + e) * (1 - n));
		mEL4.getSf(i).setNf(3, iP, 0.25 * (1 + e) * (1 + n));
		mEL4.getSf(i).setNf(4, iP, 0.25 * (1 - e) * (1 + n));


		SN = mEL4.getSf(i).getNf(1, iP) + mEL4.getSf(i).getNf(2, iP) + mEL4.getSf(i).getNf(3, iP) + mEL4.getSf(i).getNf(4, iP);

		mEL4.getSf(i).setN1(1, iP, -0.25 * (1 - n));
		mEL4.getSf(i).setN1(2, iP, 0.25 * (1 - n));
		mEL4.getSf(i).setN1(3, iP, 0.25 * (1 + n));
		mEL4.getSf(i).setN1(4, iP, -0.25 * (1 + n));

		mEL4.getSf(i).setN2(1, iP, -0.25 * (1 - e));
		mEL4.getSf(i).setN2(2, iP, -0.25 * (1 + e));
		mEL4.getSf(i).setN2(3, iP, 0.25 * (1 + e));
		mEL4.getSf(i).setN2(4, iP, 0.25 * (1 - e));
	    }
	}

	//throw new UnsupportedOperationException("Not yet implemented IniEL4");
    }

    private void InpData() {
	
	File file = new File("/Users/proteus/Documents/AGH/rok5/semestr10/Zaawansowane metody obliczeń w inżynierii/temp2d/indata.t2d");
	int ch;
	StringBuffer strContener = new StringBuffer("");
	FileInputStream fin = null;
	
	try {
	    fin = new FileInputStream(file);
	    
	    while ((ch = fin.read()) != -1) {		
		strContener.append((char)ch);
	    }
	    fin.close();
	} catch (Exception e) {
	    System.out.print(e.getMessage()+"\n");
	    e.printStackTrace();
	}
	
	String[] line = strContener.toString().split("\n");
	
	
	mTbegin = Double.parseDouble(line[4].split(" ")[0]);
	mTime = Double.parseDouble(line[5].split(" ")[0]);
	mdTime = Double.parseDouble(line[6].split(" ")[0]);
	mT_otoczenia = Double.parseDouble(line[7].split(" ")[0]);
	mAlfa = Double.parseDouble(line[8].split(" ")[0]);
	mH0 = Double.parseDouble(line[9].split(" ")[0]);
	mB0 = Double.parseDouble(line[10].split(" ")[0]);
	mNhH = Integer.parseInt(line[11].split(" ")[0]);
	mNhB = Integer.parseInt(line[12].split(" ")[0]);
	mC = Double.parseDouble(line[13].split(" ")[0]);
	mK = Double.parseDouble(line[14].split(" ")[0]);
	mR = Double.parseDouble(line[15].split(" ")[0]);

	
	
	
	//System.out.print(strContener+"\n");
	
	
	
	
	
	throw new UnsupportedOperationException("Not yet implemented InpData");
    }

    private void GenGrid2d(double _mH0, double _mB0, int _mNhH, int _mNhB, Gr2d _mGr) {
	throw new UnsupportedOperationException("Not yet implemented GenGrid2d");
    }

    private void SetControlPoints() {
	throw new UnsupportedOperationException("Not yet implemented SetControlPoints");
    }

    private void ALLOCATE_Matrix() {
	throw new UnsupportedOperationException("Not yet implemented ALLOCATE_Matrix");
    }

    private void WriteControlPointsBegin() {
	throw new UnsupportedOperationException("Not yet implemented WriteControlPointsBegin");
    }

    private void WriteControlPoints() {
	throw new UnsupportedOperationException("Not yet implemented WriteControlPoints");
    }

    private void SOLVER() {
	throw new UnsupportedOperationException("Not yet implemented SOLVER");
    }
}
