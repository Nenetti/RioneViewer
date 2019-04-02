package rione.viewer;


public class Category {

    public enum Type {
        AmbulanceTeam(1),
        FireBrigade(2),
        PoliceForce(3),
        Civilian(4),

        Road(10),
        Hydrant(11),

        Blockade(15),

        Building(20),
        Refuge(21),
        GasStation(22),
        AmbulanceCenter(25),
        FireStation(26),
        PoliceOffice(27),

        Human(100),
        Area(101);

        private final int number;

        Type (final int number) {
            this.number = number;
        }

        public int getID () {
            return number;
        }

        public static Type getURN (final int number) {
            Type[] urns = Type.values();
            for (Type urn : urns) {
                if ( urn.getID() == number ) {
                    return urn;
                }
            }
            return null;
        }
    }


    public enum Action {
        ActionRest(1),
        ActionMove(2),
        ActionSearch(3),

        ActionLoad(5),
        ActionUnload(6),
        ActionRescue(7),

        ActionExtinguish(10),
        ActionRefill(11),

        ActionClear(15);

        private final int number;

        Action (final int number) {
            this.number = number;
        }

        public int getID () {
            return number;
        }

        public static Action getURN (final int number) {
            Action[] urns = Action.values();
            for (Action urn : urns) {
                if ( urn.getID() == number ) {
                    return urn;
                }
            }
            return null;
        }

        public String getString () {
            return this.toString();
        }
    }


    public enum Message {
        MessageAmbulanceTeam(1),
        MessageFireBrigade(2),
        MessagePoliceForce(3),
        MessageCivilian(4),

        MessageRoad(10),

        MessageBuilding(20),

        CommandAmbulance(31),
        CommandFire(32),
        CommandPolice(33);

        private final int number;

        Message (final int number) {
            this.number = number;
        }

        public int getID () {
            return number;
        }

        public static Message getURN (final int number) {
            Message[] urns = Message.values();
            for (Message urn : urns) {
                if ( urn.getID() == number ) {
                    return urn;
                }
            }
            return null;
        }

        public String getString () {
            return this.toString();
        }
    }


    public enum Fieryness {
        /**
         * Not burnt at all.
         */
        UNBURNT(0),
        /**
         * On fire a bit.
         */
        HEATING(1),
        /**
         * On fire a bit more.
         */
        BURNING(2),
        /**
         * On fire a lot.
         */
        INFERNO(3),
        /**
         * Not burnt at all, but has water damage.
         */
        WATER_DAMAGE(4),
        /**
         * Extinguished but minor damage.
         */
        MINOR_DAMAGE(5),
        /**
         * Extinguished but moderate damage.
         */
        MODERATE_DAMAGE(6),
        /**
         * Extinguished but major damage.
         */
        SEVERE_DAMAGE(7),
        /**
         * Completely burnt out.
         */
        BURNT_OUT(8);

        private final int number;

        private Fieryness (final int number) {
            this.number = number;
        }

        public int getID () {
            return number;
        }

        public static Fieryness getURN (final int number) {
            Fieryness[] urns = Fieryness.values();
            for (Fieryness urn : urns) {
                if ( urn.getID() == number ) {
                    return urn;
                }
            }
            return null;
        }

        public String getString () {
            return this.toString();
        }
    }


    public enum drawModeURN {
        Polygon(1),
        Circle(2),
        Point(3),
        Line(4);

        private final int number;

        private drawModeURN (final int number) {
            this.number = number;
        }

        public int getID () {
            return number;
        }

        public static drawModeURN getURN (final int number) {
            drawModeURN[] urns = drawModeURN.values();
            for (drawModeURN urn : urns) {
                if ( urn.getID() == number ) {
                    return urn;
                }
            }
            return null;
        }

        public String getString () {
            return this.toString();
        }
    }


    public enum drawOption {
        draw(1),
        fill(2);

        private final int number;

        private drawOption (final int number) {
            this.number = number;
        }

        public int getID () {
            return number;
        }

        public static drawOption getURN (final int number) {
            drawOption[] urns = drawOption.values();
            for (drawOption urn : urns) {
                if ( urn.getID() == number ) {
                    return urn;
                }
            }
            return null;
        }

        public String getString () {
            return this.toString();
        }
    }
}