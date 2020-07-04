package kz.pompei.mars.fs;

import kz.pompei.mars.fs.errors.AlreadyDeleted;
import kz.pompei.mars.fs.errors.CannotBeDeleted;
import kz.pompei.mars.fs.errors.NewNameAlreadyExistsInFolder;
import kz.pompei.mars.fs.errors.NoParent_ItIsRoot;
import kz.pompei.mars.fs.errors.NodeIsAbsent;
import org.jetbrains.annotations.NotNull;

public interface NodeView {

  @NotNull String name() throws NodeIsAbsent;

  NodeView rename(@NotNull String newName) throws NewNameAlreadyExistsInFolder;

  @NotNull FolderView parent() throws NoParent_ItIsRoot;

  void delete() throws AlreadyDeleted, CannotBeDeleted;

}
