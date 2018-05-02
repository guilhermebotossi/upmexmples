#/bin/bash

INDEX_C_FILE_LOCATION='/home/'$USER'/Downloads/swd/index_c.txt'
KP_FILE_LOCATION='/home/'$USER'/Downloads/swd/kp.txt'
KP_FORECAST_FILE_LOCATION='/home/'$USER'/Downloads/swd/kp_forecast.txt'

export PGPASSWORD='climaespacial'

#index_c.txt
echo "------------------------------------------------" > $INDEX_C_FILE_LOCATION
echo "time | pre_index_value | post_index_value " >> $INDEX_C_FILE_LOCATION
echo "------------------------------------------------" >> $INDEX_C_FILE_LOCATION
echo " " >> $INDEX_C_FILE_LOCATION

psql -h localhost -p 5432 -U climaespacial -d climaespacial -t -A -c "SELECT 
	to_char(time_tag, 'YYYY|MM|DD|HH24|MI') AS time, 
	pre_index_value, 
	post_index_value 
	FROM swd.index_c 
	ORDER BY time_tag;" >> $INDEX_C_FILE_LOCATION

#index_c.txt
echo "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" > $KP_FILE_LOCATION
echo "time | kp1.value | kp1.flag | kp2.value | kp2.flag | kp3.value | kp3.flag | kp4.value | kp4.flag | kp5.value | kp5.flag | kp6.value | kp6.flag | kp7.value | kp7.flag | kp8.value | kp8.flag | kp9.value | kp9.flag | kpSum.value | kpSum.flag | ap | cp | most_disturbed_and_quiet_days" >> $KP_FILE_LOCATION
echo "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" >> $KP_FILE_LOCATION
echo " " >> $KP_FILE_LOCATION

psql -h localhost -p 5432 -U climaespacial -d climaespacial -t -A -c "SELECT to_char(ikp.time_tag AT TIME ZONE 'UTC', 'YYYY|MM|DD|HH24|MI') AS time, 
  kp1.value, kp1.flag,  
  kp2.value, kp2.flag, 
  kp3.value, kp3.flag, 
  kp4.value, kp4.flag,
  kp5.value, kp5.flag,
  kp6.value, kp6.flag,
  kp7.value, kp7.flag,
  kp8.value, kp8.flag,
  ikp.kp_sum,
  ikp.kp_sum_flag,
  ikp.cp,
  ikp.ap,
  ikp.most_disturbed_and_quiet_days FROM swd.index_kp ikp 
  INNER JOIN swd.kp_value kp1 ON(ikp.id=kp1.index_kp_id AND EXTRACT(HOUR FROM kp1.time_tag) = 0)
  INNER JOIN swd.kp_value kp2 ON(ikp.id=kp2.index_kp_id AND EXTRACT(HOUR FROM kp2.time_tag) = 3)
  INNER JOIN swd.kp_value kp3 ON(ikp.id=kp3.index_kp_id AND EXTRACT(HOUR FROM kp3.time_tag) = 6)
  INNER JOIN swd.kp_value kp4 ON(ikp.id=kp4.index_kp_id AND EXTRACT(HOUR FROM kp4.time_tag) = 9)
  INNER JOIN swd.kp_value kp5 ON(ikp.id=kp5.index_kp_id AND EXTRACT(HOUR FROM kp5.time_tag) = 12)
  INNER JOIN swd.kp_value kp6 ON(ikp.id=kp6.index_kp_id AND EXTRACT(HOUR FROM kp6.time_tag) = 15)
  INNER JOIN swd.kp_value kp7 ON(ikp.id=kp7.index_kp_id AND EXTRACT(HOUR FROM kp7.time_tag) = 18)
  INNER JOIN swd.kp_value kp8 ON(ikp.id=kp8.index_kp_id AND EXTRACT(HOUR FROM kp8.time_tag) = 21) ORDER BY ikp.time_tag;" >> $KP_FILE_LOCATION

#index_c.txt
echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" > $KP_FORECAST_FILE_LOCATION
echo "indexes_time_tag | predicted_time_tag | inferiorlimit_1 | inferiorlimit_2 | inferiorlimit_3 | probability_1 | probability_2 | probability_3 | upperlimit_1 | upperlimit_2 | upperlimit_3 " >> $KP_FORECAST_FILE_LOCATION
echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" >> $KP_FORECAST_FILE_LOCATION
echo " " >> $KP_FORECAST_FILE_LOCATION

psql -h localhost -p 5432 -U climaespacial -d climaespacial -t -A -c "SELECT to_char(indexes_time_tag, 'YYYY|MM|DD|HH24|MI') AS indexes_time_tag,
	 to_char(predicted_time_tag, 'YYYY|MM|DD|HH24|MI') AS predicted_time_tag , 
	 inferiorlimit_1, inferiorlimit_2, inferiorlimit_3, 
	 probability_1, probability_2, probability_3, 
	 upperlimit_1, upperlimit_2, upperlimit_3 
	 FROM swd.kp_forecast ORDER BY indexes_time_tag;" >> $KP_FORECAST_FILE_LOCATION

#| -> ;
sed -i -E "s/\;/|/g" $INDEX_C_FILE_LOCATION
sed -i -E "s/\;/|/g" $KP_FILE_LOCATION
sed -i -E "s/\;/|/g" $KP_FORECAST_FILE_LOCATION

#00.000 -> 00.00
sed -i -E "s/((\-?[0-9]{1,}\.[0-9]{2})[0-9]+)/\2/g" $INDEX_C_FILE_LOCATION
sed -i -E "s/((\-?[0-9]{1,}\.[0-9]{2})[0-9]+)/\2/g" $KP_FILE_LOCATION
sed -i -E "s/((\-?[0-9]{1,}\.[0-9]{2})[0-9]+)/\2/g" $KP_FORECAST_FILE_LOCATION
