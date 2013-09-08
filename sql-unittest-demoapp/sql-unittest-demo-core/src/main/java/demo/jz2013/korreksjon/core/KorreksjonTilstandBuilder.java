package demo.jz2013.korreksjon.core;


import java.math.BigDecimal;
import java.sql.Date;

/**
 * Interface for a builder that can create a single new tilstand
 * for a Korreksjon instance piece by piece.
 * <p/>
 * A builder cannot be re-used to build a second tilstand. After commit(), the
 * builder is in an invalid state.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: eim
 * Date: 26.01.11
 * Time: 15:33
 */
public interface KorreksjonTilstandBuilder {

    void commit();

    KorreksjonTilstandBuilder withKorreksjonsType(KorreksjonType type);

    KorreksjonTilstandBuilder withStatus(Status status);

    KorreksjonTilstandBuilder withBestillingsnummer(String bestillingsnr);

    KorreksjonTilstandBuilder withBestillingsdato(Date bestillingsdato);

    KorreksjonTilstandBuilder withCreatedByUser(String user);

    KorreksjonTilstandBuilder withCreditAccount(String account);

    KorreksjonTilstandBuilder withDebetAccount(String account);

    KorreksjonTilstandBuilder withDebetNavn(String debetNavn);

    KorreksjonTilstandBuilder withAmount(BigDecimal amount);

    KorreksjonTilstandBuilder withBlankettnummer(String blankettnummer);

    KorreksjonTilstandBuilder withArkivref(String arkivref);

    KorreksjonTilstandBuilder withOpprinneligDato(Date opprinneligDato);

    KorreksjonTilstandBuilder withCause(Cause cause);

    KorreksjonTilstandBuilder withFritekst(String fritekst);

    KorreksjonTilstandBuilder withGebyrbankRegnr(String gebyrbankRegnr);

    KorreksjonTilstandBuilder withNibeProduktOgPriskode(String bus, String alc);

    KorreksjonTilstandBuilder withKidNr(String kid);

    KorreksjonTilstandBuilder withBuntNummer(String buntnummer);

    KorreksjonTilstandBuilder withTransTekst(String transTekst);

    KorreksjonTilstandBuilder withBelopKreditSumport(BigDecimal belopKreditSumpost);

    KorreksjonTilstandBuilder withBestillingStatusUpdatePath(String bestillingStatusUpdatePath);

    KorreksjonTilstandBuilder withCreatedAt(Date createdAt);

    /**
     * @return true if any "with" method is called with a value that is different from the existing one in the korreksjon.
     */
    boolean isModified();

    KorreksjonTilstandImpl.Builder withSource(Source source);

    void validate();
}
