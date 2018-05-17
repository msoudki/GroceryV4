package app.com.groceryv4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private String TAG = "MainActivity";
    @BindView(R.id.et_phone_number)
    EditText et_phone_number;
    @BindView(R.id.bt_verify)
    Button bt_verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.bt_verify)
    void verify() {
        if (et_phone_number.getText().toString().isEmpty()) {
            Log.d(TAG, "empty Number");
        } else {
            Log.d(TAG, "Number is not empty");
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    et_phone_number.getText().toString(),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                            Log.d(TAG, "OnVerificationCompleted");

                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {
                            Log.d(TAG, "onVerificationFailed");
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                // Invalid request
                                Log.d(TAG, "Invalid Credentials");
                                // ...
                            } else if (e instanceof FirebaseTooManyRequestsException) {
                                // The SMS quota for the project has been exceeded
                                Log.d(TAG, "SMS quota exceeded");
                                // ...
                            }

                        }

                        @Override
                        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            Log.d(TAG, "onCodeSent:" + s);
                        }
                    });        // OnVerificationStateChangedCallbacks

        }
    }


}
