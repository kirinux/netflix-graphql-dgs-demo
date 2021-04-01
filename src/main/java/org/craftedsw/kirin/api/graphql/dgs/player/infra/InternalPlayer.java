package org.craftedsw.kirin.api.graphql.dgs.player.infra;

import lombok.Value;
import org.craftedsw.kirin.api.graphql.schema.types.*;

import java.util.List;

@Value
public class InternalPlayer {
    private String identifier;
    private String firstName;
    private String lastName;
    private List<Phone> phones;
    private String countryCode;
    private Gender gender;
    private List<Address> address;
}
