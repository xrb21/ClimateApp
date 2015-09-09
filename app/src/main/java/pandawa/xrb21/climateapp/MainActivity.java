package pandawa.xrb21.climateapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pandawa.xrb21.climateapp.helpers.RbHelper;
import pandawa.xrb21.climateapp.models.Weather;

public class MainActivity extends AppCompatActivity {
    private AQuery aq;
    private Context c;

    private Spinner spKota;
    private TableLayout tbCuaca;
    private ArrayList<Weather> dataCuaca;
    private String dataKota[] = {"Jakarta", "Tokyo", "London"};
    private int citySelect = 0;
    private TextView tvKota, tvAverageDay, tvAverageVarian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c = this;
        aq = new AQuery(c);
        setupView();

        //ambil data cuaca
        getData();
    }

    private void setupView() {
        tvKota = (TextView) findViewById(R.id.tvKota);
        tvAverageDay = (TextView) findViewById(R.id.tvAverageDay);
        tvAverageVarian = (TextView) findViewById(R.id.tvAverageVariant);

        spKota = (Spinner) findViewById(R.id.spCity);
        spKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                citySelect = i;
                //ambil data cuaca baru
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //add data kota ke spinner via array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_selectable_list_item, dataKota);
        spKota.setAdapter(adapter);

        tbCuaca = (TableLayout) findViewById(R.id.tbCuaca);
    }

    private void getData(){
        String namaKota = dataKota[citySelect];
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="
                + namaKota + "&mode=json&units=metric&cnt=7";
        Map<String, String> params = new HashMap<String, String>();
        ProgressDialog dialog = new ProgressDialog(c);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage("Loading...");
        try {
            aq.progress(dialog).ajax(url, params, JSONObject.class,
                    new AjaxCallback<JSONObject>() {

                        @Override
                        public void callback(String url, JSONObject json,
                                             AjaxStatus status) {
                            try {
                                Log.d("xrb21","log aquery respon "
                                        + json.toString());
                                int respon = json.getInt("cod");
                                dataCuaca = new ArrayList<Weather>();
                                if (respon == 200) {

                                    //ambil datanya
                                    JSONArray jArray = json.getJSONArray("list");
                                    for(int i = 0; i < jArray.length(); i++){
                                        //ambil tanggal
                                        JSONObject jObj = jArray.getJSONObject(i);
                                        int tgl = jObj.getInt("dt");
                                        //ambil nilai cuaca
                                        JSONObject jObjCuaca = jObj.getJSONObject("temp");

                                        //buat object weather dan masukka nilai yg diperlukan
                                        Weather w = new Weather();
                                        w.setTanggal(tgl);
                                        w.setDay(jObjCuaca.getDouble("day"));
                                        w.setMin(jObjCuaca.getDouble("min"));
                                        w.setMax(jObjCuaca.getDouble("max"));

                                        RbHelper.log("tgl " + tgl + ", cuaca : " + String.valueOf(jObjCuaca.getDouble("day")));

                                        //add weather to arraylist datacuaca
                                        dataCuaca.add(w);

                                    }

                                    //masukkan kedalam tablelayout
                                    insertToTable();
                                } else {
                                    RbHelper.pesan(c, "error get data");

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                RbHelper.pesan(c,
                                        "Error parsing data, please try again.");
                            } catch (Exception e) {
                                e.printStackTrace();
                                RbHelper.pesan(c,
                                        "Error get data, please try again.");
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            RbHelper.pesan(c,
                    "Error get task, please try again.");
        }

    }

    private void insertToTable(){
        int baris = tbCuaca.getChildCount();
        if (baris > 1) {
            tbCuaca.removeViews(1, baris - 1);
        }
        TableRow.LayoutParams params = new TableRow.LayoutParams(0,
                LayoutParams.WRAP_CONTENT, 1f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0,
                LayoutParams.WRAP_CONTENT, 1f);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(1, 0, 1, 1);

        if(dataCuaca.size() > 0) {
            double jmlhDay = 0;
            double jmlVariance = 0;
            int jmlData = dataCuaca.size();
            for (int i = 0; i < dataCuaca.size(); i++) {
                //ambil data cuaca
                Weather w = dataCuaca.get(i);
                double variance = w.getMax() - w.getMin();
                jmlhDay += w.getDay();
                jmlVariance += variance;

                //buat tabel row untuk ditmbahkan ke tablelayout
                TableRow row = new TableRow(c);
                row.setBackgroundColor(Color.WHITE);
                row.setPadding(3, 8, 3, 8);
                row.setLayoutParams(lp);

                TextView tvUraian = new TextView(c);
                tvUraian.setText(RbHelper.microToStr(w.getTanggal()));
                tvUraian.setPadding(5,5 , 25, 5);
                tvUraian.setLayoutParams(params);

                TextView tvSaldo = new TextView(c);
                tvSaldo.setPadding(10, 5, 10, 5);
                tvSaldo.setGravity(Gravity.CENTER_VERTICAL
                        | Gravity.RIGHT);
                tvSaldo.setLayoutParams(params2);
                tvSaldo.setText(String.format("%.2fC", w.getDay()));

                TextView tvSaldo2 = new TextView(c);
                tvSaldo2.setPadding(10, 5, 10, 5);
                tvSaldo2.setGravity(Gravity.CENTER_VERTICAL
                        | Gravity.RIGHT);
                tvSaldo2.setLayoutParams(params2);
                tvSaldo2.setText(String.format("%.2fC", variance));

                //masukkan texview ke table row
                row.addView(tvUraian);
                row.addView(tvSaldo);
                row.addView(tvSaldo2);
                //masukkan table row ke tablelayout
                tbCuaca.addView(row);
            }

            //masukkan nama kota, average day, average varian
            tvKota.setText(dataKota[citySelect]);
            tvAverageDay.setText(String.format("7-day average: %.2fC", jmlhDay / jmlData));
            tvAverageVarian.setText(String.format("7-day average variance: %.2fC", jmlVariance / jmlData));
        }
    }



}
