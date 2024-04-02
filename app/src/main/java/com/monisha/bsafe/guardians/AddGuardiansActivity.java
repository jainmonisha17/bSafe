package com.monisha.bsafe.guardians;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.monisha.bsafe.BaseLanguageLoaderActivity;
import com.monisha.bsafe.appinfo.guardian.GuardiansInfo;
import com.monisha.bsafe.databinding.ActivityAddGuardianBinding;

public class AddGuardiansActivity extends BaseLanguageLoaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddGuardianBinding binding = ActivityAddGuardianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String primaryGuardian = GuardiansInfo.getPrimaryGuardian(getApplication());
        String secondaryGuardian = GuardiansInfo.getSecondaryGuardian(getApplication());
        if (!primaryGuardian.equals("")) {
            binding.numberEditPrimary.setText(primaryGuardian);
        }
        if (!secondaryGuardian.equals("")) {
            binding.numberEditSecondary.setText(secondaryGuardian);
        }

        binding.finishButton.setOnClickListener(view -> {
            savePrimaryGuardian(binding.numberEditPrimary);
            saveSecondaryGuardian(binding.numberEditSecondary);
        });
    }

    private void savePrimaryGuardian(TextInputEditText numberEdit) {
        if (numberEdit.getText() != null && numberEdit.getText().length() == 10) {
            GuardiansInfo.putPrimaryGuardian(this, numberEdit.getText().toString());
            Toast.makeText(this, "Saved " + numberEdit.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter Valid Number!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSecondaryGuardian(TextInputEditText numberEdit) {
        if (numberEdit.getText() != null && numberEdit.getText().length() == 10) {
            GuardiansInfo.putSecondaryGuardian(this, numberEdit.getText().toString());
            Toast.makeText(this, "Saved " + numberEdit.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter Valid Number!", Toast.LENGTH_SHORT).show();
        }
    }
}