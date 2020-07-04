package kz.pompei.mars.fs;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FileSystemRoot {

  @NotNull List<String> rootNames();

  @NotNull String defaultRootName();

  @NotNull FolderView root(String rootName);

  default @NotNull FolderView defaultRoot() {
    return root(defaultRootName());
  }

}
