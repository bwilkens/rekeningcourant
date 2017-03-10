package nl.quintor.rc.web.rest.exception;


import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class BeanValidationExceptionProvider implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final List<ValidationError> errors = exception.getConstraintViolations()
                .stream()
                .map(e -> new ValidationError(e.getMessage())).collect(Collectors.toList());
        return Response.status(Status.BAD_REQUEST).entity(errors).build();
    }
}
