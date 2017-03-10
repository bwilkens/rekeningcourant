package nl.quintor.rc.it;

import com.consol.citrus.actions.AbstractTestAction;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestDesigner;
import com.consol.citrus.message.MessageType;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CitrusIT extends JUnit4CitrusTestDesigner {

    private static final Logger LOG = LoggerFactory.getLogger(CitrusIT.class);

    @Test
    @CitrusTest(name = "MyFirstTest")
    public void testFirst() {
        description("First test");

        variable("text", "Hello Test Framework");

        echo("${text}");
    }

    @Test
    @CitrusTest(name = "MySecondTest")
    public void testSecond() {
        description("Second test");
        echo("--- BEFORE");
        action(new AbstractTestAction() {
            @Override
            public void doExecute(TestContext context) {
                LOG.info("OLA!");
            }

        });
        echo("--- AFTER");
    }

    @Test
    @CitrusTest(name = "Create user")
    public void testCreateUser() {

        description("Create user");

        variable("user", "test");

        http().client("loginHttpClient")//
                .get("/${user}")//
                .contentType(ContentType.APPLICATION_JSON.getMimeType())//
                .accept(ContentType.APPLICATION_JSON.getMimeType())//
        ;

        http().client("loginHttpClient")//
                .response(HttpStatus.OK)//
                .messageType(MessageType.JSON)//
                .validate("$.username", "${user}")//
        ;

    }
}
