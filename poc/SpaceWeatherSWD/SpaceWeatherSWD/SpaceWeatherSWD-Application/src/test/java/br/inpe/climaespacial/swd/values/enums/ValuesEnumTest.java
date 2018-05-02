package br.inpe.climaespacial.swd.values.enums;

import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BT;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BX;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BZ;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_DENSITY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_DPR;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_EY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_RMP;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_SPEED;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_TEMPERATURE;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BT;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BX;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BZ;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.DENSITY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.DPR;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.EY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.RMP;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.SPEED;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.TEMPERATURE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValuesEnumTest {
    
    @Test
    public void btTest() {
        ValuesEnum ve = BT; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageBtTest() {
        ValuesEnum ve = AVERAGE_BT; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void byTest() {
        ValuesEnum ve = BY; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageByTest() {
        ValuesEnum ve = AVERAGE_BY; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void bxTest() {
        ValuesEnum ve = BX; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageBxTest() {
        ValuesEnum ve = AVERAGE_BX; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void bzTest() {
        ValuesEnum ve = BZ; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageBzTest() {
        ValuesEnum ve = AVERAGE_BZ; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void densityTest() {
        ValuesEnum ve = DENSITY; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageDensityTest() {
        ValuesEnum ve = AVERAGE_DENSITY; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void speedTest() {
        ValuesEnum ve = SPEED; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageSpeedTest() {
        ValuesEnum ve = AVERAGE_SPEED; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void temperatureTest() {
        ValuesEnum ve = TEMPERATURE; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageTemperatureTest() {
        ValuesEnum ve = AVERAGE_TEMPERATURE; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void eyTest() {
        ValuesEnum ve = EY; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averageeyTest() {
        ValuesEnum ve = AVERAGE_EY; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void dprTest() {
        ValuesEnum ve = DPR; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averagedprTest() {
        ValuesEnum ve = AVERAGE_DPR; 
        assertEquals("Media", ve.getValor());
    }
    
    @Test
    public void rmpTest() {
        ValuesEnum ve = RMP; 
        assertEquals("Valor", ve.getValor());
    }
    
    @Test
    public void averagermpTest() {
        ValuesEnum ve = AVERAGE_RMP; 
        assertEquals("Media", ve.getValor());
    }

}
