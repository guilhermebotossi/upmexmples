public class magAndPlasmaSqlGenerator {

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        double minBt = 0.18;
        double maxBt = 23.22;
        double minBx_gsm = -13.25;
        double maxBx_gsm = 11.98;
        double minBy_gsm = -15.86;
        double maxBy_gsm = 17.34;
        double minBz_gsm = -20.71;
        double maxBz_gsm = 15.1;
        double minLon = 0;
        double maxLon = 360;
        double minLat = -88.11;
        double maxLat = 87.69;
        
        double minDensity = 0.11;
        double maxDensity = 68.77;
        double minSpeed = 287.4;
        double maxSpeed = 1159.7;
        double minTemperature = 2000;
        double maxTemperature = 2461410;
        
        ZonedDateTime initialTimeTag = ZonedDateTime.parse("2016-04-25T00:00:00z[UTC]");
        ZonedDateTime finalTimeTag = ZonedDateTime.parse("2017-04-26T20:07:00z[UTC]");
        
        String magSql = "INSERT INTO swd.mag(id, creation_date, modification_date, bt, bx_gsm, by_gsm, bz_gsm, lat_gsm, lon_gsm, time_tag) VALUES('%s','%s','%s',%s,%s,%s,%s,%s,%s,'%s');\r\n";
        String plasmaSql = "INSERT INTO swd.plasma(id, creation_date, modification_date, density, speed, temperature, time_tag) VALUES('%s','%s','%s',%s,%s,%s,'%s');\r\n";
        
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Path magPath = Paths.get("/home/gbotossi/Documents/mag.sql");
        Path plasmaPath = Paths.get("/home/gbotossi/Documents/plasma.sql");
        
        try (BufferedWriter magWriter = Files.newBufferedWriter(magPath); BufferedWriter plasmaWriter = Files.newBufferedWriter(plasmaPath)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String now = dtf.format(ZonedDateTime.now());
        
            for(;!initialTimeTag.isAfter(finalTimeTag); initialTimeTag = initialTimeTag.plusMinutes(1)) {
                System.out.println("TimeTag " + initialTimeTag);
                UUID magId = UUID.randomUUID();
                UUID plasmaId = UUID.randomUUID();
                
                
                Double rBt = random.nextDouble(maxBt - minBt) + minBt;
                Double rBx_gsm = random.nextDouble(maxBx_gsm - minBx_gsm) + minBx_gsm;
                Double rBy_gsm = random.nextDouble(maxBy_gsm - minBy_gsm) + minBy_gsm;
                Double rBz_gsm = random.nextDouble(maxBz_gsm - minBz_gsm) + minBz_gsm;
                Double rLon = random.nextDouble(maxLon - minLon) + minLon;
                Double rLat = random.nextDouble(maxLat - minLat) + minLat;

                Double rDensity = random.nextDouble(maxDensity - minDensity) + minDensity;
                Double rSpeed = random.nextDouble(maxSpeed - minSpeed) + minSpeed;
                Double rTemperature = random.nextDouble(maxTemperature - minTemperature) + minTemperature;
                
                String timeTag = dtf.format(initialTimeTag);
                
                String formatedMagSql = String.format(magSql, magId, now, now, rBt, rBx_gsm, rBy_gsm, rBz_gsm, rLon, rLat, timeTag);
                String formatedPlasmaSql = String.format(plasmaSql, plasmaId, now, now, rDensity, rSpeed, rTemperature, timeTag);
                
                magWriter.write(formatedMagSql);
                plasmaWriter.write(formatedPlasmaSql);
            }
        }    }
}