package com.jpa.model.dto;

import com.jpa.model.converter.InputConverter;
import com.jpa.model.entity.onetoone.IDCard;
import com.jpa.model.entity.onetoone.Person;
import lombok.Data;

/**
 * Person DTO
 * @author Dragon-zg
 * @date 2019/6/10 13:40
 **/
@Data
public class PersonDTO implements InputConverter<Person> {

    private String name;

    private String cardno;

    /**
     * Update a domain by dto.
     *
     * @param person updated domain
     */
    @Override
    public void convertTo(Person person) {
        InputConverter.super.convertTo(person);
        IDCard idCard = person.getIdcard();
        if (null == idCard) {
            idCard = new IDCard();
        }
        idCard.setCardno(this.cardno);
    }
}