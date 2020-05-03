package assignment2;
/**
 * This class stores the data that each entry of HashDictionary will contain.
 * An object of this class stores a board configuration and its integer score
 */
public class Configuration {
    private String configuration;
    private int score;

    /**
     * A constructor which returns a new Configuration object with the
     * specified configuration string and score. The string config will
     * be used as the key attribute for every Configuration object.
     *
     * @param config the string configuration
     * @param score the score for this corresponding string
     */
    public Configuration(String config, int score) {
        this.configuration = config;
        this.score = score;
    }

    /**
     *
     * @return the configuration string stored in a Configuration object.
     */
    public String getStringConfiguration(){
        return this.configuration;
    }

    /**
     *
     * @return the score for the corresponding configuration
     */
    public int getScore(){
        return this.score;
    }
}
