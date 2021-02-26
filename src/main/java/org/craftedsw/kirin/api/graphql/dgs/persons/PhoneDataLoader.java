package org.craftedsw.kirin.api.graphql.dgs.persons;

import com.netflix.graphql.dgs.DgsDataLoader;
import org.craftedsw.kirin.api.graphql.schema.types.Phone;
import org.dataloader.BatchLoader;
import org.dataloader.MappedBatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "phoneLoader", maxBatchSize = 10, caching = true)
public class PhoneDataLoader implements MappedBatchLoader<String, List<Phone>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDataLoader.class);

    private final PersonsDataService personsDataService;

    public PhoneDataLoader(PersonsDataService personsDataService) {
        this.personsDataService = personsDataService;
    }

    @Override
    public CompletionStage<Map<String, List<Phone>>> load(Set<String> keys) {
        LOGGER.info("Fetch phone data for identifier {}", keys);
        return CompletableFuture.supplyAsync(() -> personsDataService.findPhoneByPerson(keys));
    }
}
