package br.com.zup.mercadolivre.controller.response;

import br.com.zup.mercadolivre.model.Characteristic;

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
}
