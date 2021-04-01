package org.craftedsw.kirin.api.graphql.dgs.player;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Address;
import org.craftedsw.kirin.api.graphql.schema.types.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@DgsComponent
public class AddressDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDataFetcher.class);

    private final PlayerService playerService;

    public AddressDataFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @DgsData(parentType = DgsConstants.PLAYER.TYPE_NAME, field = DgsConstants.PLAYER.Address)
    public List<Address> address(DataFetchingEnvironment dfe) {
        PlayerAdapted player = dfe.getSource();
        String id = player.getPayerId();
        LOGGER.info("Search Address for player with id {}", id);
        return playerService.findAddressesForPlayer(id);
    }


}
