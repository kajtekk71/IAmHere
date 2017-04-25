package com.example.okti.appan;




        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;

public class Friends extends AppCompatActivity {
    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        RowBean RowBean_data[] = new RowBean[] {

                new RowBean(R.drawable.dj, "James","Bond"),
                new RowBean(R.drawable.dj, "Peter","Parker"),
                new RowBean(R.drawable.dj, "Bruce","Wayne"),

        };

        RowAdapter adapter = new RowAdapter(this,
                R.layout.activity_list, RowBean_data);

        listView1 = (ListView)findViewById(R.id.listView);

        listView1.setAdapter(adapter);
    }
public void contactChosen(View view)
{
    Intent i = new Intent(
            getApplicationContext(),
            Message.class);
    i.putExtra("mail",true);
    startActivity(i);
}
}
