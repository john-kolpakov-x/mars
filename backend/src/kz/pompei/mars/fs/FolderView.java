package kz.pompei.mars.fs;

import kz.pompei.mars.fs.errors.NewNameAlreadyExistsInFolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FolderView extends NodeView {

  /**
   * Показывает только непосредственных наследников
   *
   * @return список наследников
   */
  @NotNull List<NodeView> children();

  @NotNull FolderView createFolder(@NotNull String folderName) throws NewNameAlreadyExistsInFolder;

  @NotNull FileCreating createFile(@NotNull String fileName) throws NewNameAlreadyExistsInFolder;

}
