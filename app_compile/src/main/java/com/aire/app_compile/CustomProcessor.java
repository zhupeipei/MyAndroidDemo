package com.aire.app_compile;

import com.aire.app_annotation.BindView;
import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @author ZhuPeipei
 * @date 2021/11/30 22:00
 */
@AutoService(Processor.class)
public class CustomProcessor extends AbstractProcessor {
    /**
     * 文件相关的辅助类
     */
    private Filer mFiler;
    /**
     * 元素相关的辅助类
     */
    private Elements mElementUtils;
    /**
     * 解析的目标注解集合
     */
    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        System.out.println("init processor");

        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        System.out.println("init2 processor " + BindView.class.getCanonicalName());

        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.out.println("init3 processor");

        mAnnotatedClassMap.clear();
        try {
            processBindView(roundEnv);
        } catch (IllegalArgumentException e) {
            return true;
        }

        try {
            for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
                annotatedClass.generateFinder().writeTo(mFiler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    private void processBindView(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            BindViewField field = new BindViewField(element);
            annotatedClass.addField(field);
            System.out.println("class: " + annotatedClass + ", p_element=" + element.getSimpleName() + ",p_set=" + element.getModifiers());
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element) {
        TypeElement encloseElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = encloseElement.getQualifiedName().toString();
        System.out.println("fullClassName: " + fullClassName);
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(encloseElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

}
