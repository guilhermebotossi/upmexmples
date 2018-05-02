package br.inpe.climaespacial.swd.kp.forecast.services;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultKPForecastCalculator.class})
public class KPForecastCalculatorTest {

    private static final List<List<Double>> SIT_0_IND_0_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(99.5, 0.5, 0.0, 99.4, 0.5, 0.0, 99.5, 0.6, 0.0), asList(97.7, 2.2, 0.1, 97.6, 2.0, 0.1, 98.0, 2.4, 0.1), asList(99.4, 0.5, 0.1, 99.1, 0.0, 0.0, 100.0, 0.9, 0.3));
    private static final List<List<Double>> SIT_0_IND_1_3h = asList(asList(93.5, 6.4, 0.1, 92.2, 5.2, 0.0, 94.7, 7.7, 0.2), asList(93.6, 6.3, 0.1, 92.2, 4.7, 0.0, 95.3, 7.8, 0.3), asList(94.7, 5.2, 0.1, 94.2, 4.7, 0.0, 95.2, 5.7, 0.1), asList(90.6, 9.2, 0.2, 89.7, 8.3, 0.1, 91.5, 10.1, 0.4), asList(91.6, 8.3, 0.1, 89.4, 6.0, 0.0, 94.0, 10.6, 0.4));
    private static final List<List<Double>> SIT_0_IND_2_3h = asList(asList(82.4, 17.4, 0.2, 79.6, 14.6, 0.0, 85.2, 20.2, 0.5), asList(85.7, 14.2, 0.1, 82.0, 10.5, 0.0, 89.5, 18.0, 0.4), asList(80.9, 18.9, 0.2, 78.7, 16.8, 0.0, 83.0, 21.1, 0.5), asList(78.4, 21.5, 0.1, 75.0, 18.0, 0.0, 82.0, 25.0, 0.4), asList(75.5, 23.6, 0.9, 68.6, 16.9, 0.0, 82.3, 30.4, 2.4));
    private static final List<List<Double>> SIT_0_IND_3_3h = asList(asList(60.0, 39.2, 0.8, 52.6, 31.8, 0.0, 67.4, 46.6, 2.2), asList(58.9, 41.0, 0.1, 45.7, 27.7, 0.0, 72.3, 54.3, 1.0), asList(61.6, 38.3, 0.1, 53.9, 30.5, 0.0, 69.5, 46.1, 0.6), asList(58.2, 41.7, 0.1, 32.8, 16.1, 0.0, 83.9, 67.2, 1.7), asList(79.9, 20.0, 0.1, 56.8, 0.0, 0.0, 100.0, 43.2, 1.9));
    private static final List<List<Double>> SIT_0_IND_4_3h = asList(asList(23.1, 69.2, 7.7, 2.3, 46.4, 0.0, 43.9, 92.0, 20.9), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_0_IND_5_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_0_IND_0_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(99.0, 0.9, 0.1, 99.0, 0.8, 0.1, 99.1, 0.9, 0.1), asList(96.7, 3.2, 0.1, 96.4, 3.0, 0.1, 96.9, 3.5, 0.2), asList(98.5, 1.4, 0.1, 97.9, 0.6, 0.0, 99.4, 2.1, 0.3));
    private static final List<List<Double>> SIT_0_IND_1_6h = asList(asList(92.8, 6.7, 0.5, 91.5, 5.5, 0.1, 94.1, 8.0, 0.8), asList(93.5, 6.3, 0.2, 92.0, 4.7, 0.0, 95.2, 7.8, 0.4), asList(93.7, 6.1, 0.2, 93.2, 5.5, 0.1, 94.3, 6.6, 0.3), asList(88.6, 11.1, 0.3, 87.7, 10.1, 0.1, 89.6, 12.0, 0.5), asList(90.7, 9.0, 0.3, 88.3, 6.7, 0.0, 93.1, 11.4, 0.7));
    private static final List<List<Double>> SIT_0_IND_2_6h = asList(asList(82.4, 16.8, 0.8, 79.6, 14.0, 0.1, 85.2, 19.5, 1.5), asList(82.4, 16.3, 1.3, 78.4, 12.4, 0.1, 86.5, 20.3, 2.4), asList(81.9, 16.9, 1.2, 79.7, 14.9, 0.6, 84.0, 19.0, 1.8), asList(79.3, 19.1, 1.6, 75.8, 15.8, 0.5, 82.7, 22.5, 2.7), asList(77.3, 19.1, 3.6, 70.6, 12.9, 0.7, 83.9, 25.3, 6.6));
    private static final List<List<Double>> SIT_0_IND_3_6h = asList(asList(69.2, 28.3, 2.5, 62.2, 21.5, 0.1, 76.2, 35.2, 4.9), asList(76.9, 20.5, 2.6, 65.5, 9.6, 0.0, 88.3, 31.4, 6.8), asList(64.5, 32.7, 2.8, 56.8, 25.2, 0.2, 72.2, 40.2, 5.5), asList(75.0, 16.7, 8.3, 52.6, 0.0, 0.0, 97.4, 36.0, 22.7), asList(89.9, 0.1, 10.0, 72.6, 0.0, 0.0, 100.0, 1.9, 27.4));
    private static final List<List<Double>> SIT_0_IND_4_6h = asList(asList(53.8, 38.5, 7.7, 29.2, 14.4, 0.0, 78.5, 62.5, 20.9), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_0_IND_5_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_1_IND_0_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(66.3, 30.3, 3.4, 62.1, 26.2, 1.8, 70.5, 34.3, 5.0), asList(66.6, 32.1, 1.3, 63.9, 29.4, 0.6, 69.4, 34.8, 1.9), asList(84.6, 7.7, 7.7, 66.8, 0.0, 0.0, 100.0, 20.9, 20.9));
    private static final List<List<Double>> SIT_1_IND_1_3h = asList(asList(39.5, 52.6, 7.9, 26.1, 39.0, 0.5, 52.9, 66.3, 15.3), asList(51.6, 48.3, 0.1, 35.9, 32.5, 0.0, 67.5, 64.1, 1.1), asList(55.2, 43.9, 0.9, 50.8, 39.4, 0.0, 59.7, 48.3, 1.7), asList(55.4, 42.3, 2.3, 52.6, 39.6, 1.5, 58.1, 45.1, 3.1), asList(53.0, 40.9, 6.1, 42.8, 30.8, 1.2, 63.3, 51.0, 11.0));
    private static final List<List<Double>> SIT_1_IND_2_3h = asList(asList(36.9, 56.0, 7.1, 28.1, 46.9, 2.5, 45.7, 65.0, 11.8), asList(48.0, 46.2, 5.8, 36.5, 34.6, 0.4, 59.7, 57.7, 11.2), asList(33.9, 57.5, 8.6, 29.3, 52.6, 5.8, 38.6, 62.4, 11.3), asList(46.8, 47.0, 6.2, 42.4, 42.6, 4.1, 51.1, 51.4, 8.4), asList(44.0, 46.0, 10.0, 32.2, 34.2, 2.9, 55.8, 57.8, 17.1));
    private static final List<List<Double>> SIT_1_IND_3_3h = asList(asList(27.9, 53.8, 18.3, 20.2, 45.2, 11.6, 35.7, 62.4, 24.9), asList(17.6, 60.8, 21.6, 8.7, 49.3, 11.9, 26.6, 72.2, 31.2), asList(22.1, 54.2, 23.7, 15.7, 46.6, 17.2, 28.4, 61.8, 30.2), asList(16.2, 67.6, 16.2, 9.1, 58.5, 9.1, 23.4, 76.6, 23.4), asList(10.5, 63.2, 26.3, 0.0, 44.0, 8.8, 22.7, 82.3, 43.8));
    private static final List<List<Double>> SIT_1_IND_4_3h = asList(asList(2.4, 65.9, 31.7, 0.0, 53.4, 19.5, 6.5, 78.3, 43.9), asList(0.1, 42.8, 57.1, 0.0, 19.4, 33.7, 1.6, 66.3, 80.6), asList(12.4, 43.8, 43.8, 0.0, 22.0, 22.0, 27.0, 65.5, 65.5), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_1_IND_5_3h = asList(asList(0.1, 33.2, 66.7, 0.0, 0.0, 27.9, 2.7, 72.1, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_1_IND_0_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(75.5, 21.1, 3.4, 71.6, 17.5, 1.8, 79.2, 24.7, 5.0), asList(79.5, 19.5, 1.0, 77.1, 17.2, 0.4, 81.8, 21.8, 1.6), asList(76.8, 23.1, 0.1, 56.1, 2.3, 0.0, 97.7, 43.9, 1.7));
    private static final List<List<Double>> SIT_1_IND_1_6h = asList(asList(50.0, 44.7, 5.3, 36.3, 31.1, 0.0, 63.7, 58.3, 11.4), asList(65.6, 31.0, 3.4, 50.5, 16.4, 0.0, 80.5, 45.6, 9.2), asList(70.1, 28.4, 1.5, 66.1, 24.3, 0.4, 74.3, 32.4, 2.5), asList(66.6, 29.6, 3.8, 63.9, 27.1, 2.7, 69.2, 32.2, 4.9), asList(60.7, 34.8, 4.5, 50.6, 25.1, 0.3, 70.6, 44.6, 8.8));
    private static final List<List<Double>> SIT_1_IND_2_6h = asList(asList(52.3, 41.7, 6.0, 43.3, 32.7, 1.7, 61.4, 50.6, 10.2), asList(50.0, 44.2, 5.8, 38.4, 32.7, 0.4, 61.6, 55.8, 11.2), asList(46.1, 45.0, 8.9, 41.2, 40.1, 6.1, 51.0, 49.9, 11.7), asList(60.4, 31.7, 7.9, 56.0, 27.6, 5.6, 64.6, 35.8, 10.3), asList(58.0, 34.0, 8.0, 46.3, 22.8, 1.6, 69.7, 45.2, 14.4));
    private static final List<List<Double>> SIT_1_IND_3_6h = asList(asList(46.2, 38.7, 15.1, 37.6, 30.3, 8.9, 54.8, 47.1, 21.2), asList(43.1, 45.1, 11.8, 31.5, 33.4, 4.2, 54.8, 56.8, 19.3), asList(31.4, 51.7, 16.9, 24.3, 44.1, 11.2, 38.4, 59.3, 22.7), asList(48.7, 35.1, 16.2, 39.0, 25.9, 9.1, 58.3, 44.4, 23.4), asList(52.7, 36.8, 10.5, 32.8, 17.7, 0.0, 72.5, 56.0, 22.7));
    private static final List<List<Double>> SIT_1_IND_4_6h = asList(asList(26.8, 48.8, 24.4, 15.2, 35.6, 13.1, 38.5, 61.9, 35.7), asList(14.3, 50.0, 35.7, 0.0, 26.3, 13.0, 30.8, 73.7, 58.4), asList(18.7, 56.3, 25.0, 1.6, 34.5, 6.0, 35.9, 78.0, 44.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_1_IND_5_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 72.1, 72.1, 72.1), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_2_IND_0_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(0.1, 83.2, 16.7, 0.0, 52.7, 0.0, 2.7, 100.0, 47.3), asList(13.8, 58.6, 27.6, 2.9, 43.1, 13.5, 24.7, 74.2, 41.7), asList(33.2, 66.7, 0.1, 0.0, 0.0, 0.0, 100.0, 100.0, 5.4));
    private static final List<List<Double>> SIT_2_IND_1_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(0.1, 99.8, 0.1, 0.0, 94.5, 0.0, 3.8, 100.0, 3.8), asList(13.6, 75.0, 11.4, 4.9, 64.0, 3.3, 22.3, 86.0, 19.4), asList(0.1, 33.2, 66.7, 0.0, 0.0, 0.0, 5.4, 100.0, 100.0));
    private static final List<List<Double>> SIT_2_IND_2_3h = asList(asList(0.1, 33.2, 66.7, 0.0, 0.0, 0.0, 5.4, 100.0, 100.0), asList(0.1, 24.9, 75.0, 0.0, 0.0, 24.0, 3.8, 76.0, 100.0), asList(13.0, 43.5, 43.5, 1.0, 25.7, 25.7, 25.1, 61.2, 61.2), asList(12.4, 46.4, 41.2, 6.8, 38.0, 32.9, 17.9, 54.8, 49.5), asList(0.1, 99.8, 0.1, 0.0, 96.1, 0.0, 2.7, 100.0, 2.7));
    private static final List<List<Double>> SIT_2_IND_3_3h = asList(asList(0.1, 55.5, 44.4, 0.0, 24.8, 13.6, 2.1, 86.4, 75.2), asList(0.1, 33.2, 66.7, 0.0, 0.0, 27.9, 2.7, 72.1, 100.0), asList(2.5, 39.0, 58.5, 0.0, 26.2, 45.6, 6.5, 51.9, 71.5), asList(5.1, 42.3, 52.6, 1.0, 33.0, 43.2, 9.3, 51.6, 62.0), asList(0.1, 29.9, 70.0, 0.0, 3.4, 43.4, 1.9, 56.6, 96.6));
    private static final List<List<Double>> SIT_2_IND_4_3h = asList(asList(0.1, 41.6, 58.3, 0.0, 16.1, 32.8, 1.7, 67.2, 83.9), asList(0.1, 19.9, 80.0, 0.0, 0.0, 41.9, 3.1, 58.1, 100.0), asList(0.1, 21.8, 78.1, 0.0, 9.5, 65.7, 1.0, 34.3, 90.5), asList(0.1, 40.8, 59.1, 0.0, 22.9, 41.1, 1.3, 58.9, 77.1), asList(0.1, 0.1, 99.8, 0.0, 0.0, 96.1, 2.7, 2.7, 100.0));
    private static final List<List<Double>> SIT_2_IND_5_3h = asList(asList(0.1, 26.6, 73.3, 0.0, 6.6, 53.2, 1.5, 46.8, 93.4), asList(0.1, 12.4, 87.5, 0.0, 0.0, 65.3, 2.2, 34.7, 100.0), asList(0.1, 0.1, 99.8, 0.0, 0.0, 94.5, 3.8, 3.8, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_2_IND_0_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.3, 50.0, 16.7, 0.0, 8.9, 0.0, 72.1, 91.1, 47.3), asList(41.4, 44.8, 13.8, 25.8, 29.1, 2.9, 56.9, 60.5, 24.7), asList(66.6, 33.3, 0.1, 0.0, 0.0, 0.0, 100.0, 100.0, 5.4));
    private static final List<List<Double>> SIT_2_IND_1_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(0.1, 99.8, 0.1, 0.0, 94.5, 0.0, 3.8, 100.0, 3.8), asList(36.4, 54.5, 9.1, 24.2, 41.9, 1.8, 48.6, 67.2, 16.4), asList(66.6, 33.3, 0.1, 0.0, 0.0, 0.0, 100.0, 100.0, 5.4));
    private static final List<List<Double>> SIT_2_IND_2_6h = asList(asList(33.2, 0.1, 66.7, 0.0, 0.0, 0.0, 100.0, 5.4, 100.0), asList(0.1, 0.1, 99.8, 0.0, 0.0, 94.5, 3.8, 3.8, 100.0), asList(52.2, 13.0, 34.8, 34.3, 1.0, 17.7, 70.1, 25.1, 51.8), asList(28.9, 50.5, 20.6, 21.2, 42.1, 13.8, 36.5, 58.9, 27.4), asList(49.9, 50.0, 0.1, 8.9, 8.9, 0.0, 91.1, 91.1, 2.7));
    private static final List<List<Double>> SIT_2_IND_3_6h = asList(asList(22.2, 22.2, 55.6, 0.0, 0.0, 24.8, 48.0, 48.0, 86.4), asList(33.3, 16.7, 50.0, 0.0, 0.0, 8.9, 72.1, 47.3, 91.1), asList(14.6, 53.7, 31.7, 5.3, 40.5, 19.5, 23.9, 66.8, 43.9), asList(24.4, 41.0, 34.6, 16.3, 31.8, 25.6, 32.5, 50.3, 43.6), asList(20.0, 50.0, 30.0, 0.0, 21.0, 3.4, 43.2, 79.0, 56.6));
    private static final List<List<Double>> SIT_2_IND_4_6h = asList(asList(25.0, 33.3, 41.7, 2.6, 8.9, 16.1, 47.4, 57.8, 67.2), asList(20.0, 40.0, 40.0, 0.0, 0.0, 0.0, 58.1, 86.7, 86.7), asList(21.8, 34.4, 43.8, 9.5, 20.1, 28.9, 34.3, 48.6, 58.6), asList(13.6, 50.0, 36.4, 1.0, 31.7, 18.7, 26.2, 68.3, 54.0), asList(16.6, 0.1, 83.3, 0.0, 0.0, 52.7, 47.3, 2.7, 100.0));
    private static final List<List<Double>> SIT_2_IND_5_6h = asList(asList(13.3, 40.0, 46.7, 0.0, 17.7, 24.0, 28.8, 62.3, 69.4), asList(12.4, 87.5, 0.1, 0.0, 65.3, 0.0, 34.7, 100.0, 2.2), asList(24.9, 75.0, 0.1, 0.0, 24.0, 0.0, 76.0, 100.0, 3.8), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    
    private static final Double ARRAY_VALUE_0 = 0.42; 
    private static final Double ARRAY_VALUE_1 = 6.05;

    private static final Long KP_SITUATION_0 = 4L;
    private static final Long KP_SITUATION_1 = 6L;
    private static final Long KP_SITUATION_2 = 8L;

    private static final Double LEVEL_0 = 0.53;
    private static final Double LEVEL_1 = 1.25;
    private static final Double LEVEL_2 = 2.32;
    private static final Double LEVEL_3 = 3.42;
    private static final Double LEVEL_4 = 4.12;
    private static final Double LEVEL_5 = 5.5;

    private static final int CASE_0 = 0;
    private static final int CASE_1 = 1;
    private static final int CASE_2 = 2;
    private static final int CASE_3 = 3;
    private static final int CASE_4 = 4;

    private static final double DELTA = 0.001;

    @Inject
    private KPForecastCalculator kpForecastCalculator;

    @Test
    public void calculate_calledWithNullCIndexList_throws() {
        RuntimeException re = null;
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt1);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.UP);

        try {
            kpForecastCalculator.calculate(null, kpv);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"cIndexList\" cannot be null.", re.getMessage());
    }

    @Test
    public void calculate_calledWithEmptyCIndexList_throws() {
        RuntimeException re = null;
        List<CIndex> cil = new ArrayList<>();
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt1);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.UP);

        try {
            kpForecastCalculator.calculate(cil, kpv);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"cIndexList\" cannot be empty.", re.getMessage());
    }

    @Test
    public void calculate_calledWithOnly2ItemsValid_throws() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt5 = ZonedDateTime.parse("2017-01-01T04:00:00z[UTC]");
        ZonedDateTime zdt6 = ZonedDateTime.parse("2017-01-01T05:00:00z[UTC]");
        ZonedDateTime zdt7 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");
        ZonedDateTime zdt8 = ZonedDateTime.parse("2017-01-01T07:00:00z[UTC]");
        ZonedDateTime zdt9 = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        CIndex ci1 = new CIndex();
        ci1.setPostValue(ARRAY_VALUE_0);
        ci1.setPreValue(ARRAY_VALUE_0);
        ci1.setTimeTag(zdt1);

        CIndex ci2 = new CIndex();
        ci2.setPostValue(ARRAY_VALUE_1);
        ci2.setPreValue(ARRAY_VALUE_1);
        ci2.setTimeTag(zdt2);

        CIndex ci3 = new CIndex();
        ci3.setPostValue(null);
        ci3.setPreValue(null);
        ci3.setTimeTag(zdt3);

        CIndex ci4 = new CIndex();
        ci4.setPostValue(null);
        ci4.setPreValue(null);
        ci4.setTimeTag(zdt4);

        CIndex ci5 = new CIndex();
        ci5.setPostValue(null);
        ci5.setPreValue(null);
        ci5.setTimeTag(zdt5);

        CIndex ci6 = new CIndex();
        ci6.setPostValue(null);
        ci6.setPreValue(null);
        ci6.setTimeTag(zdt6);

        CIndex ci7 = new CIndex();
        ci7.setPostValue(null);
        ci7.setPreValue(null);
        ci7.setTimeTag(zdt7);

        CIndex ci8 = new CIndex();
        ci8.setPostValue(null);
        ci8.setPreValue(null);
        ci8.setTimeTag(zdt8);

        CIndex ci9 = new CIndex();
        ci9.setPostValue(null);
        ci9.setPreValue(null);
        ci9.setTimeTag(zdt9);

        List<CIndex> cil = asList(ci1, ci2, ci3, ci4, ci5, ci6, ci7, ci8, ci9);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt1);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.UP);
        
        
        List<KPForecast> kpfl = kpForecastCalculator.calculate(cil, kpv);
        
        assertNotNull(kpfl);
        assertThat(kpfl, is(not(empty())));
        KPForecast kpf3h = kpfl.get(0);
        assertNotNull(kpf3h);
        assertEquals(zdt9, kpf3h.getIndexesTimeTag());
        assertEquals(zdt9.plusHours(3), kpf3h.getPredictedTimeTag());
        assertNull(kpf3h.getInferiorLimit1());
        assertNull(kpf3h.getInferiorLimit2());
        assertNull(kpf3h.getInferiorLimit3());
        assertNull(kpf3h.getUpperLimit1());
        assertNull(kpf3h.getUpperLimit2());
        assertNull(kpf3h.getUpperLimit3());
        assertNull(kpf3h.getProbability1());
        assertNull(kpf3h.getProbability2());
        assertNull(kpf3h.getProbability3());
        
        KPForecast kpf6h = kpfl.get(1);
        assertNotNull(kpf6h);
        assertEquals(zdt9, kpf6h.getIndexesTimeTag());
        assertEquals(zdt9.plusHours(6), kpf6h.getPredictedTimeTag());
        assertNull(kpf6h.getInferiorLimit1());
        assertNull(kpf6h.getInferiorLimit2());
        assertNull(kpf6h.getInferiorLimit3());
        assertNull(kpf6h.getUpperLimit1());
        assertNull(kpf6h.getUpperLimit2());
        assertNull(kpf6h.getUpperLimit3());
        assertNull(kpf6h.getProbability1());
        assertNull(kpf6h.getProbability2());
        assertNull(kpf6h.getProbability3());
        
    }

    @Test
    public void calculate_calledWithOnly1ItemsValid_throws() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt5 = ZonedDateTime.parse("2017-01-01T04:00:00z[UTC]");
        ZonedDateTime zdt6 = ZonedDateTime.parse("2017-01-01T05:00:00z[UTC]");
        ZonedDateTime zdt7 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");
        ZonedDateTime zdt8 = ZonedDateTime.parse("2017-01-01T07:00:00z[UTC]");
        ZonedDateTime zdt9 = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        CIndex ci1 = new CIndex();
        ci1.setPostValue(ARRAY_VALUE_0);
        ci1.setPreValue(ARRAY_VALUE_0);
        ci1.setTimeTag(zdt1);

        CIndex ci2 = new CIndex();
        ci2.setPostValue(null);
        ci2.setPreValue(null);
        ci2.setTimeTag(zdt2);

        CIndex ci3 = new CIndex();
        ci3.setPostValue(null);
        ci3.setPreValue(null);
        ci3.setTimeTag(zdt3);

        CIndex ci4 = new CIndex();
        ci4.setPostValue(null);
        ci4.setPreValue(null);
        ci4.setTimeTag(zdt4);

        CIndex ci5 = new CIndex();
        ci5.setPostValue(null);
        ci5.setPreValue(null);
        ci5.setTimeTag(zdt5);

        CIndex ci6 = new CIndex();
        ci6.setPostValue(null);
        ci6.setPreValue(null);
        ci6.setTimeTag(zdt6);

        CIndex ci7 = new CIndex();
        ci7.setPostValue(null);
        ci7.setPreValue(null);
        ci7.setTimeTag(zdt7);

        CIndex ci8 = new CIndex();
        ci8.setPostValue(null);
        ci8.setPreValue(null);
        ci8.setTimeTag(zdt8);

        CIndex ci9 = new CIndex();
        ci9.setPostValue(null);
        ci9.setPreValue(null);
        ci9.setTimeTag(zdt9);

        List<CIndex> cil = asList(ci1, ci2, ci3, ci4, ci5, ci6, ci7, ci8, ci9);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt1);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.UP);

        List<KPForecast> kpfl = kpForecastCalculator.calculate(cil, kpv);
            
        assertNotNull(kpfl);
        assertThat(kpfl, is(not(empty())));
        KPForecast kpf3h = kpfl.get(0);
        assertNotNull(kpf3h);
        assertEquals(zdt9, kpf3h.getIndexesTimeTag());
        assertEquals(zdt9.plusHours(3), kpf3h.getPredictedTimeTag());
        assertNull(kpf3h.getInferiorLimit1());
        assertNull(kpf3h.getInferiorLimit2());
        assertNull(kpf3h.getInferiorLimit3());
        assertNull(kpf3h.getUpperLimit1());
        assertNull(kpf3h.getUpperLimit2());
        assertNull(kpf3h.getUpperLimit3());
        assertNull(kpf3h.getProbability1());
        assertNull(kpf3h.getProbability2());
        assertNull(kpf3h.getProbability3());
        
        KPForecast kpf6h = kpfl.get(1);
        assertNotNull(kpf6h);
        assertEquals(zdt9, kpf6h.getIndexesTimeTag());
        assertEquals(zdt9.plusHours(6), kpf6h.getPredictedTimeTag());
        assertNull(kpf6h.getInferiorLimit1());
        assertNull(kpf6h.getInferiorLimit2());
        assertNull(kpf6h.getInferiorLimit3());
        assertNull(kpf6h.getUpperLimit1());
        assertNull(kpf6h.getUpperLimit2());
        assertNull(kpf6h.getUpperLimit3());
        assertNull(kpf6h.getProbability1());
        assertNull(kpf6h.getProbability2());
        assertNull(kpf6h.getProbability3());
    }

    @Test
    public void calculate_calledWithMostRecentIn4AndLessRecentIn2_returns() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt5 = ZonedDateTime.parse("2017-01-01T04:00:00z[UTC]");
        ZonedDateTime zdt6 = ZonedDateTime.parse("2017-01-01T05:00:00z[UTC]");
        ZonedDateTime zdt7 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");
        ZonedDateTime zdt8 = ZonedDateTime.parse("2017-01-01T07:00:00z[UTC]");
        ZonedDateTime zdt9 = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        CIndex ci1 = new CIndex();
        ci1.setPostValue(ARRAY_VALUE_0);
        ci1.setPreValue(ARRAY_VALUE_0);
        ci1.setTimeTag(zdt1);

        CIndex ci2 = new CIndex();
        ci2.setPostValue(ARRAY_VALUE_0);
        ci2.setPreValue(ARRAY_VALUE_0);
        ci2.setTimeTag(zdt2);

        CIndex ci3 = new CIndex();
        ci3.setPostValue(null);
        ci3.setPreValue(null);
        ci3.setTimeTag(zdt3);

        CIndex ci4 = new CIndex();
        ci4.setPostValue(LEVEL_0);
        ci4.setPreValue(LEVEL_0);
        ci4.setTimeTag(zdt4);

        CIndex ci5 = new CIndex();
        ci5.setPostValue(null);
        ci5.setPreValue(null);
        ci5.setTimeTag(zdt5);

        CIndex ci6 = new CIndex();
        ci6.setPostValue(null);
        ci6.setPreValue(null);
        ci6.setTimeTag(zdt6);

        CIndex ci7 = new CIndex();
        ci7.setPostValue(null);
        ci7.setPreValue(null);
        ci7.setTimeTag(zdt7);

        CIndex ci8 = new CIndex();
        ci8.setPostValue(null);
        ci8.setPreValue(null);
        ci8.setTimeTag(zdt8);

        CIndex ci9 = new CIndex();
        ci9.setPostValue(null);
        ci9.setPreValue(null);
        ci9.setTimeTag(zdt9);

        List<CIndex> cil = asList(ci1, ci2, ci3, ci4, ci5, ci6, ci7, ci8, ci9);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt9);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt9, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt9.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt9, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt9.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_calledWithMostRecentEqualLessRecent_returnCase4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_0, LEVEL_0, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    private List<CIndex> createCIndexList(Double value, Double level, int arrayType) {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt5 = ZonedDateTime.parse("2017-01-01T04:00:00z[UTC]");
        ZonedDateTime zdt6 = ZonedDateTime.parse("2017-01-01T05:00:00z[UTC]");
        ZonedDateTime zdt7 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");
        ZonedDateTime zdt8 = ZonedDateTime.parse("2017-01-01T07:00:00z[UTC]");
        ZonedDateTime zdt9 = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        List<ZonedDateTime> zdtl = asList(zdt1, zdt2, zdt3, zdt4, zdt5, zdt6, zdt7, zdt8, zdt9);

        List<CIndex> cil = new ArrayList<>();
        int interval = 0;

        switch (arrayType) {
            case 0:
                interval = 1;
                break;
            case 1:
                interval = 2;
                break;
            case 2:
                interval = 1;
        }

        for (int i = 0; i < zdtl.size() - interval; i++) {
            CIndex ci = new CIndex();
            ci.setPostValue(value);
            ci.setPreValue(value);
            ci.setTimeTag(zdtl.get(i));

            cil.add(ci);
        }

        for (int i = zdtl.size() - interval; i < zdtl.size(); i++) {
            CIndex ci = new CIndex();
            ci.setPostValue(level);
            ci.setPreValue(level);
            ci.setTimeTag(zdtl.get(i));

            cil.add(ci);
        }

        if (arrayType == 2) {
            cil.get(3).setPostValue(999.99);
        }

        return cil;
    }

    @Test
    public void calculate_called_returnsSituation0Level0Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level1Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level2Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));

        assertThat(kpl, hasSize(2));
        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level3Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level4Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level5Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level0Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level1Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level2Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level3Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level4Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level5Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_5, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level0Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_0, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level1Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_1, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level2Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_2, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level3Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_3, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level4Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_4, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level5Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_5, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level0Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level1Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level2Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level3Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level4Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level5Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level0Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_0_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level1Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_1_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level2Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_2_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level3Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_3_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level4Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_4, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_4_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation0Level5Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_5, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_0);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_0_IND_5_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level0Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level1Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level2Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level3Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level4Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level5Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level0Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level1Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level2Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level3Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level4Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level5Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_5, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level0Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_0, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level1Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_1, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level2Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_2, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level3Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_3, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level4Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_4, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level5Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_5, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level0Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level1Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level2Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level3Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level4Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level5Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level0Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_0_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level1Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_1_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level2Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_2_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level3Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_3_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level4Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_4, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_4_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation1Level5Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_5, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_1);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_1_IND_5_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level0Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level1Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level2Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level3Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level4Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level5Case0() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_0).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_0).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level0Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level1Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level2Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level3Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level4Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level5Case1() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_5, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_1).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_1).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level0Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_0, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level1Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_1, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level2Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_2, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level3Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_3, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level4Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_4, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level5Case2() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(LEVEL_5, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_2).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_2).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level0Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_0, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level1Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_1, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level2Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_2, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level3Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_3, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level4Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_4, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level5Case3() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_5, 0);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_3).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_3).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level0Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_0, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_0_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level1Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_1, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_1_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level2Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_2, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_2_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level3Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_3, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_3_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level4Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_0, LEVEL_4, 2);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_4_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }

    @Test
    public void calculate_called_returnsSituation2Level5Case4() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");
        List<CIndex> cil = createCIndexList(ARRAY_VALUE_1, LEVEL_5, 1);

        KPValue kpv = new KPValue();
        kpv.setTimeTag(zdt);
        kpv.setKPValue(KP_SITUATION_2);
        kpv.setKPValueFlag(KPValueFlag.DOWN);

        List<KPForecast> kpl = kpForecastCalculator.calculate(cil, kpv);

        assertNotNull(kpl);
        assertThat(kpl, is(not(empty())));
        assertThat(kpl, hasSize(2));

        assertEquals(zdt, kpl.get(0).getIndexesTimeTag());
        assertEquals(zdt.plusHours(3), kpl.get(0).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(0), kpl.get(0).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(1), kpl.get(0).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(2), kpl.get(0).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(3), kpl.get(0).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(4), kpl.get(0).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(5), kpl.get(0).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(6), kpl.get(0).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(7), kpl.get(0).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_3h.get(CASE_4).get(8), kpl.get(0).getUpperLimit3(), DELTA);

        assertEquals(zdt, kpl.get(1).getIndexesTimeTag());
        assertEquals(zdt.plusHours(6), kpl.get(1).getPredictedTimeTag());
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(0), kpl.get(1).getProbability1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(1), kpl.get(1).getProbability2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(2), kpl.get(1).getProbability3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(3), kpl.get(1).getInferiorLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(4), kpl.get(1).getInferiorLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(5), kpl.get(1).getInferiorLimit3(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(6), kpl.get(1).getUpperLimit1(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(7), kpl.get(1).getUpperLimit2(), DELTA);
        assertEquals(SIT_2_IND_5_6h.get(CASE_4).get(8), kpl.get(1).getUpperLimit3(), DELTA);
    }
}

