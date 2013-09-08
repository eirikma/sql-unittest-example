package demo.jz2013.korreksjon.core;

/**
 * Source for the creation of a Korreksjon instance, used to detect where it came from.
 */
public enum Source {

    /**
     * Korreksjon added through the web interface
     */
    KORREKSJON_WEB,

    /**
     * Korreksjon originating from bestillingsdatabasen
     */
    BESTILLINGSDB,

    /**
     * Korreksjon based upon a korreksjon originating from bestillingsdatabasen
     */
    AVLEDET_BESTILLINGSDB

}
