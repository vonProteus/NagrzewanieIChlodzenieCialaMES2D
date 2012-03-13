/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data.my_typ;

/**
 *
 * @author proteus
 */
public class Element {

    private int[] nop = new int[4];
    private int Npov;
    private int[] aPov = new int[2];

    //<editor-fold defaultstate="collapsed" desc="seters&geters">
    public int getNpov() {
	return Npov;
    }

    public void setNpov(int Npov) {
	this.Npov = Npov;
    }

    public int[] getaPov() {
	return aPov;
    }

    public void setaPov(int[] aPov) {
	this.aPov = aPov;
    }
    
    public void setaPov(int i, int val) {
	--i;
	if (i < 0 || i > aPov.length) {
	    throw new ArrayIndexOutOfBoundsException("aPov");
	}
	this.aPov[i] = val;
    }

    public int[] getNop() {
	return nop;
    }
     public int getNop(int i) {
	 --i;
	if (i < 0 || i > nop.length) {
	    throw new ArrayIndexOutOfBoundsException("nop");
	}
	return nop[i];
    }

    public void setNop(int[] nop) {
	this.nop = nop;
    }
    public void setNop(int i, int val) {
	 --i;
	if (i < 0 || i > nop.length) {
	    throw new ArrayIndexOutOfBoundsException("nop");
	}
	this.nop[i] = val;
    }
    //</editor-fold>
}
