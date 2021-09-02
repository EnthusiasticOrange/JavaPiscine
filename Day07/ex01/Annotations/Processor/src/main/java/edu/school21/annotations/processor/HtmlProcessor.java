package edu.school21.annotations.processor;

import com.google.auto.service.AutoService;
import edu.school21.annotations.processor.annotation.HtmlForm;
import edu.school21.annotations.processor.annotation.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        String[] annotations = {
                "edu.school21.annotations.processor.annotation.HtmlForm",
                "edu.school21.annotations.processor.annotation.HtmlInput"};
        return new HashSet<>(Arrays.asList(annotations));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        for (Element elem : env.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = elem.getAnnotation(HtmlForm.class);
            try {
                FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", htmlForm.fileName());
                try (PrintWriter out = new PrintWriter(fileObject.openWriter())) {
                    out.println(String.format("<form action= \"%s\" method = \"%s\">", htmlForm.action(), htmlForm.method()));
                    for (Element enclosed : elem.getEnclosedElements()) {
                        if (enclosed.getKind().isField() && enclosed.getAnnotation(HtmlInput.class) != null) {
                            HtmlInput htmlInput = enclosed.getAnnotation(HtmlInput.class);
                            out.println(String.format("<input type = \"%s\" name = \"%s\" placeholder = \"%s\">",
                                    htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
                        }
                    }
                    out.println("<input type = \"submit\" value = \"Send\">");
                    out.print("</form>");
                }
            } catch (IOException e) {
                error(elem, "Failed to create form file '%s'", htmlForm.fileName());
                return true;
            }
        }

        return true;
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
}
