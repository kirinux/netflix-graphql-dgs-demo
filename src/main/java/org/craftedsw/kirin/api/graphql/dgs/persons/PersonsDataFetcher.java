package org.craftedsw.kirin.api.graphql.dgs.persons;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Gender;
import org.craftedsw.kirin.api.graphql.schema.types.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DgsComponent
public class PersonsDataFetcher {

    private static final List<Person> PERSONS = Stream.iterate(0, i -> i + 1)
            .map(i -> new Person("" + i, "firstName" + i, "lastName" + i, i % 2 == 0 ? Gender.male : Gender.female, null, Collections.emptyList()))
            .limit(10)
            .collect(Collectors.toUnmodifiableList());

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsDataFetcher.class);

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Person)
    public Person person(String identifier, DataFetchingEnvironment dataFetchingEnvironment) {
        LOGGER.info("Fetch PERSON with identifier {}", identifier);
        return PERSONS.stream()
                .filter(person -> person.getIdentifier().equals(identifier))
                .findFirst()
                .orElseThrow();
    }

}
