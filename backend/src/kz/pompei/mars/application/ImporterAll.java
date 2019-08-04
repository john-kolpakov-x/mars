package kz.pompei.mars.application;

import kz.pompei.mars.controller.ImporterController;
import kz.pompei.mars.database.ImporterDatabase;
import kz.pompei.mars.registers.impl.ImporterRegister;
import org.springframework.context.annotation.Import;

@Import({ImporterController.class, ImporterRegister.class, ImporterDatabase.class})
public class ImporterAll {}
