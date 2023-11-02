package com.fel.oop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class addCriminal extends AppCompatActivity {

    DBHelper dbHelper;
    EditText name;
    EditText birthdate;
    EditText city;
    EditText crime;
    EditText signs;
    ImageButton uri;
    ImageButton ok_in;
    String suri = ""; // переменная в которой хранится картинка
    ImageView uspeh, neuspeh;
    TextView uspeh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcriminal);
        dbHelper = new DBHelper(this);
        name = findViewById(R.id.name_in);
        birthdate = findViewById(R.id.birthdate_in);
        city = findViewById(R.id.city_in);
        crime = findViewById(R.id.crime_in);
        signs = findViewById(R.id.signs_in);
        uri = findViewById(R.id.uri_in);
        ok_in = findViewById(R.id.ok_in);
        uspeh = findViewById(R.id.uspeh);
        uspeh1 = findViewById(R.id.uspeh1);
        neuspeh = findViewById(R.id.neuspeh);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/oswald.ttf");
        String[] items = getResources().getStringArray(R.array.country_array);
        Spinner spinner = findViewById(R.id.countrySpinner);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, items, customFont);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        ok_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewcrime();
            }
        });
        uri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                if (!selectedCountry.equals("Выберите страну")) {
                    city.setTextColor(Color.TRANSPARENT);
                    city.setText(selectedCountry);
                    Toast.makeText(addCriminal.this, "Выбрана страна: " + selectedCountry, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ничего не выбрано
            }
        });

    }


    private void getImage() {  // Открываем активность, выбора картинки из галереи
        Intent intentChooser = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentChooser, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // Обрабатываем результат выбора картинки
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {
                suri = data.getDataString();
                Uri uri1 = Uri.parse(suri);
                uri.setImageURI(uri1);
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    private void addnewcrime() {
        if (name.getText().toString().isEmpty() || birthdate.getText().toString().isEmpty() || city.getText().toString().isEmpty() || crime.getText().toString().isEmpty() || signs.getText().toString().isEmpty() || suri.isEmpty() ) {
            if (name.getText().toString().isEmpty()) {
                name.setBackgroundResource(R.drawable.underline_red);
            }
            else {
                name.setBackgroundResource(0);
            }
            if (birthdate.getText().toString().isEmpty()) {
                birthdate.setBackgroundResource(R.drawable.underline_red);
            }
            else {
                birthdate.setBackgroundResource(0);
            }
            if (city.getText().toString().isEmpty()) {
                city.setBackgroundResource(R.drawable.underline_red);
            }
            else {
                city.setBackgroundResource(0);
            }
            if (crime.getText().toString().isEmpty()) {
                crime.setBackgroundResource(R.drawable.underline_red);
            }
            else {
                crime.setBackgroundResource(0);
            }
            if (signs.getText().toString().isEmpty()) {
                signs.setBackgroundResource(R.drawable.underline_red);
            }
            else {
                signs.setBackgroundResource(0);
            }
            if (suri.isEmpty()) {
                uri.setBackgroundResource(R.drawable.underline_red);
            }
            else {
                uri.setBackgroundResource(0);
            }
            neuspeh.setVisibility(View.VISIBLE);
            uspeh1.setText("Ошибка");

        }
        else {
            name.setBackgroundResource(0);
            city.setBackgroundResource(0);
            birthdate.setBackgroundResource(0);
            crime.setBackgroundResource(0);
            signs.setBackgroundResource(0);
            uri.setBackgroundResource(0);
            // добавляем одного нового преступника в бд
            String sname = String.valueOf(name.getText());
            String sbirthdate = String.valueOf(birthdate.getText());
            String scity = String.valueOf(city.getText());
            String scrime = String.valueOf(crime.getText());
            String ssigns = String.valueOf(signs.getText());

            Criminal criminal = new Criminal(sname,sbirthdate,scity,scrime,ssigns,suri,0);
            dbHelper.AddOne(criminal);
            neuspeh.setVisibility(View.INVISIBLE);
            uspeh.setVisibility(View.VISIBLE);
            uspeh1.setText("Успешно");
        }


    }
    public class CustomSpinnerAdapter extends ArrayAdapter<String> {
        private Typeface font;

        public CustomSpinnerAdapter(Context context, int resource, String[] items, Typeface font) {
            super(context, resource, items);
            this.font = font;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }
    }
    public void startMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void setSuri(String suri) {
        this.suri = suri;
    }

}