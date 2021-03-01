package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Characteristic;

import java.util.Set;
import java.util.stream.Collectors;

public class CharacteristicResponse {
    private String name;
    private String value;

    public CharacteristicResponse(Characteristic characteristic) {
        this.name = characteristic.getName();
        this.value = characteristic.getValue();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Set<CharacteristicResponse> mapSet(Set<Characteristic> characteristics) {
        return characteristics.stream().map(CharacteristicResponse::new).collect(Collectors.toSet());
    }

}
