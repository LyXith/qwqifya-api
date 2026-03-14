package qwq.qwqifya.qwqifyaApi.commandUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BaseCommand {
    public static final List<BaseCommand> scanClasses = new CopyOnWriteArrayList<>();

    public BaseCommand() {
        scanClasses.add(this);
    }
}