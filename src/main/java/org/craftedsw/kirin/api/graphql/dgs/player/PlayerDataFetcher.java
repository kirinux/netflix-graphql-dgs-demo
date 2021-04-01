package org.craftedsw.kirin.api.graphql.dgs.player;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Player;
import org.craftedsw.kirin.api.graphql.schema.types.PlayerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class PlayerDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDataFetcher.class);

    private final PlayerService playerService;

    public PlayerDataFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.PlayersByName)
    public List<PlayerAdapted> playersByName(String lastName) {
        LOGGER.info("Search for player with lastName {}", lastName);
        return playerService.findPlayers(lastName).stream()
                .map(PlayerAdapted::fromInternal)
                .collect(Collectors.toUnmodifiableList());
    }


    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.Players)
    public List<PlayerAdapted> players(@InputArgument  PlayerFilter filter) {
        LOGGER.info("Search for player with filter {}", filter);
        return playerService.findPlayers(filter).stream()
                .map(PlayerAdapted::fromInternal)
                .collect(Collectors.toUnmodifiableList());
    }

}
