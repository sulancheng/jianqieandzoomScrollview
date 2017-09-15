package com.example.administrator.myjianqieqi.gouwaddj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.bigkoo.snappingstepper.listener.SnappingStepperValueChangeListener;
import com.example.administrator.myjianqieqi.R;

public class GouwuActivity extends Activity implements SnappingStepperValueChangeListener {
    private TextView tvValue;
    private SnappingStepper stepper;
    private TextView tvValueCustom;
    private SnappingStepper stepperCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouwu);
        tvValue = (TextView) findViewById(R.id.tvValue);
        stepper = (SnappingStepper) findViewById(R.id.stepper);
        tvValueCustom = (TextView) findViewById(R.id.tvValueCustom);
        stepperCustom = (SnappingStepper) findViewById(R.id.stepperCustom);

        tvValue.setText(String.valueOf(stepper.getValue()));
        tvValueCustom.setText(String.valueOf(stepperCustom.getValue()));
        stepper.setOnValueChangeListener(this);
        stepperCustom.setOnValueChangeListener(this);

        stepperCustom.setBackgroundColor(getResources().getColor(R.color.colorStepperButtonNormal));
        stepperCustom.setButtonBackGround(R.drawable.sl_steppercustom_button_bg);
        stepperCustom.setContentBackground(R.color.colorStepperContentBg);
        stepperCustom.setContentTextColor(R.color.colorStepperText);
        stepperCustom.setContentTextSize(18);
        stepperCustom.setLeftButtonResources(R.drawable.ic_stepper_left);
        stepperCustom.setRightButtonResources(R.drawable.ic_stepper_right);
    }
    @Override
    public void onValueChange(View view , int value) {
        switch (view.getId()){
            case R.id.stepper:
                tvValue.setText(String.valueOf(value));
                break;
            case R.id.stepperCustom:
                tvValueCustom.setText(String.valueOf(value));
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
