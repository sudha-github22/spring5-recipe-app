package com.ssd.springframework.converters;

import com.ssd.springframework.commands.UnitOfMeasureCommand;
import com.ssd.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION ="description";
    public static final Long LONG_ID = new Long(1L);

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
       UnitOfMeasureCommand source = new UnitOfMeasureCommand();
       source.setId(LONG_ID);
       source.setDescription(DESCRIPTION);

       //when
        UnitOfMeasure uom = converter.convert(source);

        //then
        assertNotNull(uom);
        assertEquals(LONG_ID,uom.getId());
        assertEquals(DESCRIPTION,uom.getDescription());
    }
}