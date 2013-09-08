package demo.jz2013.korreksjon.core;


import org.apache.commons.lang.Validate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * package local implementation of a state (or the current state) belonging
 * to the state history of a single Korreksjon
 * <p/>
 * Created by IntelliJ IDEA.
 * User: ffu
 * Date: 1/25/11
 * Time: 3:11 PM
 */
public class KorreksjonTilstandImpl /*extends AbstractBaseEntity*/ implements KorreksjonTilstand {

    private AbstractKorreksjonsdata owner;

    private KorreksjonType korreksjonType;
    private String bestillingsnummer;
    private Date bestillingsdato;
    private Timestamp createdAt;
    private String createdBy;
    private Status status;

    private String debetAccount;
    private String creditAccount;
    private BigDecimal amount;
    private String blankettnummer;
    private String arkivref;
    private Date opprinneligDato;
    private Cause cause;
    private String fritekst;
    private String gebyrBankregnummer;
    private String debetNavn;

    private String nibeBusCode;
    private String nibeAlcCode;
    private Source source;

    private String bestillingStatusUpdatePath;

    private String kidNummer;
    private String buntNummer;
    private String transTekst;
    private BigDecimal belopKreditSumpost;

    private int historyIndexNo;


    // helper class to validate on creation. names must be member fields names.
    // must be wrapped in an ordinary arrayList, or else InMemoryEntityRepository can't clone it.
    private final List<NotNull> notNulls = new ArrayList(Arrays.asList(
            notNull("owner"),
            notNull("createdAt"),
            notNull("createdBy"),
            notNull("bestillingsnummer"),
            notNull("bestillingsdato"),
            notNull("source"),
            notNull("status"),
            new NotNull("korreksjonType") {
                @Override
                public void validateIsSet(KorreksjonTilstandImpl instance) {
                    if (instance.owner instanceof Korreksjon) {
                        super.validateIsSet(instance);
                    } else if (instance.owner instanceof Bestilling) {
                        super.validateIsSet(instance);
                    } else {
                        if (instance.korreksjonType != null) {
                            throw new IllegalArgumentException("cannot set korreksjonType on Bestilling");
                        }
                    }
                }
            }
    ));


    /**
     * only for hibernate
     */
    private KorreksjonTilstandImpl() {
    }


    private KorreksjonTilstandImpl(KorreksjonTilstandImpl previousTilstand) {
        this();
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.createdBy = null;
        if (previousTilstand != null) {
            this.historyIndexNo = previousTilstand.getHistoryIndexNo() + 1;
            this.owner = previousTilstand.getOwner();
            this.amount = previousTilstand.getAmount();
            this.arkivref = previousTilstand.getArkivref();
            this.belopKreditSumpost = previousTilstand.getBelopKreditSumpost();
            this.bestillingsdato = previousTilstand.getBestillingsdato();
            this.bestillingsnummer = previousTilstand.getBestillingsnummer();
            this.bestillingStatusUpdatePath = previousTilstand.getBestillingStatusUpdatePath();
            this.blankettnummer = previousTilstand.getBlankettnummer();
            this.buntNummer = previousTilstand.getBuntnummer();
            this.cause = previousTilstand.getCause();
            this.creditAccount = previousTilstand.getCreditAccount();
            this.debetAccount = previousTilstand.getDebetAccount();
            this.debetNavn = previousTilstand.getDebetNavn();
            this.fritekst = previousTilstand.getFritekst();
            this.gebyrBankregnummer = previousTilstand.getGebyrBankregnummer();
            this.kidNummer = previousTilstand.getKidNummer();
            this.korreksjonType = previousTilstand.getKorreksjonType();
            this.nibeBusCode = previousTilstand.getNibeBusCode();
            this.nibeAlcCode = previousTilstand.getNibeAlcCode();
            this.opprinneligDato = previousTilstand.getOpprinneligDato();
            this.source = previousTilstand.getSource();
            this.status = previousTilstand.getStatus();
            this.transTekst = previousTilstand.getTransTekst();
        }

    }


    @Override
    public Cause getCause() {
        return cause;
    }

    @Override
    public String getBlankettnummer() {
        return blankettnummer;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getCreditAccount() {
        return creditAccount;
    }

    @Override
    public String getDebetAccount() {
        return debetAccount;
    }

    @Override
    public String getFritekst() {
        return fritekst;
    }

    @Override
    public String getGebyrBankregnummer() {
        return gebyrBankregnummer;
    }

    @Override
    public String getNibeBusCode() {
        return nibeBusCode;
    }

    @Override
    public String getNibeAlcCode() {
        return nibeAlcCode;
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public String getBestillingStatusUpdatePath() {
        return bestillingStatusUpdatePath;
    }

    @Override
    public String getKidNummer() {
        return kidNummer;
    }

    @Override
    public String getBuntnummer() {
        return buntNummer;
    }

    @Override
    public String getTransTekst() {
        return transTekst;
    }

    @Override
    public BigDecimal getBelopKreditSumpost() {
        return belopKreditSumpost;
    }

    @Override
    public Date getOpprinneligDato() {
        return opprinneligDato;
    }

    @Override
    public String getArkivref() {
        return arkivref;
    }

    @Override
    public KorreksjonType getKorreksjonType() {
        return korreksjonType;
    }

    @Override
    public String getBestillingsnummer() {
        return bestillingsnummer;
    }

    @Override
    public Date getBestillingsdato() {
        return bestillingsdato;
    }

    @Override
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getDebetNavn() {
        return debetNavn;
    }

    protected AbstractKorreksjonsdata getOwner() {
        return owner;
    }

    public Korreksjon getKorreksjon() {
        return (Korreksjon) getOwner();
    }

    public Bestilling getBestilling() {
        return (Bestilling) getOwner();
    }

    public int getHistoryIndexNo() {
        return historyIndexNo;
    }

    @Override
    public Date getBestillingsdatoAsDate() {
        return getBestillingsdato();// == null ? null : Date.from(getBestillingsdato());
    }

    @Override
    public String getAmountAsString() {
        return getAmount().toPlainString();//Money.nok(getAmount() != null ? getAmount() : BigDecimal.ZERO).asFormattedString();
    }

    @Override
    public Date getOpprinneligDatoAsDate() {
//        return getOpprinneligDato() == null
//                ? Date.FAR_PAST
//                : Date.from(getOpprinneligDato());
        return getOpprinneligDato();
    }


    static class NotNull {
        private Field declaredField;
        private final String name;

        public NotNull(String name) {
            this.name = name;
            try {
                declaredField = KorreksjonTilstandImpl.class.getDeclaredField(name);
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        public void validateIsSet(KorreksjonTilstandImpl instance) {
            try {
                Validate.notNull(declaredField.get(instance), name);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static NotNull notNull(String name) {
        return new NotNull(name);
    }


    private void validateAllNotNullFieldsAreSet() {
        for (NotNull notNull : notNulls) {
            notNull.validateIsSet(this);
        }
    }


    static class Builder implements KorreksjonTilstandBuilder {

        private boolean built = false;
        private final KorreksjonTilstandImpl newTilstand;
        private boolean modified;

        public Builder(AbstractKorreksjonsdata owner, KorreksjonTilstandImpl currentTilstand) {
            newTilstand = new KorreksjonTilstandImpl(currentTilstand);
            newTilstand.owner = owner;
        }

        Builder(AbstractKorreksjonsdata owner, KorreksjonType type, String bestillingsnr, Date bestillingsdato) {
            this(owner, null);
            newTilstand.korreksjonType = type;
            newTilstand.bestillingsnummer = bestillingsnr;
            newTilstand.bestillingsdato = bestillingsdato;
        }

        @Override
        public void commit() {
            validate();
            newTilstand.getOwner().addTilstand(newTilstand);
            built = true;
        }

        @Override
        public void validate() {
            validateNotBuilt();
            if (newTilstand.createdAt == null) {
//                SystemClock.now().asTimestamp(); //if not specified use the current time
            }
            newTilstand.validateAllNotNullFieldsAreSet();
            Validate.isTrue(newTilstand.getOwner().isNewTilstandAllowed(newTilstand), "Illegal new Tilstand");
        }

        private void validateNotBuilt() {
            if (built) {
                throw new IllegalStateException("KorreksjonTilstand instance already built");
            }
        }

        @Override
        public Builder withSource(Source source) {
            validateNotBuilt();
            newTilstand.source = source;
            modified = modified || !(equals(newTilstand.owner.getSource(), source));
            return this;
        }

        @Override
        public Builder withKorreksjonsType(KorreksjonType type) {
            validateNotBuilt();
            newTilstand.korreksjonType = type;
            modified = modified || !(equals(newTilstand.owner.getKorreksjonType(), type));
            return this;
        }

        @Override
        public Builder withStatus(Status status) {
            validateNotBuilt();
            newTilstand.status = status;
            modified = modified || !(equals(newTilstand.owner.getStatus(), status));
            return this;
        }

        @Override
        public Builder withBestillingsnummer(String bestillingsnr) {
            validateNotBuilt();
            newTilstand.bestillingsnummer = bestillingsnr;
            modified = modified || !(equals(newTilstand.owner.getBestillingsnummer(), bestillingsnr));
            return this;
        }

        @Override
        public Builder withBestillingsdato(Date bestillingsdato) {
            validateNotBuilt();
            modified = modified || !(equals(newTilstand.owner.getBestillingsdato(), bestillingsdato));
            newTilstand.bestillingsdato = bestillingsdato;
            return this;
        }

        @Override
        public Builder withCreatedByUser(String user) {
            validateNotBuilt();
            newTilstand.createdBy = user;
            modified = modified || !(equals(newTilstand.owner.getUser(), user));
            return this;
        }

        @Override
        public Builder withCreditAccount(String account) {
            validateNotBuilt();
            newTilstand.creditAccount = account;
            modified = modified || !(equals(newTilstand.owner.getCreditAccount(), account));
            return this;
        }

        @Override
        public Builder withDebetAccount(String account) {
            validateNotBuilt();
            newTilstand.debetAccount = account;
            modified = modified || !(equals(newTilstand.owner.getDebetAccount(), account));
            return this;
        }

        @Override
        public Builder withDebetNavn(String debetNavn) {
            validateNotBuilt();
            newTilstand.debetNavn = debetNavn;
            modified = modified || !(equals(newTilstand.owner.getDebetNavn(), debetNavn));
            return this;
        }

        @Override
        public Builder withAmount(BigDecimal amount) {
            validateNotBuilt();
            newTilstand.amount = amount;
            modified = modified || !(equals(newTilstand.owner.getAmount(), amount));
            return this;
        }

        @Override
        public Builder withBlankettnummer(String blankettnummer) {
            validateNotBuilt();
            newTilstand.blankettnummer = blankettnummer;
            modified = modified || !(equals(newTilstand.owner.getBlankettnummer(), blankettnummer));
            return this;
        }

        @Override
        public Builder withArkivref(String arkivref) {
            validateNotBuilt();
            newTilstand.arkivref = arkivref;
            modified = modified || !(equals(newTilstand.owner.getArkivref(), arkivref));
            return this;
        }

        @Override
        public Builder withOpprinneligDato(Date opprinneligDato) {
            validateNotBuilt();
            newTilstand.opprinneligDato = opprinneligDato;
            modified = modified || !(equals(newTilstand.owner.getOpprinneligDato(), opprinneligDato));
            return this;
        }

        @Override
        public Builder withCause(Cause cause) {
            validateNotBuilt();
            newTilstand.cause = cause;
            modified = modified || !(equals(newTilstand.owner.getCause(), cause));
            return this;
        }

        @Override
        public Builder withFritekst(String fritekst) {
            validateNotBuilt();
            newTilstand.fritekst = fritekst;
            modified = modified || !(equals(newTilstand.owner.getFritekst(), fritekst));
            return this;
        }

        @Override
        public Builder withGebyrbankRegnr(String gebyrbankRegnr) {
            validateNotBuilt();
            newTilstand.gebyrBankregnummer = gebyrbankRegnr;
            modified = modified || !(equals(newTilstand.owner.getGebyrBankregnummer(), gebyrbankRegnr));
            return this;
        }

        @Override
        public Builder withNibeProduktOgPriskode(String bus, String alc) {
            validateNotBuilt();
            newTilstand.nibeBusCode = bus;
            newTilstand.nibeAlcCode = alc;
            modified = modified || !(equals(newTilstand.owner.getNibeBusCode(), bus));
            modified = modified || !(equals(newTilstand.owner.getNibeAlcCode(), alc));
            return this;
        }

        @Override
        public Builder withKidNr(String kid) {
            validateNotBuilt();
            newTilstand.kidNummer = kid;
            modified = modified || !(equals(newTilstand.owner.getKidNummer(), kid));
            return this;
        }

        @Override
        public Builder withBuntNummer(String buntnummer) {
            validateNotBuilt();
            newTilstand.buntNummer = buntnummer;
            modified = modified || !(equals(newTilstand.owner.getBuntnummer(), buntnummer));
            return this;
        }

        @Override
        public Builder withTransTekst(String transTekst) {
            validateNotBuilt();
            newTilstand.transTekst = transTekst;
            modified = modified || !(equals(newTilstand.owner.getTransTekst(), transTekst));
            return this;
        }

        @Override
        public Builder withBelopKreditSumport(BigDecimal belopKreditSumpost) {
            validateNotBuilt();
            newTilstand.belopKreditSumpost = belopKreditSumpost;
            modified = modified || !(equals(newTilstand.owner.getBelopKreditSumpost(), belopKreditSumpost));
            return this;
        }

        @Override
        public Builder withBestillingStatusUpdatePath(String bestillingStatusUpdatePath) {
            validateNotBuilt();
            newTilstand.bestillingStatusUpdatePath = bestillingStatusUpdatePath;
            modified = modified || !(equals(newTilstand.owner.getBestillingStatusUpdatePath(), bestillingStatusUpdatePath));
            return this;
        }

        @Override
        public Builder withCreatedAt(Date createdAt) {
            if (createdAt == null) {
                return this;
            }

            validateNotBuilt();
            newTilstand.createdAt = new Timestamp(System.currentTimeMillis()/*createdAt.asDate().getTime()*/);
            modified = modified || !(equals(newTilstand.owner.getCreatedAt(), createdAt));
            return this;
        }

        @Override
        public boolean isModified() {
            return modified;
        }

        static boolean equals(Object a, Object b) {
            return (a == null) ? (b == null) : a.equals(b);
        }

    }


    /**
     * not public
     */
    Builder newTilstandBuilder() {
        return new Builder(this.getOwner(), this);
    }

    /**
     * not public
     */
    @Override
    public Builder newTilstandBuilder(String username) {
        Validate.notEmpty(username, "user must be specified and not blank");
        return new Builder(this.getOwner(), this).withCreatedByUser(username);
    }


    /**
     * Construct builder for the initial tilstand or for test data
     */
    static Builder initialTilstandBuilder(AbstractKorreksjonsdata owner, KorreksjonType type, String bestillingsnr, Date bestillingsdato) {
        return new Builder(owner, type, bestillingsnr, bestillingsdato);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof KorreksjonTilstandImpl)) {
            return false;
        }
        KorreksjonTilstandImpl other = (KorreksjonTilstandImpl) o;
        return Objects.equals(getOwner(), other.getOwner())
                && Objects.equals(getHistoryIndexNo(), other.getHistoryIndexNo());
    }

    @Override
    public int hashCode() {
        return (owner.hashCode() % (Integer.MAX_VALUE - 50)) + historyIndexNo;
    }

    @Override
    public String getNorwegianDate() {
        return "2020-02-20";//TimePoint.from(getTimestamp().getTime()).toString("dd.MM.yyyy", SystemClock.getTimeZone());
    }

    private Timestamp getTimestamp() {
        return getCreatedAt();
    }

    @Override
    public String getNorwegianTimeOfDay() {
        return "11:27:13";//TimePoint.from(getTimestamp().getTime()).toString("HH:mm:ss", SystemClock.getTimeZone());
    }

}
