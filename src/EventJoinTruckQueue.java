/**
 * Only created when Item is created
 */
class EventJoinTruckQueue extends Event {
    private final Item i;
    private final Building b;
    private final int DELAY_TIME = 5;

    public EventJoinTruckQueue(double time, Item i, Building b) {
        super(time, EventPriorityEnum.P_JoinTruckQueue);
        this.i = i;
        this.b = b;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(": %s joining truck queue at %s", i, b);
    }


    @Override
    public Event[] simulate() {
        this.b.enqueueTransport(this.i);
        if (Company.isBuildingX(this.b) && this.b.getTransportQueueLength() >= 5) {
            if (this.b == Company.getTruck().getCurrentLocation()) {
                return new Event[]{
                        new EventTransport(this.getTime(), this.b, false)
                };
            } else {
                return new Event[]{
                        new EventTransportDelay(this.getTime() + DELAY_TIME, this.b)
                };
            }
        }

        // For BuildingY
        return new Event[]{};
    }
}
