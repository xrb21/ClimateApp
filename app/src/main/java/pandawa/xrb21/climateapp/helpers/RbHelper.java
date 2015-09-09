package pandawa.xrb21.climateapp.helpers;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RbHelper {
	private static final int DEBUG = 1;
	public static final String APP = "x21-ClimateAPp";

	public static void pesan(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}

	public static void log(String pesan) {
		try {
			if (DEBUG == 1) {
				Log.i(APP, pesan);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public static String microToStr(int v){
		String dateAsText = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date(v * 1000L));
		return  dateAsText;
	}


}
