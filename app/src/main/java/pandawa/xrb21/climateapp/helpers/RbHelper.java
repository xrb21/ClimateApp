package pandawa.xrb21.climateapp.helpers;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RbHelper {
	private static final int DEBUG = 1;
	public static final String APP = "x21-NgetripMajelengka";
	public static final int TIME_OUT = 10 * 1000;
	public static final String pesanError = "Bad Connection, Please Try Again.\nCOntact admin "
			+ "+6285743207606.";

	public static final int NOTIFICATION_ID = 321876;

	public static void pesan(Context c, String msg) {
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}

	public static boolean isEmpty(EditText etText) {
		if (etText.getText().toString().trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isCompare(EditText etText, EditText ex) {
		String a = etText.getText().toString();
		String b = ex.getText().toString();
		if (a.equals(b)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isBatasUsia(EditText etText, int batas) {
		String a = etText.getText().toString();
		Date usia = strTodate(a);
		Date sekarang = dateAddYear(strTodate(tglSekarang()), batas);
		if (usia.before(sekarang)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * method is used for checking valid email id format.
	 * 
	 * @param email
	 * @return boolean true for valid false for invalid
	 */
	public static boolean isEmailValid(EditText email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email.getText().toString();

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean minLength(EditText etText, int jmlh) {
		if (etText.getText().toString().trim().length() >= jmlh) {
			return false;
		} else {
			return true;
		}
	}

	// untuk check koneksi internet
	public static boolean isOnline(ConnectivityManager cm) {
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	// md5 encrypt function
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String tglSekarang() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String jamSekarang() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String tglJamSekarang() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String tglJamSekarang2() {
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date now = new Date();
		RbHelper.pre("tgl simpan: " + formatter1.format(now));
		return formatter1.format(now);
	}

	@SuppressLint("SimpleDateFormat")
	public static Date tglDate() {
		Date now = new Date();
		RbHelper.pre("tanggal sekarang : " + now.toString());
		SimpleDateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = null;
		String output = null;

		try {
			output = formatter1.format(now);
			RbHelper.pre("output formater : " + output);
			date = formatter1.parse(output);
			RbHelper.pre("output formater 2 : " + date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static Date tglDate(String now) {
		SimpleDateFormat formatter, formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		String output = null;
		try {
			date = formatter1.parse(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static Date tglDatex() {
		Date now = new Date();
		String dateString = now.toString();
		// System.out.println(" 1. " + dateString);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");

		Date date = null;
		try {
			date = format.parse(format.format(now));
			// System.out.println(" 2. " + date.toString());
		} catch (ParseException pe) {
			// System.out.println("ERROR: Cannot parse \"" + dateString + "\"");
		}

		// System.out.println(" 3. " + format.format(now));
		return date;
	}

	public static Date tglDatex(String now) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss aa");

		// log("tanggal asli : " + now);

		Date date = new Date();

		try {

			date = formatter.parse(now);

			// System.out.println(date);
			// System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static String dateTimeToString(Date data) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newDateString = "";
		newDateString = df.format(data);
		return newDateString;
	}

	public static String tglJamSekarangGanti(String x) {
		Date date = strTodate(x);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(date);
	}

	public static Date strTodate(String data) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date startDate = null;
		String newDateString = "";
		try {
			startDate = df.parse(data);
			// newDateString = df.format(startDate);
			System.out.println(newDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return startDate;
	}

	public static Date strTodate(String data, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date startDate = null;
		String newDateString = "";
		try {
			startDate = df.parse(data);
			// newDateString = df.format(startDate);
			System.out.println(newDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return startDate;
	}

	public static String formatInt(int data) {
		DecimalFormat nft = new DecimalFormat("#00.###");
		return nft.format(data);
	}

	public static String dateToString(Date data) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String newDateString = "";
		newDateString = df.format(data);
		return newDateString;
	}

	public static Date dateAdd(Date in, int daysToAdd) {
		if (in == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
		return cal.getTime();

	}

	public static Date dateAddYear(Date in, int tahun) {
		if (in == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		cal.add(Calendar.YEAR, tahun);
		return cal.getTime();

	}

	public static String toRupiahFormat(String nominal) {

		DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setCurrencySymbol("Rp. ");
		dfs.setMonetaryDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		df.setDecimalFormatSymbols(dfs);

		// String rupiah = df.format(Double.parseDouble(nominal)) + ",-";
		String rupiah = df.format(Double.parseDouble(nominal));

		return rupiah;
	}

	public static String getDeviceId(Context context) {
		final String deviceId = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		if (deviceId != null) {
			return deviceId;
		} else {
			return Build.SERIAL;
		}
	}

	public static String getDeviceUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);

		String deviceMobileNo = tm.getLine1Number();

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		return deviceUuid.toString();

	}

	public static void alert(Context context, String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);


		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		// Showing Alert Message
		alertDialog.show();
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

	public static void pre(String pesan) {
		try {
			if (DEBUG == 1) {
				System.out.println(pesan);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static String[] kelamin() {
		String[] jenisKelamin = { "-", "Laki-Laki", "Perempuan" };

		return jenisKelamin;
	}

	public static String kelamin(int posisi) {
		String[] jenisKelamin = { "-", "Laki-Laki", "Perempuan" };

		return jenisKelamin[posisi];
	}

	public static String[] golonganDarah() {
		String[] darah = { "-", "A", "B", "AB", "O" };

		return darah;
	}

	public static String golonganDarah(int x) {
		String[] darah = { "-", "A", "B", "AB", "O" };
		return darah[x];
	}

	public static boolean saveFile(Bitmap imageToSave, String fileName) {
		boolean hasil = true;

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chatdokter");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdir()) {
				Log.d("error simpan file", "Failed to create directory");
			}
		}

		File file = new File(mediaStorageDir, fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			hasil = false;
			e.printStackTrace();
		}

		return hasil;
	}

	public static boolean saveFile(File fileName) {
		boolean hasil = true;

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chatdokter");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdir()) {
				Log.d("error simpan file", "Failed to create directory");
			}
		}

		File file = new File(mediaStorageDir, fileName.getName());
		if (file.exists()) {
			file.delete();
		}

		try {

			FileOutputStream fOut = new FileOutputStream(fileName);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			fOut.flush();
			fOut.close();

		} catch (Exception e) {
			hasil = false;
			e.printStackTrace();
		}

		return hasil;
	}

	public static boolean saveFile(Bitmap imageToSave, String fileName,
			String folder) {
		boolean hasil = true;

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chatdokter/");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdir()) {
				Log.d("error simpan file", "Failed to create directory");
			} else {
				mediaStorageDir = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"chatdokter/" + folder);
				if (!mediaStorageDir.exists()) {
					if (!mediaStorageDir.mkdir()) {
						Log.d("error simpan file", "Failed to create directory");
					}
				}
			}
		} else {
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"chatdokter/" + folder);
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdir()) {
					Log.d("error simpan file", "Failed to create directory");
				}
			}
		}

		File file = new File(mediaStorageDir, fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			imageToSave.compress(Bitmap.CompressFormat.PNG, 0, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			hasil = false;
			e.printStackTrace();
		}

		return hasil;
	}

	public static boolean saveFile(byte[] response, String fileName,
			String folder) {
		boolean hasil = true;

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chatdokter/");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdir()) {
				Log.d("error simpan file", "Failed to create directory");
			} else {
				mediaStorageDir = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"chatdokter/" + folder);
				if (!mediaStorageDir.exists()) {
					if (!mediaStorageDir.mkdir()) {
						Log.d("error simpan file", "Failed to create directory");
					}
				}
			}
		} else {
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"chatdokter/" + folder);
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdir()) {
					Log.d("error simpan file", "Failed to create directory");
				}
			}
		}

		File file = new File(mediaStorageDir, fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(response);
			out.flush();
			out.close();
		} catch (Exception e) {
			hasil = false;
			e.printStackTrace();
		}

		return hasil;
	}

	public static File getFile(String fileName) {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chatdokter");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdir()) {
				Log.d("error", "Failed to create directory");
			}
		}

		File file = new File(mediaStorageDir, fileName);

		return file;
	}

	public static File getFile(String fileName, String folder) {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"chatdokter/" + folder);
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdir()) {
				Log.d("error", "Failed to create directory");
			}
		}

		File file = new File(mediaStorageDir, fileName);

		return file;
	}

	@SuppressWarnings("unchecked")
	public static void startMyTask(AsyncTask<String, ?, ?> asyncTask,
			String... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		else
			asyncTask.execute(params);
	}

	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	public static String getStringFromFile(File file) throws Exception {
		FileInputStream fin = new FileInputStream(file);
		String ret = convertStreamToString(fin);
		// Make sure you close all streams.
		fin.close();
		return ret;
	}

	public static void error(Exception e) {
		if (DEBUG == 1) {
			e.printStackTrace();
		}
	}

	public static void error(Exception e, String x) {
		if (DEBUG == 1) {
			log(x);
			e.printStackTrace();
		}

	}

	public static void showSettingGps(final Context context) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

		alertBuilder.setTitle("GPS Setting");
		alertBuilder
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		alertBuilder.setPositiveButton("Setting",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);

					}
				});
		alertBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});
	}

	public static boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	// peringatan jika internet tidak konek
	public static void alertMessageNoInternet(Context c) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage(
				"Anda tidak terkoneksi dengan internet, Silahkan Aktifkan Internet Anda terlebih dahulu.")
				.setCancelable(false)
				.setTitle("Informasi Internet")
				.setNegativeButton("Tutup",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}



	// menghitung jarak antar koordinat
	public static double jarakKoordinat(double lat1, double lon1, double lat2,
			double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit.equalsIgnoreCase("K")) {
			dist = dist * 1.609344;
		} else if (unit.equalsIgnoreCase("N")) {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public static double distance(double lat1, double lng1, double lat2,
			double lng2, String unit) {
		double earthRadius = 6371.0; // 3958.75; // miles (or 6371.0 kilometers)
		if (unit.equalsIgnoreCase("M")) {
			earthRadius = 6371 * 1000;
		} else if (unit.equalsIgnoreCase("N")) {
			earthRadius = 3958.75; // miles (or 6371.0 kilometers)
		}
		double dLat = Math.toRadians(lat2 - lat1);
		// System.out.println("dlat " + s(dLat));
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

	public static String s(double value) {
		return String.valueOf(value);
	}

	public static String timeConversion(int totalSeconds) {

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
		int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
		int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
		int hours = totalMinutes / MINUTES_IN_AN_HOUR;

		if (hours == 0) {
			return minutes + " minutes";
		} else {
			return hours + " hours " + minutes + " minutes ";
		}
		// return hours + " hours " + minutes + " minutes " + seconds +
		// " seconds";

	}

	public static String timeConversion2(int totalSeconds) {

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
		int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
		int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
		int hours = totalMinutes / MINUTES_IN_AN_HOUR;

		// return hours + " hours " + minutes + " minutes " + seconds +
		// " seconds";
		return String.format("%02d", hours) + ":"
				+ String.format("%02d", minutes) + ":"
				+ String.format("%02d", seconds);
	}

	public static Double d(String string) {
		return Double.parseDouble(string);
	}

	public static String unixid() {
		long current = System.currentTimeMillis();
		return String.valueOf(current++);

	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	public static String microToStr(int v){
		String dateAsText = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date(v * 1000L));
		return  dateAsText;
	}


}
