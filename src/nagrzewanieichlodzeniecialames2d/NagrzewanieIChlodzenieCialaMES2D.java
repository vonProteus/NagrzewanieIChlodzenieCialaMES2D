/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nagrzewanieichlodzeniecialames2d;

/**
 *
 * @author proteus
 */
public class NagrzewanieIChlodzenieCialaMES2D {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	System.out.print("Main start\n");

	try {
	    TEMP2D myTemp2d = new TEMP2D();
	    myTemp2d.goPLASTOMETR2D();

	} catch (Exception e) {
	    System.out.print("error " + e.getMessage() + "\n");
	    e.printStackTrace();
	}

	System.out.print("Main end\n");
    }
}
