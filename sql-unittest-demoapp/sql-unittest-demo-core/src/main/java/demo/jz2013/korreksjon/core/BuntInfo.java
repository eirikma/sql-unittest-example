package demo.jz2013.korreksjon.core;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BuntInfo {

    private final String buntnummer;
    private final boolean acceptNullAsValidBuntnummer;
    private final static Logger Logger = LoggerFactory.getLogger(BuntInfo.class);

    public BuntInfo(String buntnummer) {
        this(buntnummer, true);
    }

    public BuntInfo(String buntnummer, boolean acceptNullAsValidBuntnummer) {
        this.buntnummer = buntnummer;
        this.acceptNullAsValidBuntnummer = acceptNullAsValidBuntnummer;
    }

    public boolean isDebetBuntType() {
        String buntType = getBuntType();
        return buntType != null && (buntType.equals("01") || buntType.equals("31"));
    }

    public boolean isCreditBuntType() {
        String buntType = getBuntType();
        return buntType != null && (buntType.equals("06") || buntType.equals("41"));
    }

    public boolean isBank() {
        final String buntType = getBuntType();
        return buntType.equals("01") || buntType.equals("06");
    }

    public boolean isPib() {
        final String buntType = getBuntType();
        return buntType.equals("41") || buntType.equals("31");
    }

    /**
     * Returns the code for the buntType, logging an error if buntnummer is not recognized. If
     * the flag to allow 'null' has been set, error is not logged, and this method returns null.
     * No buntnummer is a common case for korreksjon instances created from the web gui ('overføring')
     * rather than from the transhist + bestillingslogg.
     *
     * @return
     */
    public String getBuntType() {
        if (buntnummer == null && acceptNullAsValidBuntnummer) {
            return null;
        }
        if (!erGyldigbuntnummer()) {
            Logger.error("Mottok ugyldig buntnummer " + buntnummer);
            return null;
        }
        String buntnummerForValidering = buntnummer;
        if (buntnummer.length() == 10 && buntnummer.charAt(0) == '0') {
            buntnummerForValidering = buntnummer.substring(1, buntnummer.length());
        }

        String type = null;
        if (buntnummerForValidering.length() == 10) {
            type = buntnummerForValidering.substring(0, 2);
        } else if (buntnummerForValidering.length() == 9) {
            type = buntnummerForValidering.substring(4, 6);
        } else {
            Logger.error("Kan ikke håndtere bunttypen gitt av buntnummer " + buntnummer);
        }

        return type;
    }

    public boolean erGyldigbuntnummer() {
        if (StringUtils.isBlank(buntnummer)) {
            return false;
        }
        if (buntnummer.length() != 9 && buntnummer.length() != 10) {
            return false;
        }
        try {
            Long.parseLong(buntnummer);
        } catch (NumberFormatException exp) {
            return false;
        }

        return true;
    }

    public String getBuntnummer() {
        return buntnummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuntInfo buntInfo = (BuntInfo) o;

        return buntnummer.equals(buntInfo.getBuntnummer());

    }

    @Override
    public int hashCode() {
        return buntnummer.hashCode();
    }
}
