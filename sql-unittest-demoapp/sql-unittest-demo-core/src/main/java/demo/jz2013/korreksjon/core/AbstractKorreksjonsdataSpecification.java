package demo.jz2013.korreksjon.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.*;
import java.util.Date;

public class AbstractKorreksjonsdataSpecification implements Comparator<AbstractKorreksjonsdata> {

    // Expected search criterion
    private Source source;
    private Set<Status> validStatuses;
    private Set<KorreksjonType> korreksjonTyper;
    private Date originalDateOnOrAfter;
    private Date origDateBeforeOrOn;
    private String debetOrCreditAccount;
    private String debetOrCreditRegNr;
    private String bestillingsNr;
    private String blankettnummer;
    private Date bestillingsDato;


    private final List<OrderBy> orderBys = new ArrayList<OrderBy>();

    private Class<? extends AbstractKorreksjonsdata> type;
    private boolean lastMonthOnly;

    private AbstractKorreksjonsdataSpecification(Class<? extends AbstractKorreksjonsdata> type) {
        this.type = type;
    }

    public AbstractKorreksjonsdataSpecification() {
        this(AbstractKorreksjonsdata.class);
    }

    AbstractKorreksjonsdataSpecification(Class<? extends AbstractKorreksjonsdata> type, Source source) {
        this(type);
        setSource(source);
    }


    public void setType(Class<? extends AbstractKorreksjonsdata> type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public void setBestillingsDato(Date bestillingsDato) {
        this.bestillingsDato = bestillingsDato;
    }


    public static KorreksjonSpecification fraKildeMedRefNr(Source source, String sourceRefNo) {
        return new KorreksjonSpecification(source, sourceRefNo);
    }

    public void setValidStatuses(final Set<Status> validStatuses) {
        this.validStatuses = validStatuses;
    }

    //Convenience method
    public void setStatus(final Status status) {
        setValidStatuses(Collections.singleton(status));
    }

    public void setBestillingsnummer(String bestillingsnr) {
        this.bestillingsNr = bestillingsnr;
    }

    public void setBlankettnummer(String blankettnummer) {
        if (StringUtils.isBlank(blankettnummer))
            return;
        this.blankettnummer = blankettnummer;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setKorreksjonTyper(KorreksjonType... korreksjonTyper) {
        this.korreksjonTyper = safeSetValue(Arrays.asList(korreksjonTyper));
    }

    public void setKorreksjonTyper(List<KorreksjonType> korreksjonTyper) {
        this.korreksjonTyper = safeSetValue(korreksjonTyper);
    }

    private HashSet<KorreksjonType> safeSetValue(final List<KorreksjonType> korreksjonTyper) {
        if(korreksjonTyper == null){
            return null;
        }
        return new HashSet<KorreksjonType>(korreksjonTyper);
    }

    public void setOriginalDatoBetween(Date originalDateOnOrAfter, Date origDateBeforeOrOn) {
        this.originalDateOnOrAfter = originalDateOnOrAfter;
        this.origDateBeforeOrOn = origDateBeforeOrOn;
    }

    public void setDebetOrCreditAccount(String debetOrCreditAccount) {
        if (StringUtils.isBlank(debetOrCreditAccount)) {
            return;
        }
        this.debetOrCreditAccount = debetOrCreditAccount;
    }

    public void setDebetOrCreditRegNr(String debetOrCreditRegNr) {
        if (StringUtils.isBlank(debetOrCreditRegNr)) {
            return;
        }
        if (debetOrCreditRegNr != null) {
            Validate.isTrue(debetOrCreditRegNr.length() == 4, "regnr should have length 4");
        }
        this.debetOrCreditRegNr = debetOrCreditRegNr;
    }

    public void addOrderBys(OrderBy... orderBy) {
        if (orderBy == null) return;
        this.orderBys.addAll(Arrays.asList(orderBy));
    }

    /**
     * specification / matching method for InMemoryEntityRepository
     *
     * @param o - a possibly matching object
     * @return - true if object matches properties specified in this specification, else false.
     */
    public boolean matches(Object o) {
        if (!(getType().isAssignableFrom(o.getClass()))) {
            return false;
        }
        AbstractKorreksjonsdata korreksjonsdata = (AbstractKorreksjonsdata) o;
        boolean matches = true;
        if (source != null) {
            matches = matches && source.equals(korreksjonsdata.getSource());
        }
        if (validStatuses != null) {
            matches = matches && validStatuses.contains(korreksjonsdata.getStatus());
        }
        if (korreksjonTyper != null) {
            matches = matches && korreksjonTyper.contains(korreksjonsdata.getKorreksjonType());
        }
        if (origDateBeforeOrOn != null && originalDateOnOrAfter != null) {
//            final Date opprinneligDato = korreksjonsdata.getOpprinneligDatoAsDate();
//            final Interval interval = CalendarInterval.inclusive(originalDateOnOrAfter, origDateBeforeOrOn);
//            matches = matches && (opprinneligDato == null || interval.includes(opprinneligDato));
        }
        if (debetOrCreditAccount != null) {
            matches = matches &&
                    (debetOrCreditAccount.equals(korreksjonsdata.getCreditAccount())
                            || debetOrCreditAccount.equals(korreksjonsdata.getDebetAccount()));
        }
        if (debetOrCreditRegNr != null) {
            String creditAccount = korreksjonsdata.getCreditAccount();
            String debetAccount = korreksjonsdata.getDebetAccount();
            matches = matches && ((creditAccount != null && debetAccount.startsWith(debetOrCreditRegNr))
                    || (debetAccount != null && debetAccount.startsWith(debetOrCreditRegNr)));
        }
        if (bestillingsNr != null) {
            matches = matches && bestillingsNr.trim().equals(korreksjonsdata.getBestillingsnummer().trim());
        }
        if (blankettnummer != null) {
            matches = matches && blankettnummer.equals(korreksjonsdata.getBlankettnummer());
        }
        if (lastMonthOnly) {
            matches = matches && bestillingsDato.after(lastMonth());
        } else if (bestillingsDato != null) {
            matches = matches && bestillingsDato.equals(korreksjonsdata.getBestillingsdato());
        }
        return matches;
    }

    /**
     * specification / matching method for hibernate
     *
     * @param criteria
     */
    public void populate(Criteria criteria) {
//        super.populate(criteria);

        addEqRestrictionIfNotNull(criteria, "source", source);
        if (validStatuses != null) {
            criteria.add(Restrictions.in("status", validStatuses));
        }
        addEqRestrictionIfNotNull(criteria, "bestillingsnummer", bestillingsNr);
        Criteria currentTilstandCriteria = criteria.createCriteria("currentTilstand");
        addEqRestrictionIfNotNull(currentTilstandCriteria, "blankettnummer", blankettnummer);
        if (korreksjonTyper != null) {
            currentTilstandCriteria.add(
                    Restrictions.in("korreksjonType", korreksjonTyper)
            );
        }

        if (origDateBeforeOrOn != null && originalDateOnOrAfter != null) {
            currentTilstandCriteria.add(Restrictions.between("opprinneligDato",
                    originalDateOnOrAfter,
                    origDateBeforeOrOn));
        } else {
            if (originalDateOnOrAfter != null) {
                currentTilstandCriteria.add(Restrictions.ge("opprinneligDato", originalDateOnOrAfter));
            }
            if (origDateBeforeOrOn != null) {
                currentTilstandCriteria.add(Restrictions.le("opprinneligDato", origDateBeforeOrOn));
            }
        }
        if (lastMonthOnly) {
            criteria.add(Restrictions.gt("bestillingsdato", lastMonth()));
        } else {
            addEqRestrictionIfNotNull(criteria, "bestillingsdato", bestillingsDato);
        }

        if (!StringUtils.isBlank(debetOrCreditAccount)) {
            currentTilstandCriteria.add(Restrictions.or(
                    Restrictions.eq("creditAccount", debetOrCreditAccount),
                    Restrictions.eq("debetAccount", debetOrCreditAccount)
            ));
        }
        if (!StringUtils.isBlank(debetOrCreditRegNr)) {
            currentTilstandCriteria.add(Restrictions.or(
                    Restrictions.like("creditAccount", debetOrCreditRegNr + "%"),
                    Restrictions.like("debetAccount", debetOrCreditRegNr + "%")
            ));
        }

        // add order by clause
        for (OrderBy by : orderBys) {
            StringBuilder korreksjonTilstandAttributter = new StringBuilder();
            korreksjonTilstandAttributter.append("opprinneligDato, ");
            korreksjonTilstandAttributter.append("blankettnummer, ");
            korreksjonTilstandAttributter.append("blankettnummer, ");
            korreksjonTilstandAttributter.append("debetAccount, ");
            korreksjonTilstandAttributter.append("creditAccount, ");
            korreksjonTilstandAttributter.append("amount, ");
            korreksjonTilstandAttributter.append("cause");
            if (korreksjonTilstandAttributter.toString().contains(by.getNameOfCurrentValue())) {
                currentTilstandCriteria.addOrder(OrderBy.Order.asc.equals(by.getOrder())
                        ? Order.asc(by.getNameOfCurrentValue())
                        : Order.desc(by.getNameOfCurrentValue()));
            } else {
                criteria.addOrder(OrderBy.Order.asc.equals(by.getOrder())
                        ? Order.asc(by.getNameOfCurrentValue())
                        : Order.desc(by.getNameOfCurrentValue()));
            }
        }
    }

    private void addEqRestrictionIfNotNull(Criteria criteria, String property, Object requiredValue) {
        if (requiredValue != null) {
            criteria.add(Restrictions.eq(property, requiredValue));
        }
    }

    private Date lastMonth() {
        //return Date.today().plusMonths(-1).asDate();
        return new Date(System.currentTimeMillis() - (30L * 24L * 60L * 60L * 1000L));
    }

    /**
     * Compares korreksjon for in-memory sorting (when using InMemoryentityRepository)
     *
     * @param k1 - an instance of korreksjon
     * @param k2 - another instance of korreksjon
     * @return 0 if equal;  -1 if k1 is before k2; else +1 (if k2 is before k1)
     */
    public int compare(AbstractKorreksjonsdata k1, AbstractKorreksjonsdata k2) {
        if (k1 == k2) return 0; //same object, AbstractKorreksjonsdata does not implement equals fully

        for (OrderBy orderBy : orderBys) {
            int res = orderBy.compare(k1, k2);
            if (res != 0) return res;
        }

        return 0;
    }

    public void setDisplayLastMonthOnly(final boolean display) {
        lastMonthOnly = display;
    }
}
