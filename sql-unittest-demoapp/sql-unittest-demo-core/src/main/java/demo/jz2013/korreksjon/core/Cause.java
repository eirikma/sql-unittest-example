package demo.jz2013.korreksjon.core;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Gyldige årsakskoder.
 */
public enum Cause {
    KREDIT("Kredit"),
    KREDIT_MED_FULLMAKT("Kredit m/fullmakt"),
    DEBET("Debet"),
    DEBET_MED_FULLMAKT("Debet m/fullmakt"),
    OVERFORING("Overføring"),
    DOBBELREGISTRERING("Dobb.reg."),
    DOBBELREGISTRERING_MED_FULLMAKT("Dobb.reg. m/fullmakt"),
    BELOEP("Beløp"),
    BELOEP_MED_FULLMAKT("Beløp m/fullmakt"),
    FEIL_BANK("Feil bank"),
    ANNET("Annet"),
    UGYLDIG("Ugyldig årsak"); //Brukes under testing

    private static final Logger LOG = LoggerFactory.getLogger(Cause.class);

    public static Cause presentationStringToEnum(String presentationString) {
        for (Cause type : Cause.values()) {
            if (type.getPresentationString().equals(presentationString)) {
                return type;
            }
        }
        if (presentationString.equals("Bel?")) { //FIXME workaround for bug in Bestillingdb rest api
            LOG.warn("Workaround for inkonsistent tegnsett i bestillingdb rest api");
            return BELOEP;
        }
        throw new IllegalArgumentException("Korreksjonstypen '" + presentationString + "' eksisterer ikke. Gyldige korreksjonstyper er: " + Arrays.toString(Cause.values()));
    }

    private final String presentationString;

    Cause(String presentationString) {
        this.presentationString = presentationString;
    }

    public String getPresentationString() {
        return presentationString;
    }

    /** returns true if the current cause is in the provided list of causes */
    public boolean in(Cause... causes) {
        return causes != null && Arrays.asList(causes).contains(this);
    }
}
