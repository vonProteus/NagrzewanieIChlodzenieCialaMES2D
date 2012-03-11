/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data.my_typ;

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

    public double getN1(int i, int j) {
	--i;
	if (i < 0 || i > N1.length) {
	    throw new ArrayIndexOutOfBoundsException("N1");
	}

	--j;
	if (j < 0 || j > N1[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N1[0]");
	}
	return N1[i][j];
    }

    public void setN1(double[][] N1) {
	this.N1 = N1;
    }

    public void setN1(int i, int j, double val) {
	--i;
	if (i < 0 || i > N1.length) {
	    throw new ArrayIndexOutOfBoundsException("N1");
	}

	--j;
	if (j < 0 || j > N1[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N1[0]");
	}
	this.N1[i][j] = val;
    }

    public double[][] getN2() {
	return N2;
    }

    public double getN2(int i, int j) {
	--i;
	if (i < 0 || i > N2.length) {
	    throw new ArrayIndexOutOfBoundsException("N2");
	}

	--j;
	if (j < 0 || j > N2[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N2[0]");
	}
	return N2[i][j];
    }

    public void setN2(double[][] N2) {
	this.N2 = N2;
    }

    public void setN2(int i, int j, double val) {
	--i;
	if (i < 0 || i > N2.length) {
	    throw new ArrayIndexOutOfBoundsException("N2");
	}

	--j;
	if (j < 0 || j > N2[0].length) {
	    throw new ArrayIndexOutOfBoundsException("N2[0]");
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

    public double getNf(int i, int j) {
	--i;
	if (i < 0 || i > Nf.length) {
	    throw new ArrayIndexOutOfBoundsException("Nf");
	}

	--j;
	if (j < 0 || j > Nf[0].length) {
	    throw new ArrayIndexOutOfBoundsException("Nf[0]");
	}
	return Nf[i][j];
    }

    public void setNf(double[][] Nf) {
	this.Nf = Nf;
    }

    public void setNf(int i, int j, double val) {
	--i;
	if (i < 0 || i > Nf.length) {
	    throw new ArrayIndexOutOfBoundsException("Nf");
	}

	--j;
	if (j < 0 || j > Nf[0].length) {
	    throw new ArrayIndexOutOfBoundsException("Nf[0]");
	}
	this.Nf[i][j] = val;
    }

    public Cor_L[] getP() {
	return P;
    }

    public Cor_L getP(int i) {
	--i;
	if (i < 0 || i > P.length) {
	    throw new ArrayIndexOutOfBoundsException("P");
	}
	return P[i];
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

    public void setUZEL(int i, int val) {
	--i;
	if (i < 0 || i > UZEL.length) {
	    throw new ArrayIndexOutOfBoundsException("UZEL");
	}
	this.UZEL[i] = val;
    }

    public double[] getW() {
	return W;
    }

    public void setW(double[] W) {
	this.W = W;
    }
    //</editor-fold>

    public void allocatePW() {
//	ALLOCATE( mEL4%Sf(i)%P( mEL4%Sf(i)%N_p ) );
//	ALLOCATE( mEL4%Sf(i)%W( mEL4%Sf(i)%N_p ) );

	this.P = new Cor_L[this.N_p];
	this.W = new double[this.N_p];

	for (int a = 0; a < this.P.length; ++a) {
	    this.P[a] = new Cor_L();
	}

	//throw new UnsupportedOperationException("Not yet implemented");
    }

    public void allocateN12dUzel(int mEL4nbn, int UZELSize) {
//	ALLOCATE( mEL4%Sf(i)%N1( mEL4%nbn, mEL4%Sf(i)%N_p ) );
//	ALLOCATE( mEL4%Sf(i)%N2( mEL4%nbn, mEL4%Sf(i)%N_p ) );
//	ALLOCATE( mEL4%Sf(i)%Nf( mEL4%nbn, mEL4%Sf(i)%N_p ) );
//	ALLOCATE( mEL4%Sf(i)%UZEL( 2 ) );
	this.UZEL = new int[UZELSize];

	this.N1 = new double[mEL4nbn][this.N_p];
	this.N2 = new double[mEL4nbn][this.N_p];
	this.Nf = new double[mEL4nbn][this.N_p];


//	throw new UnsupportedOperationException("Not yet implemented");
    }
}
