package stazer.user.timedatepickeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView btDate,btTime,tvCurrent,tvSelectedDate,tvSelectedTime;
    int cYear,cMonth,cDay,sYear,sMonth,sDay;
    int cHour,cMinute,sHour,sMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        btDate = findViewById(R.id.bt_date);
        btTime = findViewById(R.id.bt_time);

        tvCurrent = findViewById(R.id.tv_current);
        tvSelectedDate=findViewById(R.id.tv_selected_date);
        tvSelectedTime=findViewById(R.id.tv_selected_time);

        //initialize calender
        Calendar calendar = Calendar.getInstance();

        //get Current Year
        cYear = calendar.get(Calendar.YEAR);
        //get Current Month
        cMonth = calendar.get(Calendar.MONTH);
        // get Current Day
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Get Current Hour
        cHour = calendar.get(Calendar.HOUR_OF_DAY);
        // Get Current Minutes
        cMinute = calendar.get(Calendar.MINUTE);

        // Get Current date in dd-MM-YYYY format
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        // Get Current Time in 12 hours format
        String time =  new SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(new Date());

        //set Current Date and Time in TextView
        tvCurrent.setText(String.format("%s\n%s", date, time));

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize date Picker Dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sYear=year;
                        sMonth = month;
                        sDay = dayOfMonth;

                        String sDate = sDay+"-"+sMonth+"-"+sYear;
                        tvSelectedDate.setText(sDate);

                    }
                },cYear,cMonth,cDay);
                // Displayed previous selected Date
                datePickerDialog.updateDate(sYear,sMonth,sDay);
                //Disabled past Date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                // Disabled future Date
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 604800000L); // 7 * 24 * 60 * 60 * 1000
                //show Date Picker Dialog
                datePickerDialog.show();
            }
        });

        btTime.setOnClickListener(v -> {
            //Initialize Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    MainActivity.this, (view, hourOfDay, minute) -> {
                        sHour = hourOfDay;
                        sMinute = minute;

                        //Initialize Calender
                        Calendar calendar1= Calendar.getInstance();

                        String sDate = tvSelectedDate.getText().toString().trim();
                        //Split Date
                        String [] strings = sDate.split("-");
                        //Get day on Calender
                        sDay = Integer.parseInt(strings[0]);
                        //set day on Calender
                        calendar1.set(Calendar.DAY_OF_MONTH,sDay);
                        //set Hour on Calender
                        calendar1.set(Calendar.HOUR_OF_DAY,sHour);
                        //set Minute on Calender
                        calendar1.set(Calendar.MINUTE,sMinute);
                        if (calendar1.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()){
                            tvSelectedTime.setText(DateFormat.format("hh:mm aa",calendar1));
                        }
                        else {
                            Toast.makeText(this, "You Selected Past Time Please Select Correct Time", Toast.LENGTH_SHORT).show();
                        }


                    }, cHour,cMinute,false);
            //show Dialog
            timePickerDialog.show();
        });
    }
}