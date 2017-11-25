package com.xiaoxige.processor;

import com.google.auto.service.AutoService;

import java.util.HashSet;
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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import cn.xiaoxige.anontation.ClassAnontation;
import cn.xiaoxige.anontation.FidleAnontation;
import cn.xiaoxige.anontation.Method2Anontation;
import cn.xiaoxige.anontation.MethodAnontation;

/**
 */

@AutoService(Processor.class)
public class AnontationProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set set = new HashSet<>();
        set.add(ClassAnontation.class.getCanonicalName());
        set.add(MethodAnontation.class.getCanonicalName());
        set.add(FidleAnontation.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set == null || set.size() <= 0) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "It's TypeElement is null or empty");
            return false;
        }

        for (TypeElement element : set) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, element.toString());
        }

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(ClassAnontation.class);

        for (Element element : elementsAnnotatedWith) {

            // 得到注解标识的是什么玩意（Em. 类，方法， 成员变量...）
            ElementKind kind = element.getKind();

            mMessager.printMessage(Diagnostic.Kind.NOTE, "kind " + kind.toString());

            // 得到注解标识的权限是什么（Em. public, private, static, final ...）
            Set<Modifier> modifiers =
                    element.getModifiers();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "--> modifiers " + element.getEnclosingElement().toString());
            // 得到注释的注解内容（比如获取的值）
            ClassAnontation annotation = element.getAnnotation(ClassAnontation.class);
            // 表示java编程语中的类型（用这个去判断是int?String?...）
            TypeMirror typeMirror = element.asType();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "--> typeMirror " + typeMirror.toString());

            if (element.getKind().isClass()) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "==>> It's a Class " + element.getSimpleName());
            }

            if (element.getKind().isField()) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "==> It's a Field ");
            }

        }

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MethodAnontation.class);
        for (Element element : elements) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "simpleName " + element.getSimpleName());
            mMessager.printMessage(Diagnostic.Kind.NOTE, "--> modifiers " + element.getEnclosingElement().toString());

            // 参数
            TypeMirror typeMirror = element.asType();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "--> typeMirror " + typeMirror.toString());

            // 方法
            ExecutableElement executableElement = (ExecutableElement) element;
            TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
            String qualifiedName = enclosingElement.getQualifiedName().toString();

            if (element.getKind() == ElementKind.METHOD) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "==> It's a method ");
            }
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "-------------------------------------------");
        Set<? extends Element> annotatedWith = roundEnvironment.getElementsAnnotatedWith(Method2Anontation.class);
        for (Element element : annotatedWith) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "simpleName " + element.getSimpleName());
            mMessager.printMessage(Diagnostic.Kind.NOTE, "--> modifiers " + element.getEnclosingElement().toString());

            // 参数
            TypeMirror typeMirror = element.asType();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "--> typeMirror " + typeMirror.toString());

            // 方法
            ExecutableElement executableElement = (ExecutableElement) element;
            TypeElement enclosingElement = (TypeElement) executableElement.getEnclosingElement();
            String qualifiedName = enclosingElement.getQualifiedName().toString();

            if (element.getKind() == ElementKind.METHOD) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "==> It's a method ");
            }
        }


        return true;
    }
}
