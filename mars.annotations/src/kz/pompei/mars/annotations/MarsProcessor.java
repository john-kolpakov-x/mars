package kz.pompei.mars.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MarsProcessor extends AbstractProcessor {

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> ret = new HashSet<>();
    ret.add(MarsAnnotation.class.getName());
    return ret;
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    String str = "annotations = " + annotations + ", roundEnv = " + roundEnv;
    try {

      for (int i = 1; true; i++) {
        Path path = Paths.get("/home/pompei/tmp/wow-" + i + ".txt");
        if (Files.exists(path)) {
          continue;
        }
        Files.write(path, str.getBytes(StandardCharsets.UTF_8));
        break;
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return false;
  }

}

