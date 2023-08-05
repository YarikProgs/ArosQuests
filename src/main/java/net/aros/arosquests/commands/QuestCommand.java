package net.aros.arosquests.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.aros.arosquests.commands.suggestion.QuestSuggest;
import net.aros.arosquests.commands.suggestion.StatusSuggest;
import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.world.QuestState;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class QuestCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        RequiredArgumentBuilder<ServerCommandSource, Identifier> quest = argument("quest_id", IdentifierArgumentType.identifier()).suggests(new QuestSuggest());
        RequiredArgumentBuilder<ServerCommandSource, String> status = argument("status", StringArgumentType.string()).suggests(new StatusSuggest());

        dispatcher.register(literal("quest")
                .requires(c -> c.hasPermissionLevel(2))
                .then(literal("set").then(quest.then(status.executes(QuestCommand::setQuest))))
                .then(literal("get").then(quest.executes(QuestCommand::getQuest)))
        );
    }

    private static int setQuest(CommandContext<ServerCommandSource> context) {
        try {
            QuestState.setQuestStatus(
                    Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id")),
                    QuestStatus.valueOf(StringArgumentType.getString(context, "status").toUpperCase()),
                    context.getSource().getServer())
            ;
            context.getSource().sendFeedback(Text.literal("Successfully!"), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }

    private static int getQuest(CommandContext<ServerCommandSource> context) {
        Quest quest = Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id"));

        try {
            context.getSource().sendFeedback(QuestState.getQuestInstance(quest, context.getSource().getWorld()).getStatus().asText(), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }
}
