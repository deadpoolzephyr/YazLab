package yazlab1.pkg02;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
/**
 *
 * @author arda_
 */

public class Kontrol implements Runnable{
    
    //Katlardaki kuyrukları kontrol eder
    //Kuyrukta bekleyen kişilerin toplam sayısı 20'yi aştığında yeni asansörü aktif eder
    //Kuyrukta bekleyenlerkapasitenin altına inince asansörlerden birisi pasif hale gelir
    
    private boolean stopController;

    // All the UP moving elevators
    private static Map<Integer, Asansorler> upMovingMap = new HashMap<Integer, Asansorler>();

    // All the DOWN moving elevators
    private static Map<Integer, Asansorler> downMovingMap = new HashMap<Integer, Asansorler>();
    // STATIONARY elevators are part of UP and DOWN map both.

    private static List<Asansorler> asansorList = new ArrayList<Asansorler>(16);

    private static final Kontrol instance = new Kontrol();
    private Kontrol(){
        if(instance != null){
            throw new IllegalStateException("Already instantiated");
        }
        setStopController(false);
        initializeElevators();
    }

    public static Kontrol getInstance(){
        return instance;
    }

    /**
     * Select an elevator from the pool of operational elevators that can serve the
     * the request optimally
     * @param elevatorRequest  Represents the request for an elevator
     * @return Selected Elevator
     */
    public synchronized Asansorler selectElevator(AsansorIstek asansorIstek) {

        Asansorler asansor = null;

        AsansorDurum asansorDurum = getRequestedElevatorDirection(asansorIstek);
        int istekKat = asansorIstek.getIstekKat();
        int hedefKat = asansorIstek.getHedefKat();

        asansor = findElevator(asansorDurum, istekKat, hedefKat);

        // So that elevators can start moving again.
        notifyAll();
        return asansor;


    }

    private static void initializeElevators(){
        for(int i=0; i<5; i++){
            Asansorler asansor = new Asansorler(i);
            Thread t = new Thread(asansor);
            t.start();

            asansorList.add(asansor);
        }
    }

    private static AsansorDurum getRequestedElevatorDirection(AsansorIstek asansorIstek){
        AsansorDurum asansorDurum = null;
        int istekKat = asansorIstek.getIstekKat();
        int hedefKat = asansorIstek.getHedefKat();
        
        if(hedefKat>=0 && hedefKat<5 && istekKat>=0 && istekKat<5){
            if(hedefKat - istekKat > 0){
                asansorDurum = AsansorDurum.UP;
            } else {
                asansorDurum = AsansorDurum.DOWN;
            }
        }
        return asansorDurum;
    }

    /**
     * Internal method to select an elevator and generate UP and/or DOWN paths for it.
     * @param elevatorState UP, DOWN or STATIONARY
     * @param requestedFloor Floor number where request is originating from
     * @param targetFloor Floor number where user wants to go
     * @return selected elevator
     */
    private static Asansorler findElevator(AsansorDurum asansorDurum, int istekKat, int hedefKat) {
        Asansorler asansor = null;

        // Data structure to hold distance of eligible elevators from the request floor
        // The keys represent the current distance of an elevator from request floor
        TreeMap<Integer, Integer> sortedKeyMap = new TreeMap<Integer, Integer>();

        if(asansorDurum.equals(AsansorDurum.UP)){

            // Let's go over all elevators that are either going UP or are STATIONARY
            for(Map.Entry<Integer, Asansorler> elvMap : upMovingMap.entrySet()){
                Asansorler elv = elvMap.getValue();
                Integer distance = istekKat - elv.getAnlikKat();
                if(distance < 0 && elv.getAsansorDurum().equals(AsansorDurum.UP)){
                    // No point selecting these elevators. They have already passed by our request floor
                    continue;
                } else {
                    sortedKeyMap.put(Math.abs(distance), elv.getId());
                }
            }

            // TODO - potential NullPointerException
            Integer selectedElevatorId = sortedKeyMap.firstEntry().getValue();
            asansor = upMovingMap.get(selectedElevatorId);


        } else if(asansorDurum.equals(AsansorDurum.DOWN)){
            // Let's go over all elevators that are either going DOWN or are STATIONARY
            for(Map.Entry<Integer, Asansorler> elvMap : downMovingMap.entrySet()){
                Asansorler elv = elvMap.getValue();
                Integer distance = elv.getAnlikKat() - istekKat;
                if(distance < 0 && elv.getAsansorDurum().equals(AsansorDurum.DOWN)){
                    // No point selecting these elevators. They have already passed by our requested floor
                    continue;
                } else {
                    sortedKeyMap.put(Math.abs(distance), elv.getId());
                }
            }
            // TODO - potential NullPointerException
            Integer selectedElevatorId = sortedKeyMap.firstEntry().getValue();
            asansor = downMovingMap.get(selectedElevatorId);

        }

        // Instructing the selected elevator to stop/pass by relavent floors
        AsansorIstek newRequest = new AsansorIstek(asansor.getAnlikKat(), istekKat);
        AsansorDurum elevatorDirection = getRequestedElevatorDirection(newRequest);

        // helpful if we are moving in opposite direction to than that of request
        AsansorIstek newRequest2 = new AsansorIstek(istekKat, hedefKat);
        AsansorDurum elevatorDirection2 = getRequestedElevatorDirection(newRequest2);

        NavigableSet<Integer> floorSet = asansor.floorStopsMap.get(elevatorDirection);
        if (floorSet == null) {
            floorSet = new ConcurrentSkipListSet<Integer>();
        }

        floorSet.add(asansor.getAnlikKat());
        floorSet.add(istekKat);
        asansor.floorStopsMap.put(elevatorDirection, floorSet);

        NavigableSet<Integer> floorSet2 = asansor.floorStopsMap.get(elevatorDirection2);
        if (floorSet2 == null) {
            floorSet2 = new ConcurrentSkipListSet<Integer>();
        }

        floorSet2.add(istekKat);
        floorSet2.add(hedefKat);
        asansor.floorStopsMap.put(elevatorDirection2, floorSet2);

        return asansor;
    }

    
    public static synchronized void updateAsansorList(Asansorler asansor){
        if(asansor.getAsansorDurum().equals(AsansorDurum.UP)){
            upMovingMap.put(asansor.getId(), asansor);
            downMovingMap.remove(asansor.getId());
        } else if(asansor.getAsansorDurum().equals(AsansorDurum.DOWN)){
            downMovingMap.put(asansor.getId(), asansor);
            upMovingMap.remove(asansor.getId());
        } else if (asansor.getAsansorDurum().equals(AsansorDurum.STATIONARY)){
            upMovingMap.put(asansor.getId(), asansor);
            downMovingMap.put(asansor.getId(),asansor);
        } else if (asansor.getAsansorDurum().equals(AsansorDurum.MAINTAINANCE)){
            upMovingMap.remove(asansor.getId());
            downMovingMap.remove(asansor.getId());
        }
    }

    @Override
    public void run() {
        stopController =  false;
        while(true){
            try {
                Thread.sleep(200);
                if(stopController){
                    break;
                }
            } catch (InterruptedException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    public void setStopController(boolean stop){
        this.stopController = stop;

    }

    public synchronized List<Asansorler> getAsansorList() {
        return asansorList;
    }

    public boolean isStopController() {
        return stopController;
    }
    
    
}
