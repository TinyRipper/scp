package me.zhennan.toolkit.android.design_pattern.command;

/**
 * Created by zhangzhennan on 15/5/7.
 */
public abstract class SimpleCommand implements Command {

    @Override
    final public void execute() {
        onExecuteCommand();
    }

    /**
     * do business logic here
     * @return
     */
    protected abstract void onExecuteCommand();
}