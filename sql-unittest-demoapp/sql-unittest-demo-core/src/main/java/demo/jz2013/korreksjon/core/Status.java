package demo.jz2013.korreksjon.core;


import java.util.*;

public enum Status {

    KLAR("Klar"),
    KLAR_TIL_VERIFISERING("Klar til verifisering"),
    KORRIGERT("Korrigert"),
    UNDER_BEHANDLING("Under behandling"),
    FERDIGBEHANDLET("Ferdigbehandlet"),
    SLETTET("Slettet"),
    VENTER_FULLMAKT("Venter på fullmakt");

    private static final Map<Status, List<Status>> legalTransitionMap = new HashMap<Status, List<Status>>(10);

    static {
        legalTransitionMap.put(KLAR, Arrays.asList(FERDIGBEHANDLET, UNDER_BEHANDLING, SLETTET));
        legalTransitionMap.put(UNDER_BEHANDLING, Arrays.asList(FERDIGBEHANDLET, SLETTET));
        legalTransitionMap.put(FERDIGBEHANDLET, Collections.<Status>emptyList());
        legalTransitionMap.put(KORRIGERT, Arrays.asList(FERDIGBEHANDLET, SLETTET));
        legalTransitionMap.put(SLETTET, Collections.<Status>emptyList());
        legalTransitionMap.put(KLAR_TIL_VERIFISERING, Arrays.asList(KORRIGERT, SLETTET, VENTER_FULLMAKT));
        legalTransitionMap.put(VENTER_FULLMAKT, Arrays.asList(KORRIGERT, SLETTET));
        assertAllStatusesHaveTransitionsDefined();
    }


    @SuppressWarnings({"UnusedDeclaration"})
    public static Status presentationStringToEnum(String presentationString) {
        for (Status status : Status.values()) {
            if (status.presentationString.equals(presentationString)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Korreksjonsstatus '" + presentationString + "' eksisterer ikke. Gyldige korreksjonsstatuser er: " + Arrays.toString(Status.values()));
    }

    private final String presentationString;

    Status(String presentationString) {
        this.presentationString = presentationString;
    }

    public String getPresentationString() {
        return presentationString;
    }

    public boolean isStateTransitionAllowed(Status newStatus, Cause cause) {
        // always legal to not change state
        if (newStatus == this) {
            return true;
        }

        // special rule for debet_med_fullmakt
        if (this.equals(KLAR_TIL_VERIFISERING) && VENTER_FULLMAKT.equals(newStatus)) {
            return cause.equals(Cause.DEBET_MED_FULLMAKT);
        }

        // other rules defined in the map of new states
        return legalTransitionMap.get(this).contains(newStatus);
    }

    private static void assertAllStatusesHaveTransitionsDefined() {
        for (Status status : Status.values()) {
            if (!legalTransitionMap.containsKey(status)) {
                throw new IllegalStateException("No state transition rules defined for state " + status);
            }
        }
    }

}
