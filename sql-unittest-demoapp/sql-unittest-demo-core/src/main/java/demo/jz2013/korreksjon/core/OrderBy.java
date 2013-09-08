package demo.jz2013.korreksjon.core;

import org.apache.commons.lang.StringUtils;

import java.util.Comparator;

public final class OrderBy {

    public static enum Field {
        bestillingsnummer("bestillingsnummer"),
        bestillingsdato("bestillingsdato"),
        opprinnelig_dato("opprinneligDato"),
        status("status"),
        blankettnummer("blankettnummer"),
        debet_account("debetAccount"),
        credit_account("creditAccount"),
        korreksjon_type("korreksjonType"),
        cause("cause"),
        amount("amount"),

        id("id"),
        jobname("jobType"), // cannot sort on non-db property jobName, so sort on type instead, as it represent the exact same grouping of entries
        jobstatus("jobStatus"),
        createdTime("createdTime"),
        finishedTime("finishedTime");

        private String property;

        Field(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public String getNameAsRelation(String relation) {
            return property.replace("TILSTAND", relation);
        }
    }

    public static enum Order {
        asc, desc
    }

    private final Field field;
    private final Order order;

    public Field getField() {
        return field;
    }

    public Order getOrder() {
        return order;
    }

    public OrderBy(Field field, Order order) {
        this.field = field;
        this.order = order;
    }

    public String getNameOfCurrentValue() {
        return field.getNameAsRelation("currentTilstand");
    }

    public int compare(AbstractKorreksjonsdata a, AbstractKorreksjonsdata b) {
        int tmpResult = 0;
        switch (field) {
            case bestillingsnummer:
                tmpResult = a.getBestillingsnummer().compareTo(b.getBestillingsnummer());
                break;
            case bestillingsdato:
                tmpResult = a.getBestillingsdato().compareTo(b.getBestillingsdato());
                break;
            case opprinnelig_dato:
                tmpResult = a.getOpprinneligDato().compareTo(b.getOpprinneligDato());
                break;
            case status:
                tmpResult = a.getStatus().getPresentationString().compareTo(b.getStatus().getPresentationString());
                break;
            case blankettnummer:
                String aBlankettnummer = (a.getBlankettnummer() == null ? "" : a.getBlankettnummer());
                tmpResult = aBlankettnummer.compareTo(b.getBlankettnummer() == null ? "" : b.getBlankettnummer());
                break;
            case debet_account:
                String aDebetAccount = (a.getDebetAccount() == null ? "" : a.getDebetAccount());
                tmpResult = aDebetAccount.compareTo(b.getDebetAccount() == null ? "" : b.getDebetAccount());
                break;
            case credit_account:
                String aCreditAccount = (a.getCreditAccount() == null ? "" : a.getCreditAccount());
                tmpResult = aCreditAccount.compareTo(b.getCreditAccount() == null ? "" : b.getCreditAccount());
                break;
            case korreksjon_type:
                tmpResult = a.getKorreksjonType().name().compareTo(b.getKorreksjonType().name());
                break;
            case cause:
                tmpResult = a.getCause().compareTo(b.getCause());
                break;
            case amount:
                tmpResult = a.getAmount().compareTo(b.getAmount());
                break;
        }
        return order == Order.asc ? tmpResult : -tmpResult;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OrderBy)) {
            return false;
        }
        OrderBy other = (OrderBy) obj;
        return this.field == other.field && this.order == other.order;
    }

    @Override
    public int hashCode() {
        return field.hashCode() + order.hashCode();
    }

    @Deprecated
    public String toSqlString() {
        return field.name().toUpperCase() + " " + order.name();
    }

    public String toGuiString() {
        return field.name() + "-" + order.name();
    }

    public static Comparator<Korreksjon> createComparator(final OrderBy... orderBy) {
        return new Comparator<Korreksjon>() {
            @Override
            public int compare(Korreksjon a, Korreksjon b) {
                for (OrderBy o : orderBy) {
                    int comparison = o.compare(a, b);
                    if (comparison != 0) {
                        return comparison;
                    }
                }
                return 0;
            }
        };
    }

    @Override
    public String toString() {
        return "OrderBy[" + field + "," + order + "]";
    }

    public static String toSqlString(OrderBy... orderBy) {
        StringBuilder builder = new StringBuilder();
        for (OrderBy o : orderBy) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(o.toSqlString());
        }
        return builder.toString();
    }

    public static String toGuiString(OrderBy... orderBy) {
        StringBuilder builder = new StringBuilder();
        for (OrderBy o : orderBy) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(o.toGuiString());
        }
        return builder.toString();
    }

    public static OrderBy valueOf(String guiString) {
        if (StringUtils.isBlank(guiString)) {
            return null;
        }
        String[] split = StringUtils.split(guiString, '-');
        return new OrderBy(Field.valueOf(split[0]), Order.valueOf(split[1]));
    }

    public static OrderBy[] parse(String guiString) {
        String[] fields = StringUtils.split(StringUtils.trimToEmpty(guiString), ",");
        OrderBy[] result = new OrderBy[fields.length];
        for (int i = 0; i < fields.length; i++) {
            result[i] = OrderBy.valueOf(fields[i]);
        }
        return result;
    }
}