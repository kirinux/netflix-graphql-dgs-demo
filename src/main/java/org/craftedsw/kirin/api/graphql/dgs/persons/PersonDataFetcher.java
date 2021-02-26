package org.craftedsw.kirin.api.graphql.dgs.persons;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DgsComponent
public class PersonDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDataFetcher.class);

    private final PersonsDataService personsDataService;

    public PersonDataFetcher(PersonsDataService personsDataService) {
        this.personsDataService = personsDataService;
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Person)
    public Person person(String identifier, DgsDataFetchingEnvironment dataFetchingEnvironment) {
        LOGGER.info("Fetch PERSON with identifier {}", identifier);
        return personsDataService.findPerson(identifier);
    }

}
