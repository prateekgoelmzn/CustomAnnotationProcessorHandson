package com.pg.processor;

import com.google.auto.service.AutoService;
import com.pg.annotation.Builder;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.pg.annotation.Builder")
public class BuilderProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(Builder.class);
        for(Element element : elementSet){
            generateFile(element);
        }

        return true;
    }

    private void generateFile(Element element) {
        String className = element.getSimpleName().toString();
        String packageName = element.getEnclosingElement().toString();
        String builderName = className+"Builder";
        String builderFullName = packageName+"."+builderName;
        List<? extends Element> fields = element.getEnclosedElements().stream().filter(e-> ElementKind.FIELD.equals(e.getKind())).collect(Collectors.toList());

        try(PrintWriter writer = new PrintWriter(
                processingEnv.getFiler().createSourceFile(builderFullName).openWriter()))
        {
            writer.printf("package %s;\n",packageName);
            writer.printf("public class %s{\n", builderName);

            fields.forEach(field-> writer.printf("private %s %s;\n",field.asType().toString(),field.getSimpleName()));

            fields.forEach(field->writer.printf("public %s %s(%s value){\n%s = value;\nreturn this;\n}\n",builderName, field.getSimpleName(),field.asType().toString(),field.getSimpleName()));

            writer.printf("public %s build(){\nreturn new %s(%s);\n}\n",className,className,fields.stream().map(Element::getSimpleName).collect(joining(", ")));

            writer.println("}");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
