package co.aurasphere.bluepair;

public class Modes {

    private static Modes obj;
    private Modes(){}

    public static Modes getModes(){
        if (obj == null){
            obj = new Modes();
        }
        return obj;
    }

    private String HydroTime = "600000 Min";
    private String AirTime = "600000 Min";
    private String WaterTime = "600000 Min";
    private String NeckTime = "600000 Min";
    private String ChromaTime = "600000  Min";
    private String OzoneTime= "600000  Min";
    private String heaterTime= "600000 Min";
    private String DrainTime="600000 Min";
    private String CleaningTime="600000 Min";
    private String cleanFill ="2";
    private String cleanDrain ="2";
    private String hydroJet1 ="5";
    private String hydroJet2 ="5";
    private String hydroJet3 ="5";
    private String hydroJet4 ="5";
    private String customSequenceHydro ="5";
    private String customSequenceAir ="5";

//    public String getHeaterTime() {
//        return heaterTime;
//    }
//
//    public void setHeaterTime(String heaterTime) {
//        this.heaterTime = heaterTime;
//    }
//
//    public String getDrainTime() {
//        return DrainTime;
//    }
//
//    public void setDrainTime(String drainTime) {
//        DrainTime = drainTime;
//    }
//
//    public String getCleaningTime() {
//        return CleaningTime;
//    }
//
//    public void setCleaningTime(String cleaningTime) {
//        CleaningTime = cleaningTime;
//    }
//
//    public String getHydroTime() {
//        return HydroTime;
//    }
//
//    public void setHydroTime(String hydroTime) {
//        HydroTime = hydroTime;
//    }
//
//    public String getAirTime() {
//        return AirTime;
//    }
//
//    public void setAirTime(String airTime) {
//        AirTime = airTime;
//    }
//
//    public String getWaterTime() {
//        return WaterTime;
//    }
//
//    public void setWaterTime(String waterTime) {
//        WaterTime = waterTime;
//    }
//
//    public String getNeckTime() {
//        return NeckTime;
//    }
//
//    public void setNeckTime(String neckTime) {
//        NeckTime = neckTime;
//    }
//
//    public String getChromaTime() {
//        return ChromaTime;
//    }
//
//    public void setChromaTime(String chromaTime) {
//        ChromaTime = chromaTime;
//    }
//
//    public String getOzoneTime() {
//        return OzoneTime;
//    }
//
//    public void setOzoneTime(String ozoneTime) {
//        OzoneTime = ozoneTime;
//    }




    public String getHeaterTime() {
        return millisToString(heaterTime);
    }

    public void setHeaterTime(String heaterTime) {
        this.heaterTime = convertToMillis(heaterTime);
    }

    public String getDrainTime() {
        return millisToString(DrainTime);
    }

    public void setDrainTime(String drainTime) {
        DrainTime = convertToMillis(drainTime);
    }

    public String getCleaningTime() {
        return millisToString(CleaningTime);
    }

    public void setCleaningTime(String cleaningTime) {
        CleaningTime = convertToMillis(cleaningTime);
    }

    public String getHydroTime() {
        return millisToString(HydroTime);
    }

    public void setHydroTime(String hydroTime) {
        HydroTime = convertToMillis(hydroTime);
    }

    public String getAirTime() {
        return millisToString(AirTime);
    }

    public void setAirTime(String airTime) {
        AirTime = convertToMillis(airTime);
    }

    public String getWaterTime() {
        return millisToString(WaterTime);
    }

    public void setWaterTime(String waterTime) {
        WaterTime = convertToMillis(waterTime);
    }

    public String getNeckTime() {
        return millisToString(NeckTime);
    }

    public void setNeckTime(String neckTime) {
        NeckTime = convertToMillis(neckTime);
    }

    public String getChromaTime() {
        return millisToString(ChromaTime);
    }

    public void setChromaTime(String chromaTime) {
        ChromaTime = convertToMillis(chromaTime);
    }

    public String getOzoneTime() {
        return millisToString(OzoneTime);
    }


    public void setOzoneTime(String ozoneTime) {
        OzoneTime = convertToMillis(ozoneTime);
    }
    public void setCleanFillTime(String fillTime) {cleanFill = fillTime;}
    public String getCleanFillTime() {return cleanFill;}

    public void setCleanDrainTime(String drainTime) {cleanDrain = drainTime;}
    public String getCleanDrainTime() {return cleanDrain;}

    public void setHydroJet1Time(String jet1Time){hydroJet1=jet1Time;}
    public String getHydroJet1Time() {return hydroJet1;}

    public void setHydroJet2Time(String jet2Time){hydroJet2=jet2Time;}
    public String getHydroJet2Time() {return hydroJet2;}

    public void setHydroJet3Time(String jet3Time){hydroJet3=jet3Time;}
    public String getHydroJet3Time() {return hydroJet3;}

    public void setHydroJet4Time(String jet4Time){hydroJet4=jet4Time;}
    public String getHydroJet4Time() {return hydroJet4;}

    public void setCustomSequenceHydro(String jet4Time){customSequenceHydro=jet4Time;}
    public String getCustomSequenceHydro() {return customSequenceHydro;}

    public void setCustomSequenceAir(String jet4Time){customSequenceAir=jet4Time;}
    public String getCustomSequenceAir() {return customSequenceAir;}


    public String convertToMillis(String time){
        return time;
    }
//    public String convertToMillis(String time){
//        String[] extractedMinute = time.split(" ");
//        Long millis= Long.valueOf(Integer.parseInt(extractedMinute[0])*60*1000);
//        return millis+" Min";
//    }
    public String millisToString(String time){
        String[] extractedMinute = time.split(" ");
        Long millis= Long.valueOf(Integer.parseInt(extractedMinute[0]));
        return (millis)+" Min";
    }
}
class CleaningObj{

}
