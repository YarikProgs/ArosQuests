package net.aros.arosquests.commands.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.aros.arosquests.util.AQRegistry;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class QuestSuggest implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, @NotNull SuggestionsBuilder builder) {
        AQRegistry.QUESTS.forEach(quest -> builder.suggest(quest.getId().toString()));
        return builder.buildFuture();
    }
}
