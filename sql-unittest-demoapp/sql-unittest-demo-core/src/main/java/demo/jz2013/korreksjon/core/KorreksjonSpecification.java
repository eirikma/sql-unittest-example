package demo.jz2013.korreksjon.core;

/**
 * Specifiaction to find Korreksjon instances.
 * <p/>
 * Doesn't 'orderBy' if used with InMemoryEntityRepository.
 * <p/>
 * User: ffu
 * Date: 1/27/11
 * Time: 11:45 AM
 */
public class KorreksjonSpecification extends AbstractKorreksjonsdataSpecification {

    public KorreksjonSpecification() {
        super();
    }

    public KorreksjonSpecification(Source source) {
        super(Korreksjon.class, source);
        setSource(source);
    }


    public KorreksjonSpecification(Source source, String bestillingsnr) {
        this(source);
        setBestillingsnummer(bestillingsnr);
    }


    @Override
    public Class getType() {
        return AbstractKorreksjonsdata.class;
    }

}
