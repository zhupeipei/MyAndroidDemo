package com.aire.android.annotation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;
import com.aire.annotation_api.ButterKnife;
import com.aire.app_annotation.BindView;

import java.lang.reflect.Field;

public class AnnotationActivity extends AppCompatActivity {

    @BindView(R.id.anno_main_tv)
    TextView mTv;

    @BindView(R.id.anno_main_tv)
    TextView mTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        ButterKnife.bind(this);

//        getAllAnnotationView();

        mTv.setText("annotation 设置成功");
    }

    private void getAllAnnotationView() {
        //获得成员变量
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                //判断注解
                if (field.getAnnotations() != null) {
                    //确定注解类型
                    if (field.isAnnotationPresent(BindView.class)) {
                        //允许修改反射属性
                        field.setAccessible(true);
                        BindView bindView = field.getAnnotation(BindView.class);
                        //findViewById将注解的id，找到View注入成员变量中
                        field.set(this, findViewById(bindView.value()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}