package com.example.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultsTv, solutionTv;

    MaterialButton btnAc, btnMult, btnDiv, btnBack, btn7, btn8, btn9, btnPlus, btn4, btn5, btn6, btnSub, btn1, btn2, btn3, btnPer, btnPt, btn0, btn00, btnSol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultsTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.sol_tv);

        assignId(btnAc, R.id.btnAC);
        assignId(btnMult, R.id.btnMult);
        assignId(btnDiv, R.id.btnDiv);
        assignId(btnBack, R.id.btnBack);
        assignId(btn7, R.id.btn7);
        assignId(btn8, R.id.btn8);
        assignId(btn9, R.id.btn9);
        assignId(btnPlus, R.id.btnPlus);
        assignId(btn4, R.id.btn4);
        assignId(btn5, R.id.btn5);
        assignId(btn6, R.id.btn6);
        assignId(btnSub, R.id.btnSub);
        assignId(btn1, R.id.btn1);
        assignId(btn2, R.id.btn2);
        assignId(btn3, R.id.btn3);
        assignId(btnPer, R.id.btnPer);
        assignId(btnPt, R.id.btnPt);
        assignId(btn0, R.id.btn0);
        assignId(btn00, R.id.btn00);
        assignId(btnSol, R.id.btnSol);
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String datatoCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultsTv.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTv.setText(resultsTv.getText());
            return;
        }
        if(buttonText.equals("C")){
            if(datatoCalculate.length() != 0) {
                datatoCalculate = datatoCalculate.substring(0, datatoCalculate.length()-1);
            }
        }else {
            datatoCalculate = datatoCalculate + buttonText;
        }

        if(datatoCalculate.endsWith("/")) {
            datatoCalculate = datatoCalculate.replace("*/", "/");
            datatoCalculate = datatoCalculate.replace("+/", "/");
            datatoCalculate = datatoCalculate.replace("-/", "/");
            datatoCalculate = datatoCalculate.replace("%/", "/");
        } else if(datatoCalculate.endsWith("*")) {
            datatoCalculate = datatoCalculate.replace("/*", "*");
            datatoCalculate = datatoCalculate.replace("+*", "*");
            datatoCalculate = datatoCalculate.replace("-*", "*");
            datatoCalculate = datatoCalculate.replace("%*", "*");
        } else if(datatoCalculate.endsWith("%")) {
            datatoCalculate = datatoCalculate.replace("*%", "%");
            datatoCalculate = datatoCalculate.replace("+%", "%");
            datatoCalculate = datatoCalculate.replace("-%", "%");
            datatoCalculate = datatoCalculate.replace("/%", "%");
        } else if(datatoCalculate.endsWith("+")) {
            datatoCalculate = datatoCalculate.replace("*+", "+");
            datatoCalculate = datatoCalculate.replace("/+", "+");
            datatoCalculate = datatoCalculate.replace("-+", "+");
            datatoCalculate = datatoCalculate.replace("%+", "+");
        } else if(datatoCalculate.endsWith("-")) {
            datatoCalculate = datatoCalculate.replace("*-", "-");
            datatoCalculate = datatoCalculate.replace("+-", "-");
            datatoCalculate = datatoCalculate.replace("/-", "-");
            datatoCalculate = datatoCalculate.replace("%-", "-");
        }
        solutionTv.setText(datatoCalculate);

        String finalResult = getResult(datatoCalculate);

        if(!finalResult.equals("Err")){
            resultsTv.setText(finalResult);
        }
    }
    String getResult(String data) {
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            ScriptableObject scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}