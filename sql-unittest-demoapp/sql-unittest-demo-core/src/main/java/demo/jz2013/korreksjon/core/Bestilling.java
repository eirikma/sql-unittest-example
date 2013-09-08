package demo.jz2013.korreksjon.core;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class Bestilling extends AbstractKorreksjonsdata {

    private static final Logger LOG = LoggerFactory.getLogger(Bestilling.class);

    private Korreksjon korreksjon;

    private Korreksjon korreksjonMedFullmakt;

    private Korreksjon reversering;

    private Korreksjon reverseringMedFullmakt;


    protected Bestilling() {
    }

    public Bestilling(String bestillingsnummer, Date bestillingsdato, String user, String debetAccount, String creditAccount, BigDecimal amount, String arkivref, String blankettnummer, Date opprinneligDato, Cause cause, String fritekst, String gebyrBankregnummer, Status status, String kidNummer, String statusPath, String nibeBusCode, String nibeAlcCode, String transTekst, String buntNummer, BigDecimal belopKreditSumpost, String debetNavn, Date createdAt) {
        // ikke nodvendig: disse feltene blir overskrevet ved KorreksjonsTilstand.Builder.commit(), som kaller addTilstand()
        this.korreksjonType = KorreksjonType.BESTILLING;
        this.source = Source.BESTILLINGSDB;
        this.bestillingsnummer = bestillingsnummer;
        this.bestillingsdato = bestillingsdato;
        KorreksjonTilstandImpl.Builder builder = KorreksjonTilstandImpl
                .initialTilstandBuilder(this,
                        KorreksjonType.BESTILLING,
                        bestillingsnummer,
                        bestillingsdato);
        builder.withCreatedByUser(user)
                .withKorreksjonsType(KorreksjonType.BESTILLING)
                .withSource(Source.BESTILLINGSDB)
                .withDebetAccount(debetAccount)
                .withCreditAccount(creditAccount)
                .withAmount(amount)
                .withArkivref(arkivref)
                .withBlankettnummer(blankettnummer)
                .withOpprinneligDato(opprinneligDato)
                .withCause(cause)
                .withFritekst(fritekst)
                .withGebyrbankRegnr(gebyrBankregnummer)
                .withStatus(status)
                .withKidNr(kidNummer)
                .withBestillingStatusUpdatePath(statusPath)
                .withNibeProduktOgPriskode(nibeBusCode, nibeAlcCode)
                .withTransTekst(transTekst)
                .withBuntNummer(buntNummer)
                .withBelopKreditSumport(belopKreditSumpost)
                .withDebetNavn(debetNavn)
                .withCreatedAt(createdAt)
                .commit();
    }

    public Korreksjon getKorreksjon() {
        return korreksjon;
    }

    public Korreksjon getKorreksjonMedFullmakt() {
        return korreksjonMedFullmakt;
    }

    public Korreksjon getReversering() {
        return reversering;
    }

    public Korreksjon getReverseringMedFullmakt() {
        return reverseringMedFullmakt;
    }

    public void setKorreksjon(Korreksjon korreksjon) {
        if (this.korreksjon != null && korreksjon != null && this.korreksjon != korreksjon) {
            throw new IllegalArgumentException("cannot set a new Overforing for this bestilling");
        }
        this.korreksjon = korreksjon;
        if (korreksjon != null) {
            korreksjon.setBestilling(this);
        }
    }

    public void setKorreksjonMedFullmakt(Korreksjon overforing) {
        if (this.korreksjonMedFullmakt != null && overforing != null && this.korreksjonMedFullmakt != overforing) {
            throw new IllegalArgumentException("cannot set a new Overforing for this bestilling");
        }
        this.korreksjonMedFullmakt = overforing;
        if (overforing != null) {
            overforing.setBestilling(this);
        }
    }


    public void setReversering(Korreksjon reversering) {
        if (this.reversering != null && reversering != null && this.reversering != reversering) {
            throw new IllegalArgumentException("cannot set a new Reversering for this bestilling");
        }
        this.reversering = reversering;
        if (reversering != null) {
            reversering.setBestilling(this);
        }
    }

    public void setReverseringMedFullmakt(Korreksjon reverseringMedFullmakt) {
        if (this.reverseringMedFullmakt != null && reverseringMedFullmakt != null && this.reverseringMedFullmakt != reverseringMedFullmakt) {
            throw new IllegalArgumentException("cannot set a new Reversering med fullmakt for this bestilling");
        }
        this.reverseringMedFullmakt = reverseringMedFullmakt;
        if (reverseringMedFullmakt != null) {
            reverseringMedFullmakt.setBestilling(this);
        }

    }

    @Override
    public boolean isEligibleForDeletion() {
        return getStatus().equals(Status.KLAR);
    }

    @Override
    protected boolean isTheSameClassAsThis(Object otherObject) {
        return otherObject instanceof Bestilling;
    }

    /**
     * Lager en builder for å opprette reverseringen til denne bestillingen,
     * med alle felter kopiert over UNNTATT BESTILLINGEN.
     *
     * @param user
     * @return
     */
    public KorreksjonBuilder createReverseringBuilder(String user) {
        Validate.isTrue(isNotBlank(user), "user must be provided");
        Validate.isTrue(this.reversering == null, "reversering finnes allrede");
        return createCloneAsKorreksjonBuilder(user)
                .withType(KorreksjonType.REVERSERING);

    }

    /**
     * Lager en builder for å opprette reverseringen til denne bestillingen,
     * med alle felter kopiert over UNNTATT BESTILLINGEN.
     *
     * @param user
     * @return
     */
    public KorreksjonBuilder createReverseringMedFullmaktBuilder(String user) {
        Validate.isTrue(isNotBlank(user), "user must be provided");
        Validate.isTrue(this.reverseringMedFullmakt == null, "reversering med fullmakt finnes allerede");
        return createCloneAsKorreksjonBuilder(user)
                .withType(KorreksjonType.REVERSERING_MED_FULLMAKT);

    }

    public Korreksjon createNewReversering(String user, String betaler) {
        return createReverseringBuilder(user).withDebetNavn(betaler).build();
    }

    public KorreksjonBuilder createKorreksjonBuilder(String user) {
        Validate.notEmpty(user, "user must be provided");
        Validate.isTrue(this.korreksjon == null, "korreksjon finnes allerede");
        return createCloneAsKorreksjonBuilder(user)
                .withType(KorreksjonType.KORREKSJON);
    }

    public KorreksjonBuilder createKorreksjonMedFullmaktBuilder(String user) {
        Validate.notEmpty(user, "user must be provided");
        Validate.isTrue(this.korreksjonMedFullmakt == null, "korreksjon med fullmakt finnes allerede");
        return createCloneAsKorreksjonBuilder(user)
                .withType(KorreksjonType.KORREKSJON_MED_FULLMAKT);
    }

    @Deprecated
    //Use the builder instead!
    public Korreksjon createNewOverforing(String user, String debetnavn,
                                          Status status, String creditAccount) {
        return createKorreksjonBuilder(user).withDebetNavn(debetnavn)
                .withStatus(status)
                .withCreditAccount(creditAccount)
                .build();
    }

    private KorreksjonBuilder createCloneAsKorreksjonBuilder(String user) {
        return KorreksjonBuilder.emptyKorreksjon()
                .withUser(user)
                .withSource(getSource())
                .withAmount(getAmount())
                .withDebetAccount(getDebetAccount())
                .withCreditAccount(getCreditAccount())
                .withArkivref(getArkivref())
                .withBlankettnummer(getBlankettnummer())
                .withBestillingsdato(getBestillingsdato())
                .withOpprinneligDato(
                        getOpprinneligDatoAsDate() == null ? null : getOpprinneligDatoAsDate())
                .withCause(getCause())
                .withFritekst(getFritekst())
                .withGebyrBankregnummer(getGebyrBankregnummer())
                .withStatus(getStatus())
                .withKidNumber(getKidNummer())
                .withNibeBusOgAlcKode(getNibeBusCode(), getNibeAlcCode())
                .withTransTekst(getTransTekst())
                .withBuntNummer(getBuntnummer())
                .withBelopKreditSumpost(getBelopKreditSumpost())
                .withDebetNavn(getDebetNavn())
                        // spiller denne noen rolle for korreksjoner egentlig?
                .withStatusUpdatePath(getBestillingStatusUpdatePath())
                .withBestillingsnummer(getBestillingsnummer())
                ;
    }

//    public Korreksjon createDebetMedFullmaktKorreksjon(EntityRepository entityRepository, String user, String nyAccount, String debetnavn) {
//        Validate.notEmpty(nyAccount);
//        Validate.isTrue(Cause.DEBET_MED_FULLMAKT.equals(getCause()), "Feil årsak, skulle ha vært DEBET, er" + getCause().getPresentationString());
//        final Korreksjon nyKorreksjon = createDebetKorreksjonBuilder(user, nyAccount, debetnavn)
//                .withCreditAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO)
//                .build();
//        entityRepository.store(nyKorreksjon);
//
//        setKorreksjon(nyKorreksjon);
//        entityRepository.store(this);
//
//        LOG.debug("Korreksjon lagret i korreksjonsregisteret [bestillingsnummer=" + getBestillingsnummer() + " id=" + this.korreksjon.getIdAsLong() + "]");
//
//        return nyKorreksjon;
//    }
//
//
//    public Korreksjon createDebetUtenFullmaktKorreksjon(EntityRepository entityRepository, String user, String nyAccount, String debetnavn) {
//        Validate.notEmpty(nyAccount);
//        Validate.isTrue(Cause.DEBET.equals(getCause()), "Feil årsak, skulle ha vært DEBET, er" + getCause().getPresentationString());
//        final Korreksjon nyKorreksjon = createDebetKorreksjonBuilder(user, nyAccount, debetnavn).build();
//        entityRepository.store(nyKorreksjon);
//
//        setKorreksjon(nyKorreksjon);
//        entityRepository.store(this);
//
//        LOG.debug("Korreksjon lagret i korreksjonsregisteret [bestillingsnummer=" + getBestillingsnummer() + " id=" + nyKorreksjon.getIdAsLong() + "]");
//
//        return nyKorreksjon;
//    }
//
//
//    public Korreksjon createDebetMedFullmaktReversering(EntityRepository entityRepository, String user, String debetAccount, String debetnavn, String gebyrBankregnummer) {
//        Validate.notEmpty(debetAccount);
//
//        final Korreksjon nyReversering = createReverseringBuilder(user)
//                .withDebetNavn(debetnavn)
//                .withCause(Cause.DEBET_MED_FULLMAKT)
//                .withDebetAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO)
//                .withCreditAccount(debetAccount)
//                .withStatus(Status.KORRIGERT)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(user)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(Cause.DEBET_MED_FULLMAKT)
//                .commit();
//
//        LOG.debug("Reversering av debet med fullmakt lagret i korreksjonsregisteret [blankettnummer=" + nyReversering.getBlankettnummer() + "]");
//
//        return nyReversering;
//    }
//
//
//    public Korreksjon createDebetUtenFullmaktReversering(EntityRepository entityRepository, String user, String debetnavn, String creditAccount, String debetAccount, String gebyrBankregnummer) {
//        Validate.notEmpty(debetAccount);
//        Validate.notEmpty(creditAccount);
//        final Korreksjon nyReversering = createReverseringBuilder(user)
//                .withDebetNavn(debetnavn)
//                .withCause(Cause.DEBET)
//                .withDebetAccount(creditAccount)
//                .withCreditAccount(debetAccount)
//                .withStatus(Status.KORRIGERT)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(user)
//                .withCause(Cause.DEBET)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .commit();
//
//        LOG.debug("Reversering av debet lagret i korreksjonsregisteret [blankettnummer=" + nyReversering.getBlankettnummer() + "]");
//
//        return nyReversering;
//    }
//
//
//    KorreksjonBuilder createDebetKorreksjonBuilder(String user, String debetAccountNumber, String betalernavn) {
//        Validate.notNull(getReversering(), "Kan ikke lage en Debet med fullmakt korreksjon uten at reverseringen finnes.");
//        Validate.notEmpty(debetAccountNumber);
//        Validate.isTrue(Status.UNDER_BEHANDLING.equals(this.getStatus()), "Bestillingen må ha status " + Status.UNDER_BEHANDLING.getPresentationString());
//
//        return this.createKorreksjonBuilder(user)
//                .withStatus(Status.KLAR_TIL_VERIFISERING)
//                .withDebetAccount(debetAccountNumber)
//                .withDebetNavn(betalernavn);
//    }
//
//
//    public Korreksjon createDobbelRegistreringUtenFullmaktReversering(EntityRepository entityRepository, final String user, final String debetNavn, final String debetAccount, final String gebyrBankregnummer) {
//        final Korreksjon nyReversering = createReverseringBuilder(user)
//                .withDebetNavn(debetNavn)
//                .withCause(Cause.DOBBELREGISTRERING)
//                .withStatus(Status.KORRIGERT)
//                .withDebetAccount(getCreditAccount())
//                .withCreditAccount(debetAccount)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(user)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(Cause.DOBBELREGISTRERING)
//                .commit();
//
//        LOG.debug("Reversering av dobbelregistrering lagret i korreksjonsregisteret [blankettnummer=" + getBlankettnummer() + "]");
//
//        return nyReversering;
//    }
//
//
//    public Korreksjon createDobbelRegistreringMedFullmaktReversering(EntityRepository entityRepository, String user, String debetnavn, final String debetAccount, String gebyrBankregnummer) {
//        final Korreksjon nyReversering = createReverseringBuilder(user)
//                .withDebetNavn(debetnavn)
//                .withCause(Cause.DOBBELREGISTRERING_MED_FULLMAKT)
//                .withStatus(Status.KORRIGERT)
//                .withDebetAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO)
//                .withCreditAccount(debetAccount)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(user)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(Cause.DOBBELREGISTRERING_MED_FULLMAKT)
//                .commit();
//
//        LOG.debug("Reversering av dobbelregistrering lagret i korreksjonsregisteret [blankettnummer=" + getBlankettnummer() + "]");
//
//        return nyReversering;
//    }
//
//    public Korreksjon createDobbelRegistreringMedFullmaktSomSkalGodkjennesReversering(EntityRepository entityRepository, String user, String debetnavn, String gebyrBankregnummer) {
//        final Korreksjon nyReverseringMedFullmakt = createReverseringMedFullmaktBuilder(user)
//                .withDebetNavn(debetnavn)
//                .withCause(Cause.DOBBELREGISTRERING_MED_FULLMAKT)
//                .withStatus(Status.VENTER_FULLMAKT)
//                .withDebetAccount(getCreditAccount())
//                .withCreditAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO)
//                .withType(KorreksjonType.REVERSERING_MED_FULLMAKT)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//
//        setReverseringMedFullmakt(nyReverseringMedFullmakt);
//        entityRepository.store(nyReverseringMedFullmakt);
//
//        LOG.debug("Reversering med fullmakt av dobbelregistrering lagret i korreksjonsregisteret [blankettnummer=" + getBlankettnummer() + "]");
//
//        return nyReverseringMedFullmakt;
//    }
//
//
//    public Korreksjon createKreditMedFullmaktKorreksjon(EntityRepository entityRepository, String user, String betalernavn, String nyAccount) {
//        Validate.notEmpty(nyAccount);
//
//        final Korreksjon nyKorreksjon = createKorreksjonBuilder(user)
//                .withDebetNavn(betalernavn)
//                .withStatus(Status.KLAR_TIL_VERIFISERING)
//                .withCreditAccount(nyAccount)
//                .withDebetAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO)
//                .withCauseType(Cause.KREDIT_MED_FULLMAKT)
//                .build();
//        entityRepository.store(nyKorreksjon);
//
//        setKorreksjon(nyKorreksjon);
//        entityRepository.store(this);
//
//        LOG.debug("Korreksjon lagret i korreksjonsregisteret [bestillingsnummer=" + getBestillingsnummer() + " id=" + nyKorreksjon.getIdAsLong() + "]");
//
//        return nyKorreksjon;
//    }
//
//
//    public Korreksjon createKreditUtenFullmaktKorreksjon(EntityRepository entityRepository, String user, String betalernavn, String debetAccount, String nyAccount) {
//        Validate.notEmpty(debetAccount);
//        Validate.notEmpty(nyAccount);
//        final Korreksjon nyKorreksjon = createKorreksjonBuilder(user)
//                .withDebetNavn(betalernavn)
//                .withDebetAccount(debetAccount)
//                .withCreditAccount(nyAccount)
//                .withCauseType(Cause.KREDIT)
//                .withStatus(Status.KLAR_TIL_VERIFISERING)
//                .build();
//        entityRepository.store(nyKorreksjon);
//
//        setKorreksjon(nyKorreksjon);
//        entityRepository.store(this);
//
//        LOG.debug("Korreksjon lagret i korreksjonsregisteret [bestillingsnummer=" + getBestillingsnummer() + " id=" + nyKorreksjon.getIdAsLong() + "]");
//
//        return nyKorreksjon;
//    }
//
//
//    public Korreksjon createKreditUtenFullmaktReversering(EntityRepository entityRepository, String user, String debetAccount, String debetnavn, String gebyrBankregnummer) {
//        Validate.notEmpty(debetAccount);
//        final Korreksjon nyReversering = createReverseringBuilder(user)
//                .withDebetNavn(debetnavn)
//                .withCause(Cause.KREDIT)
//                .withStatus(Status.KORRIGERT)
//                .withDebetAccount(getCreditAccount())
//                .withCreditAccount(debetAccount)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(user)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(Cause.KREDIT)
//                .commit();
//
//        LOG.debug("Reversering av kredit lagret i korreksjonsregisteret [blankettnummer=" + nyReversering.getBlankettnummer() + "]");
//
//        return nyReversering;
//    }
//
//    public Korreksjon createKreditMedFullmaktReversering(EntityRepository entityRepository, String user, String creditAccount, String debetnavn, String gebyrBankregnummer) {
//        final Korreksjon nyReversering = createReverseringBuilder(user)
//                .withDebetNavn(debetnavn)
//                .withCause(Cause.KREDIT_MED_FULLMAKT)
//                .withDebetAccount(creditAccount)
//                .withCreditAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO)
//                .withStatus(Status.VENTER_FULLMAKT)
//                .withGebyrBankregnummer(gebyrBankregnummer)
//                .build();
//
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(user)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(Cause.KREDIT_MED_FULLMAKT)
//                .commit();
//
//        entityRepository.store(this);
//
//        LOG.debug("Reversering av kredit med fullmakt lagret i korreksjonsregisteret [blankettnummer=" + nyReversering.getBlankettnummer() + "]");
//
//        return nyReversering;
//    }
//
//    public Korreksjon createFeilBankKorreksjon(EntityRepository entityRepository, String user, String buntnummer, String creditAccount, String debetAccount, String gebyrBankregnummer) {
//        Validate.notEmpty(debetAccount, "debetAccount må være angitt");
//        Validate.notEmpty(creditAccount, "creditAccount må være angitt");
//        Validate.notEmpty(buntnummer, "buntnummer må være angitt");
//        final Korreksjon nyKorreksjon = createKorreksjonBuilder(user).
//                withBuntNummer(buntnummer).
//                withCreditAccount(creditAccount).
//                withDebetAccount(debetAccount).
//                withCause(Cause.FEIL_BANK).
//                withGebyrBankregnummer(gebyrBankregnummer).
//                withStatus(Status.KLAR_TIL_VERIFISERING).
//                withNibeBusOgAlcKode("508", "023"). //Bruker alltid standard BUS/ALC-koder for "FEIL_BANK"
//                build();
//
//        setKorreksjon(nyKorreksjon);
//        entityRepository.store(nyKorreksjon);
//
//        newTilstandBuilder(user)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(Cause.FEIL_BANK)
//                .commit();
//
//        entityRepository.store(this);
//
//        LOG.debug("Korreksjon av feil bank lagret i korreksjonsregisteret [blankettnummer=" + nyKorreksjon.getBlankettnummer() + "]");
//
//        return nyKorreksjon;
//    }
//
//    public Korreksjon createFeilBelopReversering(EntityRepository entityRepository, final String username, final String debetAccount, final String debetnavn, final String gebyrBankregnummer) {
//        return createFeilBelopReversering(entityRepository, username, debetnavn, gebyrBankregnummer, Cause.BELOEP, getCreditAccount(), debetAccount);
//    }
//
//    public Korreksjon createFeilBelopMedFullmaktReversering(EntityRepository entityRepository, final String username, final String debetAccount, final String debetnavn, final String gebyrBankregnummer) {
//        return createFeilBelopReversering(entityRepository, username, debetnavn, gebyrBankregnummer, Cause.BELOEP_MED_FULLMAKT, KorreksjonConstants.NETS_FULLMAKTSKONTO, debetAccount);
//    }
//
//    private Korreksjon createFeilBelopReversering(final EntityRepository entityRepository, final String username, final String debetnavn, final String gebyrBankregnummer, final Cause cause, final String debetAccount, final String creditAccount) {
//        final Korreksjon nyReversering = createReverseringBuilder(username).
//                withStatus(Status.KORRIGERT).
//                withDebetAccount(debetAccount).
//                withCreditAccount(creditAccount).
//                withCause(cause).
//                withDebetNavn(debetnavn).
//                withGebyrBankregnummer(gebyrBankregnummer).
//                build();
//
//        setReversering(nyReversering);
//        entityRepository.store(nyReversering);
//
//        newTilstandBuilder(username)
//                .withStatus(Status.UNDER_BEHANDLING)
//                .withCause(cause)
//                .commit();
//        entityRepository.store(this);
//
//        return nyReversering;
//    }
//
//    public Korreksjon createFeilBelopMedFullmaktReverseringMedFullmakt(final EntityRepository entityRepository, final String username, final String debetnavn, final String gebyrBankregnummer) {
//        final Korreksjon nyReversering = createReverseringMedFullmaktBuilder(username).
//                withStatus(Status.VENTER_FULLMAKT).
//                withDebetAccount(getCreditAccount()).
//                withCreditAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO).
//                withCause(Cause.BELOEP_MED_FULLMAKT).
//                withDebetNavn(debetnavn).
//                withGebyrBankregnummer(gebyrBankregnummer).
//                build();
//
//        setReverseringMedFullmakt(nyReversering);
//        entityRepository.store(nyReversering);
//
//        entityRepository.store(this);
//
//        return nyReversering;
//    }
//
//    public Korreksjon createFeilBelopKorreksjon(final EntityRepository entityRepository, final String username, final String debetAccount, final String debetnavn, final String gebyrBankregnummer, final BigDecimal amount) {
//        return createFeilBelopKorreksjon(entityRepository, username, debetnavn, gebyrBankregnummer, amount, Cause.BELOEP, debetAccount, getCreditAccount());
//    }
//
//    public Korreksjon createFeilBelopMedFullmaktKorreksjon(final EntityRepository entityRepository, final String username, final String debetAccount, final String debetnavn, final String gebyrBankregnummer, final BigDecimal amount) {
//        return createFeilBelopKorreksjon(entityRepository, username, debetnavn, gebyrBankregnummer, amount, Cause.BELOEP_MED_FULLMAKT, debetAccount, KorreksjonConstants.NETS_FULLMAKTSKONTO);
//    }
//
//    private Korreksjon createFeilBelopKorreksjon(final EntityRepository entityRepository, final String username, final String debetnavn, final String gebyrBankregnummer, final BigDecimal amount, final Cause cause, final String debetAccount, final String creditAccount) {
//        final Korreksjon nyKorreksjon = createKorreksjonBuilder(username).
//                withDebetNavn(debetnavn).
//                withGebyrBankregnummer(gebyrBankregnummer).
//                withStatus(Status.KLAR_TIL_VERIFISERING).
//                withAmount(amount).
//                withDebetAccount(debetAccount).
//                withCreditAccount(creditAccount).
//                withCause(cause).
//                build();
//
//        setKorreksjon(nyKorreksjon);
//        entityRepository.store(nyKorreksjon);
//
//        entityRepository.store(this);
//
//        return nyKorreksjon;
//    }
//
//    public Korreksjon createFeilBelopMedFullmaktKorreksjonMedFullmakt(final EntityRepository entityRepository, final String username, final String creditAccount) {
//        final Korreksjon nyKorreksjon = createKorreksjonMedFullmaktBuilder(username).
//                withStatus(Status.KORRIGERT).
//                withDebetAccount(KorreksjonConstants.NETS_FULLMAKTSKONTO).
//                withCreditAccount(creditAccount).
//                withCause(Cause.BELOEP_MED_FULLMAKT).
//                withAmount(getKorreksjon().getAmount()).
//                withDebetNavn(getReverseringMedFullmakt().getDebetNavn()).
//                build();
//        setKorreksjonMedFullmakt(nyKorreksjon);
//        entityRepository.store(nyKorreksjon);
//
//        entityRepository.store(this);
//
//        return nyKorreksjon;
//    }
//
//    public Bestilling oppdaterTilstandEtterAllInformasjonErRegistrert(final EntityRepository entityRepository, final String username) {
//        newTilstandBuilder(username).withStatus(Status.FERDIGBEHANDLET).commit();
//        entityRepository.store(this);
//
//        return this;
//    }

    public String determineNextAction() {
        return NextActionFactory.create(this);
    }

    private static final class NextActionFactory {

        public static String create(final Bestilling bestilling) {
            //Switch på de forskjellige typene som kan ha hatt avbrudd i registreringen
            String targetAction;
            switch (bestilling.getCause()) {
                case BELOEP:
                    targetAction = "korreksjonFeilbelopOverforing.do";
                    break;
                case BELOEP_MED_FULLMAKT:
                    if (bestilling.getKorreksjon() == null) {
                        //steg 2: korreksjon ikke opprettet enda
                        targetAction = "korreksjonFeilbelopMedFullmaktOverforing.do";
                    } else {
                        //steg 3: korreksjon er opprettet, neste steg er reversering med fullmakt
                        targetAction = "korreksjonFeilbelopMedFullmaktReverseringMedFullmakt.do";
                    }
                    break;
                case DEBET:
                    targetAction = "korreksjondebetmedkonto.do";
                    break;
                case DEBET_MED_FULLMAKT:
                    targetAction = "korreksjondebetmedfullmakt.do";
                    break;
                case DOBBELREGISTRERING_MED_FULLMAKT:
                    targetAction = "korreksjondobbelregistreringmedfullmaktreversering.do";
                    break;
                case KREDIT:
                    targetAction = "korreksjonkreditmedkonto.do";
                    break;
                case KREDIT_MED_FULLMAKT:
                    targetAction = "korreksjonkreditmedfullmaktkonto.do";
                    break;
                default:
                    //Ikke håndtert type, FAIL
                    throw new IllegalStateException("Kan ikke fortsette behandling for bestillinger med årsakstype " + bestilling.getCause().getPresentationString());
            }
            return targetAction;
        }
    }
}
