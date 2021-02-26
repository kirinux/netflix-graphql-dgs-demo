package org.craftedsw.kirin.api.graphql.dgs.persons;


import org.craftedsw.kirin.api.graphql.schema.types.Gender;
import org.craftedsw.kirin.api.graphql.schema.types.Person;
import org.craftedsw.kirin.api.graphql.schema.types.Phone;
import org.craftedsw.kirin.api.graphql.schema.types.PhoneType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonsDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonsDataService.class);

    private static final List<Person> PERSONS = Stream.iterate(0, i -> i + 1)
            .map(i -> new Person("" + i, "firstName" + i, "lastName" + i, i % 2 == 0 ? Gender.male : Gender.female, null, Collections.emptyList()))
            .limit(10)
            .collect(Collectors.toUnmodifiableList());

    private static final Map<String, List<Phone>> PERSON_PHONES = Map.of(
            "1", List.of(new Phone(PhoneType.home, "0123456789")),
            "2", List.of(new Phone(PhoneType.home, "0123556789")),
            "3", List.of(new Phone(PhoneType.home, "0423456789")),
            "4", List.of(new Phone(PhoneType.home, "0199456789")),
            "5", List.of(new Phone(PhoneType.home, "0123456744"), new Phone(PhoneType.mobile, "0123453356"))
    );

    public Person findPerson(String identifier) {
        return PERSONS.stream()
                .filter(person -> person.getIdentifier().equals(identifier))
                .findFirst()
                .orElseThrow();
    }

    public Map<String, List<Phone>> findPhoneByPerson(Set<String> personId) {
        LOGGER.info("find phones by person id {}", personId);
        return PERSON_PHONES.entrySet().stream()
                .filter(entry -> personId.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
