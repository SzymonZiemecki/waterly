package pl.lodz.p.it.ssbd2023.ssbd06.exceptions;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.AccountDoesNotExistException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.AccountNotWaitingForConfirmationException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.AccountSearchPreferencesNotExistException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.AccountWithEmailAlreadyExistException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.CannotModifyPermissionsException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.IdenticalPasswordsException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.InvalidOTPException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.NoMatchingEmailException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.NotActiveAccountException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.NotConfirmedAccountException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.OperationUnsupportedException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.TokenExceededHalfTimeException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.TwoFARequestedException;
import pl.lodz.p.it.ssbd2023.ssbd06.mok.exceptions.UnmatchedPasswordsException;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.etag.exceptions.EntityIntegrityViolatedException;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.etag.exceptions.IfMatchHeaderMissingException;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.etag.exceptions.JWSException;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.etag.exceptions.NoPayloadException;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.jwt.exceptions.AuthenticationException;
import pl.lodz.p.it.ssbd2023.ssbd06.service.security.jwt.exceptions.NotAuthorizedApplicationException;

@ApplicationException(rollback = true)
public class ApplicationBaseException extends WebApplicationException {

    public static final String ERROR_UNKNOWN = "ERROR.UNKNOWN";
    public static final String ERROR_GENERAL_PERSISTENCE = "ERROR.GENERAL_PERSISTENCE";
    public static final String ERROR_OPTIMISTIC_LOCK = "ERROR.OPTIMISTIC_LOCK";
    public static final String ERROR_TRANSACTION_ROLLBACK = "ERROR.TRANSACTION_ROLLBACK";
    public static final String ERROR_ACCESS_DENIED = "ERROR.ACCESS_DENIED";
    public static final String ERROR_ACCOUNT_WITH_EMAIL_ALREADY_EXIST = "ERROR.ACCOUNT_WITH_EMAIL_EXIST";
    public static final String ERROR_ACCOUNT_WITH_PHONE_NUMBER_ALREADY_EXIST = "ERROR.ACCOUNT_WITH_PHONE_NUMBER_EXIST";
    public static final String ERROR_ACCOUNT_WITH_LOGIN_ALREADY_EXIST = "ERROR.ACCOUNT_WITH_LOGIN_EXIST";
    public static final String ERROR_CANNOT_MODIFY_PERMISSIONS = "ERROR.CANNOT_MODIFY_PERMISSIONS";
    public static final String ERROR_FORBIDDEN_OPERATION = "ERROR.FORBIDDEN_OPERATION";
    public static final String ERROR_IDENTICAL_PASSWORDS = "ERROR.IDENTICAL_PASSWORDS";
    public static final String ERROR_UNSUPPORTED_OPERATION = "ERROR.UNSUPPORTED_OPERATION";
    public static final String ERROR_TOKEN_EXCEEDED_HALF_TIME = "ERROR.TOKEN_EXCEEDED_HALF_TIME";
    public static final String ERROR_TOKEN_NOT_FOUND = "ERROR.TOKEN_NOT_FOUND";
    public static final String ERROR_ACCOUNT_NOT_FOUND = "ERROR.ACCOUNT_NOT_FOUND";
    public static final String ERROR_ACCOUNT_SEARCH_PREFERENCES_NOT_FOUND = "ERROR.ACCOUNT_SEARCH_PREFERENCES_NOT_FOUND";
    public static final String ERROR_NOT_AUTHORIZED = "ERROR.NOT_AUTHORIZED";
    public static final String ERROR_AUTHENTICATION = "ERROR.AUTHENTICATION";
    public static final String ERROR_PASSWORDS_DO_NOT_MATCH = "ERROR.NOT_MATCHING_PASSWORDS";
    public static final String ERROR_RESOURCE_NOT_FOUND = "ERROR.RESOURCE_NOT_FOUND";
    public static final String ERROR_ROLE_NOT_FOUND = "ERROR.ROLE_NOT_FOUND";

    public static final String ERROR_EMAIL_DO_NOT_MATCH = "ERROR.NO_MATCHING_EMAILS";
    public static final String ERROR_ACCOUNT_NOT_ACTIVE = "ERROR.NOT_ACTIVE_ACCOUNT";
    public static final String ERROR_ACCOUNT_NOT_CONFIRMED = "ERROR.NOT_CONFIRMED_ACCOUNT";

    public static final String ERROR_JWS_PROCESSING = "ERROR.JWS_PROCESSING";
    public static final String ERROR_NO_ETAG_PAYLOAD = "ERROR.NO_ETAG_PAYLOAD";
    public static final String ERROR_ENTITY_INTEGRITY_VIOLATED = "ERROR.ENTITY_INTEGRITY_VIOLATED";

    public static final String ERROR_IF_RECAPTCHA_INVALID = "ERROR.IF_RECAPTCHA_INVALID";
    public static final String ERROR_IF_MATCH_HEADER_MISSING = "ERROR.IF_MATCH_HEADER_MISSING";
    public static final String ERROR_INVALID_OTP = "ERROR.INVALID_OTP";

    protected static final String ERROR_ACCOUNT_NOT_WAITING_FOR_CONFIRMATION = "ERROR_ACCOUNT_NOT_WAITING_FOR_CONFIRMATION";

    protected static final String INFO_TWO_FA_CODE_REQUESTED = "INFO.TWO_FA_CODE_REQUESTES";

    public ApplicationBaseException(final Response.Status status, final String message) {
        super(Response.status(status).entity(new ErrorResponse(message)).type(MediaType.APPLICATION_JSON_TYPE).build());
    }

    public ApplicationBaseException(final Response.Status status, final String message, final Throwable cause) {
        super(message, cause, Response.status(status).entity(new ErrorResponse(message)).type(MediaType.APPLICATION_JSON_TYPE).build());
    }

    public static ApplicationBaseException generalErrorException(final Throwable cause) {
        return new ApplicationBaseException(INTERNAL_SERVER_ERROR, ERROR_UNKNOWN, cause);
    }

    public static ApplicationBaseException generalErrorException() {
        return new ApplicationBaseException(INTERNAL_SERVER_ERROR, ERROR_UNKNOWN);
    }

    public static ApplicationBaseException persistenceException(final Exception cause) {
        return new ApplicationBaseException(INTERNAL_SERVER_ERROR, ERROR_GENERAL_PERSISTENCE, cause);
    }

    public static ApplicationBaseException accessDeniedException() {
        return new ApplicationBaseException(FORBIDDEN, ERROR_ACCESS_DENIED);
    }

    public static ApplicationOptimisticLockException optimisticLockException() {
        return new ApplicationOptimisticLockException();
    }

    public static TransactionRollbackException transactionRollbackException() {
        return new TransactionRollbackException();
    }

    public static CannotModifyPermissionsException cannotModifyPermissionsException() {
        return new CannotModifyPermissionsException();
    }

    public static ForbiddenOperationException forbiddenOperationException() {
        return new ForbiddenOperationException();
    }

    public static IdenticalPasswordsException identicalPasswordsException() {
        return new IdenticalPasswordsException();
    }

    public static OperationUnsupportedException operationUnsupportedException() {
        return new OperationUnsupportedException();
    }

    public static InvalidRecaptchaException invalidRecaptchaException() {
        return new InvalidRecaptchaException();
    }

    public static TokenExceededHalfTimeException tokenExceededHalfTimeException() {
        return new TokenExceededHalfTimeException();
    }

    public static UnmatchedPasswordsException unmatchedPasswordsException() {
        return new UnmatchedPasswordsException();
    }

    public static AccountDoesNotExistException accountDoesNotExistException() {
        return new AccountDoesNotExistException();
    }

    public static NotAuthorizedApplicationException notAuthorizedException() {
        return new NotAuthorizedApplicationException();
    }

    public static AuthenticationException authenticationException() {
        return new AuthenticationException();
    }

    public static WebApplicationException resourceNotFoundException() {
        return new ResourceNotFoundException();
    }

    public static NoMatchingEmailException noMatchingEmailException() {
        return new NoMatchingEmailException();
    }

    public static AccountWithEmailAlreadyExistException accountWithEmailAlreadyExist() {
        return new AccountWithEmailAlreadyExistException(ERROR_ACCOUNT_WITH_EMAIL_ALREADY_EXIST);
    }

    public static NotActiveAccountException notActiveAccountException() {
        return new NotActiveAccountException();
    }

    public static NotConfirmedAccountException notConfirmedAccountException() {
        return new NotConfirmedAccountException();
    }

    public static AccountSearchPreferencesNotExistException accountSearchPreferencesNotExistException() {
        return new AccountSearchPreferencesNotExistException();
    }

    public static JWSException jwsException() {
        return new JWSException();
    }

    public static NoPayloadException noPayloadException() {
        return new NoPayloadException();
    }

    public static IfMatchHeaderMissingException ifMatchHeaderMissingException() {
        return new IfMatchHeaderMissingException();
    }

    public static EntityIntegrityViolatedException entityIntegrityViolatedException() {
        return new EntityIntegrityViolatedException();
    }

    public static AccountNotWaitingForConfirmationException accountNotWaitingForConfirmation() {
        return new AccountNotWaitingForConfirmationException();
    }

    public static PersistenceConstraintException persistenceConstraintException(final String message) {
        return new PersistenceConstraintException(message);
    }

    public static TwoFARequestedException twoFARequestedException() {
        return new TwoFARequestedException();
    }

    public static InvalidOTPException invalidOTPException() {
        return new InvalidOTPException();
    }
}
