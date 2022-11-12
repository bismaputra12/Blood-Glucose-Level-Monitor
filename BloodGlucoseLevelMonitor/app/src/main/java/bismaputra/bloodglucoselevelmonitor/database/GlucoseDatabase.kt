package bismaputra.bloodglucoselevelmonitor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bismaputra.bloodglucoselevelmonitor.Glucose

// create the database
// include typeconverter file to the database
// this database utilizes the room package
// the database is SQLite: relational database

@Database(entities = [Glucose::class], version = 1)
@TypeConverters(GlucoseTypeConverter::class)
abstract class GlucoseDatabase : RoomDatabase() {
    abstract fun glucoseDao(): GlucoseDao
}
