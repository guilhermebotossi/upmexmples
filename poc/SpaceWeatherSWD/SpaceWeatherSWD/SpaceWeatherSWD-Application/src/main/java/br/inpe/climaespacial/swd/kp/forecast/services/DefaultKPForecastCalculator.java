package br.inpe.climaespacial.swd.kp.forecast.services;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;
import static java.util.Arrays.asList;

import java.util.List;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.CIndexReference;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;

@Dependent
public class DefaultKPForecastCalculator implements KPForecastCalculator {

    private static final List<List<Double>> SIT_0_LEV_0_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(99.5, 0.5, 0.0, 99.4, 0.5, 0.0, 99.5, 0.6, 0.0), asList(97.7, 2.2, 0.1, 97.6, 2.0, 0.1, 98.0, 2.4, 0.1), asList(99.4, 0.5, 0.1, 99.1, 0.0, 0.0, 100.0, 0.9, 0.3));
    private static final List<List<Double>> SIT_0_LEV_1_3h = asList(asList(93.5, 6.4, 0.1, 92.2, 5.2, 0.0, 94.7, 7.7, 0.2), asList(93.6, 6.3, 0.1, 92.2, 4.7, 0.0, 95.3, 7.8, 0.3), asList(94.7, 5.2, 0.1, 94.2, 4.7, 0.0, 95.2, 5.7, 0.1), asList(90.6, 9.2, 0.2, 89.7, 8.3, 0.1, 91.5, 10.1, 0.4), asList(91.6, 8.3, 0.1, 89.4, 6.0, 0.0, 94.0, 10.6, 0.4));
    private static final List<List<Double>> SIT_0_LEV_2_3h = asList(asList(82.4, 17.4, 0.2, 79.6, 14.6, 0.0, 85.2, 20.2, 0.5), asList(85.7, 14.2, 0.1, 82.0, 10.5, 0.0, 89.5, 18.0, 0.4), asList(80.9, 18.9, 0.2, 78.7, 16.8, 0.0, 83.0, 21.1, 0.5), asList(78.4, 21.5, 0.1, 75.0, 18.0, 0.0, 82.0, 25.0, 0.4), asList(75.5, 23.6, 0.9, 68.6, 16.9, 0.0, 82.3, 30.4, 2.4));
    private static final List<List<Double>> SIT_0_LEV_3_3h = asList(asList(60.0, 39.2, 0.8, 52.6, 31.8, 0.0, 67.4, 46.6, 2.2), asList(58.9, 41.0, 0.1, 45.7, 27.7, 0.0, 72.3, 54.3, 1.0), asList(61.6, 38.3, 0.1, 53.9, 30.5, 0.0, 69.5, 46.1, 0.6), asList(58.2, 41.7, 0.1, 32.8, 16.1, 0.0, 83.9, 67.2, 1.7), asList(79.9, 20.0, 0.1, 56.8, 0.0, 0.0, 100.0, 43.2, 1.9));
    private static final List<List<Double>> SIT_0_LEV_4_3h = asList(asList(23.1, 69.2, 7.7, 2.3, 46.4, 0.0, 43.9, 92.0, 20.9), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_0_LEV_5_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_0_LEV_0_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(99.0, 0.9, 0.1, 99.0, 0.8, 0.1, 99.1, 0.9, 0.1), asList(96.7, 3.2, 0.1, 96.4, 3.0, 0.1, 96.9, 3.5, 0.2), asList(98.5, 1.4, 0.1, 97.9, 0.6, 0.0, 99.4, 2.1, 0.3));
    private static final List<List<Double>> SIT_0_LEV_1_6h = asList(asList(92.8, 6.7, 0.5, 91.5, 5.5, 0.1, 94.1, 8.0, 0.8), asList(93.5, 6.3, 0.2, 92.0, 4.7, 0.0, 95.2, 7.8, 0.4), asList(93.7, 6.1, 0.2, 93.2, 5.5, 0.1, 94.3, 6.6, 0.3), asList(88.6, 11.1, 0.3, 87.7, 10.1, 0.1, 89.6, 12.0, 0.5), asList(90.7, 9.0, 0.3, 88.3, 6.7, 0.0, 93.1, 11.4, 0.7));
    private static final List<List<Double>> SIT_0_LEV_2_6h = asList(asList(82.4, 16.8, 0.8, 79.6, 14.0, 0.1, 85.2, 19.5, 1.5), asList(82.4, 16.3, 1.3, 78.4, 12.4, 0.1, 86.5, 20.3, 2.4), asList(81.9, 16.9, 1.2, 79.7, 14.9, 0.6, 84.0, 19.0, 1.8), asList(79.3, 19.1, 1.6, 75.8, 15.8, 0.5, 82.7, 22.5, 2.7), asList(77.3, 19.1, 3.6, 70.6, 12.9, 0.7, 83.9, 25.3, 6.6));
    private static final List<List<Double>> SIT_0_LEV_3_6h = asList(asList(69.2, 28.3, 2.5, 62.2, 21.5, 0.1, 76.2, 35.2, 4.9), asList(76.9, 20.5, 2.6, 65.5, 9.6, 0.0, 88.3, 31.4, 6.8), asList(64.5, 32.7, 2.8, 56.8, 25.2, 0.2, 72.2, 40.2, 5.5), asList(75.0, 16.7, 8.3, 52.6, 0.0, 0.0, 97.4, 36.0, 22.7), asList(89.9, 0.1, 10.0, 72.6, 0.0, 0.0, 100.0, 1.9, 27.4));
    private static final List<List<Double>> SIT_0_LEV_4_6h = asList(asList(53.8, 38.5, 7.7, 29.2, 14.4, 0.0, 78.5, 62.5, 20.9), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_0_LEV_5_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_1_LEV_0_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(66.3, 30.3, 3.4, 62.1, 26.2, 1.8, 70.5, 34.3, 5.0), asList(66.6, 32.1, 1.3, 63.9, 29.4, 0.6, 69.4, 34.8, 1.9), asList(84.6, 7.7, 7.7, 66.8, 0.0, 0.0, 100.0, 20.9, 20.9));
    private static final List<List<Double>> SIT_1_LEV_1_3h = asList(asList(39.5, 52.6, 7.9, 26.1, 39.0, 0.5, 52.9, 66.3, 15.3), asList(51.6, 48.3, 0.1, 35.9, 32.5, 0.0, 67.5, 64.1, 1.1), asList(55.2, 43.9, 0.9, 50.8, 39.4, 0.0, 59.7, 48.3, 1.7), asList(55.4, 42.3, 2.3, 52.6, 39.6, 1.5, 58.1, 45.1, 3.1), asList(53.0, 40.9, 6.1, 42.8, 30.8, 1.2, 63.3, 51.0, 11.0));
    private static final List<List<Double>> SIT_1_LEV_2_3h = asList(asList(36.9, 56.0, 7.1, 28.1, 46.9, 2.5, 45.7, 65.0, 11.8), asList(48.0, 46.2, 5.8, 36.5, 34.6, 0.4, 59.7, 57.7, 11.2), asList(33.9, 57.5, 8.6, 29.3, 52.6, 5.8, 38.6, 62.4, 11.3), asList(46.8, 47.0, 6.2, 42.4, 42.6, 4.1, 51.1, 51.4, 8.4), asList(44.0, 46.0, 10.0, 32.2, 34.2, 2.9, 55.8, 57.8, 17.1));
    private static final List<List<Double>> SIT_1_LEV_3_3h = asList(asList(27.9, 53.8, 18.3, 20.2, 45.2, 11.6, 35.7, 62.4, 24.9), asList(17.6, 60.8, 21.6, 8.7, 49.3, 11.9, 26.6, 72.2, 31.2), asList(22.1, 54.2, 23.7, 15.7, 46.6, 17.2, 28.4, 61.8, 30.2), asList(16.2, 67.6, 16.2, 9.1, 58.5, 9.1, 23.4, 76.6, 23.4), asList(10.5, 63.2, 26.3, 0.0, 44.0, 8.8, 22.7, 82.3, 43.8));
    private static final List<List<Double>> SIT_1_LEV_4_3h = asList(asList(2.4, 65.9, 31.7, 0.0, 53.4, 19.5, 6.5, 78.3, 43.9), asList(0.1, 42.8, 57.1, 0.0, 19.4, 33.7, 1.6, 66.3, 80.6), asList(12.4, 43.8, 43.8, 0.0, 22.0, 22.0, 27.0, 65.5, 65.5), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_1_LEV_5_3h = asList(asList(0.1, 33.2, 66.7, 0.0, 0.0, 27.9, 2.7, 72.1, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_1_LEV_0_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(75.5, 21.1, 3.4, 71.6, 17.5, 1.8, 79.2, 24.7, 5.0), asList(79.5, 19.5, 1.0, 77.1, 17.2, 0.4, 81.8, 21.8, 1.6), asList(76.8, 23.1, 0.1, 56.1, 2.3, 0.0, 97.7, 43.9, 1.7));
    private static final List<List<Double>> SIT_1_LEV_1_6h = asList(asList(50.0, 44.7, 5.3, 36.3, 31.1, 0.0, 63.7, 58.3, 11.4), asList(65.6, 31.0, 3.4, 50.5, 16.4, 0.0, 80.5, 45.6, 9.2), asList(70.1, 28.4, 1.5, 66.1, 24.3, 0.4, 74.3, 32.4, 2.5), asList(66.6, 29.6, 3.8, 63.9, 27.1, 2.7, 69.2, 32.2, 4.9), asList(60.7, 34.8, 4.5, 50.6, 25.1, 0.3, 70.6, 44.6, 8.8));
    private static final List<List<Double>> SIT_1_LEV_2_6h = asList(asList(52.3, 41.7, 6.0, 43.3, 32.7, 1.7, 61.4, 50.6, 10.2), asList(50.0, 44.2, 5.8, 38.4, 32.7, 0.4, 61.6, 55.8, 11.2), asList(46.1, 45.0, 8.9, 41.2, 40.1, 6.1, 51.0, 49.9, 11.7), asList(60.4, 31.7, 7.9, 56.0, 27.6, 5.6, 64.6, 35.8, 10.3), asList(58.0, 34.0, 8.0, 46.3, 22.8, 1.6, 69.7, 45.2, 14.4));
    private static final List<List<Double>> SIT_1_LEV_3_6h = asList(asList(46.2, 38.7, 15.1, 37.6, 30.3, 8.9, 54.8, 47.1, 21.2), asList(43.1, 45.1, 11.8, 31.5, 33.4, 4.2, 54.8, 56.8, 19.3), asList(31.4, 51.7, 16.9, 24.3, 44.1, 11.2, 38.4, 59.3, 22.7), asList(48.7, 35.1, 16.2, 39.0, 25.9, 9.1, 58.3, 44.4, 23.4), asList(52.7, 36.8, 10.5, 32.8, 17.7, 0.0, 72.5, 56.0, 22.7));
    private static final List<List<Double>> SIT_1_LEV_4_6h = asList(asList(26.8, 48.8, 24.4, 15.2, 35.6, 13.1, 38.5, 61.9, 35.7), asList(14.3, 50.0, 35.7, 0.0, 26.3, 13.0, 30.8, 73.7, 58.4), asList(18.7, 56.3, 25.0, 1.6, 34.5, 6.0, 35.9, 78.0, 44.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));
    private static final List<List<Double>> SIT_1_LEV_5_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 72.1, 72.1, 72.1), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_2_LEV_0_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(0.1, 83.2, 16.7, 0.0, 52.7, 0.0, 2.7, 100.0, 47.3), asList(13.8, 58.6, 27.6, 2.9, 43.1, 13.5, 24.7, 74.2, 41.7), asList(33.2, 66.7, 0.1, 0.0, 0.0, 0.0, 100.0, 100.0, 5.4));
    private static final List<List<Double>> SIT_2_LEV_1_3h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(0.1, 99.8, 0.1, 0.0, 94.5, 0.0, 3.8, 100.0, 3.8), asList(13.6, 75.0, 11.4, 4.9, 64.0, 3.3, 22.3, 86.0, 19.4), asList(0.1, 33.2, 66.7, 0.0, 0.0, 0.0, 5.4, 100.0, 100.0));
    private static final List<List<Double>> SIT_2_LEV_2_3h = asList(asList(0.1, 33.2, 66.7, 0.0, 0.0, 0.0, 5.4, 100.0, 100.0), asList(0.1, 24.9, 75.0, 0.0, 0.0, 24.0, 3.8, 76.0, 100.0), asList(13.0, 43.5, 43.5, 1.0, 25.7, 25.7, 25.1, 61.2, 61.2), asList(12.4, 46.4, 41.2, 6.8, 38.0, 32.9, 17.9, 54.8, 49.5), asList(0.1, 99.8, 0.1, 0.0, 96.1, 0.0, 2.7, 100.0, 2.7));
    private static final List<List<Double>> SIT_2_LEV_3_3h = asList(asList(0.1, 55.5, 44.4, 0.0, 24.8, 13.6, 2.1, 86.4, 75.2), asList(0.1, 33.2, 66.7, 0.0, 0.0, 27.9, 2.7, 72.1, 100.0), asList(2.5, 39.0, 58.5, 0.0, 26.2, 45.6, 6.5, 51.9, 71.5), asList(5.1, 42.3, 52.6, 1.0, 33.0, 43.2, 9.3, 51.6, 62.0), asList(0.1, 29.9, 70.0, 0.0, 3.4, 43.4, 1.9, 56.6, 96.6));
    private static final List<List<Double>> SIT_2_LEV_4_3h = asList(asList(0.1, 41.6, 58.3, 0.0, 16.1, 32.8, 1.7, 67.2, 83.9), asList(0.1, 19.9, 80.0, 0.0, 0.0, 41.9, 3.1, 58.1, 100.0), asList(0.1, 21.8, 78.1, 0.0, 9.5, 65.7, 1.0, 34.3, 90.5), asList(0.1, 40.8, 59.1, 0.0, 22.9, 41.1, 1.3, 58.9, 77.1), asList(0.1, 0.1, 99.8, 0.0, 0.0, 96.1, 2.7, 2.7, 100.0));
    private static final List<List<Double>> SIT_2_LEV_5_3h = asList(asList(0.1, 26.6, 73.3, 0.0, 6.6, 53.2, 1.5, 46.8, 93.4), asList(0.1, 12.4, 87.5, 0.0, 0.0, 65.3, 2.2, 34.7, 100.0), asList(0.1, 0.1, 99.8, 0.0, 0.0, 94.5, 3.8, 3.8, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<Double>> SIT_2_LEV_0_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.3, 50.0, 16.7, 0.0, 8.9, 0.0, 72.1, 91.1, 47.3), asList(41.4, 44.8, 13.8, 25.8, 29.1, 2.9, 56.9, 60.5, 24.7), asList(66.6, 33.3, 0.1, 0.0, 0.0, 0.0, 100.0, 100.0, 5.4));
    private static final List<List<Double>> SIT_2_LEV_1_6h = asList(asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(0.1, 99.8, 0.1, 0.0, 94.5, 0.0, 3.8, 100.0, 3.8), asList(36.4, 54.5, 9.1, 24.2, 41.9, 1.8, 48.6, 67.2, 16.4), asList(66.6, 33.3, 0.1, 0.0, 0.0, 0.0, 100.0, 100.0, 5.4));
    private static final List<List<Double>> SIT_2_LEV_2_6h = asList(asList(33.2, 0.1, 66.7, 0.0, 0.0, 0.0, 100.0, 5.4, 100.0), asList(0.1, 0.1, 99.8, 0.0, 0.0, 94.5, 3.8, 3.8, 100.0), asList(52.2, 13.0, 34.8, 34.3, 1.0, 17.7, 70.1, 25.1, 51.8), asList(28.9, 50.5, 20.6, 21.2, 42.1, 13.8, 36.5, 58.9, 27.4), asList(49.9, 50.0, 0.1, 8.9, 8.9, 0.0, 91.1, 91.1, 2.7));
    private static final List<List<Double>> SIT_2_LEV_3_6h = asList(asList(22.2, 22.2, 55.6, 0.0, 0.0, 24.8, 48.0, 48.0, 86.4), asList(33.3, 16.7, 50.0, 0.0, 0.0, 8.9, 72.1, 47.3, 91.1), asList(14.6, 53.7, 31.7, 5.3, 40.5, 19.5, 23.9, 66.8, 43.9), asList(24.4, 41.0, 34.6, 16.3, 31.8, 25.6, 32.5, 50.3, 43.6), asList(20.0, 50.0, 30.0, 0.0, 21.0, 3.4, 43.2, 79.0, 56.6));
    private static final List<List<Double>> SIT_2_LEV_4_6h = asList(asList(25.0, 33.3, 41.7, 2.6, 8.9, 16.1, 47.4, 57.8, 67.2), asList(20.0, 40.0, 40.0, 0.0, 0.0, 0.0, 58.1, 86.7, 86.7), asList(21.8, 34.4, 43.8, 9.5, 20.1, 28.9, 34.3, 48.6, 58.6), asList(13.6, 50.0, 36.4, 1.0, 31.7, 18.7, 26.2, 68.3, 54.0), asList(16.6, 0.1, 83.3, 0.0, 0.0, 52.7, 47.3, 2.7, 100.0));
    private static final List<List<Double>> SIT_2_LEV_5_6h = asList(asList(13.3, 40.0, 46.7, 0.0, 17.7, 24.0, 28.8, 62.3, 69.4), asList(12.4, 87.5, 0.1, 0.0, 65.3, 0.0, 34.7, 100.0, 2.2), asList(24.9, 75.0, 0.1, 0.0, 24.0, 0.0, 76.0, 100.0, 3.8), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0), asList(33.4, 33.3, 33.3, 0.0, 0.0, 0.0, 100.0, 100.0, 100.0));

    private static final List<List<List<Double>>> SIT_0_LEVELS_3h = asList(SIT_0_LEV_0_3h, SIT_0_LEV_1_3h, SIT_0_LEV_2_3h, SIT_0_LEV_3_3h, SIT_0_LEV_4_3h, SIT_0_LEV_5_3h);
    private static final List<List<List<Double>>> SIT_0_LEVELS_6h = asList(SIT_0_LEV_0_6h, SIT_0_LEV_1_6h, SIT_0_LEV_2_6h, SIT_0_LEV_3_6h, SIT_0_LEV_4_6h, SIT_0_LEV_5_6h);

    private static final List<List<List<Double>>> SIT_1_LEVELS_3h = asList(SIT_1_LEV_0_3h, SIT_1_LEV_1_3h, SIT_1_LEV_2_3h, SIT_1_LEV_3_3h, SIT_1_LEV_4_3h, SIT_1_LEV_5_3h);
    private static final List<List<List<Double>>> SIT_1_LEVELS_6h = asList(SIT_1_LEV_0_6h, SIT_1_LEV_1_6h, SIT_1_LEV_2_6h, SIT_1_LEV_3_6h, SIT_1_LEV_4_6h, SIT_1_LEV_5_6h);

    private static final List<List<List<Double>>> SIT_2_LEVELS_3h = asList(SIT_2_LEV_0_3h, SIT_2_LEV_1_3h, SIT_2_LEV_2_3h, SIT_2_LEV_3_3h, SIT_2_LEV_4_3h, SIT_2_LEV_5_3h);
    private static final List<List<List<Double>>> SIT_2_LEVELS_6h = asList(SIT_2_LEV_0_6h, SIT_2_LEV_1_6h, SIT_2_LEV_2_6h, SIT_2_LEV_3_6h, SIT_2_LEV_4_6h, SIT_2_LEV_5_6h);

    private static final List<List<List<List<Double>>>> SITUATION_3h = asList(SIT_0_LEVELS_3h, SIT_1_LEVELS_3h, SIT_2_LEVELS_3h);
    private static final List<List<List<List<Double>>>> SITUATION_6h = asList(SIT_0_LEVELS_6h, SIT_1_LEVELS_6h, SIT_2_LEVELS_6h);

    private static final long SITUATION_THRESHOLD_1 = 5L;
    private static final long SITUATION_THRESHOLD_2 = 7L;

    private static final int CASE_0 = 0;
    private static final int CASE_1 = 1;
    private static final int CASE_2 = 2;
    private static final int CASE_3 = 3;
    private static final int CASE_4 = 4;

    @Override
    public List<KPForecast> calculate(List<CIndex> cIndexList, KPValue kpValue) {
        Double maxValue1, maxValue2;
        int situation, level;
        
        if (validateCIndexList(cIndexList)) {
            return createKPForecastList(cIndexList);
        }

        KPForecast kp3h = new KPForecast();
        KPForecast kp6h = new KPForecast();
        List<KPForecast> kpForecastList = asList(kp3h, kp6h);
        
        
        CIndexReference mostRecent = getMostRecent(cIndexList);
        CIndexReference lessButStillRecent = getLessButStillRecent(cIndexList, mostRecent.getIndex());

        maxValue1 = getMaxValue(cIndexList, 0, cIndexList.size() - 1);
        maxValue2 = getMaxValue(cIndexList, 0, cIndexList.size() - 2);

        level = defineLevel(mostRecent.getReference());
        situation = defineSituation(kpValue);
        
        if (maxValue1 < mostRecent.getReference().getPostValue()) {
            create3HourKpForecast(cIndexList, situation, level, kp3h, CASE_0);
            create6HourKpForecast(cIndexList, situation, level, kp6h, CASE_0);

        } else if (maxValue2 < mostRecent.getReference().getPostValue() && lessButStillRecent.getReference().getPostValue() == mostRecent.getReference().getPostValue()) {
            create3HourKpForecast(cIndexList, situation, level, kp3h, CASE_1);
            create6HourKpForecast(cIndexList, situation, level, kp6h, CASE_1);

        } else if (maxValue1 == mostRecent.getReference().getPostValue()) {
            create3HourKpForecast(cIndexList, situation, level, kp3h, CASE_2);
            create6HourKpForecast(cIndexList, situation, level, kp6h, CASE_2);

        } else if (lessButStillRecent.getReference().getPostValue() >= mostRecent.getReference().getPostValue()) {
            create3HourKpForecast(cIndexList, situation, level, kp3h, CASE_3);
            create6HourKpForecast(cIndexList, situation, level, kp6h, CASE_3);

        } else {
            create3HourKpForecast(cIndexList, situation, level, kp3h, CASE_4);
            create6HourKpForecast(cIndexList, situation, level, kp6h, CASE_4);
        }

        return kpForecastList;
    }

    private List<KPForecast> createKPForecastList(List<CIndex> cIndexList) {
        CIndex cIndex = cIndexList.get(cIndexList.size()-1);
        
        KPForecast kp3h = new KPForecast();
        kp3h.setIndexesTimeTag(cIndex.getTimeTag());
        kp3h.setPredictedTimeTag(cIndex.getTimeTag().plusHours(3));
        
        KPForecast kp6h = new KPForecast();
        kp6h.setIndexesTimeTag(cIndex.getTimeTag());
        kp6h.setPredictedTimeTag(cIndex.getTimeTag().plusHours(6));
        
        List<KPForecast> kpForecastList = asList(kp3h, kp6h);
        return kpForecastList;
    }

    private void create3HourKpForecast(List<CIndex> cIndexList, int situation, int level, KPForecast kpForecast, int caseNumber) {
        createKpForecast(cIndexList, situation, level, kpForecast, caseNumber, SITUATION_3h, 3);
    }
    
    private void create6HourKpForecast(List<CIndex> cIndexList, int situation, int level, KPForecast kpForecast, int caseNumber) {
        createKpForecast(cIndexList, situation, level, kpForecast, caseNumber, SITUATION_6h, 6);
    }

    private void createKpForecast(List<CIndex> cIndexList, int situation, int level, KPForecast kpForecast, int caseNumber,  List<List<List<List<Double>>>> situationList, int plusTime) {
       Double Probability1 = situationList.get(situation).get(level).get(caseNumber).get(0);
       Double Probability2 = situationList.get(situation).get(level).get(caseNumber).get(1);
       Double Probability3 = situationList.get(situation).get(level).get(caseNumber).get(2);
       Double InferiorLimit1 = situationList.get(situation).get(level).get(caseNumber).get(3);
       Double InferiorLimit2 = situationList.get(situation).get(level).get(caseNumber).get(4);
       Double InferiorLimit3 = situationList.get(situation).get(level).get(caseNumber).get(5);
       Double upperLimit1 = situationList.get(situation).get(level).get(caseNumber).get(6);
       Double upperLimit2 = situationList.get(situation).get(level).get(caseNumber).get(7);
       Double upperLimit3 = situationList.get(situation).get(level).get(caseNumber).get(8);

       kpForecast.setIndexesTimeTag(cIndexList.get(cIndexList.size() - 1).getTimeTag());
       kpForecast.setPredictedTimeTag(cIndexList.get(cIndexList.size() - 1).getTimeTag().plusHours(plusTime));
       kpForecast.setProbability1(Probability1);
       kpForecast.setProbability2(Probability2);
       kpForecast.setProbability3(Probability3);
       kpForecast.setInferiorLimit1(InferiorLimit1);
       kpForecast.setInferiorLimit2(InferiorLimit2);
       kpForecast.setInferiorLimit3(InferiorLimit3);
       kpForecast.setUpperLimit1(upperLimit1);
       kpForecast.setUpperLimit2(upperLimit2);
       kpForecast.setUpperLimit3(upperLimit3);
       
    }

    private int defineLevel(CIndex cIndex) {
        double postValue = cIndex.getPostValue();
        
        if (postValue < 1) {
            return 0;
        } else if(postValue < 2) {
            return 1;
        } else if(postValue < 3) {
            return 2;
        } else if(postValue < 4) {
            return 3;
        } else if(postValue < 5) {
            return 4;
        } else {
            return 5;
        }
    }

    private int defineSituation(KPValue kpValue) {
        Long value = kpValue.getKPValue();
        KPValueFlag valueFlag = kpValue.getKPValueFlag();

        if ((value == SITUATION_THRESHOLD_1 && valueFlag.equals(KPValueFlag.DOWN)) || (value < SITUATION_THRESHOLD_1)) {
            return 0;
        } else if (((value > SITUATION_THRESHOLD_1) && (value < SITUATION_THRESHOLD_2))
                || (value == SITUATION_THRESHOLD_1 && (valueFlag.equals(KPValueFlag.ZERO) || valueFlag.equals(KPValueFlag.UP)))
                || (value == SITUATION_THRESHOLD_2 && valueFlag.equals(KPValueFlag.DOWN))) {
            return 1;
        } else {
            return 2;
        }
    }

    private Double getMaxValue(List<CIndex> cIndexList, int startPoint, int endPoint) {
        Double max = 0.0;

        for (int i = startPoint; i < endPoint; i++) {
            if (cIndexList.get(i).getPostValue() != null && cIndexList.get(i).getPostValue() > max) {
                max = cIndexList.get(i).getPostValue();
            }
        }

        return max;
    }

    private boolean validateCIndexList(List<CIndex> cIndexList) {
        throwIfNull(cIndexList, "cIndexList");
        
        if(cIndexList.isEmpty()) {
            throw new RuntimeException("Argument \"cIndexList\" cannot be empty.");
        }
        
        if(cIndexList.size() < 3) {return true;}

        long numValidItems =  cIndexList.stream().filter(ci -> ci.getPostValue() != null).count();

        return numValidItems < 3 ? true : false;
    }

    private CIndexReference getMostRecent(List<CIndex> cIndexList) {
        CIndexReference mostRecent = new CIndexReference();

        for (int i = cIndexList.size() - 1; i > 1; i--) {
            if (cIndexList.get(i).getPostValue() != null) {
                mostRecent.setReference(cIndexList.get(i));
                mostRecent.setIndex(i);
                return mostRecent;
            }
        }

        return mostRecent;
    }

    private CIndexReference getLessButStillRecent(List<CIndex> cIndexList, int mostRecentIndex) {
        CIndexReference lessButStillRecent = new CIndexReference();

        for (int i = mostRecentIndex - 1; i > 0; i--) {
            if (cIndexList.get(i).getPostValue() != null) {
                lessButStillRecent.setReference(cIndexList.get(i));
                lessButStillRecent.setIndex(i);
                return lessButStillRecent;
            }
        }

        return lessButStillRecent;
    }
}
