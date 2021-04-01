package org.craftedsw.kirin.api.graphql.dgs.player;

import lombok.Value;
import lombok.experimental.Delegate;
import org.craftedsw.kirin.api.graphql.dgs.player.infra.InternalPlayer;
import org.craftedsw.kirin.api.graphql.schema.types.Player;

@Value
public class PlayerAdapted {

    private final String payerId;
    private final String countryCode;
    @Delegate
    private final Player player;

    public static PlayerAdapted fromInternal(InternalPlayer internalPlayer) {
        return new PlayerAdapted(
                internalPlayer.getIdentifier(),
                internalPlayer.getCountryCode(),
                new Player(
                        internalPlayer.getFirstName(),
                        internalPlayer.getLastName(),
                        internalPlayer.getGender(),
                        internalPlayer.getAddress(),
                        internalPlayer.getPhones(),
                        null
                )
        );
    }


}
