package yazlab1.pkg02;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableSet;

/**
 *
 * @author arda_
 */


public class Asansorler implements Runnable{
    
    //5 asansörün maksimum kapasite kontroolü taşımayı sağlar
    
    private boolean operating;
    private int id;
    private AsansorDurum asansorDurum;
    private int anlikKat;
    
    private NavigableSet<Integer> floorStops;
    
    
    public Map<AsansorDurum, NavigableSet<Integer>> floorStopsMap;

    public Asansorler(int id){
        this.id = id;
        setOperating(true);
    }

    public int getId() {
        return id;
    }

    public AsansorDurum getAsansorDurum() {
        return asansorDurum;
    }

    public int getAnlikKat() {
        return anlikKat;
    }

    public void setAsansorDurum(AsansorDurum asansorDurum) {
        this.asansorDurum = asansorDurum;
    }

    public boolean isOperating(){
        return this.operating;
    }

    public void setOperating(boolean state){
        this.operating = state;

        if(!state){
            setAsansorDurum(AsansorDurum.MAINTAINANCE);
            this.floorStops.clear();
        } else {
            setAsansorDurum(AsansorDurum.STATIONARY);
            this.floorStopsMap = new LinkedHashMap<AsansorDurum, NavigableSet<Integer>>();

            // To let controller know that this elevator is ready to serve
            Kontrol.updateAsansorList(this);
        }

        setAnlıkKat(0);
    }

    public void setAnlıkKat(int AnlıkKat) {
        this.anlikKat = AnlıkKat;
    }

    public void move(){
        synchronized (Kontrol.getInstance()){ // Synchronized over the Kontrol singleton.
            Iterator<AsansorDurum> iter = floorStopsMap.keySet().iterator();

            while(iter.hasNext()){
                asansorDurum = iter.next();

                // Get the floors that elevator will pass in the requested direction
                floorStops = floorStopsMap.get(asansorDurum);
                iter.remove();
                Integer currFlr = null;
                Integer nextFlr = null;

                // Start moving the elevator
                while (!floorStops.isEmpty()) {

                    if (asansorDurum.equals(AsansorDurum.UP)) {
                        currFlr = floorStops.pollFirst();
                        nextFlr = floorStops.higher(currFlr);
                        try {
                        Thread.sleep(500); // Let people get in the elevator :P
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    } else if (asansorDurum.equals(AsansorDurum.DOWN)) {
                        currFlr = floorStops.pollLast();
                        nextFlr = floorStops.lower(currFlr);
                    } else {
                        return;
                    }

                    setAnlıkKat(currFlr);

                    if (nextFlr != null) {
                        // This helps us in picking up any request that might come
                        // while we are on the way.
                        generateIntermediateFloors(currFlr, nextFlr);
                    } else {
                        setAsansorDurum(AsansorDurum.STATIONARY);
                        Kontrol.updateAsansorList(this);
                    }

                    System.out.println("Asansor ID " + this.id + " | Anlık kat - " + getAnlikKat() + " | Sonraki hareket - " + getAsansorDurum());

                    try {
                        Thread.sleep(1000); // Let people get off the elevator :P
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                // Wait till ElevatorController has scanned the state of all elevators.
                // This helps us to serve any intermediate requests that might come
                // while elevators are on their respective paths.
                Kontrol.getInstance().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method helps to generate list of floors that the elevator will
     * either stop or pass by when in motion.
     * @param initial
     * @param target
     */
    private void generateIntermediateFloors(int initial, int target){

        if(initial==target){
            return;
        }

        if(Math.abs(initial-target) == 1){
            return;
        }

        int n = 1;
        if(target-initial<0){
            // This means with are moving DOWN
            n = -1;
        }

        while(initial!=target){
            initial += n;
            if(!floorStops.contains(initial)) {
                floorStops.add(initial);
            }
        }
    }

    @Override
    public void run() {
        while(true){
            if(isOperating()){
                move();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
    }
    
    
    
}
