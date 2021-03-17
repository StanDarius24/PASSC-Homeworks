package WorkersPackage;

import ComponentsPackage.Seat;
import EventsPackage.ChairSeatDone;
import Interfaces.ChairEvents;
import Interfaces.ChairInterface;
import Interfaces.Components;
import Interfaces.Worker;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class SeatCREATOR extends Worker implements Runnable {
    private EventBus eventBus;
    private int time;
    private ChairInterface c;
    private String name;
    private ChairEvents events;
    private boolean status=false;
    Thread thread;

    public SeatCREATOR(int t,EventBus b, String name)
    {
        this.eventBus=b;
        this.time=t;
        eventBus.register(this);
        this.name=name;
        thread= new Thread(this);
        thread.start();

    }

    public boolean checkChair(ChairInterface c)
    {
        return c.CanIAssamble(Seat.getInstance());
    }

    @Subscribe
    public synchronized void operate(ChairEvents ev)
    {
        ChairInterface s= ev.getChair();
        if(s!=null)
        if(!status && s.CanIAssamble(Seat.getInstance()) && !ev.canItake())
        {
        status=true;
        ev.take();
        this.c = s;
        }
    }

    @Override
    public void run() {
        while(true) {

            if (status) {
                try {

                    Thread.sleep(time);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                c.Assamble(Seat.getInstance());
                events = new ChairSeatDone();
                events.attachChair(c);
                eventBus.post(events);

                while (!events.canItake()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    eventBus.post(events);
                }
                System.out.println(this.name + " a asamblat sezutul la scaunul cu nr " + c.getId());
                status = false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        }    }
}