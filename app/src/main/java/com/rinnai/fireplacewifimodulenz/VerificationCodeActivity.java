package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VerificationCodeActivity extends MillecActivityBase {

    String emailStr;
    EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

         emailStr = getIntent().getExtras().getString("email");
         etCode = (EditText) findViewById(R.id.et_code);

        TextView okBtn = (TextView) findViewById(R.id.artv_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationCodeActivity.this, Rinnai11gRegistration.class);
                intent.putExtra("code", etCode.getText().toString());
                intent.putExtra("email", emailStr);
                startActivity(intent);
            }
        });
    }

}
