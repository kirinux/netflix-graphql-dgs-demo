package org.craftedsw.kirin.api.graphql.dgs.player;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.craftedsw.kirin.api.graphql.schema.DgsConstants;
import org.craftedsw.kirin.api.graphql.schema.types.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DgsComponent
public class CountryDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDataFetcher.class);

    private final PlayerService playerService;

    public CountryDataFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @DgsData(parentType = DgsConstants.PLAYER.TYPE_NAME, field = DgsConstants.PLAYER.Country)
    public Country country(DataFetchingEnvironment dfe) {
        PlayerAdapted playerAdapted = dfe.getSource();
        String countryCode = playerAdapted.getCountryCode();
        return playerService.getCountry(countryCode);
    }




}
