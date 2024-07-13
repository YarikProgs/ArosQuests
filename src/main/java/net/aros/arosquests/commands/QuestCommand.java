package net.aros.arosquests.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.aros.arosquests.commands.suggestion.QuestSuggest;
import net.aros.arosquests.commands.suggestion.StatusSuggest;
import net.aros.arosquests.quests.base.Quest;
import net.aros.arosquests.util.QuestInstance;
import net.aros.arosquests.util.QuestStatus;
import net.aros.arosquests.util.QuestTime;
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
        RequiredArgumentBuilder<ServerCommandSource, Identifier> quest2 = argument("quest_id", IdentifierArgumentType.identifier()).suggests(new QuestSuggest());
        RequiredArgumentBuilder<ServerCommandSource, String> status = argument("status", StringArgumentType.string()).suggests(new StatusSuggest());

        dispatcher.register(literal("quest")
                .requires(c -> c.hasPermissionLevel(2))
                .then(literal("set").then(quest.then(status.executes(QuestCommand::setQuest))))
                .then(literal("get").then(quest.executes(QuestCommand::getQuest)))
                .then(literal("reset").then(quest.executes(QuestCommand::resetQuest)))
                .then(literal("time").then(quest2
                        .then(literal("set").then(argument("time", IntegerArgumentType.integer()).executes(context -> setTime(context, 1))
                                .then(literal("ticks").executes(context -> setTime(context, 1)))
                                .then(literal("seconds").executes(context -> setTime(context, QuestTime.SECOND)))
                                .then(literal("minutes").executes(context -> setTime(context, QuestTime.MINUTE)))
                                .then(literal("hours").executes(context -> setTime(context, QuestTime.HOUR)))
                                .then(literal("minecraft_days").executes(context -> setTime(context, QuestTime.MINECRAFT_DAY)))
                                .then(literal("real_days").executes(context -> setTime(context, QuestTime.REAL_DAY)))
                        ))
                        .then(literal("get").executes(QuestCommand::getTime))
                        .then(literal("reset").executes(QuestCommand::resetTime))
                ))
        );
    }

    private static int getTime(CommandContext<ServerCommandSource> context) {
        Quest quest = Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id"));

        try {
            context.getSource().sendFeedback(() -> Text.literal(QuestState.getQuestInstance(quest, context.getSource().getWorld()).getTime() + " ticks"), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }

    private static int setTime(CommandContext<ServerCommandSource> context, int multiplier) {
        try {
            QuestState.setQuestTime(
                    Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id")),
                    IntegerArgumentType.getInteger(context, "time") * multiplier,
                    context.getSource().getServer());
            context.getSource().sendFeedback(() -> Text.literal("Successfully!"), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }

    private static int resetTime(CommandContext<ServerCommandSource> context) {
        Quest quest = Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id"));
        try {
            QuestState.setQuestTime(
                    quest,
                    quest.getDefaultTime().getTime(),
                    context.getSource().getServer());
            context.getSource().sendFeedback(() -> Text.literal("Successfully!"), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }

    private static int setQuest(CommandContext<ServerCommandSource> context) {
        try {
            QuestState.setQuestStatus(
                    Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id")),
                    QuestStatus.valueOf(StringArgumentType.getString(context, "status").toUpperCase()),
                    context.getSource().getServer());
            context.getSource().sendFeedback(() -> Text.literal("Successfully!"), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }

    private static int getQuest(CommandContext<ServerCommandSource> context) {
        Quest quest = Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id"));

        try {
            context.getSource().sendFeedback(() -> QuestState.getQuestInstance(quest, context.getSource().getWorld()).getStatus().asText(), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }

    private static int resetQuest(CommandContext<ServerCommandSource> context) {
        Quest quest = Quest.byId(IdentifierArgumentType.getIdentifier(context, "quest_id"));

        try {
            QuestState.setQuestInstance(quest, new QuestInstance(quest, QuestStatus.NOT_STARTED), context.getSource().getServer());
            context.getSource().sendFeedback(() -> Text.literal("Successfully!"), false);
            return 0;
        } catch (Exception e) {
            context.getSource().sendError(Text.literal(e.getMessage()));
            return 1;
        }
    }
}
