package me.earth.headlessmc.command;

import me.earth.headlessmc.api.command.CommandException;

public interface YesNoCallback {
    void accept(boolean result) throws CommandException;

}
