package pl.otekplay.sectors.data;

import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.otekplay.sectors.managers.UserManager;
import pl.otekplay.sectors.storage.Settings;
import pl.otekplay.sectors.utils.TimeUtil;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Ban {
    private final User user;
    private final long date;
    private final String reason;
    private final String banner;

    public Ban(DBObject dbObject) {
        this.user = UserManager.getUser(UUID.fromString((String) dbObject.get(Settings.COLUMN_BAN_UUID_NAME)));
        this.date = (long) dbObject.get(Settings.COLUMN_BAN_TIME_NAME);
        this.reason = (String) dbObject.get(Settings.COLUMN_BAN_REASON_NAME);
        this.banner = (String) dbObject.get(Settings.COLUMN_BAN_ADMIN_NAME);
    }

    public boolean hasBan() {
        if (isPerm()) {
            return true;
        }
        if (date > System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    public boolean isPerm() {
        return date == 0;
    }

    public String getStringDate() {
        if (date == 0) {
            return "PERM";
        }
        return TimeUtil.getDate(date);
    }
}
