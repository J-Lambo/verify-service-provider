package uk.gov.ida.verifyserviceprovider.validators;

import org.joda.time.DateTime;
import uk.gov.ida.verifyserviceprovider.exceptions.SamlResponseValidationException;
import uk.gov.ida.verifyserviceprovider.utils.DateTimeComparator;

import static org.joda.time.DateTimeZone.UTC;
import static org.joda.time.format.ISODateTimeFormat.dateHourMinuteSecond;

public class TimeRestrictionValidator {
    private final DateTimeComparator dateTimeComparator;

    public TimeRestrictionValidator(DateTimeComparator dateTimeComparator) {
        this.dateTimeComparator = dateTimeComparator;
    }

    public void validateNotOnOrAfter(DateTime notOnOrAfter) {
        if (dateTimeComparator.isDefinitelyBeforeNow(notOnOrAfter)) {
            throw new SamlResponseValidationException("Assertion is not valid on or after "
                + notOnOrAfter.withZone(UTC).toString(dateHourMinuteSecond())
            );
        }
    }

    public void validateNotBefore(DateTime notBefore) {
        if (notBefore != null && dateTimeComparator.isDefinitelyAfterNow(notBefore)) {
            throw new SamlResponseValidationException(String.format("Assertion is not valid before %s", notBefore.withZone(UTC).toString(dateHourMinuteSecond())));
        }
    }
}
