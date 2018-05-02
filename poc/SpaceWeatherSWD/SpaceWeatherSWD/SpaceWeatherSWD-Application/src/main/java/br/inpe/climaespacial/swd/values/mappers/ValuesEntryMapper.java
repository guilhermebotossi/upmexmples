package br.inpe.climaespacial.swd.values.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;


public interface ValuesEntryMapper {

	List<ValueEntry> mapBT(List<BT> btList);
	List<ValueEntry> mapBY(List<BY> byList);
	List<ValueEntry> mapBX(List<BX> bxList);
	List<ValueEntry> mapBZ(List<BZ> bzList);
	List<ValueEntry> mapDensity(List<Density> densityList);
	List<ValueEntry> mapSpeed(List<Speed> speedList);
	List<ValueEntry> mapTemperature(List<Temperature> temperatureList);
	List<ValueEntry> mapEY(List<EY> eyList);
	List<ValueEntry> mapDPR(List<DPR> dprList);
	List<ValueEntry> mapRMP(List<RMP> rmpList);
	List<ValueEntry> mapAverageBT(List<AverageBT> averageBTList);
	List<ValueEntry> mapAverageBY(List<AverageBY> averageBYList);
	List<ValueEntry> mapAverageBX(List<AverageBX> averageBXList);
	List<ValueEntry> mapAverageBZ(List<AverageBZ> averageBZList);
	List<ValueEntry> mapAverageDensity(List<AverageDensity> averageDensityList);
	List<ValueEntry> mapAverageSpeed(List<AverageSpeed> averageSpeedList);
	List<ValueEntry> mapAverageTemperature(List<AverageTemperature> averageTemperatureList);
	List<ValueEntry> mapAverageEY(List<AverageEY> averageEYList);
	List<ValueEntry> mapAverageDPR(List<AverageDPR> averageDPRList);
	List<ValueEntry> mapAverageRMP(List<AverageRMP> averageRMPList);

}
