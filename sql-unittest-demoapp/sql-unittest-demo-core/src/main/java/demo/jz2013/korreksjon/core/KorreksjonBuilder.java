package demo.jz2013.korreksjon.core;


import java.math.BigDecimal;
import java.sql.Date;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public final class KorreksjonBuilder implements Cloneable {

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

    private KorreksjonBuilder() {
        //hide utility class constructor
    }

    /**
     * @deprecated - entityRepository will now assign IDs: Use this method only if strictly necessary in a test.
     */
    public KorreksjonBuilder withId(Long id) {
        KorreksjonBuilder copy = copy();
        copy.id = id;
        return copy;
    }

    public KorreksjonBuilder withSource(Source source) {
        KorreksjonBuilder copy = copy();
        copy.source = source;
        return copy;
    }

    public KorreksjonBuilder withBestillingsnummer(String bestillingsnummer) {
        KorreksjonBuilder copy = copy();
        copy.bestillingsnummer = bestillingsnummer;
        return copy;
    }

    public KorreksjonBuilder withBestillingsdato(Date bestillingsdato) {
        KorreksjonBuilder copy = copy();
        copy.bestillingsdato = bestillingsdato;
        return copy;
    }

    public KorreksjonBuilder withCreatedAt(Date createdAt) {
        KorreksjonBuilder copy = copy();
        copy.createdAt = createdAt;
        return copy;
    }

    public KorreksjonBuilder withUser(String user) {
        KorreksjonBuilder copy = copy();
        copy.user = user;
        return copy;
    }

    public KorreksjonBuilder withDebetAccount(String debetAccount) {
        KorreksjonBuilder copy = copy();
        copy.debetAccount = debetAccount;
        return copy;
    }

    public KorreksjonBuilder withCreditAccount(String creditAccount) {
        KorreksjonBuilder copy = copy();
        copy.creditAccount = creditAccount;
        return copy;
    }

    public KorreksjonBuilder withAmount(BigDecimal amount) {
        KorreksjonBuilder copy = copy();
        copy.amount = amount;
        return copy;
    }

    public KorreksjonBuilder withArkivref(String arkivref) {
        KorreksjonBuilder copy = copy();
        copy.arkivref = arkivref;
        return copy;
    }

    public KorreksjonBuilder withBlankettnummer(String blankettnummer) {
        KorreksjonBuilder copy = copy();
        copy.blankettnummer = blankettnummer;
        return copy;
    }

    public KorreksjonBuilder withOpprinneligDato(Date opprinneligDato) {
        KorreksjonBuilder copy = copy();
        copy.opprinneligDato = opprinneligDato;
        return copy;
    }

    public KorreksjonBuilder withCause(Cause cause) {
        KorreksjonBuilder copy = copy();
        copy.cause = cause;
        return copy;
    }

    public KorreksjonBuilder withCauseType(Cause cause) {
        return withCause(cause);
    }

    public KorreksjonBuilder withFritekst(String fritekst) {
        KorreksjonBuilder copy = copy();
        copy.fritekst = fritekst;
        return copy;
    }

    public KorreksjonBuilder withGebyrBankregnummer(String gebyrBankregnummer) {
        KorreksjonBuilder copy = copy();
        copy.gebyrBankregnummer = gebyrBankregnummer;
        return copy;
    }

    public KorreksjonBuilder withStatus(Status status) {
        KorreksjonBuilder copy = copy();
        copy.status = status;
        return copy;
    }

    public KorreksjonBuilder withKidNumber(String kid) {
        KorreksjonBuilder copy = copy();
        copy.kidNummer = kid;
        return copy;
    }

    public KorreksjonBuilder withStatusUpdatePath(String path) {
        KorreksjonBuilder copy = copy();
        copy.statusPath = path;
        return copy;
    }

    public KorreksjonBuilder withTransTekst(String tekst) {
        KorreksjonBuilder copy = copy();
        copy.transTekst = tekst;
        return copy;
    }

    public KorreksjonBuilder withNibeBusOgAlcKode(String bus, String alc) {
        KorreksjonBuilder copy = copy();
        copy.nibeBusCode = bus;
        copy.nibeAlcCode = alc;
        return copy;
    }

    public KorreksjonBuilder withBuntNummer(String buntnummer) {
        KorreksjonBuilder copy = copy();
        copy.buntNummer = buntnummer;
        return copy;
    }

    public KorreksjonBuilder withBelopKreditSumpost(BigDecimal belop) {
        KorreksjonBuilder copy = copy();
        copy.belopKreditSumpost = belop;
        return copy;
    }

    public KorreksjonBuilder withDebetNavn(String debetnavn) {
        KorreksjonBuilder copy = copy();
        copy.debetNavn = debetnavn;
        return copy;
    }


    public KorreksjonBuilder withType(KorreksjonType type) {
        KorreksjonBuilder copy = copy();
        copy.type = type;
        return copy;
    }

    protected KorreksjonBuilder copy() {
        try {
            return (KorreksjonBuilder) clone();
        } catch (CloneNotSupportedException e) {
            // never happens as long as it implments cloneable
            throw new IllegalStateException(e);
        }
    }


    public static KorreksjonBuilder emptyKorreksjon() {
        return new KorreksjonBuilder();
    }

    public static KorreksjonBuilder validKorreksjon() {
        return emptyKorreksjon().withType(KorreksjonType.KORREKSJON)
                .withSource(Source.KORREKSJON_WEB)
                .withUser(System.getProperty("user.name"))
                .withBuntNummer("613838462")
                .withBestillingsnummer("001011")
                .withStatus(Status.KLAR)
                .withDebetAccount("70010123451")
                .withCreditAccount("60010123456")
                .withAmount(BigDecimal.ONE)
                .withBelopKreditSumpost(BigDecimal.TEN)
                .withArkivref("arkivref")
                .withBlankettnummer("0123456789")
                .withOpprinneligDato(new Date(System.currentTimeMillis() - (24L * 60L * 60L * 1000L)))
                .withBestillingsdato(new Date(System.currentTimeMillis()))
                .withCause(Cause.OVERFORING)
                .withNibeBusOgAlcKode("500", "006");
    }


    public static Korreksjon createOverforing(String bestillingsnummer, BigDecimal amount, String debetAccount, String creditAccount,
                                              String blankettnummer, Date opprinneligDato, String fritekst, String gebyrBankregnummer,
                                              String debetnavn, String user) {
        KorreksjonBuilder builder = KorreksjonBuilder.emptyKorreksjon()
                .withType(KorreksjonType.KORREKSJON)
                .withSource(Source.KORREKSJON_WEB)
                .withBestillingsnummer(bestillingsnummer)
                .withBestillingsdato(new Date(System.currentTimeMillis()))
                .withAmount(amount)
                .withUser(user)
                .withDebetAccount(debetAccount)
                .withCreditAccount(creditAccount)
                .withBlankettnummer(blankettnummer)
                .withOpprinneligDato(opprinneligDato)
                .withArkivref("*99" + bestillingsnummer)
                .withCause(Cause.OVERFORING)
                .withFritekst(fritekst)
                .withTransTekst(fritekst)
                .withStatus(Status.KLAR_TIL_VERIFISERING);
        if (isNotBlank(gebyrBankregnummer)) {
            builder = builder.withGebyrBankregnummer(gebyrBankregnummer);
        }
        builder = builder.withDebetNavn(debetnavn);
        return builder.build();
    }

    public Korreksjon build() {
        Korreksjon korreksjon = new Korreksjon(type, source, bestillingsnummer, bestillingsdato, user, debetAccount, creditAccount, amount, arkivref, blankettnummer,
                opprinneligDato, cause, fritekst, gebyrBankregnummer, status, kidNummer, statusPath, nibeBusCode, nibeAlcCode, transTekst,
                buntNummer, belopKreditSumpost, debetNavn, createdAt);

        korreksjon.verifyKorreksjonIsValid();

        // NB NB dette er HELT FEIL OG SKAL BARE GJOERES MIDLERTIDIG I NOEN FAA TESTER
        if (id != null) {
//            korreksjon.setId(EntityId.createEntityId(id, Korreksjon.class));
        }
        return korreksjon;
    }


    /**
     * Inner class with test data builders, only to make it visible that these
     * methods are not for production code.
     */
    public static class TestData {
        private TestData() {
            //hide utility class constructor
        }

        public static Korreksjon korrigertKorreksjon() {
            Korreksjon korreksjon = validKorreksjon().withStatus(Status.KLAR_TIL_VERIFISERING).build();
            // er dette tilstrekkelig?
            korreksjon.newTilstandBuilder(System.getProperty("user.name"))
                    .withStatus(Status.KORRIGERT)
                    .withGebyrbankRegnr("7001")
                    .commit();
            return korreksjon;
        }
    }
}
