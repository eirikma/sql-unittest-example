package demo.jz2013.korreksjon.core;

import java.util.Arrays;

public enum KorreksjonType {
    BESTILLING("Bestilling"),
    REVERSERING("Reversering"),
    REVERSERING_MED_FULLMAKT("Reversering med fullmakt"),
    KORREKSJON("Korreksjon"),
    KORREKSJON_MED_FULLMAKT("Korreksjon med fullmakt");

    public static KorreksjonType presentationStringToEnum(String presentationString) {
        for (KorreksjonType type : KorreksjonType.values()) {
            if (type.presentationString.equals(presentationString)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Korreksjonstypen '" + presentationString
                + "' eksisterer ikke. Gyldige korreksjonstyper er: " + Arrays.toString(KorreksjonType.values()));
    }


    private final String presentationString;

    KorreksjonType(String presentationString) {
        this.presentationString = presentationString;
    }

    public String getPresentationString() {
        return presentationString;
    }

}
