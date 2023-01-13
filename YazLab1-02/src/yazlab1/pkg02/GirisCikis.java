package yazlab1.pkg02;

/**
 *
 * @author arda_
 */

enum AsansorDurum {
    UP,
    DOWN,
    STATIONARY,
    MAINTAINANCE,
    active
}

class AsansorIstek {
    private int istekKat;
    private int hedefKat;

    public AsansorIstek(int istekKat, int hedefKat){
        this.istekKat = istekKat;
        this.hedefKat = hedefKat;
    }

    public int getIstekKat() {
        return istekKat;
    }

    public int getHedefKat() {
        return hedefKat;
    }

    public Asansorler submitRequest(){
        return Kontrol.getInstance().selectElevator(this);
    }
}

public class GirisCikis {
    
    
    
}
