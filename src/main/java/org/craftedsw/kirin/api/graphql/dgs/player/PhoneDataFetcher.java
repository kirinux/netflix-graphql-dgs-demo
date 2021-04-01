package org.craftedsw.kirin.api.graphql.dgs.player;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Phone;
import org.craftedsw.kirin.api.graphql.schema.types.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@DgsComponent
public class PhoneDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDataFetcher.class);

    private final PlayerService playerService;

    public PhoneDataFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @DgsData(parentType = DgsConstants.PLAYER.TYPE_NAME, field = DgsConstants.PLAYER.Phones)
    public List<Phone> phones(DataFetchingEnvironment dfe) {
        PlayerAdapted player = dfe.getSource();
        String id = player.getPayerId();
        LOGGER.info("Search Phone for player with id {}", id);
        return playerService.findPhonesForPlayer(id);
    }


}
