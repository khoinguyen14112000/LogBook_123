package vn.edu.greenwich.cw_2_sample_123;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static final String _FILE_NAME = "cw_2_image_list.txt";

    protected ImageView imageView;
    protected EditText etImageLink;
    protected Button btnPrevious, btnNext, btnAdd;

    protected ArrayList<String> _imageList;
    protected int _currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        etImageLink = findViewById(R.id.etImageLink);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> addImage());
        btnNext.setOnClickListener(v -> nextImage());
        btnPrevious.setOnClickListener(v -> previousImage());

        _imageList = getImageList();
        _currentIndex = 0;

        loadImage();
    }

    // Logbook 01.
    protected ArrayList<String> getImageList() {
        ArrayList<String> imageList = new ArrayList<>();

        imageList.add("https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-6/284769066_1710083999330921_4086721875751616071_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=ShN2it2H9YIAX9JUp4t&_nc_ht=scontent.fsgn5-5.fna&oh=00_AfDW4WYPZoS1I_q2kdGfIQZuQyCX0dTfbgYd1Zh1n-hhBQ&oe=642E881D");
        imageList.add("https://scontent.fsgn5-5.fna.fbcdn.net/v/t39.30808-6/284869425_3338073136517128_6198535554054985258_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=E4QAZch_WcoAX9On_DH&_nc_ht=scontent.fsgn5-5.fna&oh=00_AfCrESUCU8RjJZbRVnuFXIFDikRUHcFgNZY1YawYYpPcPg&oe=643013D8");

        getImageListFromFile(imageList);

        Toast.makeText(this, "Get list successfully.", Toast.LENGTH_SHORT).show();

        return imageList;
    }

    // Logbook 01.
    protected void loadImage() {
        Picasso.with(this).load(_imageList.get(_currentIndex)).into(imageView);
    }

    // Logbook 02.
    protected void addImage() {
        String imageURL = etImageLink.getText().toString();
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
if (imageURL.matches(regex)){
    _imageList.add(imageURL);
    writeURLToFile(imageURL);
    etImageLink.setText("");
    Toast.makeText(this, "Added successfully.", Toast.LENGTH_SHORT).show();
} else{
    Toast.makeText(this, "Add Failed.", Toast.LENGTH_SHORT).show();

}

    }

    // Logbook 01.
    protected void nextImage() {
        ++_currentIndex;
        loadImage();
    }

    // Logbook 01.
    protected void previousImage() {
        --_currentIndex;
        loadImage();
    }

    // Logbook 03.
    protected void writeURLToFile(String url) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(_FILE_NAME, MODE_APPEND));
            outputStreamWriter.write(url);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Logbook 03.
    private void getImageListFromFile(ArrayList<String> imageList) {
        try {
            InputStream inputStream = openFileInput(_FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                // matches <http://google.com>

                String url = "";
                while ((url = bufferedReader.readLine()) != null ) {
                    imageList.add(url);
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}