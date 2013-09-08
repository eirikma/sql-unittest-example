package demo.jz2013.korreksjon.core;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface KorreksjonTilstand {

    Cause getCause();

    String getBlankettnummer();

    BigDecimal getAmount();

    String getCreditAccount();

    String getDebetAccount();

    String getFritekst();

    String getGebyrBankregnummer();

    String getNibeBusCode();

    String getNibeAlcCode();

    Source getSource();

    String getBestillingStatusUpdatePath();

    String getKidNummer();

    String getBuntnummer();

    String getTransTekst();

    BigDecimal getBelopKreditSumpost();

    Date getOpprinneligDato();

    String getArkivref();

    KorreksjonType getKorreksjonType();

    String getBestillingsnummer();

    Date getBestillingsdato();

    Timestamp getCreatedAt();

    String getCreatedBy();

    Status getStatus();

    String getDebetNavn();

    String getNorwegianTimeOfDay();

    String getNorwegianDate();

    KorreksjonTilstandBuilder newTilstandBuilder(String username);

    Date getBestillingsdatoAsDate();

    String getAmountAsString();

    Date getOpprinneligDatoAsDate();
}