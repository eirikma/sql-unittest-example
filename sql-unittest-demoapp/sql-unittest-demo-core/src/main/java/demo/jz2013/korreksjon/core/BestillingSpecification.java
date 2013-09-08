package demo.jz2013.korreksjon.core;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Specifiaction to find Bestilling instances.
 * <p/>
 * Doesn't 'orderBy' if used with InMemoryEntityRepository.
 */
public class BestillingSpecification extends AbstractKorreksjonsdataSpecification {


    public BestillingSpecification() {
        super(Bestilling.class, Source.BESTILLINGSDB);
        setKorreksjonTyper(KorreksjonType.BESTILLING);
    }


    public BestillingSpecification(String bestillingsnr) {
        this();
        setBestillingsnummer(bestillingsnr);
    }

    private BestillingSpecification(final Status... gyldigeStatuser) {
        this();
        if (gyldigeStatuser != null) {
            setValidStatuses(new HashSet<Status>(Arrays.asList(gyldigeStatuser)));
        }
    }

    @Override
    public Class getType() {
        return Bestilling.class;
    }

    public static BestillingSpecification alleBestillingerUnderBehandling() {
        return new BestillingSpecification(Status.UNDER_BEHANDLING, Status.KLAR);
    }

}
