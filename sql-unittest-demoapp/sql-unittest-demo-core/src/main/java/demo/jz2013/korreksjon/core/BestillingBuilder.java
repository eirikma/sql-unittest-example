package demo.jz2013.korreksjon.core;


import java.math.BigDecimal;
import java.sql.Date;

public final class BestillingBuilder implements Cloneable {

    private Long id = null;
    private Source source;
    private String bestillingsnummer;
    private Date bestillingsdato;
    private String user;
    private String debetAccount;
    private String creditAccount;
    private BigDecimal amount;
    private String arkivref;
    private String blankettnummer;
    private Date opprinneligDato;
    private Cause cause;
    private String fritekst;
    private String gebyrBankregnummer;
    private Status status;
    private String kidNummer;
    private String statusPath;
    private String nibeBusCode;
    private String nibeAlcCode;
    private String transTekst;
    private String buntNummer;
    private BigDecimal belopKreditSumpost;
    private String debetNavn;
    private KorreksjonType type;
    private Date createdAt;

    private BestillingBuilder() {
        //hide utility class constructor
    }

    /**
     * @deprecated - entityRepository will now assign IDs: Use this method only if strictly necessary in a test.
     */
    public BestillingBuilder withId(Long id) {
        BestillingBuilder copy = copy();
        copy.id = id;
        return copy;
    }

    public BestillingBuilder withSource(Source source) {
        BestillingBuilder copy = copy();
        copy.source = source;
        return copy;
    }

    public BestillingBuilder withBestillingsnummer(String bestillingsnummer) {
        BestillingBuilder copy = copy();
        copy.bestillingsnummer = bestillingsnummer;
        return copy;
    }

    public BestillingBuilder withBestillingsdato(Date bestillingsdato) {
        BestillingBuilder copy = copy();
        copy.bestillingsdato = bestillingsdato;
        return copy;
    }

    public BestillingBuilder withUser(String user) {
        BestillingBuilder copy = copy();
        copy.user = user;
        return copy;
    }

    public BestillingBuilder withDebetAccount(String debetAccount) {
        BestillingBuilder copy = copy();
        copy.debetAccount = debetAccount;
        return copy;
    }

    public BestillingBuilder withCreditAccount(String creditAccount) {
        BestillingBuilder copy = copy();
        copy.creditAccount = creditAccount;
        return copy;
    }

    public BestillingBuilder withAmount(BigDecimal amount) {
        BestillingBuilder copy = copy();
        copy.amount = amount;
        return copy;
    }

    public BestillingBuilder withArkivref(String arkivref) {
        BestillingBuilder copy = copy();
        copy.arkivref = arkivref;
        return copy;
    }

    public BestillingBuilder withBlankettnummer(String blankettnummer) {
        BestillingBuilder copy = copy();
        copy.blankettnummer = blankettnummer;
        return copy;
    }

    public BestillingBuilder withOpprinneligDato(Date opprinneligDato) {
        BestillingBuilder copy = copy();
        copy.opprinneligDato = opprinneligDato;
        return copy;
    }

    public BestillingBuilder withCause(Cause cause) {
        BestillingBuilder copy = copy();
        copy.cause = cause;
        return copy;
    }

    public BestillingBuilder withCauseType(Cause cause) {
        return withCause(cause);
    }

    public BestillingBuilder withFritekst(String fritekst) {
        BestillingBuilder copy = copy();
        copy.fritekst = fritekst;
        return copy;
    }

    public BestillingBuilder withGebyrBankregnummer(String gebyrBankregnummer) {
        BestillingBuilder copy = copy();
        copy.gebyrBankregnummer = gebyrBankregnummer;
        return copy;
    }

    public BestillingBuilder withStatus(Status status) {
        BestillingBuilder copy = copy();
        copy.status = status;
        return copy;
    }

    public BestillingBuilder withKidNumber(String kid) {
        BestillingBuilder copy = copy();
        copy.kidNummer = kid;
        return copy;
    }

    public BestillingBuilder withStatusUpdatePath(String path) {
        BestillingBuilder copy = copy();
        copy.statusPath = path;
        return copy;
    }

    public BestillingBuilder withTransTekst(String tekst) {
        BestillingBuilder copy = copy();
        copy.transTekst = tekst;
        return copy;
    }

    public BestillingBuilder withNibeBusOgAlcKode(String bus, String alc) {
        BestillingBuilder copy = copy();
        copy.nibeBusCode = bus;
        copy.nibeAlcCode = alc;
        return copy;
    }

    public BestillingBuilder withBuntNummer(String buntnummer) {
        BestillingBuilder copy = copy();
        copy.buntNummer = buntnummer;
        return copy;
    }

    public BestillingBuilder withBelopKreditSumpost(BigDecimal belop) {
        BestillingBuilder copy = copy();
        copy.belopKreditSumpost = belop;
        return copy;
    }

    public BestillingBuilder withDebetNavn(String debetnavn) {
        BestillingBuilder copy = copy();
        copy.debetNavn = debetnavn;
        return copy;
    }


    public BestillingBuilder withType(KorreksjonType type) {
        BestillingBuilder copy = copy();
        copy.type = type;
        return copy;
    }

    public BestillingBuilder withCreatedAt(Date createdAt) {
        if (createdAt == null) {
            return this;
        }
        BestillingBuilder copy = copy();
        copy.createdAt = createdAt;
        return copy;
    }


    protected BestillingBuilder copy() {
        try {
            return (BestillingBuilder) clone();
        } catch (CloneNotSupportedException e) {
            // never happens as long as it implments cloneable
            throw new IllegalStateException(e);
        }
    }

    public static BestillingBuilder emptyBestilling() {
        return new BestillingBuilder().withType(KorreksjonType.BESTILLING);
    }

    public static BestillingBuilder validBestilling() {
        return emptyBestilling().withSource(Source.BESTILLINGSDB)
                .withUser(System.getProperty("user.name"))
                .withBestillingsnummer("001011")
                .withBuntNummer("613806462")
                .withStatus(Status.KLAR)
                .withDebetAccount("70010123451")
                .withCreditAccount("60010123456")
                .withAmount(BigDecimal.ONE)
                .withArkivref("arkivref")
                .withBlankettnummer("0123456789")
                .withOpprinneligDato(new Date(System.currentTimeMillis() - (24L * 3500L * 1000L)))
                .withBestillingsdato(new Date(System.currentTimeMillis()))
                .withKidNumber("00123456")
                .withNibeBusOgAlcKode("500", "006")
                .withCause(Cause.KREDIT);
    }

    public Bestilling build() {
        Bestilling bestilling = new Bestilling(bestillingsnummer, bestillingsdato, user, debetAccount, creditAccount, amount, arkivref, blankettnummer,
                opprinneligDato, cause, fritekst, gebyrBankregnummer, status, kidNummer, statusPath, nibeBusCode, nibeAlcCode, transTekst,
                buntNummer, belopKreditSumpost, debetNavn, createdAt);

        // NB NB dette er HELT FEIL OG SKAL BARE GJOERES MIDLERTIDIG I NOEN FAA TESTER
        if (id != null) {
//            bestilling.setId(EntityId.createEntityId(id, Bestilling.class));
        }
        return bestilling;
    }
}
