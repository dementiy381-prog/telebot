package org.bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.*;

public class BattleshipBot extends TelegramLongPollingBot {

    private final Map<Long, Game> games = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "Battlleship_bot";
    }

    @Override
    public String getBotToken() {
        return "8910430834:AAGLJFIZLCUZYQRt_PD1_CSs6dLG1rWdFn4";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if (text.equals("/start")) {

                Game game = new Game();
                games.put(chatId, game);

                try {
                    SendMessage botField = new SendMessage();
                    botField.setChatId(String.valueOf(chatId));
                    botField.setText("🎯 ПОЛЕ БОТА");
                    botField.setReplyMarkup(GameUI.battle(game));

                    Message m1 = execute(botField);

                    SendMessage playerField = new SendMessage();
                    playerField.setChatId(String.valueOf(chatId));
                    playerField.setText("🛡 ТВОЁ ПОЛЕ");
                    playerField.setReplyMarkup(GameUI.setup(game));

                    Message m2 = execute(playerField);

                    game.setBotMsgId(m1.getMessageId());
                    game.setPlayerMsgId(m2.getMessageId());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (text.equals("/restart")) {
                games.remove(chatId);
            }
        }

        if (update.hasCallbackQuery()) {

            CallbackQuery cb = update.getCallbackQuery();
            long chatId = cb.getMessage().getChatId();

            Game game = games.get(chatId);
            if (game == null) return;

            String c = cb.getData();

            if (!game.isBattle()) {

                String res = game.place(c);

                EditMessageText e = new EditMessageText();
                e.setChatId(String.valueOf(chatId));
                e.setMessageId(game.getPlayerMsgId());
                e.setText("🚢 РАССТАНОВКА\n" + res);
                e.setReplyMarkup(GameUI.setup(game));

                try {
                    execute(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }

            String p = game.playerShot(c);
            String b = game.botShot();

            EditMessageText bot = new EditMessageText();
            bot.setChatId(String.valueOf(chatId));
            bot.setMessageId(game.getBotMsgId());
            bot.setText("🎯 БОТ\n" + p);
            bot.setReplyMarkup(GameUI.battle(game));

            EditMessageText player = new EditMessageText();
            player.setChatId(String.valueOf(chatId));
            player.setMessageId(game.getPlayerMsgId());
            player.setText("🛡 ТВОЁ ПОЛЕ\n" + b);
            player.setReplyMarkup(GameUI.setup(game));

            try {
                execute(bot);
                execute(player);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (game.isFinished()) {

                SendMessage end = new SendMessage();
                end.setChatId(String.valueOf(chatId));

                end.setText(game.isWin()
                        ? "🏆 ТЫ ВЫИГРАЛ!"
                        : "💀 ТЫ ПРОИГРАЛ!");

                end.setReplyMarkup(GameUI.restart());

                try {
                    execute(end);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
