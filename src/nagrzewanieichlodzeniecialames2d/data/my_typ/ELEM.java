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
    private double[][] Hk;
    private Cor_L[] P;
    private Cor_L[] L;
    private double[] W;
    private Gran[] Sf = new Gran[4];

    public ELEM() {
	this.Sf = new Gran[4];
	for (int a = 0; a < this.Sf.length; ++a) {
	    this.Sf[a] = new Gran();
	}
    }

    //<editor-fold defaultstate="collapsed" desc="seters&geters">
    public Cor_L[] getL() {
	return L;
    }

    public Cor_L getL(int i) {
	--i;
	if (i < 0 || i > L.length) {
	    throw new ArrayIndexOutOfBoundsException("L");
	}
	return L[i];
    }

    public void setL(Cor_L[] L) {
	this.L = L;
    }

    public double[][] getN1() {
	return N1;
    }

    public double getN1(int i, int j) {
	--i;
	if (i < 0 || i > this.N1.length) {
	    throw new ArrayIndexOutOfBoundsException("N1 i");
	}
	--j;
	if (j < 0 || j > this.N1[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N1 j");
	}
	return N1[i][j];
    }

    public void setN1(double[][] N1) {
	this.N1 = N1;
    }

    public void setN1(int i, int j, double val) {
	--i;
	if (i < 0 || i > this.N1.length) {
	    throw new ArrayIndexOutOfBoundsException("N1 i");
	}
	--j;
	if (j < 0 || j > this.N1[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N1 j");
	}

	this.N1[i][j] = val;
    }

    public double[][] getN2() {
	return N2;
    }

    public double getN2(int i, int j) {
	--i;
	if (i < 0 || i > this.N2.length) {
	    throw new ArrayIndexOutOfBoundsException("N2 i");
	}
	--j;
	if (j < 0 || j > this.N2[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N2 j");
	}
	return N2[i][j];
    }

    public void setN2(double[][] N2) {
	this.N2 = N2;
    }

    public void setN2(int i, int j, double val) {
	--i;
	if (i < 0 || i > this.N2.length) {
	    throw new ArrayIndexOutOfBoundsException("N2 i");
	}
	--j;
	if (j < 0 || j > this.N2[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N2 j");
	}

	this.N2[i][j] = val;
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

    public double getNf(int i, int j) {
	--i;
	if (i < 0 || i > this.Nf.length) {
	    throw new ArrayIndexOutOfBoundsException("Nf i");
	}
	--j;
	if (j < 0 || j > this.Nf[0].length) {
	    throw new ArrayIndexOutOfBoundsException("Nf j");
	}
	return Nf[i][j];
    }

    public void setNf(int i, int j, double val) {
	--i;
	if (i < 0 || i > this.Nf.length) {
	    throw new ArrayIndexOutOfBoundsException("Nf i");
	}
	--j;
	if (j < 0 || j > this.Nf[0].length) {
	    throw new ArrayIndexOutOfBoundsException("Nf j");
	}

	this.Nf[i][j] = val;
    }

    public Cor_L getP(int i) {
	--i;
	if (i < 0 || i > P.length) {
	    throw new ArrayIndexOutOfBoundsException("P");
	}
	return P[i];
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

    public Gran getSf(int i) {
	--i;
	if (i < 0 || i > Sf.length) {
	    throw new ArrayIndexOutOfBoundsException("Sf");
	}
	return Sf[i];
    }

    public void setSf(Gran[] Sf) {
	this.Sf = Sf;
    }

    public double[] getW() {
	return W;
    }
    

    public double getW(int i) {
	--i;
	if (i < 0 || i > W.length) {
	    throw new ArrayIndexOutOfBoundsException("W");
	}
	return W[i];
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

    public double[][] getHk() {
	return Hk;
    }

    public void setHk(double[][] Hk) {
	this.Hk = Hk;
    }
    
     public double getHk(int i, int j) {
	--i;
	if (i < 0 || i > this.Hk.length) {
	    throw new ArrayIndexOutOfBoundsException("Hk i="+i);
	}
	--j;
	if (j < 0 || j > this.Hk[0].length) {
	    throw new ArrayIndexOutOfBoundsException("Hk j="+j);
	}
	return Hk[i][j];
    }

    public void setHk(int i, int j, double val) {
	--i;
	if (i < 0 || i > this.Hk.length) {
	    throw new ArrayIndexOutOfBoundsException("Hk i="+i);
	}
	--j;
	if (j < 0 || j > this.Hk[0].length) {
	    throw new ArrayIndexOutOfBoundsException("Hk j="+j);
	}

	this.Hk[i][j] = val;
    }
    
    //</editor-fold>

    public void allocateN12fPWL() {
//	ALLOCATE(mEL4%N1( mEL4%nbn, mEL4%N_p ));
//	ALLOCATE(mEL4%N2( mEL4%nbn, mEL4%N_p ));
//	ALLOCATE(mEL4%Nf( mEL4%nbn, mEL4%N_p ));
//	ALLOCATE(mEL4%P ( mEL4%N_p ));
//	ALLOCATE(mEL4%W ( mEL4%N_p ));
//	ALLOCATE(mEL4%L ( mEL4%nbn ));

	this.N1 = new double[this.nbn][this.N_p];
	this.N2 = new double[this.nbn][this.N_p];
	this.Nf = new double[this.nbn][this.N_p];
	this.Hk = new double[this.nbnp][this.N_p];

	this.P = new Cor_L[this.N_p];
	this.W = new double[this.N_p];
	this.L = new Cor_L[this.nbn];

	for (int a = 0; a < this.P.length; ++a) {
	    this.P[a] = new Cor_L();
	}

	for (int a = 0; a < this.L.length; ++a) {
	    this.L[a] = new Cor_L();
	}

    }
}
