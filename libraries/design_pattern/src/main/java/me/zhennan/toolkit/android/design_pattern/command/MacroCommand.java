package me.zhennan.toolkit.android.design_pattern.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public abstract class MacroCommand implements Command {

    private List<Command> commands;
    final protected List<Command> getCommands(){
        if(null == commands){
            commands = new ArrayList<>();
        }
        return commands;
    }

    final protected void addSubCommand(Command subCommand){
        getCommands().add(subCommand);
    }

    @Override
    final public void execute() {
        onExecuteCommand();

        Iterator<Command> iterator = getCommands().iterator();
        while (iterator.hasNext()){
            Command command = iterator.next();
            command.execute();
        }
    }

    protected abstract void onExecuteCommand();
}