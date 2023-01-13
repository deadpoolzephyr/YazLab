package yazlab1.pkg02;

import java.util.Scanner;
/**
 *
 * @author arda_
 */


public class YazLab102{
    
    private static Kontrol kontrol;
    private static Thread kontrolThread;


    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        kontrol = Kontrol.getInstance();
        kontrolThread = new Thread(kontrol);
        kontrolThread.start();
        
        int secim;
        
        while(true) {

            
            System.out.println("Seçiminizi giriniz: \n 1. Asansör Durum \n 2. Asansör Çağır");
            secim = input.nextInt();

            if(secim == 1){
            for(int i=0;i<5;i++){
                Asansorler asansor = Kontrol.getInstance().getAsansorList().get(i);
                System.out.println("Asansör - " + asansor.getId() + " | Anlık kat - " + asansor.getAnlikKat() + " | Durum - " + asansor.getAsansorDurum());
            }
            }

            if(secim == 2) {
                
                input = new Scanner(System.in);
                System.out.println("Çağrıldığı katı giriniz (0 - 4 arası): ");
                int istekKat = input.nextInt();

                input = new Scanner(System.in);
                System.out.println("Hedef katı giriniz (0 - 4 arası): ");
                int hedefKat = input.nextInt();

                AsansorIstek asansorIstek = new AsansorIstek(istekKat, hedefKat);
                Asansorler asansorler = asansorIstek.submitRequest();


            }

        }
        
        
    }

    
    
}
