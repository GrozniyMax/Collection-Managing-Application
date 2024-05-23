package com.example.demo.ScriptExecution;


import com.example.demo.CommonClasses.Entities.Flat;
import com.example.demo.CommonClasses.Entities.View;
import com.example.demo.CommonClasses.Exceptions.EndOfStreamException;
import com.example.demo.CommonClasses.Interaction.Requests.CommandRequest;
import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UDPManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для выполнения скриптов
 */
public class ScriptExecutor {
    /**
     * Предыдущий менеджер команд
     */

    private InputManager source;

    private String unsupportedCommand="Unsupported command";
    private String invalidInput="Invalid input";
    private String failedExecution="Execution failed";

    public ScriptExecutor() {
    }

    /**
     * Множество файлов, которые уже были выполнены
     */
    static Set<String> executedFiles = new HashSet<>();

    /**
     * Выполняет скрипт
     *
     * @param filepath - путь к файлу
     */
    public boolean execute(String filepath) {
        File scriptFile=null;
        Boolean result = true;
        try {
            scriptFile = new File(filepath.strip());

            if (executedFiles.contains(scriptFile.getAbsolutePath())) {
                System.err.println("Вы создали бесконечную рекурсию. Поэтому выполнение прикращается");
                return true;
            }
            executedFiles.add(scriptFile.getAbsolutePath());
            source = new ScriptInputManager(new FileInputStream(scriptFile));
            boolean finished = false;
            String line;
            String[] lineByParts;

            String comandName = null;
            Object comandArgument = null;
            Flat readObj = null;

            while (!finished) {
                line = source.readLine();
                if (line == null) {
                    return true;
                }

                lineByParts = line.split(" ");

                try {
                    if (lineByParts.length == 1) {
                        switch (lineByParts[0]) {
                            case "add": {
                                comandName = "add";
                                readObj = source.readFlat();
                                break;
                            }
                            case "clear": {
                                comandName = "clear";
                                break;
                            }
                            case "group_counting_by_creation_date": {
                                comandName = "group_counting_by_creation_date";
                                break;
                            }
                            case "remove_lover": {
                                comandName = "remove_lover";
                                readObj = source.readFlat();
                                break;
                            }
                            default:
                                throw new ScriptExecutionFailed(unsupportedCommand);
                        }
                    } else if (lineByParts.length == 2) {
                        switch (lineByParts[0]) {
                            case "update": {
                                comandName = "update";
                                comandArgument = Long.parseLong(lineByParts[1]);
                                readObj = source.readFlat();
                                break;
                            }
                            case "remove_by_id": {
                                comandName = "remove_by_id";
                                comandArgument = Long.parseLong(lineByParts[1]);
                                break;
                            }
                            case "remove_all_by_view": {
                                comandName = "remove_all_by_view";
                                comandArgument = View.valueOf(lineByParts[1].strip().toUpperCase());
                                break;
                            }
                            case "execute_script": {
                                execute(lineByParts[1].strip());
                                break;
                            }
                            default:
                                throw new ScriptExecutionFailed(unsupportedCommand);
                        }
                    } else {
                        throw new ScriptExecutionFailed(unsupportedCommand);
                    }
                } catch (IllegalArgumentException e) {
                    throw new ScriptExecutionFailed(invalidInput);
                }

                CommandRequest request = new CommandRequest(comandName, comandArgument, readObj, SessionContext.getLanguage());
                UDPManager.getCommandSenderInstance().sendAbstractRequest(request);
                if (!UDPManager.getCommandSenderInstance().getServerResponce().status()) {
                    throw new ScriptExecutionFailed(failedExecution);
                }
            }
        }catch (Exception e) {
            result=false;
        }finally {
            if (scriptFile!=null){
                executedFiles.remove(scriptFile.getAbsolutePath());
            }
            return result;
        }

    }

}
