package org.craftedsw.kirin.api.graphql.dgs.player;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.craftedsw.kirin.api.graphql.dgs.player.infra.InternalPlayer;
import org.craftedsw.kirin.api.graphql.schema.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    private static final List<InternalPlayer> PLAYERS;
    private static final Map<String, List<Phone>> PHONES;
    private static final Map<String, List<Address>> ADDRESSES;
    private static final Map<String, Country> COUNTRIES;
    private final Map<String, Country> countryCache = new HashMap<>();

    static {
        final Faker faker = new Faker();
        PLAYERS = Stream.iterate(0, i -> i + 1)
                .map(i -> new InternalPlayer(
                        "id-" + i,
                        faker.name().firstName(),
                        faker.name().firstName(),
                        null,
                        "fr",
                        Gender.male,
                        null
                )).limit(20)
                .collect(Collectors.toUnmodifiableList());
        LOGGER.info("Generated list: {}", PLAYERS);

        PHONES = new HashMap<>();
        ADDRESSES = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            com.github.javafaker.Address address = faker.address();
            String id = "id-" + i;
            PHONES.put(
                    id,
                    List.of(new Phone(PhoneType.values()[i % PhoneType.values().length], faker.phoneNumber().phoneNumber()))
            );

            ADDRESSES.put(
                    id,
                    List.of(new Address("home", address.streetAddress(), address.city(), address.country()))
            );
        }

        COUNTRIES = new HashMap<>();
        COUNTRIES.put("FR", new Country("FRANCE", "FR"));
        COUNTRIES.put("JP", new Country("JAPAN", "JP"));
    }


    public List<InternalPlayer> findPlayers(String lastName) {
        return PLAYERS.stream()
                .filter(player -> player.getLastName().equals(lastName))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Phone> findPhonesForPlayer(String id) {
        return PHONES.get(id);
    }

    public Country getCountry(String countryCode) {
        String upperCaseCode = countryCode.toUpperCase();
        LOGGER.debug("Get country for code {}", upperCaseCode);
        return Optional.ofNullable(countryCache.get(upperCaseCode))
                .orElseGet(() -> {
                    //cache simulation
                    Country country = COUNTRIES.get(upperCaseCode);
                    countryCache.put(upperCaseCode, country);
                    return country;
                });
    }


    public List<Address> findAddressesForPlayer(String id) {
        return ADDRESSES.get(id);
    }

    public List<InternalPlayer> findPlayers(PlayerFilter filter) {
        return PLAYERS.stream()
                .filter(player -> StringUtils.isBlank(filter.getLastName()) || player.getLastName().equals(filter.getLastName()))
                .filter(player -> StringUtils.isBlank(filter.getFirstName()) || player.getFirstName().equals(filter.getFirstName()))
                .filter(player -> Objects.isNull(filter.getGender()) || player.getGender().equals(filter.getGender()))
                .collect(Collectors.toUnmodifiableList());
    }
}
