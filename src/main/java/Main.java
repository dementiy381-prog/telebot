import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            BattleshipBot bot = new BattleshipBot();

            botsApi.registerBot(bot);

            System.out.println("✅ BOT STARTED SUCCESSFULLY");

        } catch (Exception e) {
            System.out.println("❌ BOT FAILED TO START");
            e.printStackTrace();
        }
    }
}