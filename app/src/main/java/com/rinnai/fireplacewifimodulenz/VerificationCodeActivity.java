package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VerificationCodeActivity extends MillecActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        final EditText etCode = (EditText) findViewById(R.id.et_code);

        TextView okBtn = (TextView) findViewById(R.id.artv_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationCodeActivity.this, Rinnai11gRegistration.class);
                intent.putExtra("code", etCode.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

}
