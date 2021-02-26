package org.craftedsw.kirin.api.graphql.dgs.persons;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Person;
import org.craftedsw.kirin.api.graphql.schema.types.Phone;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class PhoneDataFetcher {


    @DgsData(parentType = DgsConstants.PERSON.TYPE_NAME, field =DgsConstants.PERSON.Phones)
    public CompletableFuture<Phone> phones(DgsDataFetchingEnvironment dfe) {
        DataLoader<String, Phone> dataLoader = dfe.getDataLoader(PhoneDataLoader.class);

        Person source = dfe.getSource();

        return dataLoader.load(source.getIdentifier());
    }
}
