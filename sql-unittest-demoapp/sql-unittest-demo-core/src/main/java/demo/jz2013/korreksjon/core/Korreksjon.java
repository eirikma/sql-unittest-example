package demo.jz2013.korreksjon.core;


import demo.jz2013.common.Fil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Korreksjon with type 'Korreksjon'
 */
public class Korreksjon extends AbstractKorreksjonsdata {

    private static final Logger LOG = LoggerFactory.getLogger(Korreksjon.class);


    /**
     * kan vaere null, hvis denne er laget i overforing-skjermbilde og ikke komemr far bestillingsdatabasen.
     */
    private Bestilling bestilling;

    private Fil fullmaktsbrev;
    private Fil purrebrev;

    public Korreksjon() {
    }

    /**
     * HORRIBLE - USE THE BUILDER CLASS
     *
     * @param type
     * @param source
     * @param bestillingsnummer
     * @param bestillingsdato
     * @param user
     * @param debetAccount
     * @param creditAccount
     * @param amount
     * @param arkivref
     * @param blankettnummer
     * @param opprinneligDato
     * @param cause
     * @param fritekst
     * @param gebyrBankregnummer
     * @param status
     * @param kidNummer
     * @param statusPath
     * @param nibeBusCode
     * @param nibeAlcCode
     * @param transTekst
     * @param buntNummer
     * @param belopKreditSumpost
     * @param debetNavn
     * @param createdAt
     */
    Korreksjon(KorreksjonType type, Source source, String bestillingsnummer, Date bestillingsdato,
               String user, String debetAccount, String creditAccount, BigDecimal amount,
               String arkivref, String blankettnummer, Date opprinneligDato,
               Cause cause, String fritekst, String gebyrBankregnummer,
               Status status, String kidNummer, String statusPath, String nibeBusCode, String nibeAlcCode,
               String transTekst, String buntNummer, BigDecimal belopKreditSumpost, String debetNavn, Date createdAt) {
        this.korreksjonType = type;
        this.source = source;
        this.bestillingsnummer = bestillingsnummer;
        this.bestillingsdato = (bestillingsdato);
        KorreksjonTilstandImpl.Builder builder = KorreksjonTilstandImpl
                .initialTilstandBuilder(this,
                        type,
                        bestillingsnummer,
                        bestillingsdato);
        builder.withCreatedByUser(user)
                .withSource(source)
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

    public Fil getFullmaktsbrev() {
        return fullmaktsbrev;
    }

    void setFullmaktsbrev(Fil fullmaktsbrev) {
        this.fullmaktsbrev = fullmaktsbrev;
    }

    public Fil getPurrebrev() {
        return purrebrev;
    }

    void setPurrebrev(Fil purrebrev) {
        this.purrebrev = purrebrev;
    }

//    public Fil getOrCreateFullmaktsbrev(FullmaktsbrevFilFactory fullmaktsbrevFilFactory, EntityRepository entityRepository, String signaturnavn, String endretBelop) {
//        if (!Status.VENTER_FULLMAKT.equals(this.getStatus())) {
//            throw new IllegalStateException(KorreksjonConstants.KORREKSJON_STATUS_ER_ENDRET);
//        }
//        Fil fil = getFullmaktsbrev();
//        if (fil != null) {
//            return fil;
//        }
//        try {
//            setFullmaktsbrev(generateFullmaktsbrev(fullmaktsbrevFilFactory, signaturnavn, endretBelop));
//        } catch (IOException e) {
//            LOG.error("IOException in generating fullmaktsbrev. ", e);
//            throw new RuntimeException("Noe feilet i generering av fullmaktsbrev.", e);
//        } catch (DocumentException e) {
//            LOG.error("PDF/iTextException in generating fullmaktsbrev. ", e);
//            throw new RuntimeException("Noe feilet i generering av fullmaktsbrev.", e);
//        }
//        entityRepository.store(this);
//        return getFullmaktsbrev();
//    }
//
//    public Fil getOrCreatePurrebrev(FullmaktsbrevFilFactory fullmaktsbrevFilFactory, EntityRepository entityRepository, String signaturnavn, String endretBelop) {
//        if (!Status.VENTER_FULLMAKT.equals(this.getStatus())) {
//            throw new IllegalStateException(KorreksjonConstants.KORREKSJON_STATUS_ER_ENDRET);
//        }
//        Fil fil = getPurrebrev();
//        if (fil != null) {
//            return fil;
//        }
//        try {
//            setPurrebrev(generatePurrebrev(fullmaktsbrevFilFactory, signaturnavn, endretBelop));
//        } catch (DocumentException e) {
//            throw new RuntimeException("Noe feilet under generering av purrebrev.", e);
//        } catch (IOException e) {
//            throw new RuntimeException("Noe feilet under generering av purrebrev.", e);
//        }
//        entityRepository.store(this);
//        return getPurrebrev();
//    }
//
//    private Fil generatePurrebrev(FullmaktsbrevFilFactory fullmaktsbrevFilFactory, String signaturnavn, String endretBelop) throws DocumentException, IOException {
//        return fullmaktsbrevFilFactory.createPurrebrev(this, signaturnavn, endretBelop);
//    }
//
//    private Fil generateFullmaktsbrev(FullmaktsbrevFilFactory fullmaktsbrevFilFactory, String signaturnavn, String endretBelop) throws IOException, DocumentException {
//        return fullmaktsbrevFilFactory.createFullmaktsbrev(this, signaturnavn, endretBelop);
//    }

    /**
     * only to be used from Bestilling
     *
     * @param bestilling - bestilling that takes ownership of this Korreksjon
     */
    void setBestilling(Bestilling bestilling) {
        this.bestilling = bestilling;
    }


    @Override
    public boolean isEligibleForDeletion() {
    	//Korreksjon med status slettet og ferdigbehandlet kan ikke settes til slettet.
    	//Korreksjon med status Venter fullmakt, kan slettes kun fra venterfullmaktlisten der denne metoden ikke kalles.
        return !(getStatus().equals(Status.SLETTET)
                || Status.VENTER_FULLMAKT.equals(getStatus())
                || Status.FERDIGBEHANDLET.equals(getStatus())
        );
    }

    public Bestilling getBestilling() {
        return bestilling;
    }

    @Override
    protected boolean isTheSameClassAsThis(Object otherObject) {
        return (otherObject instanceof Korreksjon);
    }

//    public void oppdaterTilstandEtterVerifiseringDebet(EntityRepository entityRepository, String username, String debetAccount) {
//        Status nyStatus;
//        if (Cause.DEBET.equals(getCause())) {
//            nyStatus = Status.KORRIGERT;
//        } else if (Cause.DEBET_MED_FULLMAKT.equals(getCause())) {
//            if (Status.VENTER_FULLMAKT.equals(getStatus())) {
//                nyStatus = Status.KORRIGERT;
//            } else {
//                nyStatus = Status.VENTER_FULLMAKT;
//            }
//        } else {
//            throw new IllegalStateException("Forventet ikke andre årsakstyper her enn Debet og Debet m/fullmakt. " +
//                    "Id:" + getIdAsLong() + ", fant:" + getCause().getPresentationString());
//        }
//        newTilstandBuilder(username)
//                .withStatus(nyStatus)
//                .withDebetAccount(debetAccount)
//                .commit();
//
//        entityRepository.store(this);
//        LOG.debug("Verifisert debet korreksjon, venter nå på fullmakt [id=" + getIdAsLong() + ", bestillingsnummer=" + getBestillingsnummer() + "]");
//    }
//
//    public void oppdaterTilstandEtterVerifiseringKreditFullmakt(EntityRepository entityRepository, String username, String debetAccount) {
//        if (Status.VENTER_FULLMAKT.equals(getStatus()) && (Cause.DOBBELREGISTRERING_MED_FULLMAKT.equals(getCause()) || Cause.KREDIT_MED_FULLMAKT.equals(getCause()))) {
//            newTilstandBuilder(username)
//                    .withStatus(Status.KORRIGERT)
//                    .withDebetAccount(debetAccount)
//                    .commit();
//
//            entityRepository.store(this);
//            if (Cause.DOBBELREGISTRERING_MED_FULLMAKT.equals(getCause())) {
//                LOG.debug("Verifisert dobbelregstrering reversing med fullmakt - klar til avregning [id=" + getIdAsLong() + ", bestillingsnummer=" + getBestillingsnummer() + "]");
//            } else {
//                LOG.debug("Verifisert kredit korreksjon med fullmakt - klar til avregning [id=" + getIdAsLong() + ", bestillingsnummer=" + getBestillingsnummer() + "]");
//            }
//        } else {
//            throw new IllegalStateException("Forventet ikke andre årsakstyper her enn Debet og Debet m/fullmakt. " +
//                    "Id:" + getIdAsLong() + ", fant:" + getCause().getPresentationString());
//        }
//    }
//
//    public Korreksjon oppdaterTilstandEtterFeilBelopFullmaktreversering(EntityRepository entityRepository, final String username, final String debetAccount) {
//        newTilstandBuilder(username)
//                .withDebetAccount(debetAccount)
//                .withStatus(Status.KORRIGERT)
//                .commit();
//        entityRepository.store(this);
//
//        return this;
//    }
//
//    public Korreksjon oppdaterTilstandEtterVerifiseringKredit(EntityRepository entityRepository, String username, String creditAccount) {
//        newTilstandBuilder(username)
//                .withStatus(Status.KORRIGERT)
//                .withCreditAccount(creditAccount)
//                .commit();
//
//        entityRepository.store(this);
//        LOG.debug("Verifisert kredit korreksjonen med fullmakt - klar til avregning [id=" + getIdAsLong() + ", bestillingsnummer=" + getBestillingsnummer() + "]");
//        return this;
//    }
//
//    public Korreksjon oppdaterTilstandEtterVerifiseringFeilBank(EntityRepository entityRepository, String user, String nyttBuntnummer) {
//        newTilstandBuilder(user)
//                .withStatus(Status.KORRIGERT)
//                .withBuntNummer(nyttBuntnummer)
//                .commit();
//
//        entityRepository.store(this);
//
//        return this;
//    }
//
//
//    public void oppdaterTilstandEtterVerifiseringBelop(final EntityRepository entityRepository, final String username, final BigDecimal nyAmount) {
//        newTilstandBuilder(username)
//                .withStatus(Status.KORRIGERT)
//                .withAmount(nyAmount)
//                .commit();
//
//        entityRepository.store(this);
//
//    }

    /**
     * Throws IllegalArgumentException if any fields are invalid or missing
     */
    public void verifyKorreksjonIsValid() {
        Validate.notNull(getSource(), "source kan ikke være null");
        Validate.notNull(getKorreksjonType(), "korreksjonType kan ikke være null");
        Validate.notNull(getCause(), "cause kan ikke være null");
        Validate.notNull(getStatus(), "status kan ikke være null");
        Validate.notNull(getBestillingsdato(), "bestillingsdato kan ikke være null");
        Validate.notNull(getOpprinneligDato(), "opprinnelig dato kan ikke være null");
        Validate.notNull(getCreatedBy(), "createdBy kan ikke være null");
        Validate.notNull(getCreatedAt(), "createdAT kan ikke være null");

        Validate.notEmpty(getBestillingsnummer(), "bestillingnummer må være utfylt");
        Validate.isTrue(StringUtils.isNumeric(getBestillingsnummer()), "bestillingnummer må bestå av bare tall");

        if (!getSource().equals(Source.KORREKSJON_WEB)) {
            Validate.notEmpty(getArkivref(), "arkivref må være utfylt");
            Validate.notEmpty(getNibeAlcCode(), "ALC-kode må være utfylt");
            Validate.notEmpty(getNibeBusCode(), "BUS-kode må være utfylt");
            if (getCause().equals(Cause.FEIL_BANK))  {
                Validate.notEmpty(getBuntnummer(), "buntnummer må være utfylt");
            }
        }

        final String debetAccountValidationErrors = AccountNumberFormatValidator.validate(getDebetAccount());
        Validate.isTrue(debetAccountValidationErrors == null, debetAccountValidationErrors);

        final String creditAccountValidationErrors = AccountNumberFormatValidator.validate(getCreditAccount());
        Validate.isTrue(creditAccountValidationErrors == null, creditAccountValidationErrors);

        //BestillingStatusUpdatePath kopieres uendret fra bestillingen og behøver ikke validering

        Validate.notNull(getAmount(), "amount kan ikke være null");
        Validate.isTrue(getAmount().abs().equals(getAmount()), "amount kan ikke være negativ");
    }

    private static class  AccountNumberFormatValidator {

        public static String validate(String account) {
            return null;
        }
    }

}
