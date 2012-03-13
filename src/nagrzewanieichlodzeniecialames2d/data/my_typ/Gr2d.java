/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d.data.my_typ;

/**
 *
 * @author proteus
 */
public class Gr2d {

    private int nh;
    private int ne;
    private int nbn;
    private int ncn;
    private int nhPov;
    private Element[] EL;
    private Node[] ND;

    //<editor-fold defaultstate="collapsed" desc="geters&setters">
    public Element[] getEL() {
	return EL;
    }

    public void setEL(Element[] EL) {
	this.EL = EL;
    }

    public Node[] getND() {
	return ND;
    }

    public void setND(Node[] ND) {
	this.ND = ND;
    }

    public int getNbn() {
	return nbn;
    }

    public void setNbn(int nbn) {
	this.nbn = nbn;
    }

    public int getNcn() {
	return ncn;
    }

    public void setNcn(int ncn) {
	this.ncn = ncn;
    }

    public int getNe() {
	return ne;
    }

    public void setNe(int ne) {
	this.ne = ne;
    }

    public int getNh() {
	return nh;
    }

    public void setNh(int nh) {
	this.nh = nh;
    }

    public int getNhPov() {
	return nhPov;
    }

    public void setNhPov(int nhPov) {
	this.nhPov = nhPov;
    }
    //</editor-fold>

    public void allocateNdEL() {
	
	ND = new Node[nh];
	EL = new Element[ne];
	
	for (int a = 0; a < ND.length ; ++a) {
	    ND[a] = new Node();
	}
	for (int a = 0; a < EL.length ; ++a) {
	    EL[a] = new Element();
	}
	throw new UnsupportedOperationException("Not yet implemented");
    }
}
