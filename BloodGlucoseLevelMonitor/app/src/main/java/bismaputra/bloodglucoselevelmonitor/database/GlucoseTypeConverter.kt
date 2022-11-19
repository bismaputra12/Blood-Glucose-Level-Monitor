package bismaputra.bloodglucoselevelmonitor.database

import androidx.room.TypeConverter
import java.util.*

// convert data types because the database cannot handle complex data types such as date, UUID, etc
// the nullable is used to return NULL on certain conditions
class GlucoseTypeConverter {
    @TypeConverter
    fun fromDate(date: Date?):Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}