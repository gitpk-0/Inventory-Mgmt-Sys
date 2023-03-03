package Model;

/**
 * @author Patrick Kell
 */

/**
 * Outsourced is a class that inherits from the Part class
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Creates an Outsourced object with the provided arguments
     *
     * @param id          The id of the part
     * @param name        The name of the part
     * @param price       The price of the part
     * @param stock       The inventory amount of the part
     * @param min         The minimum required stock of the part
     * @param max         The maximum allowed stock of the part
     * @param companyName The company name which produces the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return The company name of the part
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName The company name to be set of the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
