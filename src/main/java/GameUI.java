import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;

import java.util.*;

public class GameUI {

    public static InlineKeyboardMarkup setup(Game g) {

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            List<InlineKeyboardButton> row = new ArrayList<>();

            for (int j = 0; j < 10; j++) {

                char c = g.getPlayer(i, j);

                String t;
                if (c == 'S') t = "🚢";
                else if (c == 'X') t = "💥";
                else if (c == '•') t = "🌊";
                else t = "⬜";

                InlineKeyboardButton b = new InlineKeyboardButton();
                b.setText(t);
                b.setCallbackData("" + (char)('A' + j) + (i + 1));

                row.add(b);
            }

            rows.add(row);
        }

        InlineKeyboardMarkup m = new InlineKeyboardMarkup();
        m.setKeyboard(rows);
        return m;
    }

    public static InlineKeyboardMarkup battle(Game g) {

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            List<InlineKeyboardButton> row = new ArrayList<>();

            for (int j = 0; j < 10; j++) {

                char c = g.getBot(i, j);

                String t;
                if (c == 'X') t = "💥";
                else if (c == '•') t = "🌊";
                else t = "⬜";

                InlineKeyboardButton b = new InlineKeyboardButton();
                b.setText(t);
                b.setCallbackData("" + (char)('A' + j) + (i + 1));

                row.add(b);
            }

            rows.add(row);
        }

        InlineKeyboardMarkup m = new InlineKeyboardMarkup();
        m.setKeyboard(rows);
        return m;
    }

    public static InlineKeyboardMarkup restart() {

        InlineKeyboardButton b = new InlineKeyboardButton();
        b.setText("🔄 Играть снова");
        b.setCallbackData("/restart");

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(List.of(b));

        InlineKeyboardMarkup m = new InlineKeyboardMarkup();
        m.setKeyboard(rows);
        return m;
    }
}