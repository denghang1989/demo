package com.example;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class InjectAnnotation extends AbstractProcessor {

    private Elements mElementUtils;
    private Filer    mFiler;
    private Messager mMessager;
    private Types    mTypeUtils;

    private Map<String, ProxyInfo> map = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mTypeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        map.clear();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            if (!checkAnnotationValid(element)) {
                return false;
            }
            //filed
            VariableElement variableElement = (VariableElement) element;
            //class
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            String className = typeElement.getQualifiedName().toString();
            ProxyInfo proxyInfo = map.get(className);

            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(mElementUtils, typeElement);
                map.put(className, proxyInfo);
            }

            BindView bindView = variableElement.getAnnotation(BindView.class);
            int id = bindView.value();
            proxyInfo.mInjectElement.put(id, variableElement);
        }

        for (String key : map.keySet()) {
            ProxyInfo proxyInfo = map.get(key);
            try {
                JavaFileObject javaFile = mFiler.createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.getTypeElement());
                Writer writer = javaFile.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return true;
    }

    private boolean checkAnnotationValid(Element element) {
        boolean result = true;
        if (element.getKind() == ElementKind.FIELD) {
            result = false;
        }
        return result;
    }
}
