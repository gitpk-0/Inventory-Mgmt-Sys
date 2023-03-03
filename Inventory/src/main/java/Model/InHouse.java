package Model;

/**
 * @author Patrick Kell
 */

/**
 * InHouse is a class that inherits from the Part class
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Creates an InHouse object with the provided arguments
     *
     * @param id        The id of the part
     * @param name      The name of the part
     * @param price     The price of the part
     * @param stock     The amount of inventory of the part
     * @param min       The minimum amount required of the part
     * @param max       The maximum amount allowed of the part
     * @param machineId The machineId of the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * getter for the machineId
     *
     * @return The machineId
     */
    public int getMachineId() {
        return this.machineId;
    }

    /**
     * setter for the machineId
     *
     * @param machineId Sets the machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
