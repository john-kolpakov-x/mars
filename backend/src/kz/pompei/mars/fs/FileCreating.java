package kz.pompei.mars.fs;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;

public interface FileCreating {
  @NotNull OutputStream out();

  @NotNull FileView complete();
}
