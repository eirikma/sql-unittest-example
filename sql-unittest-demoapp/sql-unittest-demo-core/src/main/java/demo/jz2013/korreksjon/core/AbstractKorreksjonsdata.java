package demo.jz2013.korreksjon.core;


import org.apache.commons.lang.Validate;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public abstract class AbstractKorreksjonsdata  implements KorreksjonTilstandHistory {

    // unique key constraint paa disse 4
    // de er heller ikke lov aa endre
    protected Source source;
    protected String bestillingsnummer;
    protected Date bestillingsdato;
    protected KorreksjonType korreksjonType;

    private Status status;
    private int version;
    private List<KorreksjonTilstand> history;
    private KorreksjonTilstandImpl currentTilstand;
    private Long id;



    public Long getId() {
        return this.id;
    }


    /**
     * Use this to alter the state of the Korreksjon or Bestilling..
     *
     * @return a builder that can be used to compose the next state of the object.
     */
    public KorreksjonTilstandBuilder newTilstandBuilder(String username) {
        Validate.notEmpty(username, "user must be specified and not blank");
        return getTilstand().newTilstandBuilder(username);
    }

    /**
     * INTENTIONALLY NOT PUBLIC: Only package-internal calls to this method.
     *
     * @param tilstand
     */
    void addTilstand(KorreksjonTilstandImpl tilstand) {
        Validate.notNull(tilstand, "new tilstand cannot be null");
        Validate.isTrue(equals(tilstand.getOwner()), "not my tilstand: " + tilstand);

        if (getHistory() == null) {
            history = new ArrayList<KorreksjonTilstand>(1);
        }
        history.add(tilstand);
        currentTilstand = tilstand;
        // update "global" state from last state
        updateDenormalizedValuesFromNewTilstand(tilstand);
        updateVersion();
    }

    void updateDenormalizedValuesFromNewTilstand(KorreksjonTilstandImpl tilstand) {
        this.status = tilstand.getStatus();
        this.bestillingsdato = tilstand.getBestillingsdato();
        this.bestillingsnummer = tilstand.getBestillingsnummer();
        this.korreksjonType = tilstand.getKorreksjonType();
    }

    /**
     * set 'dirty' flag and grab optimistic lock on update.
     * Don't know if this is actually necessary or if hibernate will do that automatically as well.
     */
    private void updateVersion() {
        version++;
    }

    /**
     * returnerer gjeldende tilstand.
     *
     * @return the 'current tilstand' - the last element of the history list (if any such element, else null)
     */
    private KorreksjonTilstandImpl getTilstand() {
        return currentTilstand;
    }

    private KorreksjonTilstandImpl getInitialTilstand() {
        List<KorreksjonTilstand> tilstandList = getHistory();
        return (KorreksjonTilstandImpl) (tilstandList == null ? null : tilstandList.isEmpty() ? null : tilstandList.get(0));
    }


    public KorreksjonType getKorreksjonType() {
        return korreksjonType;
    }

    public Source getSource() {
        return source;
    }

    public String getBestillingsnummer() {
        return bestillingsnummer;
    }

    public Date getBestillingsdato() {
        return bestillingsdato;
    }

    public String getBuntnummer() {
        return getTilstand() == null ? null : getTilstand().getBuntnummer();
    }

    @Override
    public Date getBestillingsdatoAsDate() {
        return bestillingsdato;
    }

    public String getBestillingsdatoAsIso8601() {
//        return bestillingsdato == null ? null : (bestillingsdato).toString("yyyy-MM-dd");
        return bestillingsdato.toString();
    }

    public String getNorwegianBestillingsdato() {
//        return bestillingsdato == null ? null : Date.from(bestillingsdato).toString("dd.MM.yyyy");
        return bestillingsdato.toString();
    }

    public String getSimpleBestillingsdato() {
//        return bestillingsdato == null ? null : Date.from(bestillingsdato).toString("ddMMyy");
        return bestillingsdato.toString();
    }

    public Timestamp getCreatedAt() {
        return getInitialTilstand() == null ? null : getInitialTilstand().getCreatedAt();
    }

    public Timestamp getLastUpdatedAt() {
        return getTilstand() == null ? null : getTilstand().getCreatedAt();
    }

    public String getCreatedBy() {
        return getInitialTilstand() == null ? null : getInitialTilstand().getCreatedBy();
    }

    public String getCreatedByUser() {
        return getCreatedBy();
    }

    public String getLastUpdatedBy() {
        return getTilstand() == null ? null : getTilstand().getCreatedBy();
    }

    // huh ? er dette en rimelig timestamp?
    public Timestamp getTimestamp() {
        return getCreatedAt();
    }


    public String getNorwegianTimestamp() {
        return getNorwegianDate() + " " + getNorwegianTimeOfDay();
    }

    public String getUser() {
        return getCreatedBy();
    }

    public Status getStatus() {
        return status;
    }

    public String getDebetAccount() {
        return getTilstand() == null ? null : getTilstand().getDebetAccount();
    }

    /**
     * Format accountNumber as a string, even if it is null or too short.
     *
     * @param accountNumber account number to format.
     * @return string representation of the account number
     */
    protected String getSafeAccountNumberAsString(String accountNumber) {
        if (null == accountNumber) {
            return "";
        }
        return accountNumber;
    }

    public String getDebetAccountAsString() {
        return getSafeAccountNumberAsString(getDebetAccount());
    }

    public String getCreditAccount() {
        return getTilstand() == null ? null : getTilstand().getCreditAccount();
    }

    public String getCreditAccountAsString() {
        return getSafeAccountNumberAsString(getCreditAccount());
    }

    public BigDecimal getAmount() {
        return getTilstand() == null ? null : getTilstand().getAmount();
    }

    public String getBlankettnummer() {
        return getTilstand() == null ? null : getTilstand().getBlankettnummer();
    }

    public String getArkivref() {
        return getTilstand() == null ? null : getTilstand().getArkivref();
    }

    public Date getOpprinneligDato() {
        return getTilstand() == null ? null : getTilstand().getOpprinneligDato();
    }



    public Cause getCause() {
        return getTilstand() == null ? null : getTilstand().getCause();
    }

    public String getFritekst() {
        return getTilstand() == null ? null : getTilstand().getFritekst() == null ? "" : getTilstand().getFritekst();
    }

    public String getGebyrBankregnummer() {
        return getTilstand() == null ? null : getTilstand().getGebyrBankregnummer() == null ? "" : getTilstand().getGebyrBankregnummer();
    }

    public abstract boolean isEligibleForDeletion();

    public boolean isLowAmount() {
        return !isHighAmount();
    }

    public boolean isHighAmount() {
        if (getTilstand() == null || getTilstand().getAmount() == null) {
            return false;
        } else {
            return getTilstand().getAmount().compareTo(new BigDecimal("999999.99")) > 0;
        }
    }

    public boolean isGebyr() {
        return isNotBlank(getTilstand().getGebyrBankregnummer());
    }

    public String getNibeBusCode() {
        return getTilstand() == null ? null : getTilstand().getNibeBusCode();
    }

    public String getNibeAlcCode() {
        return getTilstand() == null ? null : getTilstand().getNibeAlcCode();
    }

    public String getBestillingStatusUpdatePath() {
        return getTilstand() == null ? null : getTilstand().getBestillingStatusUpdatePath();
    }

    public String getKidNummer() {
        return getTilstand() == null ? null : getTilstand().getKidNummer();
    }

    public String getTransTekst() {
        return getTilstand() == null ? null : getTilstand().getTransTekst();
    }

    public BigDecimal getBelopKreditSumpost() {
        return getTilstand() == null ? null : getTilstand().getBelopKreditSumpost();
    }

    @Override
    public List<KorreksjonTilstand> getHistory() {
        return history;
    }

    @Override
    public String getDebetNavn() {
        return getTilstand() == null ? null : getTilstand().getDebetNavn();
    }

    @Override
    public final int hashCode() {
        Source src = getSource();
        String nr = getBestillingsnummer();
        Date date = getBestillingsdato();
        return (int)
                (((long) (src == null ? 0 : src.hashCode())
                        + (long) (nr == null ? 0 : nr.hashCode())
                        + (long) (date == null ? 0 : date.hashCode()))
                        % Integer.MAX_VALUE);
    }

    @Override
    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (!isTheSameClassAsThis(otherObject)) {
            return false;
        }

        AbstractKorreksjonsdata other = (AbstractKorreksjonsdata) otherObject;

        return Objects.equals(getSource(), other.getSource())
                && Objects.equals(getBestillingsnummer(), other.getBestillingsnummer())
                && Objects.equals(getBestillingsdato(), other.getBestillingsdato());
    }


    protected abstract boolean isTheSameClassAsThis(Object otherObject);

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "[id=" + getId()
                + ",source=" + source
                + ",type=" + korreksjonType
                + ",status=" + status
                + ",bestillingsdato=" + bestillingsdato
                + ",bestillingsnummer=" + bestillingsnummer
                + "version=" + this.version + "]";
    }

    public boolean isNewTilstandAllowed(KorreksjonTilstandImpl newTilstand) {
        final Status currentStatus = getStatus();
        if (currentStatus != null) {
            if (!currentStatus.isStateTransitionAllowed(newTilstand.getStatus(), newTilstand.getCause())) {
                return false;
            }
        }
        return true;
    }

    public int getVersion() { //Allow read of version in order to compare versions
        return version;
    }


    public BuntInfo getBuntInfo() {
        return new BuntInfo(getBuntnummer());
    }

    @Override
    public String getNorwegianTimeOfDay() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getNorwegianDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAmountAsString() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getOpprinneligDatoAsDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
