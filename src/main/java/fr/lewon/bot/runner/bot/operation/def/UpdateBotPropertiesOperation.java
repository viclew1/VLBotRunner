package fr.lewon.bot.runner.bot.operation.def;

import fr.lewon.bot.runner.bot.Bot;
import fr.lewon.bot.runner.bot.operation.BotOperation;
import fr.lewon.bot.runner.bot.operation.OperationResult;
import fr.lewon.bot.runner.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.runner.bot.props.BotPropertyStore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateBotPropertiesOperation extends BotOperation {

    public UpdateBotPropertiesOperation() {
        super("Update bot properties");
    }

    @Override
    public List<BotPropertyDescriptor> getNeededProperties(Bot bot) {
        return bot.getBotPropertyStore().keySet().stream()
                .map(bpd -> new BotPropertyDescriptor(
                        bpd.getKey(),
                        bpd.getType(),
                        null,
                        bpd.getDescription(),
                        false,
                        bpd.isNullable(),
                        bpd.getAcceptedValues()))
                .collect(Collectors.toList());
    }

    @Override
    public Object run(Bot bot, BotPropertyStore paramsPropertyStore) throws Exception {

        for (Map.Entry<BotPropertyDescriptor, Object> e : paramsPropertyStore.entrySet()) {
            if (bot.getBotPropertyStore().containsKey(e.getKey())) {
                bot.getBotPropertyStore().put(e.getKey(), e.getValue());
            }
        }
        return new OperationResult(true, "Bot properties updated", bot.getBotPropertyStore());
    }
}
