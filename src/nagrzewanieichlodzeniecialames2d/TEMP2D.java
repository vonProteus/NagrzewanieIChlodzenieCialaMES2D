/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d;

import java.io.*;
import nagrzewanieichlodzeniecialames2d.data.my_typ.ELEM;
import nagrzewanieichlodzeniecialames2d.data.my_typ.Gr2d;
import nagrzewanieichlodzeniecialames2d.data.my_typ.REOLO;
import nagrzewanieichlodzeniecialames2d.data.my_typ.ReturnFromJacob_2d;
import org.omg.CORBA.INTERNAL;

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
    private double mH1;
    private double mB0;
    private double mV;
    private double mForce;
    private double mS;
    private int mNhH;
    private int mNhB;
    private int mLDA;
    private REOLO mREO = new REOLO();
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
    private double[][] FeSM = new double[9][9];
    private double[] FeRhs = new double[9];
    private double[] mB;
    private double[][] mA;
    private double[] mX;
    //</editor-fold>

    public void go() {
	double Asr, dTauMax, TauP;
	int iTau, n, Ntau, i, iErr;
	this.mGr = new Gr2d();
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

	for (n = 1; n <= Ntau; ++n) {
	    mTau = mTau + mdTime;
	    this.SOLVER();
	    this.WriteControlPoints();
	}

    }

    public void goPLASTOMETR2D() {
	double Asr, dTauMax, TauP;
	int iTau, n, Ntau, i, iErr;
	this.mGr = new Gr2d();
	this.IniEL4();
	this.InpData();
	this.GenGrid2d(mH0, mB0, mNhH, mNhB, mGr);
	this.SetControlPoints();

	Asr = mREO.getK() / (mREO.getC() * mREO.getR());
	mdTime = (((double) (mB0 / (1e3 * mNhB))) * ((double) (mB0 / (1e3 * mNhB)))) / (0.5 * Asr);
	mTime = (mH0 - mH1) / mV;
	Ntau = ((int) (mTime / mdTime)) + 1;
	mdTime = mTime / ((double) (Ntau));

	this.WriteControlPointsBegin();
	this.WriteControlPoints();

	mTau = 0;

	for (n = 1; n <= Ntau; ++n) {
	    mTau = mTau + mdTime;
	    this.ALLOCATE_MatrixTemp();
	    this.SOLVER_T();
	    this.DEALLOCATE_Matrix();

	    this.ALLOCATE_MatrixDef();
	    this.SOLVER_D();
	    this.DEALLOCATE_Matrix();

	    this.WriteControlPoints();
	}

	System.out.print("plastometr2d OK \n");


    }

    private void IniEL4() {
	// 1
	mEL4 = new ELEM();
	double Alfa, L1, L2, e, n, SN;
	int iP, i;

	Alfa = 1 / (Math.sqrt(3.0));
	mEL4.setNbn(4);
	mEL4.setNbnp(1);
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
	    e = mEL4.getP(iP).getE();
	    n = mEL4.getP(iP).getN();

	    mEL4.setNf(1, iP, 0.25 * (1 - e) * (1 - n));
	    mEL4.setN1(1, iP, -0.25 * (1 - n));
	    mEL4.setN2(1, iP, -0.25 * (1 - e));

	    mEL4.setNf(2, iP, 0.25 * (1 + e) * (1 - n));
	    mEL4.setN1(2, iP, 0.25 * (1 - n));
	    mEL4.setN2(2, iP, -0.25 * (1 + e));

	    mEL4.setNf(3, iP, 0.25 * (1 + e) * (1 + n));
	    mEL4.setN1(3, iP, 0.25 * (1 + n));
	    mEL4.setN2(3, iP, 0.25 * (1 + e));

	    mEL4.setNf(4, iP, 0.25 * (1 - e) * (1 + n));
	    mEL4.setN1(4, iP, -0.25 * (1 + n));
	    mEL4.setN2(4, iP, 0.25 * (1 - e));

	    mEL4.setHk(1, iP, 1.0);
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

	File file = new File("indata.p2d");
	int ch;
	StringBuffer strContener = new StringBuffer("");
	FileInputStream fin = null;

	try {
	    fin = new FileInputStream(file);

	    while ((ch = fin.read()) != -1) {
		strContener.append((char) ch);
	    }
	    fin.close();
	} catch (Exception e) {
	    System.out.print(e.getMessage() + "\n");
	    e.printStackTrace();
	}

	String[] line = strContener.toString().split("\n");


	mTbegin = Double.parseDouble(line[4].split(" ")[0]);
	mdTime = Double.parseDouble(line[5].split(" ")[0]);
	mT_otoczenia = Double.parseDouble(line[6].split(" ")[0]);
	mAlfa = Double.parseDouble(line[7].split(" ")[0]);
	mH0 = Double.parseDouble(line[8].split(" ")[0]);
	mH1 = Double.parseDouble(line[9].split(" ")[0]);
	mB0 = Double.parseDouble(line[10].split(" ")[0]);
	mV = Integer.parseInt(line[11].split(" ")[0]);
	mNhH = Integer.parseInt(line[12].split(" ")[0]);
	mNhB = Integer.parseInt(line[13].split(" ")[0]);

	mREO.setC(Double.parseDouble(line[14].split(" ")[0]));
	mREO.setK(Double.parseDouble(line[15].split(" ")[0]));
	mREO.setR(Double.parseDouble(line[16].split(" ")[0]));
	mREO.setA(Double.parseDouble(line[17].split(" ")[0]));
	mREO.setM1(Double.parseDouble(line[18].split(" ")[0]));
	mREO.setM2(Double.parseDouble(line[19].split(" ")[0]));
	mREO.setM3(Double.parseDouble(line[20].split(" ")[0]));
	mREO.setM4(Double.parseDouble(line[21].split(" ")[0]));


    }

    private void GenGrid2d(double Hmax, double Bmax, int nhH, int nhB, Gr2d Gr) {

	int i, j, inh, ine, id;
	int[] St = new int[4];

	int i1, i2, i3, i4;
	double x, y, dx, dy;
	boolean[] St_OK = new boolean[4];




	Gr.setNh(nhH * nhB);
	Gr.setNe((nhH - 1) * (nhB - 1));
	Gr.setNbn(4);
	Gr.setNcn(4);
	Gr.setNhPov(nhB);


	Gr.allocateNdEL();

	dx = Bmax / (nhB - 1);
	dy = Hmax / (nhH - 1);

	x = 0;
	inh = 0;
	for (i = 1; i <= nhB; ++i) {
	    y = 0;
	    for (j = 1; j <= nhH; ++j) {
		inh = inh + 1;
		Gr.getND(inh).setX(x);
		Gr.getND(inh).setY(y);
		Gr.getND(inh).setStatus(0);
		y = y + dy;

		Gr.getND(inh).setVx(0);
		Gr.getND(inh).setVy(0);
		Gr.getND(inh).setS(0);
		Gr.getND(inh).setEx(0);
		Gr.getND(inh).setEpsi(0);
		Gr.getND(inh).setSi(0);
	    }
	    x = x + dx;
	}

	ine = 0;
	for (i = 1; i <= nhB - 1; ++i) {
	    for (j = 1; j <= nhH - 1; ++j) {
		ine = ine + 1;
		i1 = (i - 1) * nhH + j;
		i2 = i * nhH + j;
		i3 = i * nhH + j + 1;
		i4 = (i - 1) * nhH + j + 1;
		Gr.getEL(ine).setNop(1, i1);
		Gr.getEL(ine).setNop(2, i2);
		Gr.getEL(ine).setNop(3, i3);
		Gr.getEL(ine).setNop(4, i4);
	    }
	}

	for (i = 1; i <= Gr.getNh(); ++i) {
	    x = Gr.getND(i).getX();
	    y = Gr.getND(i).getY();
	    Gr.getND(i).setStatus(0);
	    Gr.getND(i).setT(mTbegin);


	    if (x >= (Bmax - 0.00001)) {
		Gr.getND(i).setStatus(1);
	    }

	    if (x <= 0.00001) {
		Gr.getND(i).setStatus(12);
	    }

	    if (y >= (Hmax - 0.00001)) {
		Gr.getND(i).setStatus(9);
		Gr.getND(i).setVy(-mV);
		Gr.getND(i).setVx(0);
	    }
	    if (y <= 0.00001) {
		Gr.getND(i).setStatus(11);
	    }

	    if ((y <= 0.00001) && (x <= 0.00001)) {
		Gr.getND(i).setStatus(8);
	    }
	}

	for (ine = 1; ine <= Gr.getNe(); ++ine) {
	    for (i = 1; i <= 4; ++i) {
		id = Math.abs(Gr.getEL(ine).getNop(i));
		St[i - 1] = Gr.getND(id).getStatus();
	    }
	    St_OK[2 - 1] = (St[1 - 1] >= 1) && (St[2 - 1] >= 1);
	    St_OK[3 - 1] = (St[2 - 1] >= 1) && (St[3 - 1] >= 1);
	    St_OK[4 - 1] = (St[3 - 1] >= 1) && (St[4 - 1] >= 1);
	    St_OK[1 - 1] = (St[4 - 1] >= 1) && (St[1 - 1] >= 1);

	    Gr.getEL(ine).setNpov(0);
	    for (i = 1; i <= 4; ++i) {
		if (St_OK[i - 1]) {
		    Gr.getEL(ine).setNpov(Gr.getEL(ine).getNpov() + 1);
		    Gr.getEL(ine).setaPov(Gr.getEL(ine).getNpov(), i);
		}
	    }
	}




	//throw new UnsupportedOperationException("Not yet implemented GenGrid2d");
    }

    private void SetControlPoints() {


	int i, j;
	double Rr, Rmin;

	mcpX[1 - 1] = 0.0;
	mcpY[1 - 1] = 0.0;
	mcpX[2 - 1] = mB0 / 2;
	mcpY[2 - 1] = 0.0;
	mcpX[3 - 1] = mB0;
	mcpY[3 - 1] = 0.0;
	mcpX[4 - 1] = 0.0;
	mcpY[4 - 1] = mH0 / 2.0;
	mcpX[5 - 1] = mB0 / 2;
	mcpY[5 - 1] = mH0 / 2.0;
	mcpX[6 - 1] = mB0;
	mcpY[6 - 1] = mH0 / 2.0;
	mcpX[7 - 1] = 0.0;
	mcpY[7 - 1] = mH0;
	mcpX[8 - 1] = mB0 / 2;
	mcpY[8 - 1] = mH0;
	mcpX[9 - 1] = mB0;
	mcpY[9 - 1] = mH0;

	for (j = 1; j <= 9; ++j) {
	    Rmin = 1e10;
	    for (i = 1; i <= mGr.getNh(); ++i) {
		Rr = Math.sqrt((mcpX[j - 1] - mGr.getND(i).getX()) * (mcpX[j - 1] - mGr.getND(i).getX()) + (mcpY[j - 1] - mGr.getND(i).getY()) * (mcpY[j - 1] - mGr.getND(i).getY()));
		if (Rr <= Rmin) {
		    mContrPoints[j - 1] = i;
		    Rmin = Rr;
		}
	    }
	}


//	throw new UnsupportedOperationException("Not yet implemented SetControlPoints");
    }

    private void ALLOCATE_Matrix() {

	int NEL, i, j, ii, jj, jB, NeMaxB, ierr;
	int[] nk = new int[4];

	mLDA = 0;
	for (NEL = 1; NEL <= mGr.getNe(); ++NEL) {
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		nk[i - 1] = mGr.getEL(NEL).getNop(i);
	    }
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		ii = nk[i - 1];
		for (j = 1; j <= mEL4.getNbn(); ++j) {
		    jj = nk[j - 1];
		    jB = jj - ii + 1;
		    if (jB >= mLDA) {
			mLDA = jB;
			NeMaxB = NEL;
		    }
		}
	    }
	}



//	mA = new double[mLDA][mGr.getNh()];
	mA = new double[mGr.getNh()][mGr.getNh()];
	mB = new double[mGr.getNh()];
	mX = new double[mGr.getNh()];

//	throw new UnsupportedOperationException("Not yet implemented ALLOCATE_Matrix");
    }

    private void WriteControlPointsBegin() {

	try {
	    FileWriter fstreamT = new FileWriter("OutDataT.txt");
	    BufferedWriter outT = new BufferedWriter(fstreamT);
	    FileWriter fstreamCR = new FileWriter("OutDataCR.txt");
	    BufferedWriter outCR = new BufferedWriter(fstreamCR);

	    outT.write(" ********** Coordinates of the control points ************\n");
	    outCR.write(" ********** Coordinates of the control points ************\n");

	    for (int i = 0; i < 9; ++i) {
		outT.write(" No=" + mContrPoints[i] + " X=" + mcpX[i] + " Y=" + mcpY[i] + "\n");
		outCR.write(" No=" + mContrPoints[i] + " X=" + mcpX[i] + " Y=" + mcpY[i] + "\n");
	    }


	    outT.write(" **********************************************************\n\n\n");
	    outCR.write(" **********************************************************\n\n\n");


	    outCR.close();
	    outT.close();
	} catch (Exception e) {
	    System.out.print(e.getMessage() + "\n");
	    e.printStackTrace();
	}


//	throw new UnsupportedOperationException("Not yet implemented WriteControlPointsBegin");
    }

    private void WriteControlPoints() {


	BufferedWriter outT = null;
	BufferedWriter outCR = null;
	try {
	    FileWriter fstreamT = new FileWriter("OutDataT.txt", true);
	    outT = new BufferedWriter(fstreamT);
	    FileWriter fstreamCR = new FileWriter("OutDataCR.txt", true);
	    outCR = new BufferedWriter(fstreamCR);

	    StringBuffer stringCR = new StringBuffer(mTau + ";");
	    StringBuffer stringT = new StringBuffer(mTau + ";");

	    for (int a = 1; a <= 9; ++a) {
		stringT.append(mGr.getND(mContrPoints[a - 1]).getT() + ";");
		stringCR.append(mGr.getND(mContrPoints[a - 1]).getCR() + ";");
	    }

	    outCR.write(stringCR.toString());
	    outT.write(stringT.toString());

	    System.out.print(mTau + " " + mGr.getND(mContrPoints[1]).getT() + " " + mGr.getND(mContrPoints[3]).getT() + " " + mGr.getND(mContrPoints[4]).getT() + " " + mGr.getND(mContrPoints[6]).getT() + "\n");

	    outT.newLine();
	    outT.flush();
	    outCR.newLine();
	    outCR.flush();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	} finally {                       // always close the file
	    if (outCR != null) {
		try {
		    outCR.close();
		} catch (IOException ioe2) {
		    // just ignore it
		}
	    }
	    if (outT != null) {
		try {

		    outT.close();
		} catch (IOException ioe2) {
		    // just ignore it
		}

	    }

	} // end try/catch/finally



//	throw new UnsupportedOperationException("Not yet implemented WriteControlPoints");
    }

    private void SOLVER() {
	int NEL;
	int[] nk = new int[4];
	int jB, iB, NeMaxB, NCODA, i, j, ii, jj;
	int iErr;

	for (int a = 0; a < mA.length; ++a) {
	    for (int b = 0; b < mA[0].length; ++b) {
		mA[a][b] = 0;
	    }
	}

	for (int a = 0; a < mB.length; ++a) {
	    mB[a] = 0;
	}

	for (int a = 0; a < mX.length; ++a) {
	    mX[a] = 0;
	}


	for (NEL = 1; NEL <= mGr.getNe(); ++NEL) {
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		nk[i - 1] = mGr.getEL(NEL).getNop(i);
	    }


	    this.FeSM_heat(NEL);

	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		ii = nk[i - 1];
		for (j = 1; j <= mEL4.getNbn(); ++j) {
		    jj = nk[j - 1];
		    iB = mLDA + ii - jj;
//		    if ((jj >= ii) && (iB <= mLDA)) {
//			mA[iB - 1][jj - 1] = mA[iB - 1][jj - 1] + est[i - 1][j - 1];
//		    }
		    mA[ii - 1][jj - 1] = mA[ii - 1][jj - 1] + est[i - 1][j - 1]; // ! wypelnienie pelnej matrycy
		}
		mB[ii - 1] = mB[ii - 1] + r[i - 1];
	    }
	}

//	NCODA = mLDA - 1;
//	this.DLSAQS(mGr.getNh(), mA, mLDA, NCODA, mB, mX);

//	NCODA = mLDA - 1;
// TODO	CALL DLSAQS (mGr%nh, mA, mLDA, NCODA, mB, mX)
// Program rozwiazania ukladu rownan z pasmowej matrycej

	NCODA = 1;
	this.gauss();

//		NCODA=1;
// TODO	CALL DLSARG (mGr%nh, mA, mGr%nh, mB, NCODA, mX)
// Program rozwiπzania ukladu rownan z pelnej matrycej

	for (i = 1; i <= mGr.getNh(); ++i) {
	    mGr.getND(i).setCR((mGr.getND(i).getT() - mX[i - 1]) / mdTime);
	    mGr.getND(i).setT(mX[i - 1]);
	}

//	throw new UnsupportedOperationException("Not yet implemented SOLVER");
    }

    private void FeSM_heat(int NEL) {
	for (int a = 0; a < est.length; ++a) {
	    for (int b = 0; b < est[0].length; ++b) {
		est[a][b] = 0;
	    }
	}
	for (int a = 0; a < r.length; ++a) {
	    r[a] = 0;
	}
	this.PRE_heat_mat(NEL);
	this.PRE_heat_pov_mat(NEL);

//	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void PRE_heat_mat(int NEL) {

	int I, j, N, P, Id;
	double[][] J_, J_inv; //! Matruca Jakobiego i dwrotna matryca 2x2
	double DetJ, Ni, Nn, Hin, Cin;
	double T0p;
	double[] Ndx, Ndy;  //! Pochodny funkcji ksztaltu wzgledem X i Y vec4
	double[] X, Y, Temp_0; // vec4 

	J_ = new double[2][2];
	J_inv = new double[2][2];
	Ndx = new double[4];
	Ndy = new double[4];
	X = new double[4];
	Y = new double[4];
	Temp_0 = new double[4];
	DetJ = 0;


	for (I = 1; I <= mEL4.getNbn(); ++I) {
	    Id = Math.abs(mGr.getEL(NEL).getNop(I));
	    X[I - 1] = mGr.getND(Id).getX() / 1000;
	    Y[I - 1] = mGr.getND(Id).getY() / 100;
	    Temp_0[I - 1] = mGr.getND(Id).getT();
	}

	for (P = 1; P <= mEL4.getN_p(); ++P) {
	    ReturnFromJacob_2d jacobReturn = this.Jacob_2d(J_, J_inv, P, mEL4.getN_p(), mEL4.getNbn(), mEL4.getN1(), mEL4.getN2(), X, Y, DetJ);

	    //<editor-fold defaultstate="collapsed" desc="return from Jacob_2d">
	    J_ = jacobReturn.getJ_();
	    J_inv = jacobReturn.getJ_inv();
	    P = jacobReturn.getP();
	    mEL4.setN_p(jacobReturn.getN_p());
	    mEL4.setN1(jacobReturn.getN1());
	    mEL4.setN2(jacobReturn.getN2());
	    X = jacobReturn.getX();
	    Y = jacobReturn.getY();
	    DetJ = jacobReturn.getDetJ();
	    //</editor-fold>

	    T0p = 0;
	    for (I = 1; I <= mEL4.getNbn(); ++I) {
		Ndx[I - 1] = mEL4.getN1(I, P) * J_inv[1 - 1][1 - 1] + mEL4.getN2(I, P) * J_inv[1 - 1][2 - 1];
		Ndy[I - 1] = mEL4.getN1(I, P) * J_inv[2 - 1][1 - 1] + mEL4.getN2(I, P) * J_inv[2 - 1][2 - 1];
		Ni = mEL4.getNf(I, I);
		T0p = T0p + Temp_0[I - 1] * Ni;
	    }

	    DetJ = Math.abs(DetJ) * mEL4.getW(P);

	    for (N = 1; N <= mEL4.getNbn(); ++N) {
		for (I = 1; I <= mEL4.getNbn(); ++I) {
		    Ni = mEL4.getNf(I, P);
		    Nn = mEL4.getNf(N, P);
		    Hin = mK * (Ndx[N - 1] * Ndx[I - 1] + Ndy[N - 1] * Ndy[I - 1]) * DetJ;	// ! formula (3.14)
		    Cin = mC * mR * Nn * Ni * DetJ;						// ! formula(4.3)
		    est[N - 1][I - 1] = est[N - 1][I - 1] + Hin + Cin / mdTime;			// ! formula(4.8)
		    r[N - 1] = r[N - 1] + (Cin / mdTime) * T0p;
		}
//			Q = 0.8*Tp*Hp
//			r(n)=r(n) + Nn*Q*DetJ;
	    }
	}

//	throw new UnsupportedOperationException("Not yet implemented PRE_heat_mat");
    }

    private void PRE_heat_pov_mat(int NEL) {

	int I, j, N, P, Id, iPov;
	double DetJ, Ni, Nn, Pn;
	double[] X, Y;
	X = new double[4];
	Y = new double[4];
	DetJ = 0;
	Nn = 0;

	for (I = 1; I <= 4; ++I) {
	    Id = Math.abs(mGr.getEL(NEL).getNop(I));
	    X[I - 1] = mGr.getND(Id).getX() / 1000;
	    Y[I - 1] = mGr.getND(Id).getY() / 1000;
	}

	for (iPov = 1; iPov <= mGr.getEL(NEL).getNpov(); ++iPov) {

	    Id = mGr.getEL(NEL).getaPov(iPov); //! Lokalny numer powierchni elementu

	    switch (Id) {
		case 1:
		    DetJ = Math.sqrt((X[4 - 1] - X[1 - 1]) * (X[4 - 1] - X[1 - 1]) + (Y[4 - 1] - Y[1 - 1]) * (Y[4 - 1] - Y[1 - 1]));
		    break;
		case 2:
		    DetJ = Math.sqrt((X[1 - 1] - X[2 - 1]) * (X[1 - 1] - X[2 - 1]) + (Y[1 - 1] - Y[2 - 1]) * (Y[1 - 1] - Y[2 - 1]));
		    break;
		case 3:
		    DetJ = Math.sqrt((X[2 - 1] - X[3 - 1]) * (X[2 - 1] - X[3 - 1]) + (Y[2 - 1] - Y[3 - 1]) * (Y[2 - 1] - Y[3 - 1]));
		    break;
		case 4:
		    DetJ = Math.sqrt((X[3 - 1] - X[4 - 1]) * (X[3 - 1] - X[4 - 1]) + (Y[3 - 1] - Y[4 - 1]) * (Y[3 - 1] - Y[4 - 1]));
		    break;
	    }



	    for (P = 1; P <= 2; ++P) {
		for (N = 1; N <= 4; ++N) {
//		  ! Petla po gropam rownan (wierszam matrycy)
		    for (I = 1; I <= 4; ++I) {
//			  ! petla po kolumnam matrycy
			Ni = mEL4.getSf(Id).getNf(I, P);
			Nn = mEL4.getSf(Id).getNf(N, P);
			est[N - 1][I - 1] = est[N - 1][I - 1] + mAlfa * Nn * Ni * DetJ;
		    }
		    Pn = mAlfa * mT_otoczenia * Nn * DetJ;
		    r[N - 1] = r[N - 1] + Pn;
		}
	    }
	}
//	throw new UnsupportedOperationException("Not yet implemented PRE_heat_pov_mat");
    }

    private ReturnFromJacob_2d Jacob_2d(double[][] J_, double[][] J_inv, int p, int N_p, int NBN, double[][] N1, double[][] N2, double[] X, double[] Y, double DetJ) {

	int i;
	ReturnFromJacob_2d r = new ReturnFromJacob_2d();

	for (int a = 0; a < J_.length; ++a) {
	    for (int b = 0; b < J_[0].length; ++b) {
		J_[a][b] = 0;
	    }
	}

	for (int a = 0; a < J_inv.length; ++a) {
	    for (int b = 0; b < J_inv[0].length; ++b) {
		J_inv[a][b] = 0;
	    }
	}
	for (i = 1; i <= NBN; ++i) {
	    J_[1 - 1][1 - 1] = J_[1 - 1][1 - 1] + N1[i - 1][p - 1] * X[i - 1];
	    J_[1 - 1][2 - 1] = J_[1 - 1][2 - 1] + N1[i - 1][p - 1] * Y[i - 1];
	    J_[2 - 1][1 - 1] = J_[2 - 1][1 - 1] + N2[i - 1][p - 1] * X[i - 1];
	    J_[2 - 1][2 - 1] = J_[2 - 1][2 - 1] + N2[i - 1][p - 1] * Y[i - 1];
	}

	DetJ = J_[1 - 1][1 - 1] * J_[2 - 1][2 - 1] - J_[1 - 1][2 - 1] * J_[2 - 1][1 - 1];
	i = 2;

//   CALL Inv_MAT(i,J_,J_inv);

	r.setDetJ(DetJ);
	r.setJ_(J_);
	r.setJ_inv(J_inv);
	r.setN1(N1);
	r.setN2(N2);
	r.setNBN(NBN);
	r.setN_p(N_p);
	r.setP(p);
	r.setX(X);
	r.setY(Y);
	r.setI(i);

	try {
	    this.Inv_MAT(r);
	} catch (Exception e) {
	    System.out.print(e.getMessage() + "\n");
	    e.printStackTrace();
	}



	return r;
    }

    private void Inv_MAT(ReturnFromJacob_2d r) throws Exception {

	int n = r.getI();
	double[][] mat = r.getJ_();
	double[][] inv = r.getJ_inv();

	double[][] A = new double[n][2 * n];
	double[] tempRow;
	double pivElt, tarElt;
	int pivRow, tarRow;

	int k;

	pivElt = 0;


	for (int a = 0; a < A.length; ++a) {
	    for (int b = 0; b < A[0].length; ++b) {
		A[a][b] = 0;
	    }
	}

	for (int a = 1; a <= n; ++a) {
	    for (int b = 1; b <= n; ++b) {
		A[a - 1][b - 1] = mat[a - 1][b - 1];
	    }
	}



	for (int I = 1; I <= n; ++I) { //           ! identity in cols N+1 to 2N
	    A[I - 1][n + I - 1] = 1;
	}
	for (pivRow = 1; pivRow <= n; ++pivRow) {
	    pivElt = A[pivRow - 1][pivRow - 1];

	    if (pivElt == 0) {
		k = pivRow + 1;
		while (pivElt == 0 && k <= n) {
		    pivElt = A[k - 1][pivRow - 1];
		    ++k;
		}
		if (pivElt == 0) {
//	  System.out.print("Couldn't find a non-zero pivot: solution rubbish\n");
		    throw new Exception("Couldn't find a non-zero pivot: solution rubbish");
		} else {
		    tempRow = new double[2 * n];
		    for (int a = 1; a <= 2 * n; ++a) {
			tempRow[a - 1] = A[pivRow - 1][a - 1];
		    }
		    --k;
		    for (int a = 1; a <= 2 * n; ++a) {
			A[pivRow - 1][a - 1] = A[k - 1][a - 1];
		    }
		    for (int a = 1; a <= 2 * n; ++a) {
			A[pivRow - 1][a - 1] = tempRow[a - 1];
		    }
		}
	    }

	    for (int a = 1; a <= 2 * n; ++a) {
		A[pivRow - 1][a - 1] = A[pivRow - 1][a - 1] / pivElt;
	    }
	    for (tarRow = 1; tarRow <= n; ++tarRow) {
		if (tarRow != pivRow) {
		    tarElt = A[tarRow - 1][pivRow - 1];
		    for (int a = 1; a <= 2 * n; ++a) {
			A[tarRow - 1][a - 1] = A[tarRow - 1][a - 1] - A[pivRow - 1][a - 1] * tarElt;
		    }

		}
	    }
	}


	for (int a = 1; a <= n; ++a) {
	    for (int b = n + 1; b <= 2 * n; ++b) {
		inv[a - 1][b - 1 - n] = A[a - 1][b - 1];
	    }
	}



	r.setI(n);
	r.setJ_(mat);
	r.setJ_inv(inv);

// ! finally extract the inverse from columns N+1 to 2N:
// Inv = A( 1:N, N+1:2*N )

//	throw new UnsupportedOperationException("Not yet implemented");
    }

    private void DLSAQS(int nh, double[][] mA, int mLDA, int NCODA, double[] mB, double[] mX) {
	throw new UnsupportedOperationException("Not yet implemented DLSAQS");
    }

    private void gauss() {


	int m = mA.length;
	int n = mA[0].length;


	int i, j;
	double[][] matrix = new double[m][n];
	double[] b = new double[m];

	for (int a = 0; a < matrix.length; ++a) {
	    for (int c = 0; c < matrix[0].length; ++c) {
		matrix[a][c] = mA[a][c];
	    }
	    b[a] = mB[a];
	}

	double EPSILON = 1e-10;

	// zmienne pomocnicze (jako lokalne w celu mikrooptymalizacji)
	double diagValue, alpha, sum;


	// indeks wiersza z pivotem
	int max;

	// przechodzimy przez wszystkie kolumny
	for (int p = 0; p < m; p++) {

	    // szukamy pivotu
	    max = p;
	    for (int ri = p + 1; ri < m; ri++) {
		if (Math.abs(matrix[ri][p]) > Math.abs(matrix[max][p])) {
		    max = ri;
		}
	    }

	    // zamieniamy wiersze p oraz maxi miejscami
	    if (p != max) {
		double[] temp = matrix[p];
		matrix[p] = matrix[max];
		matrix[max] = temp;
		double t = b[p];
		b[p] = b[max];
		b[max] = t;
	    }

	    // dzielimy cały wiersz przez wartość na diagonali
	    diagValue = matrix[p][p];
	    for (int c = 0; c < m; c++) {
		matrix[p][c] /= diagValue;
	    }
	    b[p] /= diagValue;

	    // jeśli macierz osobliwa (lub prawie), to kończy
	    if (Math.abs(matrix[p][p]) <= EPSILON) {
		// TODO obsłużyć ten wyjątek?
		throw new RuntimeException("Macierz (prawie) osobliwa");
	    }

	    // odejmujemy nasz wiersz od pozostałych po nim
	    for (int ri = p + 1; ri < m; ri++) {
		alpha = matrix[ri][p] / matrix[p][p];
		for (int c = p; c < m; c++) {
		    matrix[ri][c] -= alpha * matrix[p][c];
		}
		b[ri] -= alpha * b[p];
	    }
	}

	// back substitution
	for (int ri = m - 1; ri >= 0; ri--) {
	    sum = 0;
	    for (int c = ri + 1; c < m; c++) {
		sum += matrix[ri][c] * b[c];
	    }
	    b[ri] = (b[ri] - sum) / matrix[ri][ri];
	}

//    outMatrix(matrix,m,m);
//    outVect(b, m);

	for (int a = 0; a < b.length; ++a) {
	    mX[a] = b[a];
	}



//	throw new UnsupportedOperationException("Not yet implemented gauss");
    }

    private void SOLVER_T() {
	int NEL;
	int[] nk = new int[4];
	int jB, iB, NeMaxB, NCODA, i, j, ii, jj;



//<editor-fold defaultstate="collapsed" desc="mA=0; 	mB=0; 	mX=0;">
	for (int a = 0; a < mA.length; ++a) {
	    for (int b = 0; b < mA[0].length; ++b) {
		mA[a][b] = 0;
	    }
	}

	for (int a = 0; a < mB.length; ++a) {
	    mB[a] = 0;
	    mX[a] = 0;
	}
//</editor-fold>

	for (NEL = 1; NEL <= mGr.getNe(); ++NEL) {
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		nk[i - 1] = mGr.getEL(NEL).getNop(i);
	    }
	    this.FeSM_heat(NEL);
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		ii = nk[i - 1];
		for (j = 1; j <= mEL4.getNbn(); ++j) {
		    jj = nk[j - 1];
		    mA[ii - 1][jj - 1] += est[i - 1][j - 1];
		}
		mB[ii - 1] += r[i - 1];
	    }
	}




	this.gauss();

	for (i = 1; i <= mGr.getNh(); ++i) {
	    mGr.getND(i).setCR((mGr.getND(i).getT() - mX[i - 1]) / mdTime);
	    mGr.getND(i).setT(mX[i - 1]);
	}
//	throw new UnsupportedOperationException("Not yet implemented SOLVER_T");
    }

    private void ALLOCATE_MatrixTemp() {
	int NEL, i, j, ii, jj, jB, NeMaxB, ierr;
	int[] nk = new int[4];
	mLDA = 0;
	for (NEL = 1; NEL <= mGr.getNe(); ++NEL) {
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		nk[i - 1] = mGr.getEL(NEL).getNop(i);
	    }
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		ii = nk[i - 1];
		for (j = 1; j <= mEL4.getNbn(); ++j) {
		    jj = nk[j - 1];
		    jB = jj - ii + 1;
		    if (jB >= mLDA) {
			mLDA = jB;
			NeMaxB = NEL;
		    }
		}
	    }
	}

	mA = new double[mGr.getNh()][mGr.getNh()];
	mB = new double[mGr.getNh()];
	mX = new double[mGr.getNh()];
//	ALLOCATE ( mA(mLDA, mGr%nh), STAT=iErr );
//!	ALLOCATE ( mA(mGr%nh, mGr%nh), STAT=iErr );
//!   Pelna matryca MES
//	ALLOCATE ( mB(mGr%nh), STAT=iErr );
//	ALLOCATE ( mX(mGr%nh), STAT=iErr );

//	throw new UnsupportedOperationException("Not yet implemented ALLOCATE_MatrixTemp");
    }

    private void DEALLOCATE_Matrix() {
	mA = null;
	mB = null;
	mX = null;
	//TODO Lepsze dealokowanie 
//	throw new UnsupportedOperationException("Not yet implemented DEALLOCATE_Matrix");
    }

    private void ALLOCATE_MatrixDef() {
	int NEL, i, j, ii, jj, jB, NeMaxB, ierr, ncn;
	int ii1, ii2, ii3, jj1, jj2, jj3;
	int[] nk = new int[4];


	ncn = 2 * mGr.getNh() + mGr.getNe();
	mLDA = 0;
	for (NEL = 1; NEL <= mGr.getNe(); ++NEL) {
	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		nk[i - 1] = mGr.getEL(NEL).getNop(i);
	    }

	    for (i = 1; i <= mEL4.getNbn(); ++i) {

		ii = nk[i - 1];
		ii1 = ii;
		ii2 = mGr.getNh() + ii;
		ii3 = 2 * mGr.getNh() + NEL;

		for (j = 1; j <= mEL4.getNbn(); ++j) {

		    jj = nk[j - 1];
		    jj1 = jj;
		    jj2 = mGr.getNh() + jj;
		    jj3 = 2 * mGr.getNh() + NEL;

		    jB = jj3 - ii1 + 1;
		    if (jB >= mLDA) {
			mLDA = jB;
			NeMaxB = NEL;
		    }
		}
	    }
	}


	mA = new double[ncn][ncn];
	mB = new double[ncn];
	mX = new double[ncn];



//	throw new UnsupportedOperationException("Not yet implemented ALLOCATE_MatrixDef");
    }

    private void SOLVER_D() {
	int NEL;
	int[] nk = new int[4];
	int jB, iB, NeMaxB, NCODA, i, j, ii, jj, ncn, ii1, ii2, jj1, jj2;


	ncn = 2 * mGr.getNh() + mGr.getNe();

	//<editor-fold defaultstate="collapsed" desc="mA=0; 	mB=0; 	mX=0;">
	for (int a = 0; a < mA.length; ++a) {
	    for (int b = 0; b < mA[0].length; ++b) {
		mA[a][b] = 0;
	    }

	}
	for (int a = 0; a < mB.length; ++a) {

	    mB[a] = 0;
	    mX[a] = 0;
	}
	//</editor-fold>

	for (NEL = 1; NEL <= mGr.getNe(); ++NEL) {

	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		nk[i - 1] = mGr.getEL(NEL).getNop(i);
	    }

	    this.calcFeSM_main(NEL);
	    this.calcFeSM_corr(NEL);

	    for (i = 1; i <= mEL4.getNbn(); ++i) {
		ii = nk[i - 1];
		ii1 = ii + mGr.getNh();
		ii2 = ii1 + NEL;

		for (j = 1; j <= mEL4.getNbn(); ++j) {
		    jj = nk[j - 1];
		    jj1 = jj + mGr.getNh();


		    mA[ii - 1][jj - 1] += FeSM[i - 1][j - 1];
		    mA[ii - 1][jj1 - 1] += FeSM[i - 1][j - 1 + mEL4.getNbn()];
		    mA[ii1 - 1][jj - 1] += FeSM[i - 1 + mEL4.getNbn()][j - 1];
		    mA[ii1 - 1][jj1 - 1] += FeSM[i - 1 + mEL4.getNbn()][ j - 1 + mEL4.getNbn()];
		}

		mA[ii - 1][  2 * mGr.getNh() + NEL - 1] += FeSM[i - 1][9 - 1];
		mA[ii1 - 1][ 2 * mGr.getNh() + NEL - 1] += FeSM[i - 1 + mEL4.getNbn()][9 - 1];
		mA[2 * mGr.getNh() + NEL - 1][ii - 1] += FeSM[9 - 1][i - 1];
		mA[2 * mGr.getNh() + NEL - 1][ii1 - 1] += FeSM[9 - 1][ i - 1 + mEL4.getNbn()];

		mB[ii - 1] += FeRhs[i - 1];
		mB[ii1 - 1] += FeRhs[i - 1 + mEL4.getNbn()];
	    }

	    mB[2 * mGr.getNh() + NEL - 1] += FeRhs[9 - 1];

	}

	this.gauss();

	for (i = 1; i <= mGr.getNh(); ++i) {
	    mGr.getND(i).setVx(mX[i - 1]);
	    mGr.getND(i).setVy(mX[i - 1 + mGr.getNh()]);
	}

	for (i = 1; i <= mGr.getNe(); ++i) {
	    mGr.getEL(i).setSe(mX[i - 1 + 2 * mGr.getNh()]);
	}
//	throw new UnsupportedOperationException("Not yet implemented SOLVER_D");
    }

    private void calcFeSM_main(int NEL) {

	int i, N, P, Id;
	int Row1, Row2, Row3, C1, C2, C3, NBN, NBNP;
	double DetJ, Esp, Nn, Ni, Rp;


	double[][] J_ = new double[2][2];
	double[][] J_inv = new double[2][2];
	double[] Ndx, Ndy, Es, X, Y, Vx, Vy, t, Eps;

	Ndx = new double[4];
	Ndy = new double[4];
	Es = new double[4];
	X = new double[4];
	Y = new double[4];
	Vx = new double[4];
	Vy = new double[4];
	t = new double[4];
	Eps = new double[4];

	int[] St = new int[4];

	double Xsr, VrSr, Ex, Ey, Exy, Etg, Ei, Si, tp, Epsp;
	int TipTaskSlv = 2;


	NBN = mEL4.getNbn();
	NBNP = mEL4.getNbnp();
	Xsr = 0;
	for (i = 1; i <= NBN; ++i) {
	    Id = Math.abs(mGr.getEL(NEL).getNop(i));
	    X[i - 1] = mGr.getND(Id).getX();
	    Xsr += X[i - 1];
	    Y[i - 1] = mGr.getND(Id).getY();
	    Es[i - 1] = mGr.getND(Id).getEs();
	    Vx[i - 1] = mGr.getND(Id).getVx();
	    Vy[i - 1] = mGr.getND(Id).getVy();
	    St[i - 1] = mGr.getND(Id).getStatus();
	    t[i - 1] = mGr.getND(Id).getT();
	    Eps[i - 1] = mGr.getND(Id).getEpsi();
	}
	Xsr = Xsr / NBN;


	for (P = 1; P <= mEL4.getN_p(); ++P) {

	    ReturnFromJacob_2d jacobReturn = this.Jacob_2d(J_, J_inv, P, mEL4.getN_p(), NBN, mEL4.getN1(), mEL4.getN2(), X, Y, 0);

	    //<editor-fold defaultstate="collapsed" desc="return from Jacob_2d">
	    J_ = jacobReturn.getJ_();
	    J_inv = jacobReturn.getJ_inv();
	    P = jacobReturn.getP();
	    mEL4.setN_p(jacobReturn.getN_p());
//	    mEL4.setNbnp(jacobReturn.getNBN());
	    mEL4.setN1(jacobReturn.getN1());
	    mEL4.setN2(jacobReturn.getN2());
	    X = jacobReturn.getX();
	    Y = jacobReturn.getY();
	    DetJ = jacobReturn.getDetJ();
	    //</editor-fold>

	    for (i = 1; i <= NBN; ++i) {
		Ndx[i - 1] = mEL4.getN1(i, P) * J_inv[1 - 1][1 - 1] + mEL4.getN2(i, P) * J_inv[1 - 1][2 - 1];
		Ndy[i - 1] = mEL4.getN1(i, P) * J_inv[2 - 1][1 - 1] + mEL4.getN2(i, P) * J_inv[2 - 1][2 - 1];
	    }


	    Ex = 0;
	    Ey = 0;
	    Exy = 0;
	    Rp = 0;
	    VrSr = 0;
	    tp = 0;
	    Epsp = 0;
	    for (i = 1; i <= NBN; ++i) {

		Rp = (int) (Rp + X[i - 1] * mEL4.getNf(i, P));
		VrSr = VrSr + Vx[i - 1] * mEL4.getNf(i, P);
		tp = tp + t[i - 1] * mEL4.getNf(i, P);
		Epsp = Epsp + Eps[i - 1] * mEL4.getNf(i, P);
		Ex = Ex + Vx[i - 1] * Ndx[i - 1];
		Ey = Ey + Vy[i - 1] * Ndy[i - 1];
		Exy = Exy + 0.5 * (Vx[i - 1] * Ndy[i - 1] + Vy[i - 1] * Ndx[i - 1]);
	    }

	    Etg = VrSr / Rp;
	    Ei = Math.sqrt((2.0 / 3.0) * ((Ex - Ey) * (Ex - Ey) + (Ey - Etg) * (Ey - Etg) + (Etg - Ex) * (Etg - Ex)) + 4 * (Exy * Exy));

	    if (Ei <= 0.0001) {
		Ei = 0.0001;
	    }
	    if (Epsp <= 0.0001) {
		Epsp = 0.0001;
	    }

	    Si = mREO.getA() * Math.exp(-mREO.getM1() * tp) * Math.pow(Epsp, mREO.getM2()) * Math.pow(Ei, mREO.getM3()) * Math.exp(-Epsp * mREO.getM4());

	    Esp = Si / Ei;


	    DetJ = DetJ * mEL4.getW(P);

	    for (N = 1; N <= NBN; ++N) {
		Row1 = N;
		Row2 = NBN + N;
		Row3 = 2 * NBN + N;
		for (i = 1; i <= NBN; ++i) {
		    C1 = i;
		    C2 = NBN-1 + i;
		    C3 = 2 * NBN -2 + i;

//		    System.out.print("c1:"+C1 + " c2:"+C2+" c3:"+C3+"\n");
		    
		    
		    Ni = mEL4.getNf(i, P);
		    Nn = mEL4.getNf(N, P);

		    if (TipTaskSlv == 1) {
//			Odksztalcenie plaskie 
			FeSM[Row1 - 1][C1 - 1] += Esp * (2 * Ndx[N - 1] * Ndx[i - 1] + Ndy[N - 1] * Ndy[i - 1]) * DetJ;
			FeSM[Row1 - 1][C2 - 1] += Esp * Ndx[i - 1] * Ndy[N - 1] * DetJ;

			if (i <= mEL4.getNbnp()) {
			    FeSM[Row1 - 1][C3 - 1] += Ndx[N - 1] * DetJ * mEL4.getHk(i, P);
			}


			FeSM[Row2 - 1][C1 - 1] += Esp * Ndx[N - 1] * Ndy[i - 1] * DetJ;
			FeSM[Row2 - 1][C2 - 1] += Esp * (2 * Ndy[N - 1] * Ndy[i - 1] + Ndx[N - 1] * Ndx[i - 1]) * DetJ;

			if (i <= mEL4.getNbnp()) {
			    FeSM[Row2 - 1][C3 - 1] += Ndy[N - 1] * DetJ * mEL4.getHk(i, P);
			}

			if (N <= mEL4.getNbnp()) {
			    FeSM[Row3 - 1][C1 - 1] += Ndx[i - 1] * DetJ * mEL4.getHk(N, P);
			    FeSM[Row3 - 1][C2 - 1] += Ndy[i - 1] * DetJ * mEL4.getHk(N, P);
			}
		    } else {
//			*********  x->R  **************
//			Odksztalcenie osiowosymetryczne 
			FeSM[Row1 - 1][C1 - 1] += Esp * (2 * Ndx[N - 1] * Ndx[i - 1] * Rp + Ndy[N - 1] * Ndy[i - 1] * Rp + 2 * Ni * Nn / Rp) * DetJ;
			FeSM[Row1 - 1][C2 - 1] += Esp * Ndx[i - 1] * Ndy[N - 1] * DetJ * Rp;

			if (i <= mEL4.getNbnp()) {
			    FeSM[Row1 - 1][C3 - 1] += (Ndx[N - 1] * Rp + Nn) * DetJ * mEL4.getHk(i, P);
			}

			FeSM[Row2 - 1][C1 - 1] += Esp * Ndx[N - 1] * Ndy[i - 1] * DetJ * Rp;
			FeSM[Row2 - 1][C2 - 1] += Esp * (2 * Ndy[N - 1] * Ndy[i - 1] + Ndx[N - 1] * Ndx[i - 1]) * DetJ * Rp;

			if (i <= mEL4.getNbnp()) {
			    FeSM[Row2 - 1][C3 - 1] += Ndy[N - 1] * DetJ * mEL4.getHk(i, P) * Rp;
			}

			if (N <= mEL4.getNbnp()) {
			    FeSM[Row3 - 1][C1 - 1] += (Ndx[i - 1] + Ni / Rp) * DetJ * mEL4.getHk(N, P) * Rp;
			    FeSM[Row3 - 1][C2 - 1] += Ndy[i - 1] * DetJ * mEL4.getHk(N, P) * Rp;
			}
		    }

		}//I
	    }// N
	}//p
//	throw new UnsupportedOperationException("Not yet implemented calcFeSM_main");
    }

    private void calcFeSM_corr(int NEL) {
	throw new UnsupportedOperationException("Not yet implemented calcFeSM_corr");
    }
}
