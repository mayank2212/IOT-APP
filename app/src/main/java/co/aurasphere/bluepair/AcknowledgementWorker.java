package co.aurasphere.bluepair;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class AcknowledgementWorker extends Worker {
    public AcknowledgementWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String cmd = "@T"+String.format("%03d",Modes.getModes().getHeaterTemp())+
                "W0"+
                "J"+Modes.getModes().getIsHydroOn()+
                "B"+Modes.getModes().getIsAirMassageOn()+
                "H"+Modes.getModes().getIsHeaterOn()+
                "O"+Modes.getModes().getIsOzoneOn()+
                "C"+Modes.getModes().getIsChromaOn()+
                "N"+Modes.getModes().getIsCascadeOn()+
                "@";
        Log.e("TAG", "doWork: "+cmd );

        return Result.success();
    }
}
