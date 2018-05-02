#/bin/bash

export MINUTE_FILE_LOCATION='/home/'$USER'/Downloads/swd/swd-minuto-a-minuto.txt'
export HOUR_FILE_LOCATION='/home/'$USER'/Downloads/swd/swd-hora-a-hora.txt'
export PGPASSWORD='#####SENHA#####'

#swd-minuto-a-minuto.txt
echo "---------------------------------------------------------------------------------------------------------" > $MINUTE_FILE_LOCATION
echo "time | dpr | ey | rmp | bt | bx_gsm | by_gsm | bz_gsm | lat_gsm | lon_gsm | density | speed | temperature " >> $MINUTE_FILE_LOCATION
echo "---------------------------------------------------------------------------------------------------------" >> $MINUTE_FILE_LOCATION
echo " " >> $MINUTE_FILE_LOCATION

psql -h localhost -p 5432 -U climaespacial -d climaespacial -t -A -c "SELECT to_char(cv.time_tag, 'YYYY|MM|DD|HH24|MI') AS time, cv.dpr, cv.ey, cv.rmp, m.bt, m.bx_gsm, m.by_gsm, m.bz_gsm, m.lat_gsm, m.lon_gsm, p.density, p.speed, p.temperature FROM swd.calculated_values cv INNER JOIN swd.mag m ON (cv.time_tag = m.time_tag) INNER JOIN swd.plasma p ON (cv.time_tag = p.time_tag) ORDER BY cv.time_tag;" >> $MINUTE_FILE_LOCATION

#swd-hora-a-hora.txt

echo "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" > $HOUR_FILE_LOCATION
echo "time | bt | bx_gsm | by_gsm  | bz_gsm | density |  speed | temperature | dpr | ey | rmp | ib.pre_index_value | ib.post_index_value | ic.pre_index_value | ic.post_index_value | iv.pre_index_value | iv.post_index_value |iz.pre_index_value | iz.post_index_value" >> $HOUR_FILE_LOCATION
echo "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" >> $HOUR_FILE_LOCATION
echo " " >> $HOUR_FILE_LOCATION

psql -h localhost -p 5432 -U climaespacial -d climaespacial -t -A -c "SELECT to_char(ha.time_tag, 'YYYY|MM|DD|HH24') AS time, ha.bt, ha.bx_gsm, ha.by_gsm, ha.bz_gsm, ha.density, ha.speed, ha.temperature, ha.dpr, ha.ey, ha.rmp, ib.pre_index_value, ib.post_index_value, ic.pre_index_value, ic.post_index_value, iv.pre_index_value, iv.post_index_value, iz.pre_index_value, iz.post_index_value FROM swd.hourly_average ha INNER JOIN swd.index_b ib ON (ha.time_tag = ib.time_tag) INNER JOIN swd.index_c ic ON (ha.time_tag = ic.time_tag) LEFT JOIN swd.index_z iz ON (ha.time_tag = iz.time_tag) LEFT JOIN swd.index_v iv ON (ha.time_tag = iv.time_tag) ORDER BY ha.time_tag;" >> $HOUR_FILE_LOCATION

#| -> ;
sed -i -E "s/\;/|/g" $MINUTE_FILE_LOCATION
sed -i -E "s/\;/|/g" $HOUR_FILE_LOCATION

#00.000 -> 00.00
sed -i -E "s/((\-?[0-9]{1,}\.[0-9]{2})[0-9]+)/\2/g" $MINUTE_FILE_LOCATION
sed -i -E "s/((\-?[0-9]{1,}\.[0-9]{2})[0-9]+)/\2/g" $HOUR_FILE_LOCATION