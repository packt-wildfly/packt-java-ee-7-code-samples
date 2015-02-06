package com.packtpub.wflydevelopment.chapter12.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SeatPositionConverter implements AttributeConverter<SeatPosition, String> {

    @Override
    public String convertToDatabaseColumn(SeatPosition attribute) {
        return attribute.getDatabaseRepresentation();
    }

    @Override
    public SeatPosition convertToEntityAttribute(String dbData) {
        for (SeatPosition seatPosition : SeatPosition.values()) {
            if (dbData.equals(seatPosition.getDatabaseRepresentation())) {
                return seatPosition;
            }
        }
        throw new IllegalArgumentException("Unknown attribute value " + dbData);
    }
}
