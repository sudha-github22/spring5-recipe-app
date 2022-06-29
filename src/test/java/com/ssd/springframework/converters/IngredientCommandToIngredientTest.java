package com.ssd.springframework.converters;

import com.ssd.springframework.commands.IngredientCommand;
import com.ssd.springframework.commands.UnitOfMeasureCommand;
import com.ssd.springframework.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    public static final Long LONG_ID = new Long(1L);
    public static final String DESCRIPTION = "cheese burger";
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final Long UOM_ID = new Long(2L);

    IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void testConvertNullUom() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(LONG_ID);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);
        command.setUom(null);

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNull(ingredient.getUom());
        assertEquals(LONG_ID,ingredient.getId());
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
    }

    @Test
    public void testConvertWithUom() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(LONG_ID);
        command.setDescription(DESCRIPTION);
        command.setAmount(AMOUNT);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUom(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(LONG_ID,ingredient.getId());
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(AMOUNT,ingredient.getAmount());
        assertEquals(UOM_ID,ingredient.getUom().getId());
    }
}