package org.craftedsw.kirin.api.graphql.dgs.persons;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.ExecutionResult;
import org.craftedsw.kirin.api.graphql.schema.client.PersonGraphQLQuery;
import org.craftedsw.kirin.api.graphql.schema.client.PersonProjectionRoot;
import org.craftedsw.kirin.api.graphql.schema.types.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
        classes = {DgsAutoConfiguration.class, PersonDataFetcher.class}
)
class PersonDataFetcherTest {

    @Autowired
    private DgsQueryExecutor queryExecutor;

    @Test
    void should_return_person_firstName_by_identifier() {
        Person person = queryExecutor.executeAndExtractJsonPathAsObject("{\n" +
                        "  person(identifier: \"1\") {\n" +
                        "    firstName\n" +
                        "  }\n" +
                        "}",
                "data.person",
                Person.class);
        assertThat(person).isNotNull();
        assertThat(person.getFirstName()).isEqualTo("firstName1");
    }

    @Test
    void should_return_person_identifier() {
        GraphQLQueryRequest request = new GraphQLQueryRequest(
                new PersonGraphQLQuery.Builder().identifier("1").build(),
                new PersonProjectionRoot().identifier().firstName().lastName()
        );
        Person person = queryExecutor.executeAndExtractJsonPathAsObject(request.serialize(),
                "data.person",
                Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getIdentifier()).isEqualTo("1");
        assertThat(person.getFirstName()).isEqualTo("firstName1");
        assertThat(person.getLastName()).isEqualTo("lastName1");

    }

    @Test
    void unknown_field_name_should_throw_error() {
        ExecutionResult result = queryExecutor.execute("{\n" +
                "  person(identifier: \"1\") {\n" +
                "    plop\n" +
                "  }\n" +
                "}");
        assertThat(result.getErrors()).isNotEmpty();
        assertThat(result.getErrors().get(0).getMessage()).contains("Validation error of type FieldUndefined");

    }


}